package com.saga.uni.vo;

import lombok.Data;

@Data
public class Transaction {

    private String accountNo;
    private String trxIdentifier;
    private TransactionType trxType;
    private Double value;

    public static enum TransactionType {
        WITHDRAW,
        DEPOSIT
    }
}