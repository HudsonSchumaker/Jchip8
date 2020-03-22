package br.com.schumaker.jchip8.core;

import br.com.schumaker.jchip8.exceptions.StackPointerOutOfBoundsExecption;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author hudson schumaker
 */
public class Chip8StackTest {

    @Test
    public void testPushPop() {
        Chip8Stack tested = mockStack();

        Integer value = 667;
        tested.stackPush(value);
        short result = tested.stackPop();
        assertEquals(value.shortValue(), result);
    }

    @Test(expected = StackPointerOutOfBoundsExecption.class)
    public void testIsStackPointerInBounds() {
        Chip8Stack tested = mockStack();
        Chip8Registers.getInstance().setSP(Integer.valueOf(30).shortValue());
        tested.isStackPointerInBounds();
    }

    private Chip8Stack mockStack() {
        Chip8 chip8 = new Chip8();
        Chip8Stack tested = new Chip8Stack(chip8);
        Chip8Registers.getInstance().initResgisters();
        tested.initStack();
        return tested;
    }
}
