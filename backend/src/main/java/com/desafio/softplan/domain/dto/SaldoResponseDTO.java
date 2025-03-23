package com.desafio.softplan.domain.dto;

public class SaldoResponseDTO {
    private Integer limite;
    private Integer saldo;

    public SaldoResponseDTO() {
    }

    public SaldoResponseDTO(Integer limite, Integer saldo) {
        this.limite = limite;
        this.saldo = saldo;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
}
