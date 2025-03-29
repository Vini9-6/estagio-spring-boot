package com.engsoft.portal.portalvaga.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.engsoft.portal.portalvaga.models.Empresa;

import java.util.Optional;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByEmailAndSenha(String email, String senha);
    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);
}
