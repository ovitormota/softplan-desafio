package com.desafio.softplan.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.softplan.config.Constantes.TipoTransacaoConstantes;
import com.desafio.softplan.domain.Cliente;
import com.desafio.softplan.domain.Transacao;
import com.desafio.softplan.domain.dto.ExtratoResponseDTO;
import com.desafio.softplan.domain.dto.SaldoInfoDTO;
import com.desafio.softplan.domain.dto.SaldoResponseDTO;
import com.desafio.softplan.domain.dto.TransacaoInfoDTO;
import com.desafio.softplan.domain.dto.TransacaoRequestDTO;
import com.desafio.softplan.repository.ClienteRepository;
import com.desafio.softplan.repository.TransacaoRepository;

import jakarta.transaction.Transactional;

@Service
public class TransacaoService {
    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public TransacaoService(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional
    public SaldoResponseDTO realizarTransacao(Long clienteId, TransacaoRequestDTO request) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        validarCampos(request);

        int novoSaldo;
        if (TipoTransacaoConstantes.DEBITO.equals(request.getTipo())) {
            novoSaldo = cliente.getSaldo() - request.getValor();
            if (novoSaldo < -cliente.getLimite()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Saldo insuficiente");
            }
            cliente.setSaldo(novoSaldo);
        } else {
            novoSaldo = cliente.getSaldo() + request.getValor();
            cliente.setSaldo(novoSaldo);
        }

        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacao.setValor(request.getValor());
        transacao.setTipo(request.getTipo());
        transacao.setDescricao(request.getDescricao());
        transacao.setRealizadaEm(Instant.now());

        transacaoRepository.save(transacao);
        clienteRepository.save(cliente);

        return new SaldoResponseDTO(cliente.getLimite(), novoSaldo);
    }

    public ExtratoResponseDTO obterExtrato(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        List<Transacao> ultimasTransacoes = transacaoRepository.findTop10ByClienteIdOrderByRealizadaEmDesc(clienteId);

        SaldoInfoDTO saldoInfo = new SaldoInfoDTO(
                cliente.getSaldo(),
                Instant.now(),
                cliente.getLimite());

        List<TransacaoInfoDTO> transacoesInfo = ultimasTransacoes.stream()
                .map(t -> new TransacaoInfoDTO(
                        t.getValor(),
                        t.getTipo(),
                        t.getDescricao(),
                        t.getRealizadaEm()))
                .toList();

        return new ExtratoResponseDTO(saldoInfo, transacoesInfo);
    }

    private void validarCampos(TransacaoRequestDTO request) {
        if (request.getValor() == null || request.getValor() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "O valor da transação deve ser um número positivo e maior que zero.");
        }

        if (request.getValor() % 1 != 0) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "O valor deve ser um número inteiro.");
        }

        if (request.getTipo() == null || (!request.getTipo().equals(TipoTransacaoConstantes.RECEBIMENTO)
                && !request.getTipo().equals(TipoTransacaoConstantes.DEBITO))) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "O tipo da transação deve ser 'r' para recebimento ou 'd' para débito.");
        }

        if (request.getDescricao() == null || request.getDescricao().isEmpty()
                || request.getDescricao().length() > 10) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "A descrição deve ser fornecida e ter entre 1 e 10 caracteres.");
        }
    }
}
