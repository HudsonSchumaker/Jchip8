package br.com.schumaker.jchip8.core;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_MEMORY_SIZE;
import static br.com.schumaker.jchip8.config.Chip8Specs.LOAD_ADDR;
import static br.com.schumaker.jchip8.core.Chip8DefaultCharacterSet.DEFAULT_CHARACTER_SET;
import br.com.schumaker.jchip8.exceptions.OutOfMemoryException;

/**
 *
 * @author Hudson Schumaker
 */
public class Chip8Memory {

    // RAM 0x000 -> 0xFFF
    private final char[] memory = new char[CHIP8_MEMORY_SIZE];

    public boolean loadRomToRam(byte[] rom) {
        boolean isLoad = false;
        int r = 0;
        for (int k = LOAD_ADDR; k < LOAD_ADDR + rom.length; k++, r++) {
            memory[k] = (char) (rom[r] & 0xFF);
        }
        Chip8Registers.getInstance().setPC(LOAD_ADDR);
        
        isLoad = true;
        return isLoad;
    }
    
    public short readMemoryInShort(int index) {
        char byte1 = readMemory(index);
        char byte2 = readMemory(++index);
        
//        short signedShort = (short) (byte1 << 8 | byte2);
//        return  (short) (signedShort & 0xFFFF);

        return (short) (byte1 << 8 | byte2);
    }
    
    public char readMemory(int index) {
        isMemoryInBounds(index);
        return this.memory[index];
    }

    public char[] readMemory(int start, int end) {
        int range = end - start;
        if (range < 0) {
            range *= -1;
        }
        char[] data = new char[range];
        for (int k = 0; k < range; k++, start++) {
            data[k] = readMemory(start);
        }
        return data;
    }
    
    public void writeMemory(int index, Integer value) {
        writeMemory(index, value.shortValue());
    }
    
    public void writeMemory(int index, short value) {
        writeMemory(index, (char) value);
    }

    public void writeMemory(int index, char value) {
        isMemoryInBounds(index);
        this.memory[index] = value;
    }

    public static void isMemoryInBounds(int index) {
        if (!(index >= 0 && index < CHIP8_MEMORY_SIZE)) {
            throw new OutOfMemoryException();
        }
    }

    public void initMemory() {
        for (int k = 0; k < memory.length; k++) {
            memory[k] = '\u0000';
        }
        System.out.println("memory...................OK");
    }
    
    public void initCharacterSet() {
        System.arraycopy(DEFAULT_CHARACTER_SET,
                0, memory, 0,
                DEFAULT_CHARACTER_SET.length);
        System.out.println("default character set....OK");
    }
}
