package com.hotel.service.raterule;


import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@GrpcService
public class HotelRateRuleServerService extends HotelRateRuleServiceGrpc.HotelRateRuleServiceImplBase {

    @Autowired
    private HotelRateRuleService hotelRateRuleService;

    @Override
    public void getHotelRateRule(HotelRateRuleRequest request, StreamObserver<HotelRateRuleResponse> responseObserver) {
        HotelRateRuleResponse response = null;
        try {
            response = hotelRateRuleService.getHotelRateRuleItemsFromSupplier(request);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of("error",ASCII_STRING_MARSHALLER), getString(e));
            responseObserver.onError(new StatusRuntimeException(Status.CANCELLED,metadata));
        }
    }

    private static String getString(Exception e) {
        String cause = e.getCause()!= null ? e.getCause().getMessage() : "Unknown code";
        String a = "{\"errorCode\" : \"" +cause +"\","+
                " \"errorMessage\" : \"" + e.getMessage() +"\"}";
        return a;
    }


}
