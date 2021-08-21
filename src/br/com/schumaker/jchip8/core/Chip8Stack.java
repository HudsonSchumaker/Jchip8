package br.com.schumaker.jchip8.core;

import br.com.schumaker.jchip8.config.Chip8Specs;
import br.com.schumaker.jchip8.exceptions.StackPointerOutOfBoundsExecption;

/**
 *
 * @author Hudson schumaker
 */
public class Chip8Stack {
    
    private final Chip8 chip8;
    private final short[] stack = new short[Chip8Specs.CHIP8_TOTAL_STACK_SIZE];
    
    public Chip8Stack(Chip8 chip8) {
        this.chip8 = chip8;
    }
    
    public void isStackPointerInBounds() {
        if (chip8.getRegisters().getSP() > stack.length) {
            throw new StackPointerOutOfBoundsExecption();
        }
    }
    
    public void stackPush(Integer value) {
       this.stackPush(value.shortValue());
    }
    
    public void stackPush(short value) {
        chip8.getRegisters().incSP();
        this.isStackPointerInBounds();
        stack[this.chip8.getRegisters().getSP()] = value;
    }
    
    public short stackPop() {
        this.isStackPointerInBounds();
        short res = this.stack[this.chip8.getRegisters().getSP()];
        chip8.getRegisters().decSP();
        return res;
    }
    
    public void initStack() {
        for (int k = 0; k < stack.length; k++) {
            stack[k] = 0;
        }
        System.out.println("stack....................OK");
    }
    
    public short[] getStack() {
        return stack;
    }
}
