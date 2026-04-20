package com.tp2.appWeb.controller;


import com.tp2.appWeb.model.Auteur;
import com.tp2.appWeb.model.Livre;
import com.tp2.appWeb.repository.AuteurRepository;
import com.tp2.appWeb.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livre")
public class LivreController {

    @Autowired
    private LivreRepository livreRepo;

    @Autowired
    private AuteurRepository auteurRepo;

    // --- Gestion des Livres ---
    @GetMapping
    public String afficher(Model model) {
        model.addAttribute("livres", livreRepo.findAll());
        model.addAttribute("livre", new Livre());
        return "livres";
    }

    @PostMapping("/ajouter")
    public String ajouter(@ModelAttribute Livre livre) {
        livreRepo.save(livre);
        return "redirect:/livre";
    }

    // --- Affectation Auteur ↔ Livre ---
    @GetMapping("/affecterAuteur")
    public String afficherAffectation(Model model) {
        model.addAttribute("livres", livreRepo.findAll());
        model.addAttribute("auteurs", auteurRepo.findAll());
        return "affectation";
    }

    @PostMapping("/affecterAuteur")
    public String affecter(@RequestParam String isbn,
                           @RequestParam Long auteurId) {
        Livre livre = livreRepo.findById(isbn).orElse(null);
        Auteur auteur = auteurRepo.findById(auteurId).orElse(null);

        if (livre != null && auteur != null) {
            // Eviter les doublons
            if (!livre.getAuteurs().contains(auteur)) {
                livre.getAuteurs().add(auteur);
                auteur.setNbrePoints(auteur.getNbrePoints() + 10); // +10 points
                livreRepo.save(livre);
                auteurRepo.save(auteur);
            }
        }
        return "redirect:/livre/affecterAuteur";
    }
}