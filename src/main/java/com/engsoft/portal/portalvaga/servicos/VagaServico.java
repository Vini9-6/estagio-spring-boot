package com.engsoft.portal.portalvaga.servicos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engsoft.portal.portalvaga.models.Vaga;
import com.engsoft.portal.portalvaga.repositorios.VagaRepositorio;

import java.util.List;
import java.util.Optional;


@Service
public class VagaServico {

    @Autowired
    private VagaRepositorio vagaRepositorio;

    public List<Vaga> listarVagasPorEmpresa(Long empresaId) {
        return vagaRepositorio.findByEmpresaId(empresaId);
    }

    public List<Vaga> listarTodasVagas() {
        return vagaRepositorio.findAll();
    }

    public void excluir(Long vagaId) {
        vagaRepositorio.deleteById(vagaId);
    }

    // Encontra uma vaga pelo ID
     public Optional<Vaga> buscarPorId(Long id) {
        return vagaRepositorio.findById(id);
    }


    // Salva uma nova vaga
    public void save(Vaga vaga) {
        vagaRepositorio.save(vaga);
    }

    // Atualiza uma vaga existente
    public void update(Vaga vaga) {
        if (vagaRepositorio.existsById(vaga.getId())) {
            vagaRepositorio.save(vaga);
        } else {
            throw new RuntimeException("Vaga não encontrada para atualização.");
        }
    }
}

