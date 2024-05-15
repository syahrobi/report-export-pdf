package test.report.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import test.report.model.Transactions;
import test.report.service.TransactionsService;
import test.report.utils.GeneratePdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/transactions")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> citiesReport() {

        var transactions = (List<Transactions>) transactionsService.getAll();

        ByteArrayInputStream bis = GeneratePdfReport.transactionsReport(transactions);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
