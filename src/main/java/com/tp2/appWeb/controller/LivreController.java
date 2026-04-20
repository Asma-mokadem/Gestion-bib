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

    @GetMapping("/modifier/{isbn}")
    public String afficherModifier(@PathVariable String isbn, Model model) {
        Livre livre = livreRepo.findById(isbn).orElse(null);
        if (livre == null) return "redirect:/livre";
        model.addAttribute("livres", livreRepo.findAll());
        model.addAttribute("livre", new Livre());
        model.addAttribute("livreEdit", livre);
        return "livres";
    }

    @PostMapping("/modifier/{isbn}")
    public String modifier(@PathVariable String isbn, @ModelAttribute Livre livre) {
        Livre existing = livreRepo.findById(isbn).orElse(null);
        if (existing != null) {
            existing.setTitre(livre.getTitre());
            existing.setAnneeParution(livre.getAnneeParution());
            livreRepo.save(existing);
        }
        return "redirect:/livre";
    }

    @PostMapping("/supprimer/{isbn}")
    public String supprimer(@PathVariable String isbn) {
        livreRepo.deleteById(isbn);
        return "redirect:/livre";
    }

    // Affectation livre à son auteur
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
            if (!livre.getAuteurs().contains(auteur)) {
                livre.getAuteurs().add(auteur);
                auteur.setNbrePoints(auteur.getNbrePoints() + 10);
                livreRepo.save(livre);
                auteurRepo.save(auteur);
            }
        }
        return "redirect:/livre/affecterAuteur";
    }

    @PostMapping("/retirerAuteur")
    public String retirerAuteur(@RequestParam String isbn,
                                @RequestParam Long auteurId) {
        Livre livre = livreRepo.findById(isbn).orElse(null);
        Auteur auteur = auteurRepo.findById(auteurId).orElse(null);

        if (livre != null && auteur != null) {
            boolean removed = livre.getAuteurs().removeIf(a -> a.getId().equals(auteurId));
            if (removed) {
                auteur.setNbrePoints(Math.max(0, auteur.getNbrePoints() - 10));
                livreRepo.save(livre);
                auteurRepo.save(auteur);
            }
        }
        return "redirect:/livre/affecterAuteur";
    }
}