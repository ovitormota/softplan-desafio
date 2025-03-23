import {
    Alert,
    Box,
    CircularProgress,
    Container,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
} from "@mui/material";
import { useEffect, useState } from "react";
import api from "../services/api";
import { canal, receberMensagem } from "../services/comunicacao";

type Transacao = {
  valor: number;
  tipo: string;
  descricao: string;
  realizadaEm: string;
};

type Extrato = {
  saldo: {
    total: number;
    dataExtrato: string;
    limite: number;
  };
  ultimasTransacoes: Transacao[];
};

export default function ExtratoPage() {
  const [clienteId, setClienteId] = useState<number | null>(null);
  const [extrato, setExtrato] = useState<Extrato | null>(null);
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState<string | null>(null);

  const buscarExtrato = async (id: number) => {
    setLoading(true);
    setErro(null);

    try {
      const response = await api.get(`/clientes/${id}/extrato`);
      setExtrato(response.data);
    } catch (error: any) {
      setErro(error.response?.data?.message || "Erro ao buscar extrato");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    receberMensagem((id) => {
      setClienteId(id);
      buscarExtrato(id);
    });

    return () => {
      canal.onmessage = null;
    };
  }, []);

  return (
    <Container maxWidth="md">
      <Box mt={4}>
        <Typography variant="h4" gutterBottom>
          Extrato de Cliente
        </Typography>

        {loading && <CircularProgress />}

        {erro && (
          <Alert severity="error" sx={{ mt: 2 }}>
            {erro}
          </Alert>
        )}

        {!clienteId && !loading && (
          <Alert severity="info" sx={{ mt: 2 }}>
            Aguardando transação da outra janela...
          </Alert>
        )}

        {extrato && (
          <Box mt={3}>
            <Paper elevation={3} sx={{ p: 2, mb: 3 }}>
              <Typography variant="h6" gutterBottom>
                Saldo
              </Typography>
              <Typography>
                Total: R${(extrato.saldo.total / 100).toFixed(2)}
              </Typography>
              <Typography>
                Limite: R${(extrato.saldo.limite / 100).toFixed(2)}
              </Typography>
              <Typography variant="body2" color="textSecondary">
                Data: {new Date(extrato.saldo.dataExtrato).toLocaleString()}
              </Typography>
            </Paper>

            <Typography variant="h6" gutterBottom>
              Últimas Transações
            </Typography>

            <TableContainer component={Paper}>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Valor</TableCell>
                    <TableCell>Tipo</TableCell>
                    <TableCell>Descrição</TableCell>
                    <TableCell>Data/Hora</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {extrato.ultimasTransacoes.map((transacao, index) => (
                    <TableRow key={index}>
                      <TableCell>
                        R${(transacao.valor / 100).toFixed(2)}
                      </TableCell>
                      <TableCell>
                        {transacao.tipo === "r" ? "Recebível" : "Débito"}
                      </TableCell>
                      <TableCell>{transacao.descricao}</TableCell>
                      <TableCell>
                        {new Date(transacao.realizadaEm).toLocaleString()}
                      </TableCell>
                    </TableRow>
                  ))}
                  {extrato.ultimasTransacoes.length === 0 && (
                    <TableRow>
                      <TableCell colSpan={4} align="center">
                        Nenhuma transação encontrada
                      </TableCell>
                    </TableRow>
                  )}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        )}
      </Box>
    </Container>
  );
}
