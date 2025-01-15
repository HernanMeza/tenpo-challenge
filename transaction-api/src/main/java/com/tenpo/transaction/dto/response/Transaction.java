package com.tenpo.transaction.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Item de Transaccion a modo de dto")
public class Transaction {

    private int transactionId;

    private int totalAmount;

    private String commerce;

    private String userName;

    private Instant transactionDate;
    
}
