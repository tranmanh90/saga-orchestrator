package com.saga.uni.models;

public class PaymentRequest {
    private String accountNo;
    private String trxIdentifier;
    private TransactionType trxType;
    private Double value;


    public PaymentRequest() {
    }

    public PaymentRequest(String accountNo, String trxIdentifier, TransactionType trxType, Double value) {
        this.accountNo = accountNo;
        this.trxIdentifier = trxIdentifier;
        this.trxType = trxType;
        this.value = value;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTrxIdentifier() {
        return trxIdentifier;
    }

    public void setTrxIdentifier(String trxIdentifier) {
        this.trxIdentifier = trxIdentifier;
    }

    public void setTrxType(TransactionType trxType) {
        this.trxType = trxType;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TransactionType getTrxType() {
        return trxType;
    }

    public Double getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "{" +
            " account_id='" + getAccountNo() + "'" +
            ", transactionIdentifier='" + getTrxIdentifier() + "'" +
            ", transactionType='" + getTrxType() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }


    public static enum TransactionType {
        WITHDRAW,
        DEPOSIT
    }
    
}