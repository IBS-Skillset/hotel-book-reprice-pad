package com.hotel.mappers.book.response;

import com.hotel.service.book.HotelBookResponse;
import com.hotel.service.book.RoomRate;
import com.hotel.service.common.Address;
import com.hotel.service.common.PnrInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfRoomStaysTypeRoomStay;
import org.opentravel.ota._2003._05.OTAHotelResRS;
import org.opentravel.ota._2003._05.RatePlanType;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.HotelReservationType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.ArrayOfRatePlanType;
import org.opentravel.ota._2003._05.BasicPropertyInfoType;
import org.opentravel.ota._2003._05.TotalType;
import org.opentravel.ota._2003._05.RoomStayType;
import org.opentravel.ota._2003._05.ParagraphType;
import org.opentravel.ota._2003._05.ArrayOfRateTypeRate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
public class HotelBookResponseMapperTest {

    @InjectMocks
    HotelBookResponseMapper hotelBookResponseMapper;

    @Mock
    ErrorResponseMapper errorResponseMapper;

    @Mock
    AddressMapper addressMapper;

    @Mock
    RateMapper rateMapper;

    @Mock
    PnrInfoMapper pnrInfoMapper;

    @Test
  public void map() {
        OTAHotelResRS response = getResponse();
        final ArrayOfRoomStaysTypeRoomStay.RoomStay roomStay = response.getHotelReservations().getHotelReservation().get(0)
                .getRoomStays().getRoomStay().get(0);
        final RatePlanType ratePlanType = roomStay.getRatePlans().getRatePlan().get(0);
        Address address = Address.newBuilder().build();
        RoomRate roomRate = RoomRate.newBuilder().build();
        PnrInfo pnrInfo = PnrInfo.newBuilder().build();
        when(addressMapper.map(response.getHotelReservations().getHotelReservation().get(0).getRoomStays().getRoomStay().get(0).getBasicPropertyInfo().getAddress())).thenReturn(address);
        when(rateMapper.map(response.getHotelReservations().getHotelReservation().get(0).getRoomStays().getRoomStay().get(0).getRoomRates().getRoomRate().get(0).getRates().getRate().get(0).getBase() ,ratePlanType.getRatePlanDescription().getText())).thenReturn(roomRate);
        when(pnrInfoMapper.map(response.getHotelReservations().getHotelReservation().get(0).getTPAExtensions(),response.getHotelReservations().getHotelReservation().get(0).getResGlobalInfo())).thenReturn(pnrInfo);
        HotelBookResponse hotelBookResponse = hotelBookResponseMapper.map(response);
        assertThat(hotelBookResponse).isNotNull();
        assertThat(response.getSuccess().getValue()).isEqualTo("SUCCESS");
        assertThat(hotelBookResponse.getStartDate()).isEqualTo("12-03-2023");
        assertThat(hotelBookResponse.getEndDate()).isEqualTo("16-03-2023");
        assertThat(hotelBookResponse.getRatePlanId()).isEqualTo("1");
        assertThat(hotelBookResponse.getRateDescription()).isEqualTo("1234");
        assertThat(hotelBookResponse.getHotelCode()).isEqualTo("123");
        assertThat(hotelBookResponse.getHotelName()).isEqualTo("Marriot");
        verify(addressMapper,atLeast(1)).map(response.getHotelReservations().getHotelReservation().get(0).getRoomStays().getRoomStay().get(0).getBasicPropertyInfo().getAddress());
        verify(rateMapper,atLeast(1)).map(response.getHotelReservations().getHotelReservation().get(0).getRoomStays().getRoomStay().get(0).getRoomRates().getRoomRate().get(0).getRates().getRate().get(0).getBase() , ratePlanType.getRatePlanDescription().getText());
        verify(pnrInfoMapper,atLeast(1)).map(response.getHotelReservations().getHotelReservation().get(0).getTPAExtensions(),response.getHotelReservations().getHotelReservation().get(0).getResGlobalInfo());
    }

    @Test
     public void testErrorCase() {

         OTAHotelResRS response = new OTAHotelResRS();
         SuccessType success = new SuccessType();
         success.setValue("FAILURE");
         response.setSuccess(success);
         ErrorsType errors = new ErrorsType();
         ErrorType error = new ErrorType();
         error.setCode("1234");
         error.setValue("room not available");
         errors.getError().add(error);
         response.setErrors(errors);
         HotelBookResponse.Builder hotelBookResponseBuilder = HotelBookResponse.newBuilder();
         when(errorResponseMapper.mapErrorResponse(response.getErrors())).thenReturn(hotelBookResponseBuilder.build());
         HotelBookResponse hotelBookResponse = hotelBookResponseMapper.map(response);
         assertThat(hotelBookResponse).isNotNull();
         verify(errorResponseMapper, atLeast(1)).mapErrorResponse(response.getErrors());

     }
    private OTAHotelResRS getResponse() {
        OTAHotelResRS response = new OTAHotelResRS();
        SuccessType success = new SuccessType();
        success.setValue("SUCCESS");
        response.setSuccess(success);
        HotelReservationsType hotelReservationsType = new HotelReservationsType();
        HotelReservationType hotelReservationType = new HotelReservationType();
        ArrayOfRoomStaysTypeRoomStay roomStays = new ArrayOfRoomStaysTypeRoomStay();
        hotelReservationType.setRoomStays(roomStays);
        ArrayOfRoomStaysTypeRoomStay.RoomStay hotelRoomStay = new ArrayOfRoomStaysTypeRoomStay.RoomStay();
        ArrayOfRatePlanType hotelRatePlanType = new ArrayOfRatePlanType();
        hotelRoomStay.setRatePlans(hotelRatePlanType);
        DateTimeSpanType dateTimeSpanType = new DateTimeSpanType();
        hotelRoomStay.setTimeSpan(dateTimeSpanType);
        RatePlanType rate = new RatePlanType();
        BasicPropertyInfoType basicPropertyInfoType = new BasicPropertyInfoType();
        hotelRoomStay.setBasicPropertyInfo(basicPropertyInfoType);
        hotelRoomStay.getRatePlans().getRatePlan().add(rate);
        hotelReservationType.getRoomStays().getRoomStay().add(hotelRoomStay);
        hotelReservationsType.getHotelReservation().add(hotelReservationType);
        response.setHotelReservations(hotelReservationsType);
        ArrayOfRoomStaysTypeRoomStay.RoomStay roomStay = response.getHotelReservations().getHotelReservation().get(0)
                .getRoomStays().getRoomStay().get(0);
        RatePlanType ratePlanType = roomStay.getRatePlans().getRatePlan().get(0);
        roomStay.getTimeSpan().setStart("12-03-2023");
        roomStay.getTimeSpan().setEnd("16-03-2023");
        ratePlanType.setRatePlanID("1");
        ParagraphType paragraphType = new ParagraphType();
        paragraphType.setText("1234");
        ratePlanType.setRatePlanDescription(paragraphType);
        roomStay.getBasicPropertyInfo().setHotelCode("123");
        roomStay.getBasicPropertyInfo().setHotelName("Marriot");
        RoomStayType.RoomRates.RoomRate roomRate = new RoomStayType.RoomRates.RoomRate();
        RoomStayType.RoomRates roomRates = new RoomStayType.RoomRates();
        roomStay.setRoomRates(roomRates);
        roomStay.getRoomRates().getRoomRate().add(roomRate);
        ArrayOfRateTypeRate.Rate rates = new ArrayOfRateTypeRate.Rate();
        ArrayOfRateTypeRate arrayOfRateTypeRate = new ArrayOfRateTypeRate();
        arrayOfRateTypeRate.getRate().add(rates);
        roomStay.getRoomRates().getRoomRate().get(0).setRates(arrayOfRateTypeRate);
        TotalType totalType = new TotalType();
        roomStay.getRoomRates().getRoomRate().get(0).setBase(totalType);
        return response;
    }
}