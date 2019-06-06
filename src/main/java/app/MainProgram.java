package app;

import java.util.List;

import static app.ProgramConstants.*;

public class MainProgram {
    public static void main(String[] args) {
        PerformanceReport performanceReport = new PerformanceReport();
        List<Employee> topEmployees = performanceReport.findTopEmployees();
        String reportDirectoryPath = performanceReport.inputReportDirectoryPath();

        String reportFileName = "performance-report";
        String reportFileExtension = "csv";
        performanceReport.createReportFile(topEmployees, reportDirectoryPath, reportFileName, reportFileExtension);

        System.out.println(CHECK_FOR_GENERATED_REPORT_FILE_MESSAGE);
    }
}





