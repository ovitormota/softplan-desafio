package com.desafio.softplan.domain.dto;

import java.util.List;

public class ExtratoResponseDTO {
    private SaldoInfoDTO saldo;
    private List<TransacaoInfoDTO> ultimasTransacoes;

    public ExtratoResponseDTO() {
    }

    public ExtratoResponseDTO(SaldoInfoDTO saldo, List<TransacaoInfoDTO> ultimasTransacoes) {
        this.saldo = saldo;
        this.ultimasTransacoes = ultimasTransacoes;
    }

    public SaldoInfoDTO getSaldo() {
        return saldo;
    }

    public void setSaldo(SaldoInfoDTO saldo) {
        this.saldo = saldo;
    }

    public List<TransacaoInfoDTO> getUltimasTransacoes() {
        return ultimasTransacoes;
    }

    public void setUltimasTransacoes(List<TransacaoInfoDTO> ultimasTransacoes) {
        this.ultimasTransacoes = ultimasTransacoes;
    }
}
