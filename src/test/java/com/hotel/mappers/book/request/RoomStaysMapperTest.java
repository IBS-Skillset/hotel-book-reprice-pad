package com.hotel.mappers.book.request;


import com.hotel.service.book.HotelBookRequest;
import com.hotel.service.book.PaymentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomStaysMapperTest {
    @InjectMocks
    RoomStaysMapper roomStaysMapper;
    @Mock
    GuaranteeMapper guaranteeMapper;
    @Mock
    GuestCountMapper guestCountMapper;
    @Mock
    RatePlanMapper ratePlanMapper;

    @Test
    public void testMap() {
        HotelBookRequest request = HotelBookRequest.newBuilder()
                .setRoomCount(1)
                .setRatePlanId("3")
                .setGuestCount(5)
                .setStartDate("2023-07-19")
                .setEndDate("2023-07-20")
                .setHotelCode("1234")
                .setPaymentInfo(PaymentInfo.newBuilder().setPaymentType("CC")
                        .setCardType("VI")
                        .setCardHolderName("Test Test")
                        .setCardNumber("1234")
                        .setCardExpireDate("0925").build())
                .build();

        ArrayOfRatePlanType ratePlans = new ArrayOfRatePlanType();
        GuestCountType guestCounts = new GuestCountType();
        GuaranteeType guarantee = new GuaranteeType();
        PaymentCardType paymentCard = new PaymentCardType();
        paymentCard.setCardNumber(request.getPaymentInfo().getCardNumber());
        when(ratePlanMapper.map(request.getRatePlanId())).thenReturn(ratePlans);
        when(guestCountMapper.map(request.getGuestCount())).thenReturn(guestCounts);
        when(guaranteeMapper.map(request.getPaymentInfo())).thenReturn(guarantee);
        ArrayOfRoomStaysTypeRoomStay roomstay = roomStaysMapper.map(request);
        assertThat(roomstay).isNotNull();
        assertThat(roomstay.getRoomStay().get(0).getRoomTypes().getRoomType().get(0).getNumberOfUnits()).isEqualTo(1);
        assertThat(roomstay.getRoomStay().get(0).getTimeSpan().getStart()).isEqualTo("2023-07-19");
        assertThat(roomstay.getRoomStay().get(0).getTimeSpan().getEnd()).isEqualTo("2023-07-20");
        assertThat(roomstay.getRoomStay().get(0).getBasicPropertyInfo().getHotelCode()).isEqualTo("1234");
        verify(ratePlanMapper,atLeast(1)).map(request.getRatePlanId());
        verify(guestCountMapper,atLeast(1)).map(request.getGuestCount());
        verify(guaranteeMapper,atLeast(1)).map(request.getPaymentInfo());

    }

}