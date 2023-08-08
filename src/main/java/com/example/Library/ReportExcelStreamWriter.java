package com.example.Library;

import com.example.Library.model.Book;
import com.example.Library.model.Logbook;
import com.example.Library.model.Reader;
import com.example.Library.repository.BookRepository;
import com.example.Library.repository.LogbookRepository;
import com.example.Library.repository.ReaderRepository;

import lombok.RequiredArgsConstructor;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class ReportExcelStreamWriter {

    private final ReaderRepository readerRepository;
    private final LogbookRepository logbookRepository;


    public void write() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet shUnload = workbook.createSheet("Выгрузка");

        createHeader(workbook, shUnload);
        createCells(workbook, shUnload);

        shUnload.autoSizeColumn(8);

        String filename= "Test"+".xlsx";
        try (var outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    public void createHeader(Workbook workbook, Sheet sheet) {
        Row header = sheet.createRow(0);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("ФИО читателя");

        headerCell = header.createCell(1);
        headerCell.setCellValue("Email читателя");

        headerCell = header.createCell(2);
        headerCell.setCellValue("Логин читателя");

        headerCell = header.createCell(3);
        headerCell.setCellValue("Пароль читателя");

        headerCell = header.createCell(4);
        headerCell.setCellValue("Название приобретенной книги");

        headerCell = header.createCell(5);
        headerCell.setCellValue("Автор приобретенной книги");

        headerCell = header.createCell(6);
        headerCell.setCellValue("Год приобретенной книги");

        headerCell = header.createCell(7);
        headerCell.setCellValue("Количество просроченных дней");
    }

    /*public void createCells(Workbook workbook, Sheet sheet) {
        List<Reader> readers = readerRepository.findAll();
        List<Logbook> logbooks = logbookRepository.findAll();

        Cell cell;
        Row row;
        int countRow = 1;
        List<Logbook> curLog = new ArrayList<>();
        long endDate;
        long startDate;
        long dif;

        for (int i = 0; i < readers.size(); i++) {
            curLog.clear();
            for (int j = 0; j < logbooks.size(); j++) {
                if (readers.get(i).getId() == logbooks.get(j).getReader().getId()) {
                    curLog.add(logbooks.get(j));
                }
            }

            row = sheet.createRow(countRow);
            cell = row.createCell(0);
            cell.setCellValue(readers.get(i).getFio());

            cell = row.createCell(1);
            cell.setCellValue(readers.get(i).getEmail());

            cell = row.createCell(2);
            cell.setCellValue(readers.get(i).getUsername());

            cell = row.createCell(3);
            cell.setCellValue(readers.get(i).getPassword());

            for (int k = 0; k < curLog.size(); k++) {
                row = sheet.createRow(countRow);

                cell = row.createCell(4);
                cell.setCellValue(curLog.get(k).getBook().getName());

                cell = row.createCell(5);
                cell.setCellValue(curLog.get(k).getBook().getAuthor());

                cell = row.createCell(6);
                cell.setCellValue(curLog.get(k).getBook().getYear());

                endDate = logbooks.get(k).getDeliveryDate().getTimeInMillis();
                startDate = logbooks.get(k).getIssueDate().getTimeInMillis();
                dif = TimeUnit.MILLISECONDS.toDays(endDate - startDate);

                cell = row.createCell(7);
                if (dif > 30) {
                    cell.setCellValue(dif - 30);
                } else {
                    cell.setCellValue("нет");
                }
                countRow++;
            }
        }
    }*/

    public void createCells(Workbook workbook, Sheet sheet) {
        List<Reader> readers = readerRepository.findAll();

        //sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A4"));

        Cell cell;
        Row row;
        int countRow=1;
        long endDate;
        long startDate;
        long dif;

        for(int i=0; i<readers.size(); i++) {
            row= sheet.createRow(countRow);

            cell = row.createCell(0);
            cell.setCellValue(readers.get(i).getFio());

            cell = row.createCell(1);
            cell.setCellValue(readers.get(i).getEmail());

            cell = row.createCell(2);
            cell.setCellValue(readers.get(i).getUsername());

            cell = row.createCell(3);
            cell.setCellValue(readers.get(i).getPassword());

            List<Logbook> logbooks = (List<Logbook>) readers.get(i).getLogbooks();
            for(int j=0; j<logbooks.size(); j++) {
                row = sheet.createRow(countRow);

                cell = row.createCell(4);
                cell.setCellValue(logbooks.get(j).getBook().getName());

                cell = row.createCell(5);
                cell.setCellValue(logbooks.get(j).getBook().getAuthor());

                cell = row.createCell(6);
                cell.setCellValue(logbooks.get(j).getBook().getYear());

                endDate = logbooks.get(j).getDeliveryDate().getTimeInMillis();
                startDate = logbooks.get(j).getIssueDate().getTimeInMillis();
                dif = TimeUnit.MILLISECONDS.toDays(endDate - startDate);

                cell = row.createCell(7);
                if (dif > 30) {
                    cell.setCellValue(dif - 30);
                } else {
                    cell.setCellValue("нет");
                }
                countRow++;
            }

        }
    }

}
