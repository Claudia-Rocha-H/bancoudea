# 🏦 Banco UDEA - Sistema de Gestión Bancaria

Sistema completo de gestión bancaria desarrollado con **Spring Boot** (backend) y **React** (frontend). Permite realizar transferencias entre cuentas bancarias y consultar el historial de transacciones.

[![Java](https://img.shields.io/badge/Java-24-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.1.1-blue.svg)](https://reactjs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue.svg)](https://www.mysql.com/)

## 📁 Estructura del Proyecto

```
bancoudea/
├── back/                     # Backend (Spring Boot)
│   ├── src/main/java/com/udea/bancoudea/
│   │   ├── controller/       # Controladores REST
│   │   ├── service/          # Lógica de negocio
│   │   ├── repository/       # Repositorios JPA
│   │   ├── entity/           # Entidades JPA
│   │   ├── DTO/             # Data Transfer Objects
│   │   └── mapper/          # Mappers MapStruct
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── src/test/            # Pruebas unitarias e integración
│   ├── pom.xml              # Dependencias Maven
│   └── README.md            # Documentación backend
├── front/                   # Frontend (React)
│   ├── src/
│   │   ├── components/      # Componentes React
│   │   ├── api.js          # Configuración Axios
│   │   ├── App.js          # Componente principal
│   │   └── App.css         # Estilos
│   ├── package.json        # Dependencias npm
│   └── README.md           # Documentación frontend
└── README.md               # Este archivo
```

## ✨ Características Principales

### 🔒 Seguridad y Concurrencia
- **Transferencias Atómicas**: Uso de `@Transactional` con bloqueo pesimista
- **Validaciones Robustas**: Saldo suficiente, cuentas válidas, montos positivos
- **Manejo de Errores**: Respuestas HTTP apropiadas con `@ControllerAdvice`

### 🌐 API REST
- `POST /api/transactions/transfer` - Realizar transferencias
- `GET /api/transactions/account/{accountNumber}` - Histórico de transacciones
- `GET /api/customers` - Gestión de clientes
- **Documentación**: Swagger/OpenAPI disponible en `/swagger-ui.html`

### 🎨 Interfaz de Usuario
- **3 Vistas Principales**: Clientes, Transferencias, Histórico


## 🚀 Inicio Rápido

### 📋 Prerrequisitos
- **Java 24+**
- **Node.js 18+**
- **MySQL 8.0+**
- **Maven 3.6+** (incluido como wrapper)

### 🗄️ Base de Datos
```sql
CREATE DATABASE bancoudea;
USE bancoudea;
```

### ⚙️ Backend (Spring Boot)
```bash
cd back/
./mvnw spring-boot:run
```
**URL**: http://localhost:8080  
**Swagger**: http://localhost:8080/swagger-ui.html

### 🎨 Frontend (React)
```bash
cd front/
npm install
npm start
```
**URL**: http://localhost:3000

## 🧪 Pruebas

### Ejecutar Pruebas del Backend
```bash
cd back/
./mvnw test
```

### Pruebas de la API
```bash
# Crear cliente
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{"accountNumber":"123456789","firstName":"Juan","lastName":"Pérez","balance":1000.0}'

# Realizar transferencia
curl -X POST http://localhost:8080/api/transactions/transfer \
  -H "Content-Type: application/json" \
  -d '{"senderAccountNumber":"123456789","receiverAccountNumber":"987654321","amount":150.50}'

# Consultar histórico
curl -X GET http://localhost:8080/api/transactions/account/123456789
```

### Pruebas del Frontend
1. Navegar a http://localhost:3000
2. Verificar las 3 vistas principales
3. Probar transferencias y validaciones
4. Consultar histórico de transacciones

## 🛠️ Stack Tecnológico

### Backend
- **Spring Boot 3.5.0** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **MySQL 8.0+** - Base de datos
- **MapStruct** - Mapeo de objetos
- **Swagger/OpenAPI** - Documentación de API
- **Maven** - Gestión de dependencias

### Frontend
- **React 19.1.1** - Biblioteca de UI
- **React Router DOM** - Enrutamiento
- **Axios** - Cliente HTTP
- **CSS3** - Estilos

## 📋 Reglas de Negocio

- **Atomicidad**: Transferencias completamente atómicas
- **Concurrencia**: Bloqueo pesimista para evitar condiciones de carrera
- **Validaciones**: Monto > 0, cuentas existentes, saldo suficiente
- **Auditoría**: Registro completo de transacciones con timestamp

## 🔧 Configuración

### Base de Datos
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/bancoudea
spring.datasource.username=root
spring.datasource.password=tu_password
```

### CORS
```java
@CrossOrigin(origins = "http://localhost:3000")
```

## 📚 Documentación Adicional

- [Backend README](back/README.md) - Documentación detallada del backend
- [Frontend README](front/README.md) - Documentación del frontend
- [Swagger UI](http://localhost:8080/swagger-ui.html) - Documentación interactiva de la API


