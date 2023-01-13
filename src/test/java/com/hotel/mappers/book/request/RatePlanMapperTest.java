package com.hotel.mappers.book.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfRatePlanType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class RatePlanMapperTest {

    @InjectMocks
    RatePlanMapper ratePlanMapper;

    @Test
    void map() {

        ArrayOfRatePlanType ratePlan = ratePlanMapper.map("3");
        assertThat(ratePlan).isNotNull();
        assertThat(ratePlan.getRatePlan().get(0).getRatePlanID()).isEqualTo("3");
    }
}