# ¡Bienvenidos al Tenpo Challenge! 🚀

¡Hola Equipo Tenpo! Mi nombre es Hernán Meza y este es mi resultado para el challenge, este repositorio contiene la implementación de una aplicación diseñada para gestionar transacciones de clientes. A continuación, encontrarán toda la información necesaria para ejecutar, explorar y evaluar este proyecto. 🛠️

¡Quién sabe! Quizás esta API guste tanto que terminemos integrándola en algún lado. 😎

## 🚀 **Inicia el proyecto en pocos pasos**

1. Clona el repositorio en tu máquina local:

   ```bash
   git clone https://github.com/HernanMeza/tenpo-challenge
   cd tenpo-challenge
   ```

2. Levanta la aplicación con Docker:

   ```bash
   docker-compose up --build
   ```

3. Una vez que los contenedores estén corriendo, accede a la aplicación a través de:
   [http://localhost:3000/](http://localhost:3000/)

4. También puedes obtener la imagen directamente desde DockerHub:
   ```bash
   docker pull neuralgoods/tenpo-frontend:latest
   docker pull neuralgoods/transaction-api:latest
   ```

## 🩻 **Anatomia del Proyecto**

```plainText
     \tenpo-challenge               <---- Repositorio
     ├── docker-compose.yml         <---- Orquestador de Docker
     ├── README.md                  <---- Estás leyendo aqui
     ├── tenpo-transaction-frt      <---- Proyecto de Front End React
     │       ├── Dockerfile
     └── transaction-api            <---- API de Back End Springboot
             └── Dockerfile

```

## 🧑‍💻 **Tecnologías Utilizadas**

- **Frontend:** React con JSX
- **Backend:** Java 17 con Spring Boot 3.4.1
- **Base de Datos:** PostgreSQL
- **Contenedores:** Docker y Docker Compose

## 📖 **Dependencias Clave**

- **Spring Boot**: Web, JPA, Validation
- **Lombok**: Para evitar código repetitivo
- **Flyway**: Gestión de migraciones de base de datos para el despliegue del esquema y tabla
- **Swagger**: Documentación de la API
- **Bucket4j**: Depencia para implementacion del Rate Limiting

## 🔍 **Explora la API con cURL**

Aquí tienes algunos ejemplos de cómo interactuar con los endpoints principales:

### 📝 OBTENER TRANSACCIÓN POR ID DE TRANSACCIÓN

```bash
curl --location 'http://localhost:8080/tenpo/api/v1/transaction?transactionId=11'
```

### 🔍 OBTENER TRANSACCIÓN POR NOMBRE DE TENPISTA

```bash
curl --location 'http://localhost:8080/tenpo/api/v1/transaction/tenpista/userName=alisonmandel'
```

### ➕ CREAR TRANSACCIÓN

```bash
curl --location 'http://localhost:8080/tenpo/api/v1/transaction' \
--header 'Content-Type: application/json' \
--data '{
  "totalAmount": 35000,
  "commerce": "neuralgoods-tech",
  "userName": "hmezariv",
  "transactionDate": "2025-01-11T12:05:16Z"
}'
```

### ✏️ ACTUALIZAR TRANSACCIÓN

```bash
curl --location --request PUT 'http://localhost:8080/tenpo/api/v1/transaction' \
--header 'Content-Type: application/json' \
--data '{
  "transactionId": 11,
  "totalAmount": 35000,
  "commerce": "neuralgoods-tech",
  "userName": "hmezariv",
  "transactionDate": "2025-01-08T12:03:00Z"
}'
```

### ❌ ELIMINAR TRANSACCIÓN

```bash
curl --location --request DELETE 'http://localhost:8080/tenpo/api/v1/transaction?transactionId=11'
```

## 📚 **Documentación Adicional**

Para más detalles sobre los endpoints disponibles, tipos de respuesta y cómo usarlos, accede a la documentación generada vìa Swagger en:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

¡Gracias por revisar este proyecto Tenpista! Espero que disfruten explorándolo tanto como yo disfruté el reto de desarrollarlo. 💻😊
