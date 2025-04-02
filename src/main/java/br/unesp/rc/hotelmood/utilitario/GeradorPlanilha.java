package br.unesp.rc.hotelmood.utilitario;

import br.unesp.rc.hotelmood.modelo.Hotel;
import br.unesp.rc.hotelmood.repositorio.HotelRepositorio;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author User
 */
public class GeradorPlanilha {
    public void hotelParaExcel(HotelRepositorio hrepositorio, String filePath) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Avaliações por Hotel");

        //Estilo de célula para centralização
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        //Estilo de célula para cabeçalho (centralizado e em negrito)
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        //Estilo de célula com bordas
        CellStyle borderedCellStyle = workbook.createCellStyle();
        borderedCellStyle.setAlignment(HorizontalAlignment.CENTER);
        borderedCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        borderedCellStyle.setBorderTop(BorderStyle.THIN);
        borderedCellStyle.setBorderBottom(BorderStyle.THIN);
        borderedCellStyle.setBorderLeft(BorderStyle.THIN);
        borderedCellStyle.setBorderRight(BorderStyle.THIN);

        //Primeira linha: cabeçalhos com os nomes dos hotéis
        Row headerRow = sheet.createRow(0);
        Cell cell = headerRow.createCell(0);
        cell.setCellValue("");
        cell.setCellStyle(headerStyle);
        int col = 1;
        for (Hotel h : hrepositorio.getHoteis()) {
            cell = headerRow.createCell(col++);
            cell.setCellValue(h.getNome());
            cell.setCellStyle(headerStyle);
        }

        //Segunda linha: avaliações positivas
        Row positivaRow = sheet.createRow(1);
        cell = positivaRow.createCell(0);
        cell.setCellValue("Positivo");
        cell.setCellStyle(headerStyle);
        col = 1;
        for (Hotel h : hrepositorio.getHoteis()) {
            cell = positivaRow.createCell(col++);
            cell.setCellValue(h.getNumeroComentariosPositivos());
            cell.setCellStyle(borderedCellStyle);
        }

        //Terceira linha: avaliações neutras
        Row neutraRow = sheet.createRow(2);
        cell = neutraRow.createCell(0);
        cell.setCellValue("Neutro");
        cell.setCellStyle(headerStyle);
        col = 1;
        for (Hotel h : hrepositorio.getHoteis()) {
            cell = neutraRow.createCell(col++);
            cell.setCellValue(h.getNumeroComentariosNeutros());
            cell.setCellStyle(borderedCellStyle);
        }

        //Quarta linha: avaliações negativas
        Row negativaRow = sheet.createRow(3);
        cell = negativaRow.createCell(0);
        cell.setCellValue("Negativo");
        cell.setCellStyle(headerStyle);
        col = 1;
        for (Hotel h : hrepositorio.getHoteis()) {
            cell = negativaRow.createCell(col++);
            cell.setCellValue(h.getNumeroComentariosNegativos());
            cell.setCellStyle(borderedCellStyle);
        }

        //Auto ajustar a largura das colunas
        for (int i = 0; i <= hrepositorio.getHoteis().size(); i++) {
            sheet.autoSizeColumn(i);
        }

        //Salvar planilha
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            
        } catch (IOException e) {
            System.out.println("Erro ao salvar planilha");
        }
    }
}
