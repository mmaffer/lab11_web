package pe.edu.tecsup.s09.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.tecsup.s09.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}