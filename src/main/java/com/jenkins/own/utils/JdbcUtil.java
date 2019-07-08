package com.jenkins.own.utils;

import java.sql.*;

public class JdbcUtil {

    private static final String SQLURL = "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    /**
     * 获取连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConn() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(SQLURL, USERNAME, PASSWORD);
        return connection;
    }


    /**
     * 释放资源的方法
     */
    public static void close(Statement stmt,Connection conn){
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * 释放资源的方法
     */
    public static void close(ResultSet rs,Statement stmt,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }




    /**
     * 查询操作
     */

    public static void selcetTest() throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtil.getConn();
        String sql = "SELECT * FROM sys_log WHERE id < ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,100000);
        ResultSet resultSet = ppst.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("username"));
            System.out.println(resultSet.getString("operation"));
            System.out.println(resultSet.getString("params"));
        }
        close(resultSet,ppst,conn);
    }

    /**
     * 插入操作
     */

    public static void insertTest() throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtil.getConn();
        String sql = "INSERT INTO test(`id`,`name`,`password`) VALUES (?,?,?)";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,123456);
        ppst.setString(2,"haohong");
        ppst.setString(3,"ha1ohong");
        ppst.executeUpdate();
        close(ppst,conn);
    }



    /**
     * 更新操作
     */

    public static void updateTest() throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtil.getConn();
        String sql = "UPDATE test SET name = ? ,password = ? where id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1,"haoc2hong");
        ppst.setString(2,"ha1czcohong");
        ppst.setInt(3,123456);
        int i = ppst.executeUpdate();
        System.out.println(i);
        close(ppst,conn);
    }


    /**
     * 更新操作
     */

    public static void deleteTest() throws SQLException, ClassNotFoundException {
        Connection conn = JdbcUtil.getConn();
        String sql = "DELETE FROM test where id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,123456);
        int i = ppst.executeUpdate();
        System.out.println(i);
        close(ppst,conn);
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
      //  selcetTest();
     //   insertTest();
     //   updateTest();
        deleteTest();
    }

}
