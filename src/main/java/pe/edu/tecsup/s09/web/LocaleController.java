package pe.edu.tecsup.s09.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {
    @GetMapping("/")
    public String home() {
        return "redirect:/cursos";
    }
}