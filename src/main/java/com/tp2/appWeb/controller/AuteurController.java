package com.tp2.appWeb.controller;

import com.tp2.appWeb.model.Auteur;
import com.tp2.appWeb.repository.AuteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/modifier/{id}")
    public String afficherModifier(@PathVariable Long id, Model model) {
        Auteur auteur = auteurRepo.findById(id).orElse(null);
        if (auteur == null) return "redirect:/auteur";
        model.addAttribute("auteurs", auteurRepo.findAll());
        model.addAttribute("auteur", new Auteur());
        model.addAttribute("auteurEdit", auteur);
        return "auteurs";
    }

    @PostMapping("/modifier/{id}")
    public String modifier(@PathVariable Long id, @ModelAttribute Auteur auteur) {
        Auteur existing = auteurRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setNomPrenom(auteur.getNomPrenom());
            auteurRepo.save(existing);
        }
        return "redirect:/auteur";
    }

    @PostMapping("/supprimer/{id}")
    public String supprimer(@PathVariable Long id) {
        auteurRepo.deleteById(id);
        return "redirect:/auteur";
    }
}