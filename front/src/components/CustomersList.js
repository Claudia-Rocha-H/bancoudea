import React, { useState, useEffect } from 'react';
import api from '../api';

/**
 * Componente para mostrar la lista de clientes bancarios
 */
const CustomersList = () => {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchCustomers();
  }, []);

  const fetchCustomers = async () => {
    try {
      setLoading(true);
      const response = await api.get('/customers');
      setCustomers(response.data);
      setError(null);
    } catch (err) {
      setError('Error al cargar los clientes: ' + (err.response?.data?.error || err.message));
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Cargando clientes...</div>;
  }

  if (error) {
    return <div className="error">Error: {error}</div>;
  }

  return (
    <div className="customers-list">
      <h2>Lista de Clientes</h2>
      
      <table className="customers-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Número de Cuenta</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Saldo</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.id}>
              <td>{customer.id}</td>
              <td>{customer.accountNumber}</td>
              <td>{customer.firstName}</td>
              <td>{customer.lastName}</td>
              <td>${customer.balance?.toFixed(2) || '0.00'}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      {customers.length === 0 && (
        <p>No hay clientes registrados.</p>
      )}
    </div>
  );
};

export default CustomersList;
