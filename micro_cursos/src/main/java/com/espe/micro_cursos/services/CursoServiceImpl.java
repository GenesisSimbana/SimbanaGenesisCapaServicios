package com.espe.micro_cursos.services;

import com.espe.micro_cursos.clients.UsuarioClientRest;
import com.espe.micro_cursos.models.Usuario;
import com.espe.micro_cursos.models.entities.Curso;
import com.espe.micro_cursos.models.entities.CursoUsuario;
import com.espe.micro_cursos.repositories.CursoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CursoServiceImpl implements CursoService{
    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest clientRest;


    @Override
    public List<Curso> findAll() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Curso save(Curso curso) {
        return  repository.save(curso);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Usuario> addUsuario(Usuario usuario, Long id) {
        // Verificar si el curso existe
        Optional<Curso> optionalCurso = repository.findById(id);
        if (optionalCurso.isEmpty()) {
            throw new NoSuchElementException("El curso con ID " + id + " no existe.");
        }

        // Verificar si el usuario existe
        Usuario usuarioTemp;
        try {
            usuarioTemp = clientRest.findById(usuario.getId());
        } catch (FeignException e) {
            throw new NoSuchElementException("El usuario con ID " + usuario.getId() + " no existe.");
        }

        // Agregar usuario al curso
        Curso curso = optionalCurso.get();
        CursoUsuario cursoUsuario = new CursoUsuario();
        cursoUsuario.setUsuarioId(usuarioTemp.getId());
        curso.addCursoUsuario(cursoUsuario);
        repository.save(curso);
        return Optional.of(usuarioTemp);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return clientRest.save(usuario);
    }

    @Override
    public Optional<Usuario> removeUsuario(Long usuarioId, Long cursoId) {
        Optional<Curso> cursoOptional = repository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            curso.removeCursoUsuarioByUsuarioId(usuarioId);
            repository.save(curso);
            return Optional.of(clientRest.findById(usuarioId));
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> getUsuariosByCursoId(Long cursoId) {
        Optional<Curso> cursoOptional = repository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            List<Usuario> usuarios = new ArrayList<>();
            for (CursoUsuario cu : curso.getCursoUsuarios()) {
                Usuario usuario = clientRest.findById(cu.getUsuarioId());
                usuarios.add(usuario);
            }
            return usuarios;
        }
        return Collections.emptyList();
    }

}
