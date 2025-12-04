package pe.edu.tecsup.s09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import pe.edu.tecsup.s09.entities.Curso;
import pe.edu.tecsup.s09.entities.Rol;
import pe.edu.tecsup.s09.entities.Usuario;
import pe.edu.tecsup.s09.persistence.CursoRepository;
import pe.edu.tecsup.s09.persistence.RolRepository;
import pe.edu.tecsup.s09.persistence.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class S09DevDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(S09DevDemo01Application.class, args);
    }

    @Bean
    CommandLineRunner initData(CursoRepository cursoRepo,
                               UsuarioRepository usuarioRepo,
                               RolRepository rolRepo,
                               BCryptPasswordEncoder encoder) {
        return args -> {
            // Cursos demo
            if (cursoRepo.count() == 0) {
                cursoRepo.save(new Curso(null, "CS101", "Programación", 4, true));
                cursoRepo.save(new Curso(null, "CS201", "Desarrollo Web", 3, true));
            }

            // Roles
            Rol adminRole = rolRepo.findByNombre("ROLE_ADMIN").orElseGet(() -> rolRepo.save(new Rol(null, "ROLE_ADMIN")));
            Rol userRole = rolRepo.findByNombre("ROLE_USER").orElseGet(() -> rolRepo.save(new Rol(null, "ROLE_USER")));

            // Usuarios
            if (usuarioRepo.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin"));
                admin.setEnabled(true);
                admin.getRoles().add(adminRole);
                admin.getRoles().add(userRole);
                usuarioRepo.save(admin);
            }

            if (usuarioRepo.findByUsername("user").isEmpty()) {
                Usuario user = new Usuario();
                user.setUsername("user");
                user.setPassword(encoder.encode("user"));
                user.setEnabled(true);
                user.getRoles().add(userRole);
                usuarioRepo.save(user);
            }
        };
    }

}
