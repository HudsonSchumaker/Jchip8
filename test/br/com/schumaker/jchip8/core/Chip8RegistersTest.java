package br.com.schumaker.jchip8.core;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author hudson schumaker
 */
public class Chip8RegistersTest {

    @Test
    public void testRegistersInit() {
        Chip8Registers.getInstance().initResgisters();
        
        assertEquals('\u0000', Chip8Registers.getInstance().getST());
        assertEquals('\u0000', Chip8Registers.getInstance().getDT());
        assertEquals(0, Chip8Registers.getInstance().getI());
        assertEquals(0, Chip8Registers.getInstance().getPC());
        assertEquals(0, Chip8Registers.getInstance().getSP());
       
        char[] v = Chip8Registers.getInstance().getV();
        for (int k = 0; k < v.length; k++) {
            assertEquals('\u0000', v[k]);
        }
    }
    
    @Test
    public void testPc() {
        Chip8Registers.getInstance().initResgisters();
        Chip8Registers.getInstance().incPC2x();
        
        assertEquals(2, Chip8Registers.getInstance().getPC());
    }
}
