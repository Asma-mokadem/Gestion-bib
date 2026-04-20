package com.tp2.appWeb.repository;

import com.tp2.appWeb.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivreRepository extends JpaRepository<Livre, String> {}
