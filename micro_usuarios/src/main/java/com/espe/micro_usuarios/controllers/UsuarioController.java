package com.espe.micro_usuarios.controllers;

import com.espe.micro_usuarios.models.entities.Usuarios;
import com.espe.micro_usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Usuarios usuario, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(
                    error -> errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuario));
    }

    @GetMapping
    public List<Usuarios> listar() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> buscarPorId(@PathVariable Long id) {
        Optional<Usuarios> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuarios usuario, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validarErrores(result);
        }

        Optional<Usuarios> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuarios usuarioDB = usuarioOptional.get();
            usuarioDB.setNombre(usuario.getNombre());
            usuarioDB.setApellido(usuario.getApellido());
            usuarioDB.setEmail(usuario.getEmail());
            usuarioDB.setTelefono(usuario.getTelefono());
            usuarioDB.setFechaNacimiento(usuario.getFechaNacimiento());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(usuarioDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Usuarios> usuarioOptional = service.findById(id);
        if (usuarioOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validarErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(
                error -> errores.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errores);
    }
}
