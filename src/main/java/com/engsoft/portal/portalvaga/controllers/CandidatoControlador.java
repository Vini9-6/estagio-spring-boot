package com.engsoft.portal.portalvaga.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.engsoft.portal.portalvaga.models.Candidato;
import com.engsoft.portal.portalvaga.models.Vaga;
import com.engsoft.portal.portalvaga.servicos.CandidatoServico;
import com.engsoft.portal.portalvaga.servicos.CandidaturaService;
import com.engsoft.portal.portalvaga.servicos.VagaServico;

import java.util.List;
import java.util.Optional;

@Controller
public class CandidatoControlador {

    @Autowired
    private CandidatoServico candidatoServico;

    @Autowired
    private VagaServico vagaServico;

    @Autowired
    private CandidaturaService candidaturaService;  

    @GetMapping("/candidatos/login")
    public String mostrarLoginCandidato(@RequestParam(required = false) String error,
                                        @RequestParam(required = false) String logout,
                                        Model model) {
        if (error != null) {
            model.addAttribute("error", true);
        }
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        return "login-candidatura";
    }

    @PostMapping("/candidatos/login")
    public String loginCandidato(@RequestParam String email,
                                 @RequestParam String senha,
                                 HttpServletRequest request,
                                 Model model) {
        Optional<Candidato> candidato = candidatoServico.autenticar(email, senha);
        if (candidato.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("candidatoLogado", candidato.get());
            return "redirect:/vagas-candidato";
        } else {
            return "redirect:/candidatos/login?error=true";
        }
    }

    @GetMapping("/candidatos/registrar")
    public String mostrarRegistroCandidato(@RequestParam(required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("success", true);
        }
        model.addAttribute("candidato", new Candidato());
        return "registrar-candidato";
    }

    @PostMapping("/candidatos/registrar")
    public String registrarCandidato(@Valid Candidato candidato,
                                     @RequestParam String confirmarSenha,
                                     BindingResult result,
                                     Model model) {
        if (result.hasErrors()) {
            return "registrar-candidato";
        }

        if (!candidato.getSenha().equals(confirmarSenha)) {
            model.addAttribute("senhaErro", "As senhas não coincidem.");
            return "registrar-candidato";
        }

        if (candidatoServico.existePorEmail(candidato.getEmail())) {
            model.addAttribute("emailErro", "Email já está em uso.");
            return "registrar-candidato";
        }

        if (candidatoServico.existePorCpf(candidato.getCpf())) {
            model.addAttribute("cpfErro", "CPF já está em uso.");
            return "registrar-candidato";
        }

        candidatoServico.salvar(candidato);

        model.addAttribute("success", true);
        return "registrar-candidato";
    }

    @GetMapping("/vagas-candidato")
    public String listarVagasCandidato(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Candidato candidatoLogado = (Candidato) session.getAttribute("candidatoLogado");

        if (candidatoLogado == null) {
            return "redirect:/candidatos/login";
        }

        List<Vaga> vagas = vagaServico.listarTodasVagas(); 
        model.addAttribute("vagas", vagas);
        return "vagas-candidato";
    }

    @GetMapping("/candidatos/candidatar")
    public String mostrarFormularioCandidatura(@RequestParam Long vagaId, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Candidato candidatoLogado = (Candidato) session.getAttribute("candidatoLogado");
    
        if (candidatoLogado == null) {
            return "redirect:/candidatos/login";  // Redireciona para a página de login se o candidato não estiver logado
        }
    
        Optional<Vaga> vaga = vagaServico.buscarPorId(vagaId);
        if (vaga.isPresent()) {
            model.addAttribute("vaga", vaga.get());
            model.addAttribute("candidato", candidatoLogado);  // Adiciona o candidato ao modelo
            return "candidatar";  // Retorna para o formulário de candidatura
        }
    
        return "redirect:/vagas-candidatos";  // Se a vaga não for encontrada, redireciona para a lista de vagas
    }
    

    @PostMapping("/candidatos/candidatar")
    public String candidatar(@RequestParam Long vagaId, @RequestParam Long candidatoId, @RequestParam String mensagem, RedirectAttributes redirectAttributes) {
        try {
            candidaturaService.candidatar(vagaId, candidatoId, mensagem);
            redirectAttributes.addFlashAttribute("success", "Candidatura realizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace(); // Exibe o erro no console
            redirectAttributes.addFlashAttribute("error", "Erro ao se candidatar. " + e.getMessage()); // Exibe a mensagem de erro no frontend
        }
        return "redirect:/vagas-candidatos";
    }

    @GetMapping("/logout-candidato")
    public String logoutCandidato(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/candidatos/login?logout=true";
    }
}
