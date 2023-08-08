package com.example.Library.controller;

import com.example.Library.ReportExcelStreamWriter;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
//@AllArgsConstructor
public class ExcelController {
    private final ReportExcelStreamWriter reportExcelStreamWriter;


    @GetMapping("/excel")
    public ResponseEntity<?> sendExcel() throws IOException {
        reportExcelStreamWriter.write();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
