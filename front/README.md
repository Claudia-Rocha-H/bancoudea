# 🎨 Banco UDEA - Frontend

Frontend del sistema bancario desarrollado con **React 19.1.1**.

[![React](https://img.shields.io/badge/React-19.1.1-blue.svg)](https://reactjs.org/)
[![Node.js](https://img.shields.io/badge/Node.js-18+-green.svg)](https://nodejs.org/)

## 🚀 Inicio Rápido

### Prerrequisitos
- **Node.js 18+**
- **npm** (incluido con Node.js)
- **Backend ejecutándose** en puerto 8080

### Instalación y Ejecución
```bash
# Instalar dependencias
npm install

# Ejecutar en desarrollo
npm start
```

**URL**: http://localhost:3000

## 🏗️ Arquitectura

### Estructura del Proyecto
```
src/
├── components/
│   ├── CustomersList.js      # Lista de clientes
│   ├── TransferForm.js       # Formulario de transferencias
│   └── TransactionsTable.js  # Histórico de transacciones
├── api.js                    # Configuración Axios
├── App.js                    # Componente principal + routing
├── App.css                   # Estilos globales
└── index.js                  # Punto de entrada
```

### Patrones Implementados
- **Componentes Funcionales**: Hooks de React (`useState`, `useEffect`)
- **Routing**: React Router DOM para navegación
- **HTTP Client**: Axios con interceptores
- **Estado Local**: Manejo de estado con `useState`

## 🔧 Componentes

### CustomersList
- **Función**: Mostrar lista de clientes con saldos
- **API**: `GET /api/customers`
- **Características**: Carga automática, manejo de errores

### TransferForm
- **Función**: Realizar transferencias entre cuentas
- **API**: `POST /api/transactions/transfer`
- **Validaciones**: Frontend + backend, mensajes específicos de error

### TransactionsTable
- **Función**: Consultar histórico por número de cuenta
- **API**: `GET /api/transactions/account/{accountNumber}`
- **Características**: Búsqueda por cuenta, formato de fechas

## 🌐 API Integration

### Configuración Axios
```javascript
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: { 'Content-Type': 'application/json' }
});
```

### Endpoints Consumidos
| Método | Endpoint | Componente |
|--------|----------|------------|
| `GET` | `/api/customers` | CustomersList |
| `POST` | `/api/transactions/transfer` | TransferForm |
| `GET` | `/api/transactions/account/{accountNumber}` | TransactionsTable |

### Manejo de Errores
- **Interceptores Axios**: Logging de requests/responses
- **Mensajes específicos**: Diferentes errores del backend
- **Estados de carga**: Loading states en formularios


## 🛠️ Stack Tecnológico

### Core
- **React 19.1.1** - Biblioteca principal
- **React Router DOM 7.9.3** - Enrutamiento
- **Axios 1.12.2** - Cliente HTTP

### Herramientas
- **Create React App** - Configuración base
- **npm** - Gestión de dependencias

## 🔧 Configuración

### Variables de Entorno
```bash
# .env (opcional)
REACT_APP_API_URL=http://localhost:8080/api
```

### CORS
El backend está configurado para aceptar requests desde:
```
http://localhost:3000
```

## 📱 Navegación

| Ruta | Componente | Descripción |
|------|------------|-------------|
| `/` | CustomersList | Lista de clientes |
| `/transfer` | TransferForm | Realizar transferencias |
| `/transactions` | TransactionsTable | Histórico de transacciones |

## 🔍 Debugging

### Errores Comunes
- **CORS**: Verificar que backend esté en puerto 8080
- **Network Error**: Backend no ejecutándose
- **404**: Endpoint incorrecto o backend no disponible

---

**Ver [README principal](../../README.md) para información general del proyecto.**