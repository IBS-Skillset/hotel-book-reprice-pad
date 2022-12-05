package com.hotel.mappers.book.request;


import com.hotel.service.book.HotelBookRequest;
import com.hotel.service.book.PaymentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.ArrayOfRoomStaysTypeRoomStay;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RoomStaysMapperTest {
    @InjectMocks
    RoomStaysMapper roomStaysMapper;


    @Test
    public void testMap() {
        HotelBookRequest request = HotelBookRequest.newBuilder()
                .setRoomCount(1)
                .setRatePlanId("3f45")
                .setGuestCount(1)
                .setStartDate("2023-07-19")
                .setEndDate("2023-07-20")
                .setHotelCode("1234")
                .setPaymentInfo(PaymentInfo.newBuilder().setPaymentType("CC")
                        .setCardType("VI")
                        .setCardHolderName("Test Test")
                        .setCardNumber("1234")
                        .setCardExpireDate("0925").build())
                .build();
        ArrayOfRoomStaysTypeRoomStay roomstay = roomStaysMapper.map(request);
        assertThat(roomstay).isNotNull();
        assertThat(roomstay.getRoomStay().get(0).getGuarantee().get(0).getGuaranteesAccepted().getGuaranteeAccepted()
                .get(0).getGuaranteeTypeCode()).isEqualTo("CC");
        assertThat(roomstay.getRoomStay().get(0).getGuarantee().get(0).getGuaranteesAccepted().getGuaranteeAccepted()
                .get(0).getPaymentCard().getCardNumber()).isEqualTo("1234");
        assertThat(roomstay.getRoomStay().get(0).getRoomTypes().getRoomType().get(0).getNumberOfUnits()).isEqualTo(1);
        assertThat(roomstay.getRoomStay().get(0).getRatePlans().getRatePlan().get(0).getRatePlanID()).isEqualTo("3f45");
        assertThat(roomstay.getRoomStay().get(0).getGuestCounts().getGuestCount().get(0).getAgeQualifyingCode()).isEqualTo("10");
        assertThat(roomstay.getRoomStay().get(0).getTimeSpan().getStart()).isEqualTo("2023-07-19");
        assertThat(roomstay.getRoomStay().get(0).getTimeSpan().getEnd()).isEqualTo("2023-07-20");
        assertThat(roomstay.getRoomStay().get(0).getBasicPropertyInfo().getHotelCode()).isEqualTo("1234");


    }

}