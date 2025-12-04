package pe.edu.tecsup.s09.views;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.s09.entities.Curso;

import java.io.OutputStream;
import java.util.List;

@Component
public class CursoPdfView {

    public void render(List<Curso> cursos, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=cursos.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Listado de Cursos", titleFont));

            Table table = new Table(3);
            table.addCell("Código");
            table.addCell("Nombres");
            table.addCell("Créditos");

            for (Curso c : cursos) {
                table.addCell(String.valueOf(c.getId()));
                table.addCell(c.getNombre());
                table.addCell(String.valueOf(c.getCreditos()));
            }

            document.add(table);
            document.close();
        }
    }
}