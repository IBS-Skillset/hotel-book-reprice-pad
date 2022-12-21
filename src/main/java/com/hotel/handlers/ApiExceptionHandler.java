package com.hotel.handlers;

import com.google.protobuf.Any;
import com.google.rpc.Status;
import com.hotel.exception.HotelBookException;
import com.hotel.service.common.ResponseStatus;
import com.hotel.util.APIConstants;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class ApiExceptionHandler {

    @GrpcExceptionHandler(HotelBookException.class)
    public StatusRuntimeException handleHotelBookException(HotelBookException hotelBookException){
        log.error("Exception Occurred in book-pad service" ,hotelBookException);
        var errorInfo = ResponseStatus.newBuilder()
                .setErrorCode(APIConstants.SUPPLIER_SERVER_ERROR)
                .setErrorMessage(hotelBookException.getMessage())
                .build();
        var status = Status.newBuilder()
                .setCode(999)
                .setMessage("Exception occurred in pad service")
                .addDetails(Any.pack(errorInfo))
                .build();
        return StatusProto.toStatusRuntimeException(status);
    }
    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleGenericException(Exception exception){
        log.error("Exception Occurred in book-pad service" ,exception);
        var errorInfo = ResponseStatus.newBuilder()
                .setErrorCode(APIConstants.SUPPLIER_SERVER_ERROR)
                .setErrorMessage(exception.getMessage())
                .build();
        var status = Status.newBuilder()
                .setCode(999)
                .setMessage("Exception occurred in pad service")
                .addDetails(Any.pack(errorInfo))
                .build();
        return StatusProto.toStatusRuntimeException(status);
    }
}
