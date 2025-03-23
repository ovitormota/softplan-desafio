package com.desafio.softplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.softplan.domain.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findTop10ByClienteIdOrderByRealizadaEmDesc(Long clienteId);
}
