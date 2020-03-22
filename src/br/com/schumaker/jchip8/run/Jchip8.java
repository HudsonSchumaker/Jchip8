package br.com.schumaker.jchip8.run;

import br.com.schumaker.jchip8.ui.FrMain;

/**
 *
 * @author hudson schumaker
 */
public class Jchip8 {
    //static { System.load(System.getProperty("user.dir")+"/SDL2.dll"); }
    //private static native void SDL_Init();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(";------------------------------------------------");
        System.out.println("; Jchip8");
        System.out.println("; Chip8 Emulator");
        System.out.println("; Create by Hudson Schumaker 2020-03-18");
        System.out.println("; Copyright (c) SchumakerTeam 2020");
        System.out.println(";------------------------------------------------");
        java.awt.EventQueue.invokeLater(() -> {
            new FrMain().setVisible(true);
        }); 
    }
}
