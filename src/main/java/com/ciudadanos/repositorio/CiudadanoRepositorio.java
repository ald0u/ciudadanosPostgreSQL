package com.ciudadanos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ciudadanos.modelo.Ciudadano;

public interface CiudadanoRepositorio extends JpaRepository<Ciudadano, Integer> {

}
