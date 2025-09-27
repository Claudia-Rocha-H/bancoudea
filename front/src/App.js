import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import CustomersList from './components/CustomersList';
import TransferForm from './components/TransferForm';
import TransactionsTable from './components/TransactionsTable';
import './App.css';

/**
 * Componente principal de la aplicación bancaria
 */
function App() {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
          <h1>Banco UDEA</h1>
          <nav className="navigation">
            <Link to="/" className="nav-link">Clientes</Link>
            <Link to="/transfer" className="nav-link">Transferencias</Link>
            <Link to="/transactions" className="nav-link">Histórico</Link>
          </nav>
        </header>

        <main className="App-main">
          <Routes>
            <Route path="/" element={<CustomersList />} />
            <Route path="/transfer" element={<TransferForm />} />
            <Route path="/transactions" element={<TransactionsTable />} />
            <Route path="/transactions/:accountNumber" element={<TransactionsTable />} />
          </Routes>
        </main>

        <footer className="App-footer">
          <p>Sistema de Gestión Bancaria - UDEA</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;