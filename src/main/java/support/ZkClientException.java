package support;

public class ZkClientException extends RuntimeException {
    public ZkClientException() {
    }

    public ZkClientException(String message) {
        super(message);
    }

    public ZkClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkClientException(Throwable cause) {
        super(cause);
    }
}

