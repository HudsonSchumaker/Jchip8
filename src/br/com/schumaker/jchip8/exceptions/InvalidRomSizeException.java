package br.com.schumaker.jchip8.exceptions;

/**
 *
 * @author hudson schumaker
 */
public class InvalidRomSizeException extends RuntimeException {
    private static final long serialVersionUID = 1310374142012373070L;
    public static final String MESSAGE = "invalid.rom.size";

    public InvalidRomSizeException() {
        super(MESSAGE);
    }
}
