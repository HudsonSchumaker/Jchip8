package br.com.schumaker.jchip8.io;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_HEIGHT;
import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_PIXEL_SIZE;
import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_WIDTH;
import static br.com.schumaker.jchip8.config.Chip8Specs.SCREEN_H;
import static br.com.schumaker.jchip8.config.Chip8Specs.SCREEN_W;
import br.com.schumaker.jchip8.core.Chip8;
import br.com.schumaker.jchip8.core.Chip8Registers;
import br.com.schumaker.jchip8.core.Chip8Screen;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 *
 * @author hudson schumaker
 */
public class MainLoop extends Canvas implements Runnable {

    private final Chip8 chip8;
    private BufferStrategy strategy;
    private boolean isRunning;

    public MainLoop(Chip8 chip8) {
        this.chip8 = chip8;
        this.isRunning = false;
    }

    public void start() {
        setSize(SCREEN_W, SCREEN_H);
        setGfxBuffer();
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        for (;;) {
            try {
                if (isRunning) {
                    this.update();
                    this.paintComponent();
                    Thread.sleep(this.delayTime() ? 55 : 10);
                    this.soundTimer();
                    short opCode = chip8.getMemory()
                            .readMemoryInShort(chip8.getRegisters().getPC());

                    this.programCounter();
                    chip8.execOpCode(opCode);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void paintComponent() {
        Graphics2D g2d = (Graphics2D) strategy.getDrawGraphics();
        this.drawForeground(g2d);
        g2d.dispose();

        Toolkit.getDefaultToolkit().sync();
        strategy.show();
    }

    private void programCounter() {
        chip8.getRegisters().incPC2x();
    }

    private boolean delayTime() {
        return Chip8Registers.getInstance().getDT() > 1;
    }

    private void soundTimer() {
        if (Chip8Registers.getInstance().getST() > 0) {
            Toolkit.getDefaultToolkit().beep();
            Chip8Registers.getInstance().decST();
        }
    }

    private void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        for (int x = 0; x < CHIP8_WIDTH; x++) {
            for (int y = 0; y < CHIP8_HEIGHT; y++) {
                if (Chip8Screen.isPixelSet(x, y)) {
                    g2d.fillRect(x * CHIP8_PIXEL_SIZE,
                            y * CHIP8_PIXEL_SIZE,
                            CHIP8_PIXEL_SIZE,
                            CHIP8_PIXEL_SIZE);
                }
            }
        }
    }

    private void update() {
        this.repaint();
    }

    private void setGfxBuffer() {
        this.createBufferStrategy(2);
        strategy = getBufferStrategy();
        this.setBackground(Color.black);
    }
    
    public void setIsRunning(boolean value) {
        this.isRunning = value;
    }
}
