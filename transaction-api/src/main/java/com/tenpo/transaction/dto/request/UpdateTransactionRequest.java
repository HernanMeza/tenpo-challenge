package com.tenpo.transaction.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request para API de actualizacion de Transaccion")
public class UpdateTransactionRequest {
    
    @NotNull
    private int transactionId;

    @NotNull @Min(value = 0, message = "El monto no puede ser negativo ")
    private int totalAmount;

    @NotNull @Size(min = 2, max = 100)
    private String commerce;

    @NotNull @Size(min = 3, max = 30)
    private String userName;

    @NotNull @PastOrPresent
    private Instant transactionDate;

}



