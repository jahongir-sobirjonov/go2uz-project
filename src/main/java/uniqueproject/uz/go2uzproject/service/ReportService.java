package uniqueproject.uz.go2uzproject.service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.report.OrderReportRequest;
import uniqueproject.uz.go2uzproject.entity.Order;
import uniqueproject.uz.go2uzproject.entity.enums.ReportFormat;
import uniqueproject.uz.go2uzproject.repository.OrderRepository;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;

    public ByteArrayInputStream generateReport(OrderReportRequest request, ReportFormat format) {
        List<Order> orders = orderRepository.findOrdersBetweenDates(
                request.getStartDate(),
                request.getEndDate(),
                request.getTravelTypes(),
                request.getBookingStatuses()
        );

        switch (format) {
            case PDF:
                return generatePdfReport(orders);
            case EXCEL:
                return generateExcelReport(orders);
            default:
                throw new IllegalArgumentException("Invalid report format");
        }
    }

    private ByteArrayInputStream generatePdfReport(List<Order> orders) {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add title to the PDF document
            document.add(new Paragraph("Order Reports:"));
            document.add(new Paragraph("This reports generated on: " + LocalDate.now()));
            document.add(new Paragraph("scan to view full details"));
            document.add(new Paragraph("----------"));

            // Generate and add QR code for each order
            for (Order order : orders) {
                // Order details
                document.add(new Paragraph("Order ID: " + order.getId()));
                document.add(new Paragraph("Tours: " + order.getTour().getTitle()));
                document.add(new Paragraph("Booking Status: " + order.getStatus()));
                document.add(new Paragraph("Order Date: " + order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
                document.add(new Paragraph("-------------- scan me --------------"));

                // QR code content
                String qrContent = "Order ID: " + order.getId() +
                        "\nUser: " + order.getUser().getName() + " " + order.getUser().getSurname() +
                        "\nPhone Number: " + order.getPhoneNumber() +
                        "\nAgency: " + order.getTour().getAgency().getName() +
                        "\nTour: " + order.getTour().getTitle() +
                        "\nBooking Status: " + order.getStatus() +
                        "\nNumber of seats: " + order.getNumberOfSeats() +
//                        "\nTotal cost: " + order.getTotalCost() +
//                        "\nPayment type: " + order.getUrl() + // url kim orqali to'lov qilgani(Visa, Uzcard, Click...)
                        "\nTotal cost: " + (order.getTotalCost() != null ? order.getTotalCost() : "N/A") +
                        "\nPayment type: " + (order.getUrl() != null ? order.getUrl() : "N/A") +
                        "\nOrder Date: " + order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE) ;

                BufferedImage qrImage = generateQRCodeImage(qrContent);

                // Convert QR code image to byte array
                ByteArrayOutputStream qrBaos = new ByteArrayOutputStream();
                ImageIO.write(qrImage, "PNG", qrBaos);
                byte[] qrImageBytes = qrBaos.toByteArray();

                // Add QR code image to PDF
                Image qrCodeImage = Image.getInstance(qrImageBytes);
                qrCodeImage.setAlignment(Element.ALIGN_CENTER);
                document.add(qrCodeImage);

                document.add(new Paragraph("----------"));
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(baos.toByteArray());
    }

    private BufferedImage generateQRCodeImage(String qrContent) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private ByteArrayInputStream generateExcelReport(List<Order> orders) {
        // Using Apache POI to generate Excel
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Orders");
        Row headerRow = sheet.createRow(0);

        // Define header cells
        headerRow.createCell(0).setCellValue("Order ID");
        headerRow.createCell(1).setCellValue("Tour");
        headerRow.createCell(2).setCellValue("Booking Status");
        headerRow.createCell(3).setCellValue("Order Date");

        // Populate data rows
        int rowIndex = 1;
        for (Order order : orders) {

            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(order.getId().toString());
            row.createCell(1).setCellValue(order.getTour().getTitle());
            row.createCell(2).setCellValue(order.getStatus().toString());
            row.createCell(3).setCellValue(order.getOrderDate().toString());
        }

        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
