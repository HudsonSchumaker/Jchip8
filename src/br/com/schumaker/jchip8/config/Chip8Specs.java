package br.com.schumaker.jchip8.config;

/**
 *
 * @author Hudson Schumaker
 */
public class Chip8Specs {
    public static final int CHIP8_MEMORY_SIZE = 4096;
    public static final short CHIP8_PROGRAM_LOAD_ADDRESS = 0x200; // 512
    public static final short LOAD_ADDR = CHIP8_PROGRAM_LOAD_ADDRESS;
    
    public static final int CHIP8_WIDTH = 64;
    public static final int CHIP8_HEIGHT = 32;
    public static final int CHIP8_PIXEL_SIZE = 10;
    public static final int CHIP8_DEFAULT_SPRITE_HEIGHT = 5;
    public static final int SCREEN_W = CHIP8_WIDTH * CHIP8_PIXEL_SIZE;
    public static final int SCREEN_H = CHIP8_HEIGHT * CHIP8_PIXEL_SIZE;
    
    public static final int CHIP8_TOTAL_DATA_REGISTERS = 16;
    public static final int CHIP8_TOTAL_STACK_SIZE = 16;
    public static final int CHIP8_TOTAL_KEYS = 16;
}
