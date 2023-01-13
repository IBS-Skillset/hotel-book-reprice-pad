package com.hotel.mappers.raterule.request;

import com.hotel.mappers.common.POSMapper;
import com.hotel.service.raterule.HotelRateRuleRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.OTAHotelBookingRuleRQ;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateRuleRequestMapperTest {

   @Mock
   private POSMapper posMapper;

   @Mock
   private RuleMessageMapper ruleMessageMapper;

   @InjectMocks
   private RateRuleRequestMapper rateRuleRequestMapper;

   @Test
   void testGetOTAHotelBookingRuleRQ() {
      HotelRateRuleRequest rateRuleRequest = HotelRateRuleRequest.newBuilder().build();
      when(posMapper.mapPOS(rateRuleRequest.getRequestContext(), rateRuleRequest.getLanguageCode())).thenReturn(mock(ArrayOfSourceType.class));
      when(ruleMessageMapper.mapRuleMessage(rateRuleRequest)).thenReturn(mock(OTAHotelBookingRuleRQ.RuleMessage.class));
      OTAHotelBookingRuleRQ bookingRuleRQ = rateRuleRequestMapper.getOTAHotelBookingRuleRQ(rateRuleRequest);
      assertThat(bookingRuleRQ).isNotNull();
   }
}