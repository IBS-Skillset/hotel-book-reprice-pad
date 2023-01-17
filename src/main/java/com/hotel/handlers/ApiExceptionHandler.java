package com.hotel.handlers;

import com.google.protobuf.Any;
import com.google.rpc.Status;
import com.hotel.exception.HotelBookException;
import com.hotel.service.common.ResponseStatus;
import com.hotel.util.APIConstants;
import com.hotel.util.ErrorMappings;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class ApiExceptionHandler {

    private final ErrorMappings errorMappings;

    public ApiExceptionHandler(ErrorMappings errorMappings) {
        this.errorMappings = errorMappings;
    }

    @GrpcExceptionHandler(HotelBookException.class)
    public StatusRuntimeException handleHotelBookException(HotelBookException hotelBookException){
        log.error(errorMappings.getMessage() ,hotelBookException);
        var errorInfo = ResponseStatus.newBuilder()
                .setErrorCode(errorMappings.getErrorMapping().get(APIConstants.SUPPLIER).getErrorCode())
                .setErrorMessage(hotelBookException.getMessage())
                .build();
        var status = Status.newBuilder()
                .setCode(Integer.parseInt(errorMappings.getErrorMapping().get(APIConstants.SUPPLIER).getErrorCode()))
                .setMessage(hotelBookException.getMessage())
                .addDetails(Any.pack(errorInfo))
                .build();
        return StatusProto.toStatusRuntimeException(status);
    }
    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleGenericException(Exception exception){
        log.error(errorMappings.getMessage() ,exception);
        var errorInfo = ResponseStatus.newBuilder()
                .setErrorCode(errorMappings.getErrorMapping().get(APIConstants.SUPPLIER).getErrorCode())
                .setErrorMessage(exception.getMessage())
                .build();
        var status = Status.newBuilder()
                .setCode(Integer.parseInt(errorMappings.getErrorMapping().get(APIConstants.SUPPLIER).getErrorCode()))
                .setMessage(exception.getMessage())
                .addDetails(Any.pack(errorInfo))
                .build();
        return StatusProto.toStatusRuntimeException(status);
    }
}
