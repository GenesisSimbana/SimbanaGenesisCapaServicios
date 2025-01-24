package com.espe.micro_usuarios.repositories;

import com.espe.micro_usuarios.models.entities.Usuarios;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuarios, Long> {
}
