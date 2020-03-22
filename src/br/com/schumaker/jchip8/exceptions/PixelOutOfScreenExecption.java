package br.com.schumaker.jchip8.exceptions;

/**
 *
 * @author hudson schumaker
 */
public class PixelOutOfScreenExecption extends RuntimeException {
    private static final long serialVersionUID = 6824437414116673470L;
    public static final String MESSAGE = "pixel.out.of.screen";

    public PixelOutOfScreenExecption() {
        super(MESSAGE);
    }
    
}
