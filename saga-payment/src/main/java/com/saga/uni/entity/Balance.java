package com.saga.uni.entity;

import com.saga.uni.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "balance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false,
            columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "balance")
    private Double balance;

    public void deposit(double value) {
        balance = balance + value;
    }

    public void withdraw(double value) throws InsufficientBalanceException {
        if ((balance - value) < 0) {
            throw new InsufficientBalanceException(balance, value);
        }
        balance = balance - value;
    }
}
