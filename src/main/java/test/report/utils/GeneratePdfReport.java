package test.report.utils;

import lombok.extern.slf4j.Slf4j;
import test.report.model.Transactions;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class GeneratePdfReport {

    public static ByteArrayInputStream transactionsReport(List<Transactions> transactionsList) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            export(out, transactionsList);
        } catch (DocumentException ex) {
            log.error("Error occurred: {0}", ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont("Arial", 12, Font.NORMAL);
        font.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Courier", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Address", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Qty", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total", font));
        table.addCell(cell);

    }

    private static void writeTableData(PdfPTable table, List<Transactions> transactionsList) {
        for (Transactions transactions : transactionsList) {
            table.addCell(String.valueOf(transactions.getId()));
            table.addCell(transactions.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            table.addCell(transactions.getCourier().getName());
            table.addCell(transactions.getConsumer().getAddress());
            table.addCell(String.valueOf(transactions.getQty()));
            table.addCell(transactions.getProduct().getPrice());
            table.addCell(transactions.getTotal());
        }
    }

    private static void export(ByteArrayOutputStream out, List<Transactions> transactionsList) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);

        document.open();
        Font font = FontFactory.getFont("Arial", 18, Font.BOLD);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("LIST OF TRANSACTIONS", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 3.5f, 2.5f, 3.5f, 3.5f, 3.5f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, transactionsList);

        document.add(table);

        document.close();

    }
}
