package com.engsoft.portal.portalvaga.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.engsoft.portal.portalvaga.models.Empresa;
import com.engsoft.portal.portalvaga.repositorios.EmpresaRepositorio;

import java.util.Optional;

@Service
public class EmpresaServico {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    public Optional<Empresa> autenticar(String email, String senha) {
        return empresaRepositorio.findByEmailAndSenha(email, senha);
    }

    public void salvar(Empresa empresa) {
        empresaRepositorio.save(empresa);
    }

    public boolean existePorEmail(String email) {
        return empresaRepositorio.existsByEmail(email);
    }

    public boolean existePorCnpj(String cnpj) {
        return empresaRepositorio.existsByCnpj(cnpj);
    }
}

