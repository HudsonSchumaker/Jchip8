package br.com.schumaker.jchip8.core;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_HEIGHT;
import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_WIDTH;
import br.com.schumaker.jchip8.exceptions.PixelOutOfScreenExecption;

/**
 *
 * @author Hudson Schumaker
 */
public class Chip8Screen {

    private static final boolean[][] PIXELS = new boolean[CHIP8_HEIGHT][CHIP8_WIDTH];
    
    public Chip8Screen() {
        System.out.println("screen...................OK");
    }

    public static boolean setSprite(int x, int y, char[] sprite, int num) {
        boolean pixelCollision = false;

        for (int ly = 0; ly < num; ly++) {
            byte c = (byte) sprite[ly];
            for (int lx = 0; lx < 8; lx++) {
                if ((c & (0b10000000 >> lx)) == 0) {
                    continue;
                }

                if (PIXELS[(ly + y) % CHIP8_HEIGHT][(lx + x) % CHIP8_WIDTH]) {
                    pixelCollision = true;
                }

                PIXELS[(ly + y) % CHIP8_HEIGHT][(lx + x) % CHIP8_WIDTH] ^= true;
            }
        }

        return pixelCollision;
    }

    public static void isOnScreen(int x, int y) {
        if (!(x >= 0 && x < CHIP8_WIDTH && y >= 0 && y < CHIP8_HEIGHT)) {
            throw new PixelOutOfScreenExecption();
        }
    }

    public void setPixel(int x, int y) {
        isOnScreen(x, y);
        PIXELS[y][x] = true;
    }
    
    public static boolean getPixel(int x, int y) {
        isOnScreen(x, y);
        return PIXELS[y][x];
    }

    public static boolean isPixelSet(int x, int y) {
        isOnScreen(x, y);
        return PIXELS[y][x];
    }
    
    public static void cleanScreen(){
         for (int x = 0; x < CHIP8_WIDTH; x++) {
            for (int y = 0; y < CHIP8_HEIGHT; y++) {
                PIXELS[y][x] = false;
            }
        }
    }

    public boolean[][] getPixels() {
        return PIXELS;
    }
}
