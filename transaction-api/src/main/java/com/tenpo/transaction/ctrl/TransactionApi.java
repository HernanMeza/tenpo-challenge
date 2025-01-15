package com.tenpo.transaction.ctrl;

import com.tenpo.transaction.dto.request.CreateTransactionRequest;
import com.tenpo.transaction.dto.request.UpdateTransactionRequest;
import com.tenpo.transaction.dto.response.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Transaction API", description = "API para gestionar transacciones.")
public interface TransactionApi {

    @Operation(summary = "Obtener transacción", description = "Obtiene los detalles de una transacción por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción encontrada con éxito",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"00\",\"description\":\"Transacción encontrada con éxito\"},\"body\":{\"transactions\":[{\"transactionId\":9,\"totalAmount\":1200,\"commerce\":\"Walmart\",\"userName\":\"janedoe\",\"transactionDate\":\"2025-01-08T11:00:00Z\"}]}}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Solicitud inválida\"}}"))),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"02\",\"description\":\"Transacción no encontrada\"},\"body\":null}"))),
            @ApiResponse(responseCode = "429", description = "Máximo de peticiones alcanzado",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"05\",\"description\":\"Máximo de peticiones por usuario alcanzado\"}}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Error interno del servidor\"}}")))
    })
    @GetMapping("/tenpo/api/v1/transaction")
    ResponseEntity<TransactionResponse> getTransaction(@NotNull @RequestParam(value = "transactionId") String transactionId);

    @Operation(summary = "Obtener transacciones por usuario", description = "Obtiene todas las transacciones asociadas a un usuario por su nombre.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacciones encontradas con éxito",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"00\",\"description\":\"Transacción encontrada con éxito\"},\"body\":{\"transactions\":[{\"transactionId\":1,\"totalAmount\":500,\"commerce\":\"Starbucks\",\"userName\":\"johndoe\",\"transactionDate\":\"2025-01-08T12:30:00Z\"},{\"transactionId\":3,\"totalAmount\":2300,\"commerce\":\"Apple\",\"userName\":\"johndoe\",\"transactionDate\":\"2025-01-08T14:00:00Z\"}]}}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Solicitud inválida\"}}"))),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"02\",\"description\":\"Transacción no encontrada\"},\"body\":null}"))),
            @ApiResponse(responseCode = "429", description = "Máximo de peticiones alcanzado",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"05\",\"description\":\"Máximo de peticiones por usuario alcanzado\"}}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Error interno del servidor\"}}")))
    })
    @GetMapping("/tenpo/api/v1/transaction/tenpista")
    ResponseEntity<TransactionResponse> getTransactionByTenpista(@NotNull @RequestParam(value="userName") String userName);

    @Operation(summary = "Crear transacción", description = "Crea una nueva transacción con los detalles proporcionados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción creada con éxito",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class), examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"00\",\"description\":\"Transacción creada con éxito\"},\"body\":{\"transactions\":[{\"transactionId\":115,\"totalAmount\":1800,\"commerce\":\"Starbucks\",\"userName\":\"marston\",\"transactionDate\":\"2025-01-06T16:13:43Z\"}]}}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Solicitud inválida\"}}"))),
            @ApiResponse(responseCode = "409", description = "Transacción duplicada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"03\",\"description\":\"Transacción Duplicada\"}}"))),
            @ApiResponse(responseCode = "429", description = "Máximo de peticiones alcanzado",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"05\",\"description\":\"Máximo de peticiones por usuario alcanzado\"}}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Error interno del servidor\"}}")))
    })
    @PostMapping("/tenpo/api/v1/transaction")
    ResponseEntity<TransactionResponse> createTransaction(@Parameter(description = "Información de la transacción a crear", required = true)
                                                          @Valid @NotNull @RequestBody CreateTransactionRequest transactionRequest);

    @Operation(summary = "Actualizar transacción", description = "Actualiza los detalles de una transacción existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción actualizada correctamente",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"00\",\"description\":\"Transacción actualizada correctamente\"},\"body\":{\"transactions\":[{\"transactionId\":7,\"totalAmount\":3001,\"commerce\":\"Nike\",\"userName\":\"annsmith\",\"transactionDate\":\"2025-01-06T23:30:00Z\"}]}}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Solicitud inválida\"}}"))),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"02\",\"description\":\"Transacción no encontrada\"},\"body\":null}"))),
            @ApiResponse(responseCode = "429", description = "Máximo de peticiones alcanzado",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"05\",\"description\":\"Máximo de peticiones por usuario alcanzado\"}}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Error interno del servidor\"}}")))
    })
    @PutMapping("/tenpo/api/v1/transaction")
    ResponseEntity<TransactionResponse> updateTransaction(@Parameter(description = "Información de la transacción a actualizar", required = true)@Valid @NotNull @RequestBody UpdateTransactionRequest updateTransactionRequest);

    @Operation(summary = "Eliminar transacción", description = "Elimina una transacción existente por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción eliminada correctamente",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"00\",\"description\":\"Transacción eliminada correctamente\"},\"body\":null}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Solicitud inválida\"}}"))),
            @ApiResponse(responseCode = "404", description = "Transacción no encontrada",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"02\",\"description\":\"Transacción no encontrada\"},\"body\":null}"))),
            @ApiResponse(responseCode = "429", description = "Máximo de peticiones alcanzado",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"05\",\"description\":\"Máximo de peticiones por usuario alcanzado\"}}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\"header\":{\"statusCode\":\"01\",\"description\":\"Error interno del servidor\"}}")))
    })
    @DeleteMapping("/tenpo/api/v1/transaction")
    ResponseEntity<TransactionResponse> deleteTransaction(@NotNull @RequestParam(value = "transactionId") String transactionId);
}


