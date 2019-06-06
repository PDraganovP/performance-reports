package app;

import com.google.gson.Gson;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static app.ProgramConstants.*;

public class PerformanceReport {

    public PerformanceReport() {
    }

    public List<Employee> findTopEmployees() {
        String dataFilePath = inputDataFilePath();
        String validatedDataFilePath = validatedFilePath(dataFilePath);
        FileIO fileIO = new FileIO();
        String employeesInJsonFormat = fileIO.readFile(validatedDataFilePath);
        Employee[] employees = convertJsonToEmployees(employeesInJsonFormat);

        String reportDefinitionFilePath = inputReportDefinitionFilePath();
        String validatedReportDefinitionFilePath = validatedFilePath(reportDefinitionFilePath);
        String reportDefinitionInJsonFormat = fileIO.readFile(validatedReportDefinitionFilePath);
        ReportDefinition reportDefinition = convertJsonToReportDefinition(reportDefinitionInJsonFormat);

        int topPerformersThreshold = reportDefinition.getTopPerformersThreshold();
        int periodLimit = reportDefinition.getPeriodLimit();
        boolean useExperienceMultiplier = reportDefinition.isUseExprienceMultiplier();

        List<Employee> employeesInPeriodLimit = Arrays.stream(employees)
                .filter(employee -> employee.getSalesPeriod() <= periodLimit)
                .collect(Collectors.toList());

        calculateScoreForEveryEmployee(employeesInPeriodLimit, useExperienceMultiplier);

        List<Employee> sortByScoreDescendingOrder = employeesInPeriodLimit.stream()
                .sorted(Comparator.comparingDouble(Employee::getScore).reversed())
                .collect(Collectors.toList());

        List<Employee> employeesByTopPerformersThreshold = sortByScoreDescendingOrder.stream()
                .limit(topPerformersThreshold)
                .collect(Collectors.toList());

        return employeesByTopPerformersThreshold;
    }

    public void createReportFile(List<Employee> topEmployees, String reportDirectoryPath, String reportFileName, String reportFileExtension) {
        FileIO fileIO = new FileIO();
        DecimalFormat decimalFormat = createDecimalFormat("###.##", '.');
        String firstColumnName = "Name";
        String secondColumnName = "Score";

        String csvFormatReport = createCsvFormatReport(topEmployees,
                decimalFormat, firstColumnName, secondColumnName);

        //Print result as csv
        // System.out.println(csvFormatReport);

        fileIO.writeFile(reportDirectoryPath, reportFileName, reportFileExtension, csvFormatReport);
    }

    public String inputReportDirectoryPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(INPUT_REPORT_DIRECTORY_PATH_MESSAGE);

        String reportDirectoryPath = scanner.nextLine();
        String validatedDirectoryPath = validatedDirectoryPath(reportDirectoryPath);

        return validatedDirectoryPath;
    }

    private String inputDataFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(INPUT_DATA_FILE_PATH_MESSAGE);
        String dataFilePath = scanner.nextLine();

        return dataFilePath;
    }

    private static String inputReportDefinitionFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(INPUT_REPORT_DEFINITION_FILE_PATH_MESSAGE);
        String reportDefinitionFilePath = scanner.nextLine();

        return reportDefinitionFilePath;
    }

    private Employee[] convertJsonToEmployees(String dataInJsonFormat) {
        Gson gson = new Gson();
        Employee[] employees = gson.fromJson(dataInJsonFormat, Employee[].class);

        return employees;
    }

    private ReportDefinition convertJsonToReportDefinition(String dataInJsonFormat) {
        Gson gson = new Gson();
        ReportDefinition reportDefinition = gson.fromJson(dataInJsonFormat, ReportDefinition.class);

        return reportDefinition;
    }

    private void calculateScoreForEveryEmployee(List<Employee> employees, boolean useExperienceMultiplier) {
        for (Employee employee : employees) {
            int salesPeriod = employee.getSalesPeriod();
            double experienceMultiplier = employee.getExperienceMultiplier();
            int totalSales = employee.getTotalSales();

            double score = employee.calculateScore(useExperienceMultiplier, totalSales, salesPeriod, experienceMultiplier);
            employee.setScore(score);
        }
    }

    private String createCsvFormatReport(List<Employee> employees, DecimalFormat decimalFormat, String firstColumnName, String secondColumnName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstColumnName).append(", ").append(secondColumnName).append("\n");
        for (Employee employee : employees) {

            String name = employee.getName().trim();
            double score = employee.getScore();
            String formattedScore = decimalFormat.format(score);

            stringBuilder.append(name).append(", ").append(formattedScore).append("\n");
        }
        String csvFormatReport = stringBuilder.toString();
        return csvFormatReport;

    }

    private DecimalFormat createDecimalFormat(String pattern, char decimalSeparator) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();

        decimalFormatSymbols.setDecimalSeparator(decimalSeparator);
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);

        return decimalFormat;
    }


    private String validatedFilePath(String filePath) {
        Scanner scanner = new Scanner(System.in);
        boolean isFile = new File(filePath).isFile();
        while (!isFile) {
            System.out.println(VALIDATION_FILE_PATH_MESSAGE);
            filePath = scanner.nextLine();
            isFile = new File(filePath).isFile();
        }
        return filePath;
    }

    private String validatedDirectoryPath(String directoryPath) {
        Scanner scanner = new Scanner(System.in);
        boolean isDirectory = new File(directoryPath).isDirectory();
        while (!isDirectory) {
            System.out.println(VALIDATION_DIRECTORY_PATH_MESSAGE);
            directoryPath = scanner.nextLine();
            isDirectory = new File(directoryPath).isDirectory();
        }
        return directoryPath;
    }

}
