package com.hotel.service.raterule;


import com.hotel.adapter.HotelRateRuleAdapter;
import com.hotel.mappers.rateRule.request.RateRuleRequestMapper;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@GrpcService
@AllArgsConstructor
public class HotelRateRuleServerService extends HotelRateRuleServiceGrpc.HotelRateRuleServiceImplBase {

    private HotelRateRuleAdapter rateRuleAdapter;

    private RateRuleRequestMapper rateRuleRequestMapper;

    @Override
    public void getHotelRateRule(HotelRateRuleRequest request, StreamObserver<HotelRateRuleResponse> responseObserver) {
        HotelRateRuleResponse response = null;
        try {
            response = rateRuleAdapter.restClient(rateRuleRequestMapper.getOTAHotelBookingRuleRQ(request),request);
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
        String errorMessage = "{\"errorCode\" : \"" +cause +"\","+
                " \"errorMessage\" : \"" + e.getMessage() +"\"}";
        return errorMessage;
    }
}
