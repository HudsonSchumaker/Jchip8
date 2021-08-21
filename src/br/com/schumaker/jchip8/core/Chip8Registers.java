package br.com.schumaker.jchip8.core;

import br.com.schumaker.jchip8.config.Chip8Specs;

/**
 *
 * @author Hudson Schumaker
 */
public class Chip8Registers {

    private static final Chip8Registers INSTANCE = new Chip8Registers();

    // Should be 1 byte ???
    private char[] V = new char[Chip8Specs.CHIP8_TOTAL_DATA_REGISTERS];
    private char DT = '\u0000'; //delay_timer
    private char ST = '\u0000'; //sound_timer
    private short I = 0;
    private short PC = 0; //program_counter
    private short SP = 0; //stack_pointer

    private Chip8Registers() {
        this.initResgisters();
        System.out.println("registers................OK");
    }

    public void initResgisters() {
        for (int k = 0; k < V.length; k++) {
            V[k] = '\u0000';
        }
        
        setDT('\u0000'); //delay_timer
        setST('\u0000'); //sound_timer
        setI(Integer.valueOf(0).shortValue());
        setPC(Integer.valueOf(0).shortValue()); //program_counter
        setSP(Integer.valueOf(0).shortValue()); //stack_pointer
    }

    public static Chip8Registers getInstance() {
        return INSTANCE;
    }

    public void incPC() {
        this.PC++;
    }

    public void incPC2x() {
        incPC();
        incPC();
    }

    //decrement stack pointer
    public void decSP() {
        this.SP--;
    }

    //increment stack pointer
    public void incSP() {
        this.SP++;
    }

    public void decST() {
        this.ST--;
    }

    public char[] getV() {
        return V;
    }

    public void setV(char[] V) {
        this.V = V;
    }

    public short getI() {
        return I;
    }

    public void setI(short I) {
        this.I = I;
    }

    public char getDT() {
        return DT;
    }

    public void setDT(char DT) {
        this.DT = DT;
    }

    public char getST() {
        return ST;
    }

    public void setST(char ST) {
        this.ST = ST;
    }

    public short getPC() {
        return PC;
    }

    public void setPC(short PC) {
        this.PC = PC;
    }

    public short getSP() {
        return SP;
    }

    public void setSP(short SP) {
        this.SP = SP;
    }
}
