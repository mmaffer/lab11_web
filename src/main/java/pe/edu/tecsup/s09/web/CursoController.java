package pe.edu.tecsup.s09.web;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import pe.edu.tecsup.s09.entities.Curso;
import pe.edu.tecsup.s09.services.CursoService;
import pe.edu.tecsup.s09.views.CursoPdfView;
import pe.edu.tecsup.s09.views.CursoXlsView;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final CursoPdfView pdfView;
    private final CursoXlsView xlsView;

    public CursoController(CursoService cursoService, CursoPdfView pdfView, CursoXlsView xlsView) {
        this.cursoService = cursoService;
        this.pdfView = pdfView;
        this.xlsView = xlsView;
    }

    @GetMapping
    public String index() {
        return "redirect:/cursos/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de Cursos");
        model.addAttribute("cursos", cursoService.listar());
        return "cursos/listar";
    }

    @GetMapping("/export/pdf")
    public ModelAndView exportPdf(jakarta.servlet.http.HttpServletResponse response) throws Exception {
        pdfView.render(cursoService.listar(), response);
        return null; // la respuesta ya fue escrita
    }

    @GetMapping("/export/xls")
    public ModelAndView exportXls(jakarta.servlet.http.HttpServletResponse response) throws Exception {
        xlsView.render(cursoService.listar(), response);
        return null; // la respuesta ya fue escrita
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("titulo", "Formulario de Curso");
        model.addAttribute("curso", new Curso());
        return "cursos/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("curso") Curso curso, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Curso");
            return "cursos/form";
        }
        cursoService.guardar(curso);
        return "redirect:/cursos/listar";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Optional<Curso> c = cursoService.obtener(id);
        c.ifPresent(cur -> cursoService.eliminar(id));
        return "redirect:/cursos/listar";
    }
}