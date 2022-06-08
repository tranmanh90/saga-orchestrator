package com.saga.uni.models;

public class PaymentResponse {
    private String accountNo;
    private String trxIdentifier;
    private Double requestedValue;
    private ResultType resultType;
    private String cause;


    public PaymentResponse() {
    }

    public PaymentResponse(String accountNo, String trxIdentifier, Double requestedValue, ResultType resultType, String cause) {
        this.accountNo = accountNo;
        this.trxIdentifier = trxIdentifier;
        this.requestedValue = requestedValue;
        this.resultType = resultType;
        this.cause = cause;
    }

    public String getAccountNo() {
        return this.accountNo;
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

    public Double getRequestedValue() {
        return this.requestedValue;
    }

    public void setRequestedValue(Double requestedValue) {
        this.requestedValue = requestedValue;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public String getCause() {
        return this.cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "{" +
                " account_id='" + getAccountNo() + "'" +
                " transactionIdentifier='" + getTrxIdentifier() + "'" +
                ", requestedValue='" + getRequestedValue() + "'" +
                ", resultType='" + getResultType() + "'" +
                ", cause='" + getCause() + "'" +
                "}";
    }


    public static enum ResultType {
        APPROVED,
        DENIED
    }

}