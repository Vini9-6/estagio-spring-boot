package com.engsoft.portal.portalvaga.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.engsoft.portal.portalvaga.models.Empresa;
import com.engsoft.portal.portalvaga.models.Vaga;
import com.engsoft.portal.portalvaga.servicos.EmpresaServico;
import com.engsoft.portal.portalvaga.servicos.VagaServico;

import java.util.Optional;

@Controller
public class EmpresaControlador {

    @Autowired
    private EmpresaServico empresaServico;

    @Autowired
    private VagaServico vagaServico;

    @GetMapping("/empresas/login")
    public String mostrarLoginEmpresa(@RequestParam(required = false) String error,
                                      @RequestParam(required = false) String logout,
                                      Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        return "login-empresa";
    }

    @PostMapping("/empresas/login")
    public String loginEmpresa(@RequestParam String email,
                               @RequestParam String senha,
                               HttpServletRequest request,
                               Model model) {
        Optional<Empresa> empresa = empresaServico.autenticar(email, senha);
        if (empresa.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("empresaLogada", empresa.get());
            return "redirect:/vagas-empresa";
        } else {
            return "redirect:/empresas/login?error=true";
        }
    }

    @GetMapping("/empresas/registrar")
    public String mostrarRegistroEmpresa(@RequestParam(required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("success", true);
        }
        model.addAttribute("empresa", new Empresa());
        return "registrar-empresa";
    }

    @PostMapping("/empresas/registrar")
    public String registrarEmpresa(@Valid Empresa empresa,
                                   @RequestParam String confirmarSenha,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            return "registrar-empresa";
        }
        
        if (!empresa.getSenha().equals(confirmarSenha)) {
            model.addAttribute("senhaErro", "As senhas não coincidem.");
            return "registrar-empresa";
        }

        if (empresaServico.existePorEmail(empresa.getEmail())) {
            model.addAttribute("emailErro", "Email já está em uso.");
            return "registrar-empresa";
        }

        if (empresaServico.existePorCnpj(empresa.getCnpj())) {
            model.addAttribute("cnpjErro", "CNPJ já está em uso.");
            return "registrar-empresa";
        }

        empresaServico.salvar(empresa);
        return "redirect:/empresas/registrar?success=true";
    }

    @GetMapping("/vagas-empresa")
    public String listarVagasEmpresa(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogada");

        if (empresaLogada == null) {
            return "redirect:/empresas/login";
        }

        model.addAttribute("vagas", vagaServico.listarVagasPorEmpresa(empresaLogada.getId()));
        return "vagas-empresa";
}


    @PostMapping("/vagas/excluir")
    public String excluirVaga(@RequestParam Long vagaId) {
        vagaServico.excluir(vagaId);
        return "redirect:/vagas-empresa";
    }

    @GetMapping("/vagas/cadastrar")
    public String mostrarFormularioVaga(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogada");

        if (empresaLogada == null) {
            return "redirect:/empresas/login";
        }

        model.addAttribute("vaga", new Vaga());
        return "vaga-formulario";
    }

    @PostMapping("/vagas/cadastrar")
    public String cadastrarVaga(@Valid Vaga vaga, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            return "vaga-formulario";
        }

        HttpSession session = request.getSession();
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogada");

        if (empresaLogada == null) {
            return "redirect:/empresas/login";
        }

        vaga.setEmpresa(empresaLogada);
        vagaServico.save(vaga);
        return "redirect:/vagas-empresa";
    }
    @GetMapping("/vagas/editar/{vagaId}")
    public String mostrarFormularioEdicaoVaga(@PathVariable Long vagaId, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogada");

        if (empresaLogada == null) {
            return "redirect:/empresas/login";
        }

        Optional<Vaga> vaga = vagaServico.buscarPorId(vagaId);

        if (vaga.isPresent() && vaga.get().getEmpresa().equals(empresaLogada)) {
            model.addAttribute("vaga", vaga.get());
            return "vaga-formulario";
        } else {
            return "redirect:/vagas-empresa";
        }
    }

    @PostMapping("/vagas/editar/{vagaId}")
    public String editarVaga(@PathVariable Long vagaId, @Valid Vaga vaga, BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            return "vaga-formulario";
        }

        HttpSession session = request.getSession();
        Empresa empresaLogada = (Empresa) session.getAttribute("empresaLogada");

        if (empresaLogada == null) {
            return "redirect:/empresas/login";
        }

        Optional<Vaga> vagaExistente = vagaServico.buscarPorId(vagaId);

        if (vagaExistente.isPresent() && vagaExistente.get().getEmpresa().equals(empresaLogada)) {
            Vaga vagaAtualizada = vagaExistente.get();
            vagaAtualizada.setTitulo(vaga.getTitulo());
            vagaAtualizada.setDescricao(vaga.getDescricao());
            vagaServico.save(vagaAtualizada);
        }

        return "redirect:/vagas-empresa";
    }

    @GetMapping("/logout-empresa")
    public String logoutEmpresa(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/empresas/login?logout=true";
    }
}

