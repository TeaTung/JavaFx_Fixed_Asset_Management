package project.javafx_fixed_asset_management.Models;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DATABASE_DAO<T> {

    final Class<T> typeParameterClass;

    public DATABASE_DAO(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public T selectOne(String selectSQl, String... args) {
        Connection conn = CONNECT_DB.connect_db.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        T emp = null;

        DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        ResultSetHandler<T> resultHandler = new BeanHandler<T>(this.typeParameterClass);
        try {
            if (args.length != 0) {
                emp = queryRunner.query(conn,
                        selectSQl, resultHandler, args);
            } else {
                emp = queryRunner.query(conn,
                        selectSQl, resultHandler);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return emp;
    }

    public List<T> selectList(String selectSQL, String... args) {
        Connection conn = CONNECT_DB.connect_db.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        List<T> emp = new ArrayList<>();
        DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        ResultSetHandler<List<T>> resultHandler = new BeanListHandler<T>(typeParameterClass);
        try {
            if (args.length != 0) {
                emp = queryRunner.query(conn,
                        selectSQL, resultHandler, args);
            } else {
                emp = queryRunner.query(conn,
                        selectSQL, resultHandler);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return emp;
    }


    //    public List<Object> selectJoin(String selectSQL, String... args) {
//        Connection conn = CONNECT_DB.connect_db.getConnection();
//        QueryRunner queryRunner = new QueryRunner();
//        List<Object> emp = new ArrayList<>();
//        DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//        ResultSetHandler<List<Object>> resultHandler = new BeanListHandler<Object>(Object.class);
//        try {
//            if (args.length != 0) {
//                emp = queryRunner.query(conn,
//                        selectSQL, resultHandler, args);
//            } else {
//                emp = queryRunner.query(conn,
//                        selectSQL, resultHandler);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } finally {
//            try {
//                DbUtils.close(conn);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return emp;
//    }
    public int update(String updateSQL, String... arg) {
        Connection conn = CONNECT_DB.connect_db.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        int recordChanges = 0;
        try {
            recordChanges = queryRunner.update(conn, updateSQL, arg);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return recordChanges;
    }

    public int insert(String insertSQL, String... arg) {
        Connection conn = CONNECT_DB.connect_db.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        int numRowsInserted = 0;
        try {
            if (arg.length != 0) {
                numRowsInserted = queryRunner.update(conn, insertSQL, arg);
            } else {
                numRowsInserted = queryRunner.update(conn, insertSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numRowsInserted;
    }

    public int delete(String deleteSQL, String... arg) {
        Connection conn = CONNECT_DB.connect_db.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        int numRowsDeleted = 0;
        try {
            if (arg.length != 0) {
                numRowsDeleted = queryRunner.update(conn, deleteSQL, arg);
            } else {
                numRowsDeleted = queryRunner.update(conn, deleteSQL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return numRowsDeleted;
    }


}
