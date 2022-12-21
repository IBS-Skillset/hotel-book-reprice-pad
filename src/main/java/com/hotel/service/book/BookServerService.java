package com.hotel.service.book;

import com.hotel.adapter.DjocaClient;
import com.hotel.exception.HotelBookException;
import com.hotel.mappers.book.request.HotelReservationsMapper;
import com.hotel.mappers.book.response.HotelBookResponseMapper;
import com.hotel.util.APIConstants;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.opentravel.ota._2003._05.OTAHotelResRS;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.JAXBException;
import java.util.Objects;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@GrpcService
@Slf4j
public class BookServerService extends HotelBookServiceGrpc.HotelBookServiceImplBase {

    @Autowired
    private HotelReservationsMapper hotelReservationsMapper;

    @Autowired
    private DjocaClient djocaClient;

    @Autowired
    private HotelBookResponseMapper hotelBookResponseMapper;


    @Override
    public void getHotelBook(HotelBookRequest request, StreamObserver<HotelBookResponse> responseObserver) {
        OTAHotelResRS hotelResRS = new OTAHotelResRS();
        try {
            Object response = djocaClient.restClient(hotelReservationsMapper.map(request), request.getRequestContext().getSupplierUrl(), APIConstants.BOOK_SERVICE);
            if (Objects.nonNull(response)) {
                hotelResRS = (OTAHotelResRS)response;
            }
            HotelBookResponse hotelBookResponse = hotelBookResponseMapper.map(hotelResRS);
            responseObserver.onNext(hotelBookResponse);
            responseObserver.onCompleted();

        } catch (Exception b) {
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of("error",ASCII_STRING_MARSHALLER), getString(b));
            responseObserver.onError(new StatusRuntimeException(Status.CANCELLED,metadata));
        }

    }

    private static String getString(Exception e) {
        String cause = APIConstants.SUPPLIER_SERVER_ERROR;
        String a = "{\"errorCode\" : \"" +cause +"\","+
                " \"errorMessage\" : \"" + e.getMessage() +"\"}";
        return a;
    }

}
