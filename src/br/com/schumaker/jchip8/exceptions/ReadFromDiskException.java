package br.com.schumaker.jchip8.exceptions;

/**
 *
 * @author Hudson Schumaker
 */
public class ReadFromDiskException  extends RuntimeException {
    
    private static final long serialVersionUID = 1315284142012373060L;
    public static final String MESSAGE = "error.read.from.disk";

    public ReadFromDiskException() {
        super(MESSAGE);
    }
}
