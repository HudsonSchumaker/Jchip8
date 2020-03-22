package br.com.schumaker.jchip8.io;

import static br.com.schumaker.jchip8.config.Chip8Specs.CHIP8_TOTAL_KEYS;
import br.com.schumaker.jchip8.exceptions.KeyOutOfBoundsExecption;
import java.awt.event.KeyEvent;

/**
 *
 * @author hudson schumaker
 */
public class Chip8Keyboard {
    
    private static boolean keys[] = new boolean[CHIP8_TOTAL_KEYS];
    private static char map[] = new char[CHIP8_TOTAL_KEYS];
    
    static {
        map[0] = KeyEvent.VK_0;
        map[1] = KeyEvent.VK_1;
        map[2] = KeyEvent.VK_2;
        map[3] = KeyEvent.VK_3;
        map[4] = KeyEvent.VK_4;
        map[5] = KeyEvent.VK_5;
        map[6] = KeyEvent.VK_6;
        map[7] = KeyEvent.VK_7;
        map[8] = KeyEvent.VK_8;
        map[9] = KeyEvent.VK_9;
        map[10] = (char)KeyEvent.VK_A;
        map[11] = (char)KeyEvent.VK_B;
        map[12] = (char)KeyEvent.VK_C;
        map[13] = (char)KeyEvent.VK_D;
        map[14] = (char) KeyEvent.VK_E;
        map[15] = (char)KeyEvent.VK_F;
    }
    
    public static void isKeyInBounds(int key) {
        if (!(key >= 0 && key < CHIP8_TOTAL_KEYS)) {
           throw new KeyOutOfBoundsExecption();
        }
    }
    
    public void setDown(int key) {
        isKeyInBounds(key);
        keys[key] = true;
    }
    
    public void setUp(int key) {
        isKeyInBounds(key);
        keys[key] = false;
    }
    
    public boolean isDown(int key) {
        isKeyInBounds(key);
        return keys[key];
    }
    
    public int map(char key) {
        for (int k = 0; k < CHIP8_TOTAL_KEYS; k++) {
            if (map[k] == Character.toUpperCase(key)) {
                return k;
            }
        }
        return -1;
    }
    
    public void initKeyboard() {
        for (int k = 0; k < CHIP8_TOTAL_KEYS; k++) {
            keys[k] = false;
        }
    }
}
