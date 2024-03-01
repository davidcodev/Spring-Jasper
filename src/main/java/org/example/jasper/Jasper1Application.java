package org.example.jasper;

import net.sf.jasperreports.engine.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootApplication
public class Jasper1Application {

    public static void main(String[] args) {
        SpringApplication.run(Jasper1Application.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            String destinationPath = "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "static" + File.separator +
                    "reportGenerated.pdf";
            String filePath = "src" + File.separator +
                    "main" + File.separator +
                    "resources" + File.separator +
                    "templates" + File.separator +
                    "report" + File.separator +
                    "Report.jrxml";

            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            Map<String, Object> params = new HashMap<>();
            params.put("voucherId", "0987654");
            params.put("voucherDate", formatter.format(date));
            params.put("amountPaid", new BigDecimal(30000));
            params.put("paymentMethod", "Cash");
            params.put("parentName", "David Dávila");
            params.put("studentName", "Luis Dávila");
            params.put("imageDir", "classpath:/static/images/");

            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print, destinationPath);
        };
    }

}
