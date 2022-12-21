package com.hotel.exception;

public class HotelBookException extends RuntimeException{

    private final String code;

    public HotelBookException(String message, String code) {
        super(message);
        this.code = code;
    }

    public HotelBookException(String message, Throwable cause, String code) {
        super(message ,cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
