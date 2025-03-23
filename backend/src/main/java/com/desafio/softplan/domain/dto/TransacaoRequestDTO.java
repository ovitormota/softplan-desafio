package com.desafio.softplan.domain.dto;

public class TransacaoRequestDTO {
    private Integer valor;
    private String tipo;
    private String descricao;

    public TransacaoRequestDTO() {
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
