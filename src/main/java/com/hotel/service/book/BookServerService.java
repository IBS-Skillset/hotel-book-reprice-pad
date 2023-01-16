package com.hotel.service.book;

import com.hotel.adapter.DjocaClient;
import com.hotel.mappers.book.request.HotelReservationsMapper;
import com.hotel.mappers.book.response.HotelBookResponseMapper;
import com.hotel.util.APIConstants;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.opentravel.ota._2003._05.OTAHotelResRS;

import java.util.Objects;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@GrpcService
@Slf4j
@AllArgsConstructor
public class BookServerService extends HotelBookServiceGrpc.HotelBookServiceImplBase {


    private HotelReservationsMapper hotelReservationsMapper;


    private DjocaClient djocaClient;


    private HotelBookResponseMapper hotelBookResponseMapper;


    @Override
    public void getHotelBook(HotelBookRequest request, StreamObserver<HotelBookResponse> responseObserver) {
        OTAHotelResRS hotelResRS = new OTAHotelResRS();
        try {
            log.info("HotelBookRequest code: " + request.getHotelCode());
            log.info("HotelBookRequest paymentType: " + request.getPaymentInfo().getPaymentType());
            log.info("HotelBookRequest roomCount: " + request.getRoomCount());
            Object response = djocaClient.restClient(hotelReservationsMapper.map(request), request.getRequestContext().getSupplierUrl(), APIConstants.BOOK_SERVICE);
            if (Objects.nonNull(response)) {
                hotelResRS = (OTAHotelResRS) response;
            }
            HotelBookResponse hotelBookResponse = hotelBookResponseMapper.map(hotelResRS);
            responseObserver.onNext(hotelBookResponse);
            responseObserver.onCompleted();

        } catch (Exception b) {
            log.error("Exception occurred in getHotelBook");
            Metadata metadata = new Metadata();
            metadata.put(Metadata.Key.of(APIConstants.ERROR, ASCII_STRING_MARSHALLER), getString(b));
            responseObserver.onError(new StatusRuntimeException(Status.CANCELLED, metadata));
        }

    }

    private static String getString(Exception e) {
        String cause = APIConstants.SUPPLIER_SERVER_ERROR;
        return "{\"errorCode\" : \"" + cause + "\"," +
                " \"errorMessage\" : \"" + e.getMessage() + "\"}";
    }

}
