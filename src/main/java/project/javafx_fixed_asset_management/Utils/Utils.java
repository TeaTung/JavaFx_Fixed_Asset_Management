package project.javafx_fixed_asset_management.Utils;

import project.javafx_fixed_asset_management.Models.UNIT;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
            while ( resultSet.next()){
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

    }
