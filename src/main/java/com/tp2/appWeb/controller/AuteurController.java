package com.tp2.appWeb.controller;


import com.tp2.appWeb.model.Auteur;
import com.tp2.appWeb.repository.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auteur")
public class AuteurController {

    @Autowired
    private AuteurRepository auteurRepo;

    @GetMapping
    public String afficher(Model model) {
        model.addAttribute("auteurs", auteurRepo.findAll());
        model.addAttribute("auteur", new Auteur());
        return "auteurs";
    }

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute Auteur auteur) {
        auteurRepo.save(auteur);
        return "redirect:/auteur";
    }
}
