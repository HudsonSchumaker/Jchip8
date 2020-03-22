package br.com.schumaker.jchip8.exceptions;

/**
 *
 * @author hudson schumaker
 */
public class OutOfMemoryException extends RuntimeException {
   private static final long serialVersionUID = 6825537414226673470L;
   public static final String MESSAGE = "out.of.memory";
   
   public OutOfMemoryException() {
       super(MESSAGE);
   }
}
