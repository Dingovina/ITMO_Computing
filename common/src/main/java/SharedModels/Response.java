package SharedModels;

import SharedUtility.ResponseStatus;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseStatus status;
    private String message;

    public Response(ResponseStatus status, String message){
        this.message = message;
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
