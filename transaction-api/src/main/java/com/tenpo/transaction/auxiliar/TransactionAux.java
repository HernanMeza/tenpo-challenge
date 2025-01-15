package com.tenpo.transaction.auxiliar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionAux {

    public Integer parseTransactionId(String transactionId) {
        Integer rtn = null;

        try {

            rtn = Integer.valueOf(transactionId);

        } catch (NumberFormatException e) {
            log.error("ID de transacción no válido: {}", transactionId, e);
        }

        return rtn;
    }
}
