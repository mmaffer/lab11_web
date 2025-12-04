package pe.edu.tecsup.s09.views;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.s09.entities.Curso;

import java.io.OutputStream;
import java.util.List;

@Component
public class CursoXlsView {

    public void render(List<Curso> cursos, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=cursos.xlsx");

        try (Workbook wb = new XSSFWorkbook(); OutputStream out = response.getOutputStream()) {
            Sheet sheet = wb.createSheet("Cursos");
            int rowIdx = 0;
            Row header = sheet.createRow(rowIdx++);
            header.createCell(0).setCellValue("Código");
            header.createCell(1).setCellValue("Nombres");
            header.createCell(2).setCellValue("Créditos");

            for (Curso c : cursos) {
                Row r = sheet.createRow(rowIdx++);
                r.createCell(0).setCellValue(c.getId());
                r.createCell(1).setCellValue(c.getNombre());
                r.createCell(2).setCellValue(c.getCreditos());
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            wb.write(out);
        }
    }
}