package com.engsoft.portal.portalvaga.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engsoft.portal.portalvaga.models.Candidato;
import com.engsoft.portal.portalvaga.models.Candidatura;
import com.engsoft.portal.portalvaga.models.Vaga;
import com.engsoft.portal.portalvaga.repositorios.CandidatoRepositorio;
import com.engsoft.portal.portalvaga.repositorios.CandidaturaRepositorio;
import com.engsoft.portal.portalvaga.repositorios.VagaRepositorio;

import java.util.Optional;


@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepositorio candidaturaRepository;

    @Autowired
    private VagaRepositorio vagaRepository;

    @Autowired
    private CandidatoRepositorio candidatoRepository;

    public void candidatar(Long vagaId, Long candidatoId, String mensagem) throws Exception {
    try {
        // Verificar se a vaga e o candidato existem
        Optional<Vaga> vagaOpt = vagaRepository.findById(vagaId);
        Optional<Candidato> candidatoOpt = candidatoRepository.findById(candidatoId);
        
        if (!vagaOpt.isPresent()) {
            throw new Exception("Vaga não encontrada");
        }
        if (!candidatoOpt.isPresent()) {
            throw new Exception("Candidato não encontrado");
        }

        Vaga vaga = vagaOpt.get();
        Candidato candidato = candidatoOpt.get();

        // Lógica de salvar a candidatura
        Candidatura candidatura = new Candidatura();
        candidatura.setVaga(vaga);
        candidatura.setCandidato(candidato);
        candidatura.setMensagem(mensagem);
        
        candidaturaRepository.save(candidatura);
    } catch (Exception e) {
        System.out.println("Erro ao salvar a candidatura: " + e.getMessage());
        throw e; // Lançando novamente a exceção para o controlador
    }
}

}
