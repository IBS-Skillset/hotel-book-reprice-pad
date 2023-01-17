package com.hotel.util;

import com.hotel.exception.ErrorModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMappings {
    private int status;
    private String message;
    private Map<String , ErrorModel> errorMapping;
}
