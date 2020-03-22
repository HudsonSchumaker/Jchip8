package br.com.schumaker.jchip8.exceptions;

/**
 *
 * @author hudson schumaker
 */
public class StackPointerOutOfBoundsExecption extends RuntimeException {
    private static final long serialVersionUID = 6826637414226673470L;
    public static final String MESSAGE = "stack.pointer.out.of.bounds";

    public StackPointerOutOfBoundsExecption() {
        super(MESSAGE);
    }
}
