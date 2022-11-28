package com.grpc.service;


import com.grpc.dto.RateRuleResponse;
import com.raterule.BookAuthorServiceGrpc;
import com.raterule.RateRuleRequest;
import com.raterule.ResponseRateRule;
import io.grpc.stub.StreamObserver;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.naming.AuthenticationException;
import java.io.StringReader;

@GrpcService
public class BookAuthorServerService extends BookAuthorServiceGrpc.BookAuthorServiceImplBase {
    @Override
    public void getRateRule(RateRuleRequest request, StreamObserver<ResponseRateRule> responseObserver) {
        try {
            String rateRuleResponse ; //supplier response
            Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(RateRuleResponse.class).createUnmarshaller();
            RateRuleResponse result= (RateRuleResponse) jaxbUnmarshaller.unmarshal(new StringReader(rateRuleResponse));

            ResponseRateRule response = ResponseRateRule.newBuilder()
                    .setPrice(result.getPrice())
                    .setCurrencyCode(result.getCurrencyCode())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new AuthenticationException("authentication exception"));
        }
    }
}
