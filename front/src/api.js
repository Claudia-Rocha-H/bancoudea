import axios from 'axios';

/**
 * Configuración de Axios para comunicación con el backend
 */
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    console.log('Realizando petición a:', config.url);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    console.log('Respuesta recibida:', response.status);
    return response;
  },
  (error) => {
    console.error('Error de API:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export default api;
