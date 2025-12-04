package pe.edu.tecsup.s09.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.tecsup.s09.entities.Curso;
import pe.edu.tecsup.s09.persistence.CursoRepository;

@Service
@Transactional
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> obtener(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
}