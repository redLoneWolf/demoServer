package com.example.demoserver.data;

import com.example.demoserver.Database;

import java.sql.*;

public class UserDao {

    static public User save(String email,String password) {
        Connection connection= Database.getConnection();
        User out = null;
        int status = 0;
        String query = "insert into users(email, password) values ('"+email+"','"+password+"');";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            status = pstmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if(status==1){
                ResultSet keys = pstmt.getGeneratedKeys();
                keys.next();
                out =  get(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
    public static User get(String email,String password){

        Connection connection= Database.getConnection();
        User out = null;
        String query = "select id,email from users where email='"+email+"' and password='"+password+"' limit 1;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet set = pstmt.executeQuery(query);
            while (set.next()){
                out = new User(set.getInt("id"),set.getString("email"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public static User get(int id){
        Connection connection= Database.getConnection();
        User out = null;
        String query = "select id,email from users where id="+id+" limit 1;";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet set = pstmt.executeQuery(query);
            while (set.next()){
                out = new User(set.getInt("id"),set.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}
