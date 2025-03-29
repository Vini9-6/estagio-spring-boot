package com.engsoft.portal.portalvaga.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engsoft.portal.portalvaga.models.Vaga;

@Repository
public interface VagaRepositorio extends JpaRepository<Vaga, Long> {
    List<Vaga> findByEmpresaId(Long empresaId);
}
