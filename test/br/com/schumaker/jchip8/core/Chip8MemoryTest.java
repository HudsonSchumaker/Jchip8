package br.com.schumaker.jchip8.core;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_PROGRAM_LOAD_ADDRESS;
import static br.com.schumaker.jchip8.core.Chip8DefaultCharacterSet.DEFAULT_CHARACTER_SET;
import br.com.schumaker.jchip8.exceptions.OutOfMemoryException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Hudson Schumaker
 */
public class Chip8MemoryTest {

    @Test
    public void testInitMemory() {
        Chip8Memory tested = new Chip8Memory();
        tested.initMemory();

        for (int k = 0; k < 4096; k++) {
            assertEquals('\u0000', tested.readMemory(k));
        }
    }

    @Test
    public void testDefaultCharacterSet() {
        int size = DEFAULT_CHARACTER_SET.length;
        Chip8Memory tested = new Chip8Memory();
        tested.initCharacterSet();
        
        for (int k = 0; k < size; k++) {
            assertEquals(DEFAULT_CHARACTER_SET[k], tested.readMemory(k));
        }
    }
    
    @Test
    public void testWriteReadMemory() {
        Chip8Memory tested = new Chip8Memory();
        tested.initMemory();
        
        Integer value = 65;
        tested.writeMemory(100, value);
        char result = tested.readMemory(100);
        assertEquals('A', result);
    }
    
    @Test
    public void testReadMemoryInShort() {
        Chip8Memory tested = new Chip8Memory();
        tested.initMemory();
        
        char value1 = '\u0000';
        char value2 = '\u0001';
        tested.writeMemory(1, value1);
        tested.writeMemory(2, value2);
        
        short result = tested.readMemoryInShort(1);
        assertEquals(1, result);
    }
    
    @Test(expected = OutOfMemoryException.class)
    public void testIsMemoryInBoundsWithException() {
        Chip8Memory tested = new Chip8Memory();
        tested.initMemory();
        
        char value = '\u0001';
        tested.writeMemory(4096, value);
    }
    
    @Test(expected = OutOfMemoryException.class)
    public void testIsMemoryInBoundsNegative() {
        Chip8Memory tested = new Chip8Memory();
        tested.initMemory();
        
        char value = '\u0001';
        tested.writeMemory(-4095, value);
    }
    
    @Test
    public void testLoadRomToRamTest() {
        String strRom = "Teste de uma rom bem simples.";
        byte[] rom = strRom.getBytes();
        Chip8Memory tested = new Chip8Memory();
        tested.initMemory();
        tested.loadRomToRam(rom);
        
        char[] result = tested.readMemory(CHIP8_PROGRAM_LOAD_ADDRESS, 
                CHIP8_PROGRAM_LOAD_ADDRESS + rom.length);
        
        String strResult = new String(result);
        assertEquals(strRom, strResult);
    }
}
