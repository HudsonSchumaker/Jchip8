package br.com.schumaker.jchip8.core;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_HEIGHT;
import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_WIDTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author hudson schumaker
 */
public class Chip8Test {
    
    @Test
    public void testMainInitilization() {
        // TODO      
    }
    
    @Test  // CLS: Clear the Display
    public void testOpCode0x00E0() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.execOpCode((short)0x00E0);
        
        for (int x = 0; x < CHIP8_WIDTH; x++) {
            for (int y = 0; y < CHIP8_HEIGHT; y++) {
                assertFalse(Chip8Screen.getPixel(x, y));
            }
        } 
    }
    
    @Test // RET: Return from subroutine
    public void testOpCode0x00EE() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Integer value1 = 100;
        Integer value2 = 200;
        tested.getStack().stackPush(value1);
        tested.getStack().stackPush(value2);
        tested.execOpCode((short)0x00ee);
        
        assertEquals(value2.shortValue(), Chip8Registers.getInstance().getPC());
    }
    
    @Test // JP: addr, 1nnn Jump to location nnn's
    public void testOpCode0x1000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        tested.execOpCode((short)0x1ff2);
        Integer value = Integer.valueOf(0x0ff2);
        assertEquals(value.shortValue(), Chip8Registers.getInstance().getPC());
    }
    
    @Test // CALL: addr, 2nnn Call subroutine at location nnn
    public void testOpCode0x2000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        tested.execOpCode((short)0x2ff2);
        Integer value = Integer.valueOf(0x0ff2);
        assertEquals(value.shortValue(), Chip8Registers.getInstance().getPC());
    }
    
    @Test // SE: Vx, byte - 3xkk Skip next instruction if Vx=kk
    public void testOpCode0x3000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[0] = 1; //x
        
        tested.execOpCode((short)0x3001);
        assertEquals(2, Chip8Registers.getInstance().getPC()); //program_counter
        assertEquals(1, Chip8Registers.getInstance().getV()[0]); //x
    }
    
    @Test // SNE: Vx, byte - 3xkk Skip next instruction if Vx!=kk
    public void testOpCode0x4000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[0] = 1; //x
        
        tested.execOpCode((short)0x4002);
        assertEquals(2, Chip8Registers.getInstance().getPC()); //program_counter
    }
    
    @Test // 5xy0 - SE, Vx, Vy, skip the next instruction if Vx = Vy
    public void testOpCode0x5000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[0] = 1; //x
        Chip8Registers.getInstance().getV()[1] = 1; //y
        
        tested.execOpCode((short)0x5012);
        assertEquals(2, Chip8Registers.getInstance().getPC()); //program_counter
    }
    
    @Test // 6xkk - LD Vx, byte, Vx = kk
    public void testOpCode0x6000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[1] = 1; //x
        
        tested.execOpCode((short)0x6102);
        assertEquals(2, Chip8Registers.getInstance().getV()[1]); //x
    }
    
    @Test // 7xkk - ADD Vx, byte. Set Vx = Vx + kk
    public void testOpCode0x7000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[0] = 1; //x
        
        tested.execOpCode((short)0x7002);
        assertEquals(3, Chip8Registers.getInstance().getV()[0]); //x
    }
    
    @Test // 8xkk - ADD Vx, byte. Set Vx = Vx + kk
    public void testOpCode0x8000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[1] = 0x30; //y
         
        assertEquals(0, Chip8Registers.getInstance().getV()[0]); //x
        
        tested.execOpCode((short)0x8010);
        assertEquals(0x30, Chip8Registers.getInstance().getV()[0]); //x
    }
    
    @Test // 8xy0 - LD Vx, Vy. Vx = Vy
    public void testOpCode0x8001() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[0] = 0x10; //x
        Chip8Registers.getInstance().getV()[1] = 0x20; //y
        
        tested.execOpCode((short)0x8011);
        assertEquals(0x30, Chip8Registers.getInstance().getV()[0]); //x    
    }
    
    @Test // 8xy2 - AND Vx, Vy. Performs a bitwise AND on Vx and Vy stores the result in Vx
    public void testOpCode0x8002() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Chip8Registers.getInstance().getV()[0] = 0x10; //x
        Chip8Registers.getInstance().getV()[1] = 0x20; //y
        
        tested.execOpCode((short)0x8012);
        assertEquals(0x00 , Chip8Registers.getInstance().getV()[0]); //x    
    }
    
    @Test // 8xy3 - XOR Vx, Vy. Performs a bitwise XOR on Vx and Vy stores the result in Vx
    public void testOpCode0x8003() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Chip8Registers.getInstance().getV()[0] = 0x10; //x
        Chip8Registers.getInstance().getV()[1] = 0x20; //x
        
        tested.execOpCode((short)0x8012);
        assertEquals(0x00, Chip8Registers.getInstance().getV()[0]); //x    
    }
    
    @Test // 8xy4 - ADD Vx, Vy. Set Vx = Vx + Vy, set VF = carry
    public void testOpCode0x8004() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Chip8Registers.getInstance().getV()[0] = 200; //x
        Chip8Registers.getInstance().getV()[1] = 60; //y
        
        tested.execOpCode((short)0x8014);
        
        assertEquals(4, Chip8Registers.getInstance().getV()[0]); //x
        assertEquals(1, Chip8Registers.getInstance().getV()[15]); //VF
    }
    
    @Test // 8xy5 - SUB Vx, Vy. Set vx = Vx - Vy, set VF = Not borrow
    public void testOpCode0x8005() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Chip8Registers.getInstance().getV()[0] = 200; //x
        Chip8Registers.getInstance().getV()[1] = 60;  //y
        
        tested.execOpCode((short)0x8015);
        
        assertEquals(140, Chip8Registers.getInstance().getV()[0]); //x
        assertEquals(1, Chip8Registers.getInstance().getV()[15]); //VF
    }
    
    @Test // 8xy6 - SHR Vx {, Vy}
    public void testOpCode0x8006() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Chip8Registers.getInstance().getV()[0] = 0x01; //x
        tested.execOpCode((short)0x8006);
        
        assertEquals(1, Chip8Registers.getInstance().getV()[15]); //VF
        assertEquals(0, Chip8Registers.getInstance().getV()[0]); //x
        
        Chip8Registers.getInstance().getV()[0] = 0x02; //x
        
        tested.execOpCode((short)0x8006);
        assertEquals(1, Chip8Registers.getInstance().getV()[0]); //x
    }
    
    @Test // 8xy7 - SUBN Vx, Vy
    public void testOpCode0x8007() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        Chip8Registers.getInstance().getV()[0] = 20; //x
        Chip8Registers.getInstance().getV()[1] = 60; //y
        
        tested.execOpCode((short)0x8017);
        
        assertEquals(1, Chip8Registers.getInstance().getV()[15]); //VF
        assertEquals(40, Chip8Registers.getInstance().getV()[0]); //x
        
    }
    
    @Test // 9xy0 - SNE Vx, Vy. Skip next instruction if Vx != Vy
    public void testOpCode0x9000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[5] = 1;
        Chip8Registers.getInstance().getV()[2] = 2;
        
        tested.execOpCode((short)0x9520);
        assertEquals(2, Chip8Registers.getInstance().getPC());
    }
    
    @Test // Annn - LD I, addr. Sets the I register to nnn
    public void testOpCode0xA000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        tested.execOpCode((short)0xA111);
        assertEquals(0x0111, Chip8Registers.getInstance().getI());
    }
    
    @Test // bnnn - Jump to location nnn + V0
    public void testOpCode0xB000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().getV()[0] = 1;
        
        tested.execOpCode((short) 0xB001);
        assertEquals(2, Chip8Registers.getInstance().getPC());
    }
    
    @Test // Cxkk - RND Vx, byte
    public void testOpCode0xC000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        
        tested.execOpCode((short) 0xC409);
        assertNotEquals(0, Chip8Registers.getInstance().getV()[4]);
    }
    
    @Test // Dxyn - DRW Vx, Vy, nibble. Draws sprite to the screen
    public void testOpCode0xD000() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().setI((short)0x00);
        tested.getRegisters().getV()[0] = 0;
        tested.getRegisters().getV()[1] = 0;
        
        tested.execOpCode((short)0xD015);
        
        //line 1 
        assertTrue(Chip8Screen.isPixelSet(0,0));
        assertTrue(Chip8Screen.isPixelSet(0,1));
        assertTrue(Chip8Screen.isPixelSet(0,2));
        assertTrue(Chip8Screen.isPixelSet(0,3));
    }
    
    @Test // fx07 - LD Vx, DT. Set Vx to the delay timer value
    public void testOpCode0xF007() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().setDT((char)5);
        
        tested.execOpCode((short)0xf307); //x=3
        
        assertEquals(5, tested.getRegisters().getV()[3]);
    }
    
    @Test // fx0a - LD Vx, K
    public void testOpCode0xF00A() {
        // TODO
    }
    
    @Test // fx15 - LD DT, Vx, set the delay timer to Vx
    public void testOpCode0xF015() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().getV()[3] = 5;
        
        tested.execOpCode((short)0xf315); //x=3
        
        assertEquals(5, tested.getRegisters().getDT());
    }
    
    @Test // fx18 - LD ST, Vx, set the sound timer to Vx
    public void testOpCode0xF018() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().getV()[3] = 5;
        
        tested.execOpCode((short)0xf318); //x=3
        
        assertEquals(5, tested.getRegisters().getST());
    }
    
    @Test // fx1e - Add I, Vx
    public void testOpCode0xF01e() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().setI((short)250); //I=255
         tested.getRegisters().getV()[3] = 5; 
        
        tested.execOpCode((short)0xf31e); //x=3
        assertEquals(255, tested.getRegisters().getI());
    }
    
    @Test // fx29 - LD F, Vx
    public void testOpCode0xF029() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().getV()[1] = 5; //x
        
        tested.execOpCode((short)0xf129);
        
        assertEquals(25, Chip8Registers.getInstance().getI());
    }
    
    @Test // fx33 - LD B, Vx
    public void testOpCode0xF033() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().getV()[1] = 121; //x
        
        tested.execOpCode((short)0xf133);
        
        assertEquals(0x01, tested.getMemory().readMemory(0x00));
        assertEquals(0x02, tested.getMemory().readMemory(0x01));
        assertEquals(0x01, tested.getMemory().readMemory(0x02));
    }
    
    @Test // fx55 - LD [I], Vx
    public void testOpCode0xF055() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getMemory().initMemory();
        tested.getRegisters().setI((short)512); //I=512, memory index
        
        // to be writed in memory
        tested.getRegisters().getV()[0] = 0x01;
        tested.getRegisters().getV()[1] = 0x02;
        tested.getRegisters().getV()[2] = 0x03;
        tested.getRegisters().getV()[3] = 0x04;

        tested.execOpCode((short)0xf355); //x=3
        assertEquals(0x01, tested.getMemory().readMemory(512)); //I
        assertEquals(0x02, tested.getMemory().readMemory(513));
        assertEquals(0x03, tested.getMemory().readMemory(514));
        assertEquals(0x04, tested.getMemory().readMemory(515));
    }
    
    @Test // fx65 - LD Vx, [I]
    public void testOpCode0xF065() {
        Chip8 tested = new Chip8();
        Chip8Registers.getInstance().initResgisters();
        tested.getRegisters().setI((short)0); //I=0, memory index
        
        tested.execOpCode((short)0xf365); //x=3

        assertEquals(0xf0, tested.getMemory().readMemory(0)); //I
        assertEquals(0x90, tested.getMemory().readMemory(1));
        assertEquals(0x90, tested.getMemory().readMemory(2));
        assertEquals(0x90, tested.getMemory().readMemory(3)); 
    }    
}