package com.desafio.softplan.domain.dto;

import java.time.Instant;

public class SaldoInfoDTO {
    private Integer total;

    private Instant dataExtrato;

    private Integer limite;

    public SaldoInfoDTO() {
    }

    public SaldoInfoDTO(Integer total, Instant dataExtrato, Integer limite) {
        this.total = total;
        this.dataExtrato = dataExtrato;
        this.limite = limite;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Instant getDataExtrato() {
        return dataExtrato;
    }

    public void setDataExtrato(Instant dataExtrato) {
        this.dataExtrato = dataExtrato;
    }

    public Integer getLimite() {
        return limite;
    }

    public void setLimite(Integer limite) {
        this.limite = limite;
    }
}
