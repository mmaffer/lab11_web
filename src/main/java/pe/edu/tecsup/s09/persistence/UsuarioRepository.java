package pe.edu.tecsup.s09.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.tecsup.s09.entities.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}