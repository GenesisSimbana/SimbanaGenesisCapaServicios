package com.espe.micro_usuarios.services;

import com.espe.micro_usuarios.models.entities.Usuarios;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuarios> findAll();

    Optional<Usuarios> findById(Long id);

    Usuarios save(Usuarios usuario);

    void deleteById(Long id);
}
