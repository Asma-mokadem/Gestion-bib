package com.tp2.appWeb.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Livre {

    @Id
    private String isbn;
    private String titre;
    private int anneeParution;

    @ManyToMany
    @JoinTable(
            name = "livre_auteur",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "auteur_id")
    )
    private List<Auteur> auteurs = new ArrayList<>();

    // Getters & Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public int getAnneeParution() { return anneeParution; }
    public void setAnneeParution(int anneeParution) { this.anneeParution = anneeParution; }

    public List<Auteur> getAuteurs() { return auteurs; }
    public void setAuteurs(List<Auteur> auteurs) { this.auteurs = auteurs; }
}
