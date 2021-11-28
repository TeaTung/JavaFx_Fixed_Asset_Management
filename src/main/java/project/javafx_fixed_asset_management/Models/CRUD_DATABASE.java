package project.javafx_fixed_asset_management.Models;

import javafx.concurrent.Task;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class CRUD_DATABASE extends CONNECT_DB {

    public CRUD_DATABASE() {
        super();
    }

    public static CRUD_DATABASE crud_database = new CRUD_DATABASE();


    /*
     INSERT INTO tbUnit( unitId, UnitName, note ) values ( 'unit7', "DIEN THOAI C", "NOTE")

     crud_database.insert("tbUnit", new String[] {"unitId" , "unitName", "Note"},
                new String[]{"unit7", "DIEN THOAI C", "NOTE"});
     */
    public int insert(String tableName, String[] columns, String[] values) {

        int number = Math.min(columns.length, values.length);
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < number; i++) {
            queryBuilder.append(columns[i]);
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(") ");
        queryBuilder.append(" VALUES (");
        for (int i = 0; i < number; i++) {
            queryBuilder.append("'");
            queryBuilder.append(values[i]);
            queryBuilder.append("'");
            if (i < number - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(");");

        int result = 1;
        try {
            Connection con = this.getConnection();
            String sql_query = queryBuilder.toString();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            System.out.println("INSERT " + tableName + " succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống INSERT " + tableName);
            err.printStackTrace();
            result = 0;
        }
        return result;
    }


    /*
    DELETE FROM tbUnit where unitId = 'unit4'
    crud_database.delete("tbUnit", "unitId" , "unit4");
     */
    public int delete(String tableName, String nameOfTablePrimaryKey, String primaryIdValue) {
        int result = 1;
        try {
            Connection con = this.getConnection();

            String sql_query = "DELETE FROM " + tableName;

            if (!nameOfTablePrimaryKey.isEmpty()) {
                sql_query += " WHERE " + nameOfTablePrimaryKey + " = '" + primaryIdValue + "'";
            }

            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            System.out.println("DELETE " + tableName + " succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống DELETE " + tableName);
            err.printStackTrace();
            result = 0;
        }


        return result;
    }


    /*
    update tbUnit set UnitName = 'A', Note = 'Note' where unitId = 'unit1'
    => crud_database.update("tbUnit", "unitId" , "unit1", new String[] {"unitName", "note"}, new String[] {"A", "Note"});
     */
    public int update(String tableName, String nameOfFieldToSearch, String valueOfFieldToSearch, String[] columns, String[] values) {
        int number = Math.min(columns.length, values.length);
        StringBuilder queryBuilder = new StringBuilder("UPDATE " + tableName + " SET ");

        //  COLLUMNS
        for (int i = 0; i < number; i++) {
            queryBuilder.append(columns[i]);
            queryBuilder.append(" = ");
            queryBuilder.append("'");
            queryBuilder.append(values[i]);
            queryBuilder.append("'");

            if (i < number - 1) queryBuilder.append(", ");
        }

        //  WHERE
        if (!nameOfFieldToSearch.isEmpty()) {
            queryBuilder.append(" WHERE " + nameOfFieldToSearch + " = " + "'" + valueOfFieldToSearch + "'");
        }

        System.out.println(queryBuilder);
        return 1;
    }


    /*
    SELECT * FROM tbDevice WHERE deviceId = 'hello' ORDER BY deviceId
    =>   crud_database.select("", new String[] {},
                "tbDevice", "deviceId", "hello", "deviceId");

    SELECT TOP(1) modelId, deviceStatus FROM tbDevice WHERE modelId = 'model1' ORDER BY deviceId DESC
    =>   crud_database.select("1", new String[] {"modelId", "deviceStatus"},
                "tbDevice", "modelId", "model1", "deviceId DESC");
     */
    public ResultSet select(String selectTopNumber, String[] columns,
                            String tableName, String nameOfFieldToSearch, String valueOfFieldToSearch,
                            String orderBy) {

        StringBuilder queryBuilder = new StringBuilder("SELECT ");


        //  SELECT TOP
        if (!selectTopNumber.isEmpty()) {
            queryBuilder.append("TOP(" + selectTopNumber + ") ");
        }


        if (columns.length != 0) {
            int number = columns.length;
            for (int i = 0; i < number; i++) {
                queryBuilder.append(columns[i]);
                if (i < number - 1) queryBuilder.append(", ");
            }
        } else {
            queryBuilder.append(" * ");
        }

        //  FROM
        queryBuilder.append(" FROM " + tableName + " ");

        //  WHERE
        if (!nameOfFieldToSearch.isEmpty()) {
            queryBuilder.append(" WHERE " + nameOfFieldToSearch + " = " + "'" + valueOfFieldToSearch + "' ");
        }

        //  ORDER BY
        if (!orderBy.isEmpty()) {
            queryBuilder.append("ORDER BY " + orderBy);
        }


        System.out.println(queryBuilder.toString());


        ResultSet resultSet = null;
        try {
            Connection con = this.getConnection();
            String sql_query = queryBuilder.toString();
            PreparedStatement pstmt = con.prepareStatement(sql_query, Statement.RETURN_GENERATED_KEYS);
            resultSet = pstmt.executeQuery();
            System.out.println("SELCET " + tableName + " succeed");
        } catch (SQLException err) {
            System.out.println("Lỗi hệ thống SELECT " + tableName);
            err.printStackTrace();
        }
        return resultSet;
    }



    public void aa(){
        ResultSetHandler<UNIT> resultHandler = new BeanHandler<UNIT>(UNIT.class);
        Connection con = this.getConnection();
        try {
            DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            QueryRunner queryRunner = new QueryRunner();
            Object emp = queryRunner.query(con, "SELECT  *  FROM tbUnit", resultHandler);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DbUtils.close(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
