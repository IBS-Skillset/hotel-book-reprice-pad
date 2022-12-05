package com.hotel.service.book;

import com.hotel.adapter.DjocaClient;
import com.hotel.mappers.book.request.HotelReservationsMapper;
import com.hotel.util.APIConstants;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;

@GrpcService
@Slf4j
public class BookServerService extends HotelBookServiceGrpc.HotelBookServiceImplBase {

    @Autowired
    HotelReservationsMapper hotelReservationsMapper;

    @Autowired
    DjocaClient djocaClient;



    @Override
    public void getHotelBook(HotelBookRequest request, StreamObserver<HotelBookResponse> responseObserver) {
     try {
         Object response = djocaClient.restClient(hotelReservationsMapper.map(request),request.getRequestContext().getSupplierUrl(), APIConstants.BOOK_SERVICE);

         //need to call response mappers and return the response from here
     } catch (JAXBException b){
         log.error("JaxbException caught", b);

     }

    }

}
