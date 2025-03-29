package com.engsoft.portal.portalvaga.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engsoft.portal.portalvaga.models.Candidato;

import java.util.Optional;

@Repository
public interface CandidatoRepositorio extends JpaRepository<Candidato, Long> {
    Optional<Candidato> findByEmailAndSenha(String email, String senha);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
