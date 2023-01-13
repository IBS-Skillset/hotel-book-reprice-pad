package com.hotel.mappers.book.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.GuestCountType;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class GuestCountMapperTest {

    @InjectMocks
    GuestCountMapper guestCountMapper;


    @Test
    void map() {

        GuestCountType guest = guestCountMapper.map(5);
        assertThat(guest).isNotNull();
        assertThat(guest.getGuestCount().get(0).getCount()).isEqualTo("5");
        assertThat(guest.getGuestCount().get(0).getAgeQualifyingCode()).isEqualTo("10");

    }
}
