package app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIO {

    public FileIO() {
    }

    public String readFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        String readFileContent = stringBuilder.toString();

        return readFileContent;
    }

    public void writeFile(String directoryPath, String fileName, String fileExtension, String content) {
        String pathSeparator = File.separator;
        String fileExtensionToLowerCase = fileExtension.toLowerCase();
        String pointDelimiter = ".";

        String fullFilePath = directoryPath + pathSeparator + fileName + pointDelimiter + fileExtensionToLowerCase;
        try (FileWriter writer = new FileWriter(fullFilePath);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(content);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
