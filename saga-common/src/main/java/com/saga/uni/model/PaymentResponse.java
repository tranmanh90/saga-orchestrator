package com.saga.uni.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String accountNo;
    private UUID trxIdentifier;
    private Double requestedValue;
    private ResultType resultType;
    private String cause;

    public enum ResultType {
        APPROVED,
        DENIED
    }
}