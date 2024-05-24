package Exceptions;

/**
 * Исключение, выбрасываемое при некорректной работе функции
 */
public class FunctionFailedException extends Exception{
    public FunctionFailedException(String message) {
        super(message);
    }
}
