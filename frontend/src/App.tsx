import { BrowserRouter, Routes, Route } from 'react-router-dom';
import TransacoesPage from './pages/TransacoesPage';
import ExtratoPage from './pages/ExtratoPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route index element={<ExtratoPage />} /> 
        <Route path="/extrato" element={<ExtratoPage />} />
        <Route path="/transacoes" element={<TransacoesPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
