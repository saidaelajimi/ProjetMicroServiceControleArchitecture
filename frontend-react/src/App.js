import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Transfert from './pages/Transfert';
import TauxChange from './pages/TauxChange';
import Comptes from './pages/Comptes'; // Keeping existing if needed
import CreateAccount from './pages/CreateAccount';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />

        {/* Protected Routes wrapped in Layout */}
        <Route path="/" element={<Layout />}>
          <Route index element={<Navigate to="/login" replace />} />
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="create-account" element={<CreateAccount />} />
          <Route path="comptes" element={<Comptes />} />
          <Route path="transfert" element={<Transfert />} />
          <Route path="change" element={<TauxChange />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;