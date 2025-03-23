import {
    Alert,
    Box,
    Button,
    CircularProgress,
    Container,
    Typography,
} from "@mui/material";
import { useState } from "react";
import api from "../services/api";
import { enviarMensagem } from "../services/comunicacao";

export default function TransacoesPage() {
  const [loading, setLoading] = useState(false);
  const [resultado, setResultado] = useState<any>(null);
  const [erro, setErro] = useState<string | null>(null);

  const gerarTransacao = async () => {
    setLoading(true);
    setErro(null);

    const clienteId = Math.floor(Math.random() * 5) + 1;

    const valor = Math.floor(Math.random() * 9999) + 1;

    const tipo = Math.random() > 0.5 ? "r" : "d";

    const descricao = `Trans${Math.floor(Math.random() * 1000)}`;

    try {
      const response = await api.post(`/clientes/${clienteId}/transacoes`, {
        valor,
        tipo,
        descricao,
      });

      setResultado({
        clienteId,
        ...response.data,
      });

      enviarMensagem(clienteId);
    } catch (error: any) {
      setErro(error.response?.data?.message || "Erro ao processar transação");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="sm">
      <Box mt={4} display="flex" flexDirection="column" alignItems="center">
        <Typography variant="h4" gutterBottom>
          Gerar Transação Aleatória
        </Typography>

        <Button
          variant="contained"
          color="primary"
          onClick={gerarTransacao}
          disabled={loading}
          sx={{ mt: 2 }}
        >
          {loading ? <CircularProgress size={24} /> : "Gerar Transação"}
        </Button>

        {erro && (
          <Alert severity="error" sx={{ mt: 2, width: "100%" }}>
            {erro}
          </Alert>
        )}

        {resultado && (
          <Box
            mt={3}
            p={2}
            bgcolor="background.paper"
            borderRadius={1}
            width="100%"
          >
            <Typography variant="h6" gutterBottom>
              Transação realizada para Cliente ID: {resultado.clienteId}
            </Typography>
            <Typography>
              Novo saldo: R${(resultado.saldo / 100).toFixed(2)}
            </Typography>
            <Typography>
              Limite: R${(resultado.limite / 100).toFixed(2)}
            </Typography>
          </Box>
        )}
      </Box>
    </Container>
  );
}
