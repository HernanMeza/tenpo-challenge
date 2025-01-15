package com.tenpo.transaction.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tenpo Challenge FullStack")
                        .description(" ## ¡Hola Equipo Tenpo, bienvenidos a la *Transaction API*! 🚀  \n\n" +
                                "Una **API propuesta a modo de challenge** para la **gestión de transacciones de los verdaderos Tenpistas**.   \n\n" +
                                "Esta API está diseñada para permitir la **creación**, **obtención**, **actualización** y **eliminación** de transacciones de clientes de manera rápida. Ya sea que necesites: \n" +
                                "- Registrar una nueva **transacción**, \n" +
                                "- Consultar el historial de **transacciones**, \n" +
                                "- Actualizar detalles,  \n" +
                                "- O eliminar registros,  \n\n" +
                                "**¿Eso suena bien verdad?** ¡Esta API te ofrece métodos necesarios para interactuar con los datos de manera sencilla! 🔥  \n\n" +
                                "**¡Quién sabe! Quizás esta API guste tanto que terminemos integrándola en algún lado.** 😎  \n\n" +
                                "A todo esto; quizás les gustaría saber qué tecnologías se utilizaron para **crear** y **usar** esta API, aquí va un listado:  \n\n" +
                                "### **Tecnologías:**   \n" +
                                "- **Java 17** ⚙️   \n" +
                                "- **Spring Boot 3.4.1** 🔥   \n" +
                                "- **PostgreSQL** 🗃️  \n\n" +
                                "---  \n\n" +
                                "### **Dependencias:**  \n\n" +
                                "- **spring-boot-starter-web**:        Permite construir aplicaciones web RESTful, facilitando la creación de endpoints para la API.  \n" +
                                "- **spring-boot-starter-data-jpa**: Para integrar con JPA y gestionar el acceso a datos de manera eficiente, simplificando las interacciones con la base de datos.  \n" +
                                "- **spring-boot-starter-validation**: Usado para la validación automática de los datos de request en los endpoints.  \n" +
                                "- **postgresql**: Controlador JDBC para conectar la aplicación a la base de datos PostgreSQL.  \n" +
                                "- **lombok**: Al día de hoy, ya un verdadero clásico moderno. Para reducir el código boilerplate, generando automáticamente constructores, getters, setters y otros métodos comunes, entre nos... me encanta usar Builder y también dejar el Autowired lo más de lado posible, ¡constructores all the way!.  \n" +
                                "- **spring-boot-starter-test**: Las herramientas para realizar pruebas unitarias y de integración.  \n" +
                                "- **flyway-core**: Utilizado para el control de versiones de la base de datos, si bien su uso está más pensado para migraciones, facilita muchísimo la creación de esquema, tablas y secuencias.  \n" +
                                "- **flyway-database-postgresql**: Extensión de PostgreSQL para Flyway.  \n" +
                                "- **rest-assured**: Herramienta para realizar pruebas automáticas de integración de APIs RESTful, asegurando que los endpoints funcionan correctamente bajo diversas condiciones.  \n" +
                                "- **bucket4j-core**: Implementación de limitación de tasa (rate-limiting) para controlar el número de peticiones a los endpoints, asegurando que la API sea escalable y esté protegida contra abusos.  \n" +
                                "- **swagger-core**: Se utiliza para habilitar la documentación de la API, así facilitamos la creación de especificaciones y la visualización de los endpoints y las estructuras de request y response; **por cierto, este html lo generó Swagger ¡Insolito!** 🤩.  \n\n" +
                                "---  \n\n" +
                                "Este conjunto de tecnologías y dependencias ha sido seleccionado para garantizar una **API escalable** y **fácil de mantener**. 😄")

                        .termsOfService("")
                        .version("1.0.0")
                        .license(new License()
                                .name("")
                                .url("http://unlicense.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("")));
    }

}
