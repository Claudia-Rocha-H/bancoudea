import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import api from '../api';

/**
 * Componente para mostrar el historial de transacciones por cuenta
 */
const TransactionsTable = () => {
  const { accountNumber } = useParams();
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [searchAccount, setSearchAccount] = useState(accountNumber || '');

  useEffect(() => {
    if (accountNumber) {
      fetchTransactions(accountNumber);
    }
  }, [accountNumber]);

  const fetchTransactions = async (accountNum) => {
    try {
      setLoading(true);
      const response = await api.get(`/transactions/account/${accountNum}`);
      setTransactions(response.data);
      setError(null);
    } catch (err) {
      setError('Error al cargar las transacciones: ' + (err.response?.data?.error || err.message));
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchAccount.trim()) {
      fetchTransactions(searchAccount.trim());
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    try {
      return new Date(dateString).toLocaleString('es-ES');
    } catch {
      return dateString;
    }
  };

  return (
    <div className="transactions-table">
      <h2>Histórico de Transacciones</h2>
      
      <form onSubmit={handleSearch} className="search-form">
        <div className="search-group">
          <input
            type="text"
            value={searchAccount}
            onChange={(e) => setSearchAccount(e.target.value)}
            placeholder="Número de cuenta"
            className="search-input"
          />
          <button type="submit" className="search-btn">
            Buscar
          </button>
        </div>
      </form>

      {loading && (
        <div className="loading">Cargando transacciones...</div>
      )}

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}

      {!loading && !error && transactions.length === 0 && searchAccount === '' && (
        <div className="info-message">
          <p>Ingresa un número de cuenta para buscar el historial de transacciones.</p>
        </div>
      )}

      {transactions.length > 0 ? (
        <table className="transactions-table-content">
          <thead>
            <tr>
              <th>ID</th>
              <th>Cuenta Remitente</th>
              <th>Cuenta Destinatario</th>
              <th>Monto</th>
              <th>Fecha y Hora</th>
            </tr>
          </thead>
          <tbody>
            {transactions.map((transaction) => (
              <tr key={transaction.id}>
                <td>{transaction.id}</td>
                <td>{transaction.senderAccountNumber}</td>
                <td>{transaction.receiverAccountNumber}</td>
                <td>${transaction.amount?.toFixed(2) || '0.00'}</td>
                <td>{formatDate(transaction.timestamp)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        !error && (
          <p>No se encontraron transacciones para esta cuenta.</p>
        )
      )}
    </div>
  );
};

export default TransactionsTable;
