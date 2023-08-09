package com.example.Library.controller;

import com.example.Library.service.ExcelService;
import com.example.Library.dto.CalendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
//@AllArgsConstructor
public class ExcelController {
    private final ExcelService reportExcelStreamWriter;


    @GetMapping("/excel")
    public ResponseEntity<?> sendExcel(@RequestBody CalendarDto calendarDto) throws IOException {
        reportExcelStreamWriter.write(calendarDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
