package com.tenpo.transaction.ctrl.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tenpo.transaction.ctrl.TransactionApiCtrl;
import com.tenpo.transaction.dto.request.CreateTransactionRequest;
import com.tenpo.transaction.dto.request.UpdateTransactionRequest;
import com.tenpo.transaction.dto.response.BodyData;
import com.tenpo.transaction.dto.response.Header;
import com.tenpo.transaction.dto.response.Transaction;
import com.tenpo.transaction.dto.response.TransactionResponse;
import com.tenpo.transaction.enums.CustomResponseStatus;
import com.tenpo.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TransactionApiCtrlIntegrationTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionApiCtrl transactionApiCtrl;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionApiCtrl).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    public void testGetTransaction() throws Exception {

        String transactionId = "3";

        Transaction[] transactions = new Transaction[1];
        transactions[0] = Transaction.builder().transactionId(3)
                .totalAmount(2300)
                .commerce("apple")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-08T14:00:00Z"))
                .build();

        TransactionResponse mockResponse = TransactionResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.SUCCESS_CREATE.getCode())
                        .description(CustomResponseStatus.SUCCESS_CREATE.getDescription())
                        .build())
                .body(BodyData.builder().transactions(transactions).build()).build();

        long timestamp = Instant.parse("2025-01-08T14:00:00Z").getEpochSecond();

        when(transactionService.getTransaction(transactionId)).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(get("/tenpo/api/v1/transaction")
                        .param("transactionId", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.statusCode").value("00"))
                .andExpect(jsonPath("$.header.description").value(CustomResponseStatus.SUCCESS_CREATE.getDescription()))
                .andExpect(jsonPath("$.body.transactions[0].transactionId").value(3))
                .andExpect(jsonPath("$.body.transactions[0].totalAmount").value(2300))
                .andExpect(jsonPath("$.body.transactions[0].commerce").value("apple"))
                .andExpect(jsonPath("$.body.transactions[0].userName").value("johndoe"))
                .andExpect(jsonPath("$.body.transactions[0].transactionDate").value(timestamp));
    }

    @Test
    public void testGetTransactionByTenpista() throws Exception {
        // Datos de entrada
        String userName = "johndoe";

        // Crear transacciones mock
        Transaction[] transactions = new Transaction[2];
        transactions[0] = Transaction.builder()
                .transactionId(1)
                .totalAmount(500)
                .commerce("starbucks")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-08T12:30:00Z"))
                .build();
        transactions[1] = Transaction.builder()
                .transactionId(6)
                .totalAmount(400)
                .commerce("mcdonalds")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-07T18:00:00Z"))
                .build();

        // Crear respuesta mock
        TransactionResponse mockResponse = TransactionResponse.builder()
                .header(Header.builder()
                        .statusCode("00")
                        .description("Transacción encontrada con éxito")
                        .build())
                .body(BodyData.builder().transactions(transactions).build())
                .build();

        // Definir el comportamiento del servicio mockeado
        when(transactionService.getTransactionByTenpista(userName)).thenReturn(ResponseEntity.ok(mockResponse));

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/tenpo/api/v1/transaction/tenpista").param("userName", userName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.statusCode").value("00"))
                .andExpect(jsonPath("$.header.description").value("Transacción encontrada con éxito"))
                .andExpect(jsonPath("$.body.transactions[0].transactionId").value(1))
                .andExpect(jsonPath("$.body.transactions[0].totalAmount").value(500))
                .andExpect(jsonPath("$.body.transactions[0].commerce").value("starbucks"))
                .andExpect(jsonPath("$.body.transactions[0].userName").value("johndoe"))
                .andExpect(jsonPath("$.body.transactions[0].transactionDate").value(Instant.parse("2025-01-08T12:30:00Z").getEpochSecond()))
                .andExpect(jsonPath("$.body.transactions[1].transactionId").value(6))
                .andExpect(jsonPath("$.body.transactions[1].totalAmount").value(400))
                .andExpect(jsonPath("$.body.transactions[1].commerce").value("mcdonalds"))
                .andExpect(jsonPath("$.body.transactions[1].userName").value("johndoe"))
                .andExpect(jsonPath("$.body.transactions[1].transactionDate").value(Instant.parse("2025-01-07T18:00:00z").getEpochSecond()));
    }


    @Test
    public void testCreateTransaction() throws Exception {

        UpdateTransactionRequest transactionRequest = UpdateTransactionRequest.builder()
                .transactionId(3)
                .totalAmount(2300)
                .commerce("apple")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-08T14:00:00Z"))
                .build();

        long timestamp = Instant.parse("2025-01-08T14:00:00Z").getEpochSecond();

        String json =  objectMapper.writeValueAsString(transactionRequest);

        Transaction[] transactions = new Transaction[1];
        transactions[0] = Transaction.builder().transactionId(3)
                .totalAmount(2300)
                .commerce("apple")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-08T14:00:00Z"))
                .build();

        TransactionResponse mockResponse = TransactionResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.SUCCESS_CREATE.getCode())
                        .description(CustomResponseStatus.SUCCESS_CREATE.getDescription())
                        .build())
                .body(BodyData.builder()
                        .transactions(transactions)
                        .build())
                .build();

        when(transactionService.createTransaction(any(CreateTransactionRequest.class))).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(post("/tenpo/api/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))  // `asJsonString` es un helper para convertir el objeto a JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.statusCode").value("00"))
                .andExpect(jsonPath("$.header.description").value(CustomResponseStatus.SUCCESS_CREATE.getDescription()))
                .andExpect(jsonPath("$.body.transactions[0].transactionId").value(3))
                .andExpect(jsonPath("$.body.transactions[0].totalAmount").value(2300))
                .andExpect(jsonPath("$.body.transactions[0].commerce").value("apple"))
                .andExpect(jsonPath("$.body.transactions[0].userName").value("johndoe"))
                .andExpect(jsonPath("$.body.transactions[0].transactionDate").value(timestamp));  // Verifica el timestamp
    }

    @Test
    public void testUpdateTransaction() throws Exception {

        UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
                .transactionId(3)
                .totalAmount(2500)
                .commerce("apple")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-08T14:00:00Z"))
                .build();

        String json =  objectMapper.writeValueAsString(updateTransactionRequest);

        Transaction[] transactions = new Transaction[1];
        transactions[0] = Transaction.builder().transactionId(3)
                .totalAmount(2300)
                .commerce("apple")
                .userName("johndoe")
                .transactionDate(Instant.parse("2025-01-08T14:00:00Z"))
                .build();

        TransactionResponse mockResponse = TransactionResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.SUCCESS_UPDATE.getCode())
                        .description(CustomResponseStatus.SUCCESS_UPDATE.getDescription())
                        .build())
                .body(BodyData.builder()
                        .transactions(transactions)
                        .build())
                .build();

        long timestamp = Instant.parse("2025-01-08T14:00:00Z").getEpochSecond();

        when(transactionService.updateTransaction(any(UpdateTransactionRequest.class))).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(put("/tenpo/api/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))  // `asJsonString` es un helper para convertir el objeto a JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.statusCode").value("00"))
                .andExpect(jsonPath("$.header.description").value(CustomResponseStatus.SUCCESS_UPDATE.getDescription()))
                .andExpect(jsonPath("$.body.transactions[0].transactionId").value(3))
                .andExpect(jsonPath("$.body.transactions[0].totalAmount").value(2300))
                .andExpect(jsonPath("$.body.transactions[0].commerce").value("apple"))
                .andExpect(jsonPath("$.body.transactions[0].userName").value("johndoe"))
                .andExpect(jsonPath("$.body.transactions[0].transactionDate").value(timestamp));  // Verifica el timestamp
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        String transactionId = "3";

        TransactionResponse mockResponse = TransactionResponse.builder()
                .header(Header.builder()
                        .statusCode(CustomResponseStatus.SUCCESS_DELETE.getCode())
                        .description(CustomResponseStatus.SUCCESS_DELETE.getDescription())
                        .build())
                .build();

        when(transactionService.deleteTransaction(transactionId)).thenReturn(ResponseEntity.ok(mockResponse));

        mockMvc.perform(delete("/tenpo/api/v1/transaction")
                        .param("transactionId", transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.statusCode").value(CustomResponseStatus.SUCCESS_DELETE.getCode()))
                .andExpect(jsonPath("$.header.description").value(CustomResponseStatus.SUCCESS_DELETE.getDescription()));  // Verifica el timestamp
    }


}