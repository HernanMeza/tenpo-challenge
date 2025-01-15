package com.tenpo.transaction.facade;

import com.tenpo.transaction.dto.request.CreateTransactionRequest;
import com.tenpo.transaction.dto.response.BodyData;
import com.tenpo.transaction.dto.response.Header;
import com.tenpo.transaction.dto.response.Transaction;
import com.tenpo.transaction.dto.response.TransactionResponse;
import com.tenpo.transaction.entity.TransactionEntity;
import com.tenpo.transaction.enums.CustomResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacade {

    public ResponseEntity<TransactionResponse> createResponseSuccess(TransactionEntity transactionEntity, CustomResponseStatus customResponseStatus) {

        Transaction[] transactions = new Transaction[1];

        transactions[0]=Transaction.builder()
                .transactionId(transactionEntity.getTransactionId())
                .totalAmount(transactionEntity.getTotalAmount())
                .commerce(transactionEntity.getCommerce())
                .userName(transactionEntity.getUserName())
                .transactionDate(transactionEntity.getTransactionDate()).build();

        return ResponseEntity.ok(
                TransactionResponse.builder()
                        .header(createHeaderUsingResponseStatus(customResponseStatus))
                        .body(BodyData.builder().transactions(transactions).build()).build()
                );

    }

    public ResponseEntity<TransactionResponse> createResponseSuccessTenpista(TransactionEntity[] transactionEntities, CustomResponseStatus customResponseStatus) {
        Transaction[] transactions = transactionsMapping(transactionEntities);
        return ResponseEntity.ok(
                TransactionResponse.builder()
                        .header(createHeaderUsingResponseStatus(customResponseStatus))
                        .body(BodyData.builder().transactions(transactions).build()).build()
        );
    }



    public ResponseEntity<TransactionResponse> createResponseSuccessDelete(CustomResponseStatus customResponseStatus) {

        return ResponseEntity.ok(
                TransactionResponse.builder()
                        .header(createHeaderUsingResponseStatus(customResponseStatus))
                        .build()
        );
    }

    public ResponseEntity<TransactionResponse> createResponseError(CustomResponseStatus customResponseStatus, HttpStatus httpStatus) {

        return new ResponseEntity<>(
                TransactionResponse.builder()
                        .header(createHeaderUsingResponseStatus(customResponseStatus))
                        .build()
                , httpStatus
        );

    }

    private static Header createHeaderUsingResponseStatus(CustomResponseStatus customResponseStatus) {

        return Header.builder()
                .statusCode(customResponseStatus.getCode())
                .description(customResponseStatus.getDescription())
                .build();
    }

    public TransactionEntity mapRequestToEntityCreation(CreateTransactionRequest request) {

        return TransactionEntity.builder()
                .commerce(request.getCommerce())
                .totalAmount(request.getTotalAmount())
                .userName(request.getUserName())
                .transactionDate(request.getTransactionDate())
                .build();
    }

    private static Transaction[] transactionsMapping(TransactionEntity[] transactionEntities) {
        Transaction[] transactions = new Transaction[transactionEntities.length];

        int i = 0;
        for(TransactionEntity item : transactionEntities){
            transactions[i] =Transaction.builder()
                    .transactionId(item.getTransactionId())
                    .totalAmount(item.getTotalAmount())
                    .commerce(item.getCommerce())
                    .userName(item.getUserName())
                    .transactionDate(item.getTransactionDate()).build();
            i++;
        }
        return transactions;
    }

}


