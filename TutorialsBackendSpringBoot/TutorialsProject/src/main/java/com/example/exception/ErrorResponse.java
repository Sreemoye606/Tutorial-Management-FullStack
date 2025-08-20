package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private String path;

    public ErrorResponse(String message, String error, String path){
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.error = error;
        this.path = path;
    }
}
