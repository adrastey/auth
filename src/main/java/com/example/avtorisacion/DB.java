package com.example.avtorisacion;

import java.sql.*;

public class DB {

    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "priloj_java";
    private final String LOGIN = "root";
    private final String PASS = "root";

    private Connection dbConn = null;

    //Подключение к jdbc и mysql
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    //Проверка на подключение
    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }

    public boolean isExistUser(String login) {
        String sql = "SELECT `id` FROM `users` WHERE `login` = ?";
        try
        {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,login);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void regUser(String login, String email, String password) {
        String sql = "INSERT INTO `users` (`login`, `email`, `password`) VALUES(?, ?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,login);
            prSt.setString(2,email);
            prSt.setString(3,password);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean authUser(String login, String password) {
        String sql = "SELECT `id` FROM `users` WHERE `login` = ? AND `password` = ?";
        try
        {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,login);
            prSt.setString(2, password);

            ResultSet res = prSt.executeQuery();
            return res.next();
        } catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getArticles() {
        String sql = "SELECT `title`, `intro` FROM `articles`";
        Statement statement = null;
        try {
            statement = getDbConnection().createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addArticle(String title, String intro, String fullText) {
        String sql = "INSERT INTO `articles` (`title`, `intro`, `text`) VALUES(?, ?, ?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1,title);
            prSt.setString(2,intro);
            prSt.setString(3,fullText);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTextArea(int id) {
        String sql = "SELECT `text` FROM `articles` WHERE `id` = ?";
        try
        {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, String.valueOf(id));

            return String.valueOf(prSt.executeUpdate());
        } catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public String getTitle(int id) {
        String sql = "SELECT `title` FROM `articles` WHERE `id` = ?";
        try
        {
            PreparedStatement prSt = getDbConnection().prepareStatement(sql);
            prSt.setString(1, String.valueOf(id));

            return String.valueOf(prSt.executeUpdate());
        } catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
}
