package bj.gouv.sineb.common.advice.exception;

public class CustomException extends RuntimeException{
    private String requestBody;
    private String startProcessing;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, String requestBody, String startProcessing) {
        super(message);
        this.requestBody = requestBody;
        this.startProcessing = startProcessing;
    }
}
