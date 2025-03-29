package com.engsoft.portal.portalvaga.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.engsoft.portal.portalvaga.models.Candidato;
import com.engsoft.portal.portalvaga.models.Candidatura;
import com.engsoft.portal.portalvaga.models.Vaga;

public interface CandidaturaRepositorio extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByVaga(Vaga vaga);
    List<Candidatura> findByCandidato(Candidato candidato);
}
