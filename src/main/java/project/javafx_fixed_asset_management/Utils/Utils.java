package project.javafx_fixed_asset_management.Utils;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import project.javafx_fixed_asset_management.Models.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Utils {
    public static void printResultSet(ResultSet resultSet) {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + ":" + rsmd.getColumnName(i));
                }
                System.out.println("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("END");
    }

    public static ArrayList<UNIT> mapResultToObject(ResultSet resultSet) {
        printResultSet(resultSet);
        ArrayList<UNIT> units = new ArrayList<>();
        try {
            while (resultSet.next()) {
                UNIT unit = new UNIT();
                unit.setUnitId(resultSet.getString(0));
                unit.setUnitName(resultSet.getString(1));
                unit.setNote(resultSet.getString(2));
                units.add(unit);
                System.out.println('1');

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return units;
    }

    public static StringConverter<LocalDate> getConverter(DatePicker datePicker) {
        return new StringConverter<LocalDate>() {
            String pattern = "dd-MM-yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            {
                datePicker.setPromptText(pattern.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    public static void exportExcelRepair(Stage stage, TableView<REPAIR> tableView) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
        spreadsheet.setColumnWidth(1, 15000);
        spreadsheet.setColumnWidth(2, 5000);
        spreadsheet.setColumnWidth(3, 10000);
        spreadsheet.setColumnWidth(4, 10000);
        spreadsheet.setColumnWidth(5, 10000);

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tableView.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tableView.getColumns().get(j).getText());

        }

        for (int i = 0; i < tableView.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tableView.getColumns().size(); j++) {
                if (tableView.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(tableView.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Worksheet", ".xls"));
        File file = fileChooser.showSaveDialog(stage);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();

    }

    public static void exportExcelTransfer(Stage stage, TableView<TRANSFORM_HISTORY> tableView) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
        spreadsheet.setColumnWidth(1, 15000);
        spreadsheet.setColumnWidth(2, 5000);
        spreadsheet.setColumnWidth(3, 10000);
        spreadsheet.setColumnWidth(4, 10000);
        spreadsheet.setColumnWidth(5, 10000);

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tableView.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tableView.getColumns().get(j).getText());

        }

        for (int i = 0; i < tableView.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tableView.getColumns().size(); j++) {
                if (tableView.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(tableView.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Worksheet", ".xls"));
        File file = fileChooser.showSaveDialog(stage);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();

    }

    public static void exportExcelInventory(Stage stage, TableView<INVENTORY> tableView) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
        spreadsheet.setColumnWidth(1, 8000);
        spreadsheet.setColumnWidth(2, 8000);

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tableView.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tableView.getColumns().get(j).getText());

        }

        for (int i = 0; i < tableView.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tableView.getColumns().size(); j++) {
                if (tableView.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(tableView.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Worksheet", ".xls"));
        File file = fileChooser.showSaveDialog(stage);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();

    }

    public static void exportExcelDevices(Stage stage, TableView<DEVICE> tableView) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");
        spreadsheet.setColumnWidth(1, 8000);
        spreadsheet.setColumnWidth(2, 8000);

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < tableView.getColumns().size(); j++) {
            row.createCell(j).setCellValue(tableView.getColumns().get(j).getText());

        }

        for (int i = 0; i < tableView.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < tableView.getColumns().size(); j++) {
                if (tableView.getColumns().get(j).getCellData(i) != null) {
                    row.createCell(j).setCellValue(tableView.getColumns().get(j).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Worksheet", ".xls"));
        File file = fileChooser.showSaveDialog(stage);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();

    }


}
