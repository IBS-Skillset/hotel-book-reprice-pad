package com.hotel.mappers.book.response;

import com.hotel.service.book.RoomRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.TaxesType;
import org.opentravel.ota._2003._05.TotalType;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ExtendWith(MockitoExtension.class)
public class RateMapperTest {

    @InjectMocks
    RateMapper rateMapper;

    @Test
    public void map() {
        TotalType rateResponse = getRate();
        RoomRate rate = rateMapper.map(rateResponse);
        assertThat(rate).isNotNull();
        assertThat(rate.getCurrencyCode()).isEqualTo("EUR");
        assertThat(rate.getTotalAmount()).isEqualTo(14456.65f);
    }

    private TotalType getRate() {
        TotalType rateResponse = new TotalType();
        rateResponse.setAdditionalFeesExcludedIndicator(Boolean.TRUE);
        rateResponse.setCurrencyCode("EUR");
        rateResponse.setAmountAfterTax(BigDecimal.valueOf(14456.65));
        TaxesType taxesType = new TaxesType();
        rateResponse.setTaxes(taxesType);
        rateResponse.setDecimalPlaces(BigInteger.valueOf(12345));
        return rateResponse;
    }
}