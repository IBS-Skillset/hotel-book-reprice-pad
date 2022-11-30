package com.hotel.service.raterule;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.AuthenticationException;

@GrpcService
public class HotelRateRuleServerService extends HotelRateRuleServiceGrpc.HotelRateRuleServiceImplBase {

    @Autowired
    private HotelRateRuleService hotelRateRuleService;

    @Override
    public void getHotelRateRule(HotelRateRuleRequest request, StreamObserver<HotelRateRuleResponse> responseObserver) {
        try {
            HotelRateRuleResponse response = hotelRateRuleService.getHotelRateRuleItemsFromSupplier(request);
            System.out.println();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new AuthenticationException("authentication exception"));
        }
    }
}
