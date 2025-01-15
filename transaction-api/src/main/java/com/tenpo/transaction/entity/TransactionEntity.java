package com.tenpo.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "transactions")
@SequenceGenerator(name = "transaction_seq", sequenceName = "tenpo.transactions_seq", allocationSize = 1)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @Column(name="transaction_id")
    private int transactionId;

    @Column(name="total_amount")
    private int totalAmount;

    @Column(name="commerce")
    private String commerce;

    @Column(name="user_name")
    private String userName;

    @Column(name="transaction_date")
    private Instant transactionDate;

}
