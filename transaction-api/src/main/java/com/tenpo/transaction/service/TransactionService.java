package com.tenpo.transaction.service;

import com.tenpo.transaction.auxiliar.TransactionAux;
import com.tenpo.transaction.dto.request.CreateTransactionRequest;
import com.tenpo.transaction.dto.request.UpdateTransactionRequest;
import com.tenpo.transaction.dto.response.TransactionResponse;
import com.tenpo.transaction.entity.TransactionEntity;
import com.tenpo.transaction.enums.CustomResponseStatus;
import com.tenpo.transaction.facade.TransactionFacade;
import com.tenpo.transaction.repository.TransactionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionFacade transactionFacade;

    private final TransactionAux aux;

    @Value("${app.tenpo.max-transactions-per-user}")
    private Long maxTransactionPerUser;

    public ResponseEntity<TransactionResponse> getTransaction(String transactionId) {

        ResponseEntity<TransactionResponse> rtn = null;
        transactionId = transactionId.trim();
        log.info("Buscando transaccion {}", transactionId);
        Optional<TransactionEntity> transactionFound = transactionRepository.findById(aux.parseTransactionId(transactionId));

        if(transactionFound.isPresent()){
            log.info("Transaccion {} Encontrada correctamente.", transactionId);

            rtn = transactionFacade.createResponseSuccess(transactionFound.get(), CustomResponseStatus.SUCCESS_FOUND);

        }else{
            log.warn("Transaccion {} No Encontrada.", transactionId);
            rtn = transactionFacade.createResponseError(CustomResponseStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return rtn;

    }

    public ResponseEntity<TransactionResponse> getTransactionByTenpista(String tenpista) {
        ResponseEntity<TransactionResponse> rtn = null;
        tenpista = tenpista.trim().toLowerCase();
        log.info("Buscando transacciones del tenpista {}", tenpista);

        Long cantPreviousTransactions = transactionRepository.checkMaxTransactionsPerUser(tenpista);
        if(cantPreviousTransactions > 0){ /** Se valida la cantidad Maxima de transacciones */

            Optional<TransactionEntity[]> transactionsFound = transactionRepository.findByUserName(tenpista);

            if(transactionsFound.isPresent()){
                log.info("{} Transacciones  encontrada correctamente.", transactionsFound.get().length);

                rtn = transactionFacade.createResponseSuccessTenpista(transactionsFound.get(), CustomResponseStatus.SUCCESS_FOUND);

            }else{
                log.warn("No se encontraron transacciones para el tenpista {}", tenpista);
                rtn = transactionFacade.createResponseError(CustomResponseStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
            }

        }else{
            log.warn("No se encontraron transacciones para el tenpista {}", tenpista);
            rtn = transactionFacade.createResponseError(CustomResponseStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return rtn;

    }



    public ResponseEntity<TransactionResponse> createTransaction(CreateTransactionRequest request) {

        ResponseEntity<TransactionResponse> rtn = null;
        request.setUserName(request.getUserName().trim().toLowerCase());
        request.setCommerce(request.getCommerce().trim().toLowerCase());//Lower Casing forzado en caso de ejecucion por postman
        boolean isDuplicate = transactionRepository.validateDuplicateTransaction(request.getUserName(),
                request.getCommerce(),
                request.getTotalAmount(),
                request.getTransactionDate());

        Long cantPreviousTransactions = transactionRepository.checkMaxTransactionsPerUser(request.getUserName());

        if(!isDuplicate){ /** No debiese existir una transaccion con iguales monto, giro y usuario a la misma HORA */

            if(cantPreviousTransactions <= maxTransactionPerUser){ /** Se valida la cantidad Maxima de transacciones */

                log.info("Guardando Transaccion...");
                TransactionEntity savedTransaction = transactionRepository.save(transactionFacade.mapRequestToEntityCreation(request));
                log.info("Transacción {} guardada correctamente para el usuario {}.", savedTransaction.getTransactionId(), request.getUserName());

                rtn = transactionFacade.createResponseSuccess(savedTransaction, CustomResponseStatus.SUCCESS_CREATE);

            }else{

                log.warn("Maximo de transacciones alcanzado para el usuario {}.", request.getUserName());
                rtn = transactionFacade.createResponseError(CustomResponseStatus.MAX_TRANSACTIONS_PER_USER, HttpStatus.CONFLICT);

            }

        }else{

            log.warn("Transaccion Duplicada");
            rtn = transactionFacade.createResponseError(CustomResponseStatus.DUPLICATE, HttpStatus.CONFLICT);

        }

        return rtn;
    }

    public ResponseEntity<TransactionResponse> updateTransaction(UpdateTransactionRequest request) {

        ResponseEntity<TransactionResponse> rtn = null;
        log.info("Actualización transacción: {}", request.getTransactionId());

        // Buscar la transacción existente
        Optional<TransactionEntity> transactionOpt = transactionRepository.findById(request.getTransactionId());
        if (transactionOpt.isPresent()) {

            // Validar y actualizar campos
            TransactionEntity existingTransaction = transactionOpt.get();

            log.debug("Transaccion {} encontrada", existingTransaction.getTransactionId());

            existingTransaction.setTotalAmount(request.getTotalAmount());
            existingTransaction.setTransactionDate(request.getTransactionDate());
            existingTransaction.setCommerce(request.getCommerce().trim().toLowerCase());
            existingTransaction.setUserName(request.getUserName().trim().toLowerCase());

            // Guardar la transacción actualizada
            TransactionEntity updatedTransaction = transactionRepository.save(existingTransaction);

            log.info("Transacción {} actualizada correctamente.", updatedTransaction.getTransactionId());
            rtn = transactionFacade.createResponseSuccess(updatedTransaction, CustomResponseStatus.SUCCESS_UPDATE);

        } else {

            log.warn("Transacción {} no encontrada.", request.getTransactionId());
            rtn = transactionFacade.createResponseError(CustomResponseStatus.NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return rtn;
    }


    public ResponseEntity<TransactionResponse> deleteTransaction(String transactionId) {

        ResponseEntity<TransactionResponse> rtn = null;
        transactionId = transactionId.trim();
        if (transactionRepository.existsById(aux.parseTransactionId(transactionId))) {

            log.info("Eliminando Transaccion {}...", transactionId);
            transactionRepository.deleteById(aux.parseTransactionId(transactionId));
            log.info("Transaccion {} eliminada correctamente.", transactionId);

            rtn = transactionFacade.createResponseSuccessDelete(CustomResponseStatus.SUCCESS_DELETE);

        } else {
            log.warn("Transaccion {} no encontrada.", transactionId);
            rtn = transactionFacade.createResponseError(CustomResponseStatus.NOT_FOUND, HttpStatus.NOT_FOUND);

        }

        return rtn;

    }


}