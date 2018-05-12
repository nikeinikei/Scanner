package compiler;

/**
 * This class should be thrown whenever something doesn't work in an implementation of a transition function
 */
public class TransitionFunctionException extends Exception {
    public TransitionFunctionException() {
        super();
    }
}