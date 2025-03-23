package com.desafio.softplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.softplan.domain.dto.ExtratoResponseDTO;
import com.desafio.softplan.domain.dto.SaldoResponseDTO;
import com.desafio.softplan.domain.dto.TransacaoRequestDTO;
import com.desafio.softplan.service.TransacaoService;

@RestController
@RequestMapping("/api")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/clientes/{id}/transacoes")
    public ResponseEntity<SaldoResponseDTO> realizarTransacao(@PathVariable Long id,
            @RequestBody TransacaoRequestDTO request) {
        return ResponseEntity.ok(transacaoService.realizarTransacao(id, request));
    }

    @GetMapping("/clientes/{id}/extrato")
    public ResponseEntity<ExtratoResponseDTO> obterExtrato(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.obterExtrato(id));
    }
}
