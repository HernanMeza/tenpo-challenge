package com.tenpo.transaction.ctrl;

import com.tenpo.transaction.dto.request.CreateTransactionRequest;
import com.tenpo.transaction.dto.request.UpdateTransactionRequest;
import com.tenpo.transaction.dto.response.TransactionResponse;
import com.tenpo.transaction.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tenpo/api/v1/")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TransactionApiCtrl implements TransactionApi{

    private final TransactionService transactionService;

    @GetMapping("transaction")
    public ResponseEntity<TransactionResponse> getTransaction(@NotNull @RequestParam(value="transactionId" ) String transactionId ){
        log.info("=== getTransaction ==");
        return transactionService.getTransaction(transactionId);
    }
    @GetMapping("transaction/tenpista")
    public ResponseEntity<TransactionResponse> getTransactionByTenpista(@NotNull @RequestParam(value="userName" ) String userName ){
        log.info("=== getTransactionByTenpista ==");
        return transactionService.getTransactionByTenpista(userName);
    }

    @PostMapping("transaction")
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @NotNull @RequestBody CreateTransactionRequest transactionRequest) {
        log.info("=== createTransaction ==");
        return transactionService.createTransaction(transactionRequest);
    }

    @PutMapping("transaction")
    public ResponseEntity<TransactionResponse> updateTransaction(@Valid @NotNull @RequestBody UpdateTransactionRequest updateTransactionRequest) {
        log.info("=== updateTransaction ==");
        return transactionService.updateTransaction(updateTransactionRequest);
    }

    @DeleteMapping("transaction")
    public ResponseEntity<TransactionResponse> deleteTransaction(@NotNull @RequestParam(value="transactionId" ) String transactionId ) {
        log.info("=== deleteTransaction ==");
        return transactionService.deleteTransaction(transactionId);
    }

}
