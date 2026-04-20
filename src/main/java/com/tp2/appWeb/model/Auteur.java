package com.tp2.appWeb.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Auteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomPrenom;
    private int nbrePoints = 0;

    @ManyToMany(mappedBy = "auteurs")
    private List<Livre> livres = new ArrayList<>();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomPrenom() { return nomPrenom; }
    public void setNomPrenom(String nomPrenom) { this.nomPrenom = nomPrenom; }

    public int getNbrePoints() { return nbrePoints; }
    public void setNbrePoints(int nbrePoints) { this.nbrePoints = nbrePoints; }

    public List<Livre> getLivres() { return livres; }
    public void setLivres(List<Livre> livres) { this.livres = livres; }
}