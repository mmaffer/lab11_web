package pe.edu.tecsup.s09.security;

import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import pe.edu.tecsup.s09.entities.Usuario;
import pe.edu.tecsup.s09.persistence.UsuarioRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
            UserDetails ud = org.springframework.security.core.userdetails.User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .disabled(!usuario.isEnabled())
                    .authorities(usuario.getRoles().stream().map(r -> r.getNombre()).collect(Collectors.toList()).toArray(new String[]{}))
                    .build();
            return ud;
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService uds, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                        .requestMatchers("/", "/cursos", "/cursos/listar").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/cursos/form/**", "/cursos/eliminar/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .successHandler(loginSuccessHandler())
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll())
                .csrf(csrf -> csrf.disable());

        // H2 console needs frame options disabled
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}