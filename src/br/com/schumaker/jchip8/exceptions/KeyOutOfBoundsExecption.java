package br.com.schumaker.jchip8.exceptions;

/**
 *
 * @author hudson schumaker
 */
public class KeyOutOfBoundsExecption extends RuntimeException {

    private static final long serialVersionUID = 2326637414226673470L;
    public static final String MESSAGE = "key.out.of.bounds";

    public KeyOutOfBoundsExecption() {
        super(MESSAGE);
    }
}
