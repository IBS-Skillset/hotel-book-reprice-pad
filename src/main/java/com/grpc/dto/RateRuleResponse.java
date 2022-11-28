package com.grpc.dto;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

@XmlRootElement(name = "Response")
@Getter
public class RateRuleResponse {
    @XmlElement(name = "EstimatedTotalAmount", required = true)
    private String price;


    @XmlElement(name = "EstimatedTotalCurrencyCode", required = true)
    private String currencyCode;
}
