package com.example.app.responses;
import java.time.LocalDateTime;

public class SuccessResponse<T> extends AbstractResponse{
    
    private T data;


    public SuccessResponse(T data) {
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
        this.status = 200;
        this.message = "Success";
    }

    public SuccessResponse(T data, String message) {
        this.timestamp = LocalDateTime.now().toString();
        this.data = data;
        this.status = 200;
        this.message = message;
    }

    public static SuccessResponse<?> response(Object data) {
        return new SuccessResponse<>(data, "success");
    }

    public static SuccessResponse<?> response(String message) {
        return new SuccessResponse<>(null, message);
    }

    public String getMessage(){
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
