package com.example.Library.service;

import com.example.Library.dto.CalendarDto;
import com.example.Library.model.Logbook;
import com.example.Library.model.Reader;
import com.example.Library.repository.LogbookRepository;
import com.example.Library.repository.ReaderRepository;

import lombok.RequiredArgsConstructor;



import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class ExcelService {

    private final ReaderRepository readerRepository;
    private final LogbookRepository logbookRepository;


    public void write(CalendarDto calendarDto) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet shUnload = workbook.createSheet("Отчет за " + calendarDto.getMonth() + "." + calendarDto.getYear() + "г.");

        createHeader(workbook, shUnload);
        createCells(workbook, shUnload, calendarDto);

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
        headerCell.setCellValue("Название приобретенной книги");

        headerCell = header.createCell(3);
        headerCell.setCellValue("Автор приобретенной книги");

        headerCell = header.createCell(4);
        headerCell.setCellValue("Год приобретенной книги");

        headerCell = header.createCell(5);
        headerCell.setCellValue("Количество просроченных дней");
    }

    /*public void createCells(Workbook workbook, Sheet sheet, CalendarDto calendarDto) {
        List<Reader> readers = readerRepository.findAll();

        //sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A4"));

        Cell cell;
        Row row;
        int countRow=1;

        for(int i=0; i<readers.size(); i++) {
            row= sheet.createRow(countRow);

            cell = row.createCell(0);
            cell.setCellValue(readers.get(i).getFio());

            cell = row.createCell(1);
            cell.setCellValue(readers.get(i).getEmail());

            //Set<Logbook> logbookSet =  readers.get(i).getLogbooks();
            //List<Logbook> logbooks = new ArrayList<>(logbookSet);
            List<Logbook> logbooks = periodLogbooks(readers.get(i),calendarDto);
            if(logbooks.isEmpty()) {
                cell = row.createCell(2);
                cell.setCellValue("-");

                cell = row.createCell(3);
                cell.setCellValue("-");

                cell = row.createCell(4);
                cell.setCellValue("-");

                cell = row.createCell(5);
                cell.setCellValue("-");
                countRow++;
            }
            else {
                for (int j = 0; j < logbooks.size(); j++) {
                    row = sheet.createRow(countRow);
                    ////////////////////////////////////////////
                    cell = row.createCell(0);
                    cell.setCellValue(readers.get(i).getFio());

                    cell = row.createCell(1);
                    cell.setCellValue(readers.get(i).getEmail());
                    /////////////////////////////////////////////

                    cell = row.createCell(2);
                    cell.setCellValue(logbooks.get(j).getBook().getName());

                    cell = row.createCell(3);
                    cell.setCellValue(logbooks.get(j).getBook().getAuthor());

                    cell = row.createCell(4);
                    cell.setCellValue(logbooks.get(j).getBook().getYear());

                    cell = row.createCell(5);
                    if (getDayCount(logbooks.get(j).getIssueDate(), logbooks.get(j).getDeliveryDate())!=0) {
                        cell.setCellValue(getDayCount(logbooks.get(j).getIssueDate(), logbooks.get(j).getDeliveryDate()));
                    } else {
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

    public long getDayCount(Calendar issueDate, Calendar deliveryDate) {
        long endDate = deliveryDate.getTimeInMillis();
        long startDate = issueDate.getTimeInMillis();
        long dif = TimeUnit.MILLISECONDS.toDays(endDate - startDate);
        if(dif<=30)
            return 0;
        return dif-30;
    }

    public List<Logbook> periodLogbooks(Reader reader, CalendarDto calendarDto) {
        //Calendar start = new GregorianCalendar(calendarDto.getYear(), calendarDto.getMonth(), 1);
        //Calendar end = new GregorianCalendar(calendarDto.getYear(), calendarDto.getMonth(), 31);
        //Set<Logbook> logbookSet = reader.getLogbooks();
        List<Logbook> logbooks = new ArrayList<>(reader.getLogbooks());
        List<Logbook> curLogbooks = new ArrayList<>();
        for(int i=0; i<logbooks.size(); i++) {
            Date date = logbooks.get(i).getIssueDate().getTime();
            //int month = date.getMonth();
            //int year = date.getYear();
            Calendar c = logbooks.get(i).getIssueDate();
            int month = c.get(Calendar.MONTH)+1;
            int year = c.get(Calendar.YEAR);
            if(calendarDto.getYear()==year && calendarDto.getMonth()==month) {
                curLogbooks.add(logbooks.get(i));
            }
            *//*if(!(logbooks.get(i).getIssueDate().before(start)) && !(logbooks.get(i).getIssueDate().after(end))) {
                curLogbooks.add(logbooks.get(i));
            }*//*
        }
        return curLogbooks;
    }*/

    public void createCells(Workbook workbook, Sheet sheet, CalendarDto calendarDto) {
        List<Reader> readers = readerRepository.findAll();
        Cell cell;
        Row row;
        int countRow=1;

        //sheet.addMergedRegion(CellRangeAddress.valueOf("A1:A4"));

        for(int i=0; i<readers.size(); i++) {
            List<Logbook> logbooks = periodLogbooks(readers.get(i),calendarDto);
            if(!logbooks.isEmpty()) {
                row= sheet.createRow(countRow);

                cell = row.createCell(0);
                cell.setCellValue(readers.get(i).getFio());

                cell = row.createCell(1);
                cell.setCellValue(readers.get(i).getEmail());

                for (int j = 0; j < logbooks.size(); j++) {
                    row = sheet.createRow(countRow);
                    ////////////////////////////////////////////
                    cell = row.createCell(0);
                    cell.setCellValue(readers.get(i).getFio());

                    cell = row.createCell(1);
                    cell.setCellValue(readers.get(i).getEmail());
                    /////////////////////////////////////////////

                    cell = row.createCell(2);
                    cell.setCellValue(logbooks.get(j).getBook().getName());

                    cell = row.createCell(3);
                    cell.setCellValue(logbooks.get(j).getBook().getAuthor());

                    cell = row.createCell(4);
                    cell.setCellValue(logbooks.get(j).getBook().getYear());

                    cell = row.createCell(5);
                    if (getDayCount(logbooks.get(j).getIssueDate(), logbooks.get(j).getDeliveryDate())!=0) {
                        cell.setCellValue(getDayCount(logbooks.get(j).getIssueDate(), logbooks.get(j).getDeliveryDate()));
                    } else {
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

    public long getDayCount(Calendar issueDate, Calendar deliveryDate) {
        long endDate = deliveryDate.getTimeInMillis();
        long startDate = issueDate.getTimeInMillis();
        long dif = TimeUnit.MILLISECONDS.toDays(endDate - startDate);
        if(dif<=30)
            return 0;
        return dif-30;
    }

    public List<Logbook> periodLogbooks(Reader reader, CalendarDto calendarDto) {
        //Calendar start = new GregorianCalendar(calendarDto.getYear(), calendarDto.getMonth(), 1);
        //Calendar end = new GregorianCalendar(calendarDto.getYear(), calendarDto.getMonth(), 31);
        //Set<Logbook> logbookSet = reader.getLogbooks();
        List<Logbook> logbooks = new ArrayList<>(reader.getLogbooks());
        List<Logbook> curLogbooks = new ArrayList<>();
        for(int i=0; i<logbooks.size(); i++) {
            Date date = logbooks.get(i).getIssueDate().getTime();
            //int month = date.getMonth();
            //int year = date.getYear();
            Calendar c = logbooks.get(i).getIssueDate();
            int month = c.get(Calendar.MONTH)+1;
            int year = c.get(Calendar.YEAR);
            if(calendarDto.getYear()==year && calendarDto.getMonth()==month) {
                curLogbooks.add(logbooks.get(i));
            }
            /*if(!(logbooks.get(i).getIssueDate().before(start)) && !(logbooks.get(i).getIssueDate().after(end))) {
                curLogbooks.add(logbooks.get(i));
            }*/
        }
        return curLogbooks;
    }

}
