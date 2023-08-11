package com.example.Library.service.impl;

import com.example.Library.dto.CalendarDto;
import com.example.Library.model.Logbook;
import com.example.Library.model.Reader;
import com.example.Library.repository.ReaderRepository;

import com.example.Library.service.interfaces.LogbookService;
import lombok.RequiredArgsConstructor;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.*;
import java.util.*;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class ExcelService {

    private final ReaderRepository readerRepository;
    private final LogbookService logbookService;


    public void write(CalendarDto calendarDto) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet shUnload = workbook.createSheet("Отчет за " + calendarDto.getMonth() + "." + calendarDto.getYear() + "г.");

        createHeader(shUnload);
        createCells(shUnload, calendarDto);

        shUnload.autoSizeColumn(8);

        String filename= "files/" + "Test" + ".xlsx";
        try (var outputStream = new FileOutputStream(filename)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    public void createHeader(Sheet sheet) {
        Row header = sheet.createRow(0);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("ФИО читателя");

        headerCell = header.createCell(1);
        headerCell.setCellValue("Email читателя");

        headerCell = header.createCell(2);
        headerCell.setCellValue("Название приобретенной книги");

        headerCell = header.createCell(3);
        headerCell.setCellValue("Автор книги");

        headerCell = header.createCell(4);
        headerCell.setCellValue("Год издания");

        headerCell = header.createCell(5);
        headerCell.setCellValue("Количество просроченных дней");
    }

    public void createCells(Sheet sheet, CalendarDto calendarDto) {
        List<Reader> readers = readerRepository.findAll();
        Cell cell;
        Row row;
        int countRow=1;

        //sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A4"));

        for(int i=0; i<readers.size(); i++) {
            List<Logbook> logbooks = periodLogbooks(readers.get(i),calendarDto);
            if(!logbooks.isEmpty()) {

                for (int j = 0; j < logbooks.size(); j++) {
                    row = sheet.createRow(countRow);
                    cell = row.createCell(0);
                    cell.setCellValue(readers.get(i).getFio());

                    cell = row.createCell(1);
                    cell.setCellValue(readers.get(i).getEmail());

                    cell = row.createCell(2);
                    cell.setCellValue(logbooks.get(j).getBook().getName());

                    cell = row.createCell(3);
                    cell.setCellValue(logbooks.get(j).getBook().getAuthor());

                    cell = row.createCell(4);
                    cell.setCellValue(logbooks.get(j).getBook().getYear());

                    cell = row.createCell(5);
                    Calendar start = logbooks.get(j).getIssueDate();
                    Calendar end = logbooks.get(j).getDeliveryDate();
                    if (logbookService.getOverdueDays(start, end)!=0) {
                        cell.setCellValue(logbookService.getOverdueDays(start, end));
                    }
                    else {
                        cell.setCellValue("нет");
                    }
                    countRow++;
                }
            }
            //countRow++;
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
    }

    public List<Logbook> periodLogbooks(Reader reader, CalendarDto calendarDto) {
        List<Logbook> logbooks = new ArrayList<>(reader.getLogbooks());
        List<Logbook> curLogbooks = new ArrayList<>();
        for (Logbook logbook : logbooks) {
            Calendar c = logbook.getIssueDate();
            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            if (calendarDto.getYear() == year && calendarDto.getMonth() == month) {
                curLogbooks.add(logbook);
            }
        }
        return curLogbooks;
    }

}
