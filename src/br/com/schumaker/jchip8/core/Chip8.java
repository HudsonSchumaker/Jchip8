package br.com.schumaker.jchip8.core;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_DEFAULT_SPRITE_HEIGHT;
import br.com.schumaker.jchip8.io.Chip8Keyboard;
import java.util.Random;

/**
 *
 * @author hudson schumaker
 */
public class Chip8 {

    private final Chip8Memory memory;
    private final Chip8Stack stack;
    private final Chip8Registers registers;
    private final Chip8Keyboard keyboard;
    private final Chip8Screen screen;
    
    private boolean waitingForKey;
    private char buffer;

    public Chip8() {
        this.memory = new Chip8Memory();
        this.stack = new Chip8Stack(this);
        this.registers = Chip8Registers.getInstance();
        this.keyboard = new Chip8Keyboard();
        this.screen = new Chip8Screen();
        this.initComponents();
    }

    private void initComponents() {
        this.waitingForKey = false;
        this.buffer = 'z';
        this.memory.initMemory();
        this.memory.initCharacterSet();
        this.stack.initStack();
        System.out.println("chip8....................OK");
        
        // test area
    }

    public void execOpCode(short opCode) {
        switch (opCode) {
            case 0x00E0: // CLS: Clear the Display
                Chip8Screen.cleanScreen();
                break;

            case 0x00EE: // RET: Return from subroutine
                registers.setPC(stack.stackPop());
                break;

            default:
                this.execOpCodeExtended(opCode);
        }
    }

    public void execOpCodeExtended(short opCode) {
        short nnn = (short) (opCode & 0x0fff);
        char x = (char) ((opCode >> 8) & 0x000f);
        char y = (char) ((opCode >> 4) & 0x000f);
        char kk = (char) (opCode & 0x00ff);
        char n = (char) (opCode & 0x000f);

        switch (opCode & 0xf000) {
            case 0x1000: // JP addr, 1nnn Jump to location nnn's
                registers.setPC(nnn);
                break;
            case 0x2000: // CALL addr, 2nnn Call subroutine at location nnn
                stack.stackPush(registers.getPC());
                registers.setPC(nnn);
                break;
   
            case 0x3000: // SE Vx, byte - 3xkk Skip next instruction if Vx=kk
                if (registers.getV()[x] == kk) {
                    registers.incPC2x();
                }
                break;

            case 0x4000: // SNE Vx, byte - 3xkk Skip next instruction if Vx!=kk
                if (registers.getV()[x] != kk) {
                    registers.incPC2x();
                }
                break;

            case 0x5000: // 5xy0 - SE, Vx, Vy, skip the next instruction if Vx = Vy
                if (registers.getV()[x] == registers.getV()[y]) {
                    registers.incPC2x();
                }
                break;

            case 0x6000: // 6xkk - LD Vx, byte, Vx = kk
                registers.getV()[x] = kk;
                break;

            case 0x7000: // 7xkk - ADD Vx, byte. Set Vx = Vx + kk
                registers.getV()[x] += kk;
                break;

            case 0x8000:
                this.execOpCodeExtended8000(opCode);
                break;

            case 0x9000: // 9xy0 - SNE Vx, Vy. Skip next instruction if Vx != Vy
                if (registers.getV()[x] != registers.getV()[y]) {
                    registers.incPC2x();
                }
                break;

            case 0xA000: // Annn - LD I, addr. Sets the I register to nnn
                registers.setI(nnn);
                break;

            case 0xB000: // bnnn - Jump to location nnn + V0
                short v0 = (short) registers.getV()[0];
                short value = (short) (nnn + v0);
                registers.setPC(value);
                break;
                
//                short v0 = (short) registers.getV()[0];
//                short value = (short) (nnn + v0);
//                registers.setPC(value);
//                break;

            case 0xC000: // Cxkk - RND Vx, byte
                Random rand = new Random();
                int randomNumber = rand.nextInt(256);
                int intKK = kk;
                int res = randomNumber & intKK;
                registers.getV()[x] = (char) res;
                break;

            case 0xD000:  // Dxyn - DRW Vx, Vy, nibble. Draws sprite to the screen
                char[] sprite = memory.readMemory(registers.getI(), n);
                boolean result = Chip8Screen.setSprite(
                        registers.getV()[x],
                        registers.getV()[y],
                        sprite,
                        n);

                if (result) {
                    registers.getV()[0x0f] = '\u0001';
                } else {
                    registers.getV()[0x0f] = '\u0000';
                }

                break;

            case 0xE000:  // Keyboard operations
                this.keyboardOpCode(opCode);
                break;

            case 0xF000:
                this.execOpCodeExtendedF(opCode);
                break;
        }
    }

    public void execOpCodeExtended8000(short opCode) {
        char x = (char) ((opCode >> 8) & 0x000f);
        char y = (char) ((opCode >> 4) & 0x000f);
        char finalFourBits = (char) (opCode & 0x000f);
        switch (finalFourBits) {
            case 0x00: // 8xy0 - LD Vx, Vy. Vx = Vy
                registers.getV()[x] = registers.getV()[y];
                break;

            case 0x01: // 8xy1 - OR Vx, Vy. Performs a bitwise OR on Vx and Vy stores the result in Vx
                registers.getV()[x] = (char) (registers.getV()[x] | registers.getV()[y]);
                break;

            case 0x02: // 8xy2 - AND Vx, Vy. Performs a bitwise AND on Vx and Vy stores the result in Vx
                registers.getV()[x] = (char) (registers.getV()[x] & registers.getV()[y]);
                break;

            case 0x03: // 8xy3 - XOR Vx, Vy. Performs a bitwise XOR on Vx and Vy stores the result in Vx
                registers.getV()[x] = (char) (registers.getV()[x] ^ registers.getV()[y]);
                break;

            case 0x04: // 8xy4 - ADD Vx, Vy. Set Vx = Vx + Vy, set VF = carry
                short tmp = (short) (registers.getV()[x] + registers.getV()[y]);
                registers.getV()[0x0f] = '\u0000';
                
                //Because char in java use 2bytes
                if (tmp > 0xff) {
                    tmp = (short) (tmp - (short)256);
                    registers.getV()[0x0f] = '\u0001';
                }
                registers.getV()[x] = (char) tmp;
                break;

            case 0x05: // 8xy5 - SUB Vx, Vy. Set vx = Vx - Vy, set VF = Not borrow
                registers.getV()[0x0f] = '\u0000';
                if (registers.getV()[x] > registers.getV()[y]) {
                    registers.getV()[0x0f] = '\u0001';
                }
                registers.getV()[x] = (char) (registers.getV()[x] - registers.getV()[y]);
                break;

            case 0x06: // 8xy6 - SHR Vx {, Vy}
                registers.getV()[0x0f] = (char) (registers.getV()[x] & 0x01);
                registers.getV()[x] /= 2;
                break;

            case 0x07: // 8xy7 - SUBN Vx, Vy
                if (registers.getV()[y] > registers.getV()[x]) {
                    registers.getV()[0x0f] = '\u0001';
                } else {
                    registers.getV()[0x0f] = '\u0000';
                }

                registers.getV()[x] = (char) (registers.getV()[y] - registers.getV()[x]);
                break;

            case 0x0E: // 8xyE - SHL Vx {, Vy}
                registers.getV()[0x0f] = (char) (registers.getV()[x] & 0b10000000);
                registers.getV()[x] *= 2;
                break;
        }
    }

    public void execOpCodeExtendedF(short opCode) {
        char x = (char) ((opCode >> 8) & 0x000f);
        switch (opCode & 0x00ff) {
            
            case 0x07: // fx07 - LD Vx, DT. Set Vx to the delay timer value
                registers.getV()[x] = registers.getDT();
                break;
            
            case 0x0A: // fx0a - LD Vx, K
                char pressed_key = waitForKeyPress();
                registers.getV()[x] = pressed_key;
                break; 

            case 0x15:  // fx15 - LD DT, Vx, set the delay timer to Vx
                registers.setDT(registers.getV()[x]);
                break;

            case 0x18:  // fx18 - LD ST, Vx, set the sound timer to Vx
                registers.setST(registers.getV()[x]);
                break;

            case 0x1e: // fx1e - Add I, Vx
                short i = (short) (registers.getI() + registers.getV()[x]);
                registers.setI(i);
                break;

            case 0x29: // fx29 - LD F, Vx
                registers.setI((short) (registers.getV()[x] * 
                        CHIP8_DEFAULT_SPRITE_HEIGHT));
                break;
                
            case 0x33:  // fx33 - LD B, Vx
                char hundreds = (char) (registers.getV()[x] / 100);
                char tens = (char) (registers.getV()[x] / 10 % 10);
                char units = (char) (registers.getV()[x] % 10);
                
                memory.writeMemory(registers.getI(), hundreds);
                memory.writeMemory(registers.getI() + 1, tens);
                memory.writeMemory(registers.getI() + 2, units);
                break;
            
            case 0x55: // fx55 - LD [I], Vx
                for (int j = 0; j <= x; j++) {
                    memory.writeMemory(registers.getI() + j, registers.getV()[j]);
                }
                break;    
            
            case 0x65: // fx65 - LD Vx, [I]
                for (int k = 0; k <= x; k++) {
                    registers.getV()[k] = memory.readMemory(registers.getI() + k);
                }
            break;
        }
    }

    public void keyboardOpCode(short opCode) {
        char x = (char) ((opCode >> 8) & 0x000f);

        switch (opCode & 0x00ff) {
            case 0x9e:  // Ex9e - SKP Vx, Skip the next instruction if the key with the value of Vx is pressed
                if (keyboard.isDown(registers.getV()[x])) {
                    registers.incPC2x();
                }
                break;

            case 0xa1: // Exa1 - SKNP Vx - Skip the next instruction if the key with the value of Vx is not pressed
                if (!keyboard.isDown(registers.getV()[x])) {
                    registers.incPC2x();
                }
                break;
        }
    }
    
    public boolean isWaitingForKey() {
        return waitingForKey;
    }
    
    public void resetChip8() {
        Chip8Registers.getInstance().initResgisters();
        Chip8Screen.cleanScreen();
        this.keyboard.initKeyboard();  
        this.waitingForKey = false;
        this.buffer = 'z';
        this.memory.initMemory();
        this.memory.initCharacterSet();
        this.stack.initStack();
    }
    
    private char waitForKeyPress() {
        System.out.println("Chip8 is waiting for key");
        waitingForKey = true;
        while(buffer == 'z') {
        }
        
        char res = buffer;
        buffer = 'z'; 
        return res;
    }

    public void setBuffer(char buffer) {
        this.buffer = buffer;
    }
    
    public Chip8Memory getMemory() {
        return this.memory;
    }

    public Chip8Registers getRegisters() {
        return registers;
    }

    public Chip8Stack getStack() {
        return stack;
    }

    public Chip8Keyboard getKeyboard() {
        return keyboard;
    }

    public Chip8Screen getScreen() {
        return screen;
    }
}
