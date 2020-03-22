package br.com.schumaker.jchip8.core;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_HEIGHT;
import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_WIDTH;
import br.com.schumaker.jchip8.exceptions.PixelOutOfScreenExecption;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author hudson schumaker
 */
public class Chip8ScreenTest {

    @Test
    public void testCleanScreen() {
        Chip8Screen tested = new Chip8Screen();
        tested.setPixel(0, 0);
        tested.setPixel(0, 2);
        tested.setPixel(0, 3);

        Chip8Screen.cleanScreen();

        boolean[][] result = tested.getPixels();

        for (int x = 0; x < CHIP8_WIDTH; x++) {
            for (int y = 0; y < CHIP8_HEIGHT; y++) {
                assertFalse(result[y][x]);
            }
        }
    }

    @Test
    public void testSetSprite() {
        Chip8Memory memory = new Chip8Memory();
        memory.initMemory();
        memory.initCharacterSet();

        boolean result1 = Chip8Screen.setSprite(30, 20, memory.readMemory(20, 25), 5);
        boolean result2 = Chip8Screen.setSprite(23, 10, memory.readMemory(0, 5), 5);
        boolean result3 = Chip8Screen.setSprite(0, 0, memory.readMemory(5, 10), 5);
        boolean result4 = Chip8Screen.setSprite(30, 20, memory.readMemory(10, 15), 5);

        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertTrue(result4);
    }
    
    @Test
    public void testIsPixelSet() {
        Chip8Screen.cleanScreen();
        assertFalse(Chip8Screen.isPixelSet(0,0));
    } 

    @Test(expected = PixelOutOfScreenExecption.class)
    public void testIsOnScreenXLessThanZero() {
        Chip8Screen tested = new Chip8Screen();
        tested.setPixel(-1, 0);
    }

    @Test(expected = PixelOutOfScreenExecption.class)
    public void testIsOnScreenXGreaterThanWidth() {
        Chip8Screen tested = new Chip8Screen();
        tested.setPixel(500, 1);
    }

    @Test(expected = PixelOutOfScreenExecption.class)
    public void testIsOnScreenYLessThanZero() {
        Chip8Screen tested = new Chip8Screen();
        tested.setPixel(0, -1);
    }

    @Test(expected = PixelOutOfScreenExecption.class)
    public void testIsOnScreenYGreaterThanWidth() {
        Chip8Screen tested = new Chip8Screen();
        tested.setPixel(1, 500);
    }
}
