package com.example.springboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class XyzJdbc {
    public Connection conn; // 连接对象
    public PreparedStatement pstmt; // 预处理通道
    public ResultSet rs;// 查询集合
    public XyzJdbc() {
        // 1.加载类驱动
        // url指向要访问的数据库my_DB
        String url = "jdbc:mysql://localhost:3306/xyz";
        // mysql用户名
        String user = "root";
        // 密码
        String password = "qq282246101";
        /*
         * try {
         * Class.forName("com.mysql.cj.jdbc.driver");
         * } catch (ClassNotFoundException e) {
         * System.out.println("找不到驱动程序类 ，加载驱动失败！");
         * e.printStackTrace();
         * 
         * }
         */
        try {
            this.conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException se) {
            System.out.println("数据库连接失败！");
            se.printStackTrace();

        }
    }

    public int rowCount(String sql, Object[] objects) throws Exception {
        this.pstmt = this.conn.prepareStatement(sql);
        int count = 0;
        if (objects == null || objects.equals("")) {
            count = this.pstmt.executeUpdate();
            this.XyzJdbcClose();
            return count;
        } else {
            for (int i = 0; i < objects.length; i++) {
                this.pstmt.setObject(i + 1, objects[i]);
            }
            count = this.pstmt.executeUpdate();
            this.XyzJdbcClose();
            return count;
        }
    }

    public Map<String, Object> fetchLine(String sql, Object[] objects) throws Exception {
        this.pstmt = this.conn.prepareStatement(sql);
        // var map = new Hashtable<String, String>();
        Map<String, Object> map = new HashMap<String, Object>();

        if (objects == null) {
            this.rs = this.pstmt.executeQuery();
            this.rs.next();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();// 获取列数量
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);// 通过序号获取列名 起始值为1
                String ColumnValue = rs.getString(columnName);// 通过列名取值 空为NULL
                map.put(columnName, ColumnValue);
            }
            this.XyzJdbcClose();
            return map;
        } else {
            // 如果不为空 循环给所有 ? 赋值 下标是从 1开始
            for (int i = 0; i < objects.length; i++) {
                this.pstmt.setObject(i + 1, objects[i]);
            }
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();// 获取列数量
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);// 通过序号获取列名 起始值为1
                    String ColumnValue = rs.getString(columnName);// 通过列名取值 空为NULL
                    map.put(columnName, ColumnValue);
                }
            }
            this.XyzJdbcClose();
            return map;
        }
    }

    public Map<Integer, Map> fetchAll(String sql, Object[] objects) throws Exception {
        this.pstmt = this.conn.prepareStatement(sql);
        int ii = 0;
        Map<Integer, Map> maps = new HashMap<Integer, Map>();
        if (objects == null) {
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();// 获取列数量
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);// 通过序号获取列名 起始值为1
                    String ColumnValue = rs.getString(columnName);// 通过列名取值 空为NULL
                    map.put(columnName, ColumnValue);
                }
                maps.put(ii, map);
                ii++;
            }
            this.XyzJdbcClose();
            return maps;
        } else {
            // 如果不为空 循环给所有 ? 赋值 下标是从 1开始
            for (int i = 0; i < objects.length; i++) {
                this.pstmt.setObject(i + 1, objects[i]);
            }
            this.rs = this.pstmt.executeQuery();
            while (this.rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();// 获取列数量
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);// 通过序号获取列名 起始值为1
                    String ColumnValue = rs.getString(columnName);// 通过列名取值 空为NULL
                    map.put(columnName, ColumnValue);
                }
                maps.put(ii, map);
                ii++;
            }
            this.XyzJdbcClose();
            return maps;
        }

    }

    public void XyzJdbcClose() throws Exception {
        if (this.rs != null) {
            rs.close();
        }
        if (this.pstmt != null) {
            this.pstmt.close();
        }
        if (this.conn != null) {
            this.conn.close();
        }
    }
}
/*
     String sql="UPDATE users SET grade=? WHERE user=?";
        XyzJdbc x=new XyzJdbc();
       try {
        var a= x.rowCount(sql, new Object[]{ 8 ,"jishu" });
        System.out.println(a);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

*/