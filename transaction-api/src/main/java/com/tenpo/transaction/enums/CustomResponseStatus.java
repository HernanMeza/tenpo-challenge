package com.tenpo.transaction.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum CustomResponseStatus {

    SUCCESS_FOUND("00", "Transacción encontrada con éxito"),
    SUCCESS_CREATE("00", "Transacción creada con éxito"),
    SUCCESS_UPDATE("00", "Transacción actualizada correctamente"),
    SUCCESS_DELETE("00", "Transacción eliminada correctamente"),
    ERROR("01", "Error durante la ejecución"),
    NOT_FOUND("02", "Transacción no encontrada"),
    DUPLICATE("03", "Transacción Duplicada"),
    MAX_TRANSACTIONS_PER_USER("04", "Máximo de transacciones por usuario en DB alcanzado"),
    MAX_PETITIONS_PER_USER("05", "Máximo de peticiones por usuario alcanzado"),
    WRONG_PETITION("06", "Error en la peticion");

    private final String code;
    private final String description;

}
