package com.tenpo.transaction.ctrl;

import com.tenpo.transaction.enums.CustomResponseStatus;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionApiCtrlTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    public void testGetTransaction() {

        given()
                .queryParam("transactionId", "3")
                .when()
                .get("tenpo/api/v1/transaction")
                .then()
                .statusCode(200)
                .body("header.statusCode", equalTo(CustomResponseStatus.SUCCESS_FOUND.getCode()))
                .body("header.description", equalTo(CustomResponseStatus.SUCCESS_FOUND.getDescription()))
                .body("body.transactions[0].transactionId", equalTo(3))
                .body("body.transactions[0].totalAmount", equalTo(2300))
                .body("body.transactions[0].commerce", equalTo("apple"))
                .body("body.transactions[0].userName", equalTo("johndoe"))
                .body("body.transactions[0].transactionDate", equalTo("2025-01-08T14:00:00Z")); // Verifica el timestamp
    }

    @Test
    @Order(2)
    public void testGetTransactionByTenpista() {

        String userName = "johndoe";

        given()
                .queryParam("userName", userName) // Establecer el parámetro de la consulta
                .when()
                .get("/tenpo/api/v1/transaction/tenpista") // Realizar la solicitud GET
                .then()
                .statusCode(200) // Verificar que el código de estado sea 200
                .body("header.statusCode", equalTo("00")) // Verificar el código de estado de la respuesta
                .body("header.description", equalTo("Transacción encontrada con éxito")) // Verificar la descripción
                .body("body.transactions[0].transactionId", equalTo(1)) // Verificar el transactionId de la primera transacción
                .body("body.transactions[0].totalAmount", equalTo(500)) // Verificar el totalAmount de la primera transacción
                .body("body.transactions[0].commerce", equalTo("starbucks")) // Verificar el comercio de la primera transacción
                .body("body.transactions[0].userName", equalTo("johndoe")) // Verificar el userName de la primera transacción
                .body("body.transactions[0].transactionDate", equalTo("2025-01-08T12:30:00Z")) // Verificar la fecha de la primera transacción
                .body("body.transactions[2].transactionId", equalTo(6)) // Verificar el transactionId de la segunda transacción
                .body("body.transactions[2].totalAmount", equalTo(400)) // Verificar el totalAmount de la segunda transacción
                .body("body.transactions[2].commerce", equalTo("mcdonalds")) // Verificar el comercio de la segunda transacción
                .body("body.transactions[2].userName", equalTo("johndoe")) // Verificar el userName de la segunda transacción
                .body("body.transactions[2].transactionDate", equalTo("2025-01-07T18:00:00Z")); // Verificar la fecha de la segunda transacción
    }

    @Test
    @Order(3)
    public void testUpdateTransaction() {
        String requestBody = """
        {
            "transactionId": 3,
            "totalAmount": 2500,
            "commerce": "neuralgoods-food",
            "userName": "hmezariv",
            "transactionDate": "2025-01-09T14:00:00Z"
        }
        """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("tenpo/api/v1/transaction")
                .then()
                .statusCode(200)
                .body("header.statusCode", equalTo(CustomResponseStatus.SUCCESS_UPDATE.getCode()))
                .body("header.description", equalTo(CustomResponseStatus.SUCCESS_UPDATE.getDescription()))
                .body("body.transactions[0].transactionId", equalTo(3))
                .body("body.transactions[0].totalAmount", equalTo(2500))
                .body("body.transactions[0].commerce", equalTo("neuralgoods-food"))
                .body("body.transactions[0].userName", equalTo("hmezariv"))
                .body("body.transactions[0].transactionDate", equalTo("2025-01-09T14:00:00Z")); // Verifica el timestamp
    }

    @Test
    @Order(4)
    public void testDeleteTransaction() {
        given()
                .queryParam("transactionId", "3")
                .when()
                .delete("tenpo/api/v1/transaction")
                .then()
                .statusCode(200)
                .body("header.statusCode", equalTo(CustomResponseStatus.SUCCESS_DELETE.getCode()))
                .body("header.description", equalTo(CustomResponseStatus.SUCCESS_DELETE.getDescription()));
    }

    @Test
    @Order(5)
    public void testCreateTransaction() {
        String requestBody = """
        {
            "totalAmount": 35000,
            "commerce": "neuralgoods-tech",
            "userName": "hmezariv",
            "transactionDate": "2025-01-10T14:00:00Z"
        }
        """;
//        long timestamp = Instant.parse("2025-01-08T14:00:00Z").getEpochSecond();

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("tenpo/api/v1/transaction")
                .then()
                .statusCode(200)
                .body("header.statusCode", equalTo(CustomResponseStatus.SUCCESS_CREATE.getCode()))
                .body("header.description", equalTo(CustomResponseStatus.SUCCESS_CREATE.getDescription()))
                .body("body.transactions[0].totalAmount", equalTo(35000))
                .body("body.transactions[0].commerce", equalTo("neuralgoods-tech"))
                .body("body.transactions[0].userName", equalTo("hmezariv"))
                .body("body.transactions[0].transactionDate", equalTo("2025-01-10T14:00:00Z")); // Verifica el timestamp
    }

}
