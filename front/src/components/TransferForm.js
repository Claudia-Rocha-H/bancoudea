import React, { useState } from 'react';
import api from '../api';

/**
 * Componente para realizar transferencias entre cuentas bancarias
 */
const TransferForm = () => {
  const [formData, setFormData] = useState({
    senderAccountNumber: '',
    receiverAccountNumber: '',
    amount: ''
  });
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    setMessage('');
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.senderAccountNumber || !formData.receiverAccountNumber || !formData.amount) {
      setError('Todos los campos son obligatorios');
      return;
    }

    if (parseFloat(formData.amount) <= 0) {
      setError('El monto debe ser mayor a 0');
      return;
    }

    if (formData.senderAccountNumber === formData.receiverAccountNumber) {
      setError('El remitente y destinatario no pueden ser la misma cuenta');
      return;
    }

    try {
      setLoading(true);
      setError('');
      
      const response = await api.post('/transactions/transfer', {
        senderAccountNumber: formData.senderAccountNumber,
        receiverAccountNumber: formData.receiverAccountNumber,
        amount: parseFloat(formData.amount)
      });

      setMessage(`✅ Transferencia exitosa! 
      • ID de transacción: ${response.data.id}
      • Monto: $${response.data.amount?.toFixed(2) || '0.00'}
      • De: ${response.data.senderAccountNumber}
      • Para: ${response.data.receiverAccountNumber}
      • Fecha: ${new Date(response.data.timestamp).toLocaleString('es-ES')}`);
      
      setFormData({
        senderAccountNumber: '',
        receiverAccountNumber: '',
        amount: ''
      });
      
    } catch (err) {
      let errorMessage = 'Error desconocido';
      
      if (err.response?.data?.error) {
        const backendError = err.response.data.error;
        
        if (backendError.includes('Insufficient funds')) {
          errorMessage = '❌ Saldo insuficiente. No tienes fondos suficientes para realizar esta transferencia.';
        } else if (backendError.includes('Sender account not found')) {
          errorMessage = '❌ Cuenta remitente no encontrada. Verifica el número de cuenta.';
        } else if (backendError.includes('Receiver account not found')) {
          errorMessage = '❌ Cuenta destinatario no encontrada. Verifica el número de cuenta.';
        } else if (backendError.includes('Sender and receiver cannot be the same account')) {
          errorMessage = '❌ No puedes transferir a la misma cuenta.';
        } else if (backendError.includes('Amount must be greater than 0')) {
          errorMessage = '❌ El monto debe ser mayor a 0.';
        } else if (backendError.includes('Sender account number cannot be null or empty')) {
          errorMessage = '❌ El número de cuenta remitente es obligatorio.';
        } else if (backendError.includes('Receiver account number cannot be null or empty')) {
          errorMessage = '❌ El número de cuenta destinatario es obligatorio.';
        } else {
          errorMessage = `❌ ${backendError}`;
        }
      } else if (err.response?.status === 400) {
        errorMessage = '❌ Datos inválidos. Verifica la información ingresada.';
      } else if (err.response?.status === 404) {
        errorMessage = '❌ Cuenta no encontrada. Verifica los números de cuenta.';
      } else if (err.response?.status === 500) {
        errorMessage = '❌ Error interno del servidor. Intenta nuevamente.';
      } else if (err.message === 'Network Error') {
        errorMessage = '❌ Error de conexión. Verifica que el servidor esté funcionando.';
      } else {
        errorMessage = `❌ ${err.message}`;
      }
      
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="transfer-form">
      <h2>Realizar Transferencia</h2>
      
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="senderAccountNumber">Número de Cuenta Remitente:</label>
          <input
            type="text"
            id="senderAccountNumber"
            name="senderAccountNumber"
            value={formData.senderAccountNumber}
            onChange={handleChange}
            placeholder="Ej: 123456789"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="receiverAccountNumber">Número de Cuenta Destinatario:</label>
          <input
            type="text"
            id="receiverAccountNumber"
            name="receiverAccountNumber"
            value={formData.receiverAccountNumber}
            onChange={handleChange}
            placeholder="Ej: 987654321"
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="amount">Monto:</label>
          <input
            type="number"
            id="amount"
            name="amount"
            value={formData.amount}
            onChange={handleChange}
            placeholder="0.00"
            step="0.01"
            min="0.01"
            required
          />
        </div>

        <button 
          type="submit" 
          disabled={loading}
          className="submit-btn"
        >
          {loading ? 'Procesando...' : 'Realizar Transferencia'}
        </button>
      </form>

      {message && (
        <div className="success-message">
          {message}
        </div>
      )}

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}
    </div>
  );
};

export default TransferForm;
