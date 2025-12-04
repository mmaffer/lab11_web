package pe.edu.tecsup.s09.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.tecsup.s09.entities.Rol;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}