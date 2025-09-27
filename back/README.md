# 🏦 Banco UDEA - Backend

Backend del sistema bancario desarrollado con **Spring Boot 3.5.0** y **Java 24**.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-24-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)

## 🚀 Inicio Rápido

### Prerrequisitos
- **Java 24+**
- **Maven 3.6+** (incluido como wrapper)
- **MySQL 8.0+**

### Configuración
1. **Base de datos**:
   ```sql
   CREATE DATABASE bancoudea;
   ```

2. **Aplicación** - Editar `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bancoudea?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=tu_password
   ```

3. **Ejecutar**:
   ```bash
   ./mvnw spring-boot:run
   ```

**URLs**: http://localhost:8080 | **Swagger**: http://localhost:8080/swagger-ui.html

## 🔧 API Endpoints

### Clientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/customers` | Listar todos los clientes |
| `GET` | `/api/customers/{id}` | Obtener cliente por ID |
| `POST` | `/api/customers` | Crear nuevo cliente |

### Transacciones
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/transactions/transfer` | Realizar transferencia |
| `GET` | `/api/transactions/account/{accountNumber}` | Histórico por cuenta |

## 🧪 Pruebas

### Ejecutar Pruebas
```bash
# Todas las pruebas
./mvnw test

# Por categoría
./mvnw test -Dtest="*ServiceTest"        # Unitarias
./mvnw test -Dtest="*ControllerTest"     # Controladores
./mvnw test -Dtest="*IntegrationTest"    # Integración
```

### Tipos de Pruebas
- **Unitarias**: Lógica de negocio (`TransactionService`, `CustomerService`)
- **Controlador**: Endpoints REST (`TransactionController`, `CustomerController`)
- **Integración**: Base de datos (`BankingSystemIntegrationTest`)
- **Aplicación**: Carga del contexto (`BancoudeaApplicationTests`)

**Configuración**: H2 en memoria, perfil `test`

## 🏗️ Arquitectura

### Patrones Implementados
- **MVC**: `@RestController` + Entidades JPA + DTOs
- **Repository**: `JpaRepository` con métodos personalizados
- **Service**: Lógica de negocio con `@Transactional`
- **DTO**: Transferencia de datos entre capas

### Seguridad y Concurrencia
```java
@Transactional(propagation = Propagation.REQUIRED)
@Lock(LockModeType.PESSIMISTIC_WRITE)
public TransactionDTO transferMoney(TransferRequestDTO request) {
    // Operación atómica con bloqueo pesimista
}
```

### Validaciones de Negocio
- Monto > 0
- Cuentas existentes
- Saldo suficiente
- Cuentas diferentes

## 🛠️ Stack Tecnológico

### Core
- **Spring Boot 3.5.0** - Framework principal
- **Spring Data JPA** - Persistencia
- **Spring Web** - API REST
- **Spring Transaction** - Gestión transaccional

### Base de Datos
- **MySQL 8.0+** - Producción
- **H2** - Pruebas

### Utilidades
- **MapStruct** - Mapeo de objetos
- **Swagger/OpenAPI** - Documentación API

### Testing
- **JUnit 5** - Framework de pruebas
- **Mockito** - Mocking
- **Spring Boot Test** - Integración


## 📚 Documentación
- [Swagger/OpenAPI](https://swagger.io/docs/)

---

**Ver [README principal](../../README.md) para información general del proyecto.**