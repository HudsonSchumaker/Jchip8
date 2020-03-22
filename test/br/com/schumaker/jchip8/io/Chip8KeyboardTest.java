package br.com.schumaker.jchip8.io;

import br.com.schumaker.jchip8.exceptions.KeyOutOfBoundsExecption;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 *
 * @author hudson schumaker
 */
public class Chip8KeyboardTest {
    
    @Test
    public void testSetDown() {
        Chip8Keyboard tested = new Chip8Keyboard();
        tested.setDown(1);
        assertTrue(tested.isDown(1));
    }
    
    @Test
    public void testSetUp() {
        Chip8Keyboard tested = new Chip8Keyboard();
        tested.setDown(1);
        assertTrue(tested.isDown(1));
        tested.setUp(1);
        assertFalse(tested.isDown(1));
    }
    
    @Test
    public void testMap() {
        Chip8Keyboard tested = new Chip8Keyboard();
        int result = tested.map('A');
        assertEquals(10, result);
    }
    
    @Test
    public void testMapFail() {
        Chip8Keyboard tested = new Chip8Keyboard();
        int result = tested.map('Ü');
        assertEquals(-1, result);
    }
    
    @Test(expected = KeyOutOfBoundsExecption.class)
    public void testIsKeyInBoundError() {
        Chip8Keyboard.isKeyInBounds(50);
    }
    
    @Test(expected = KeyOutOfBoundsExecption.class)
    public void testIsKeyInBoundNegative() {
        Chip8Keyboard.isKeyInBounds(-50);
    }
}
