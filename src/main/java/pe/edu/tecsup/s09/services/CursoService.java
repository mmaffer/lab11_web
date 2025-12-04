package pe.edu.tecsup.s09.services;

import java.util.List;
import java.util.Optional;

import pe.edu.tecsup.s09.entities.Curso;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> obtener(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
}