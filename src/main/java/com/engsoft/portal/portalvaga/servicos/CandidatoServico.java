package com.engsoft.portal.portalvaga.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engsoft.portal.portalvaga.models.Candidato;
import com.engsoft.portal.portalvaga.repositorios.CandidatoRepositorio;

import java.util.Optional;

@Service
public class CandidatoServico {

    @Autowired
    private CandidatoRepositorio candidatoRepositorio;

    public Optional<Candidato> autenticar(String email, String senha) {
        return candidatoRepositorio.findByEmailAndSenha(email, senha);
    }

    public void salvar(Candidato candidato) {
        candidatoRepositorio.save(candidato);
    }

    public boolean existePorEmail(String email) {
        return candidatoRepositorio.existsByEmail(email);
    }

    public boolean existePorCpf(String cpf) {
        return candidatoRepositorio.existsByCpf(cpf);
    }

    
    public Optional<Candidato> buscarCandidatoPorId(Long id) {
        return candidatoRepositorio.findById(id);
    }
}
