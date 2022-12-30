package com.hotel.mappers.book.response;

import com.hotel.service.book.HotelBookResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseMapperTest {
    @InjectMocks
    private ErrorResponseMapper errorResponseMapper;

    @Test
    public void testMapErrorResponse() {
        ErrorsType errorsType = new ErrorsType();
        ErrorType errorType = new ErrorType();
        errorType.setCode("222");
        errorType.setValue("Booking Failed");
        errorsType.getError().add(errorType);
        HotelBookResponse response = errorResponseMapper.mapErrorResponse(errorsType);
        assertThat(response).isNotNull();
        assertThat(response.getResponseStatus().getStatus()).isEqualTo(0);
        assertThat(response.getResponseStatus().getErrorCode()).isEqualTo("222");
        assertThat(response.getResponseStatus().getErrorMessage()).isEqualTo("Booking Failed");
    }

}
