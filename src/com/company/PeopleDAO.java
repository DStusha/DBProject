package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class PeopleDAO {

    private String connectionUrl;
    private String user;
    private String password;
    private String sql = null;
    private Properties prop;

    public PeopleDAO() throws IOException {

        FileInputStream fis = new FileInputStream("dbprop.prop");
        prop = new Properties();
        prop.load(fis);
        connectionUrl = prop.getProperty("jdbc")+prop.getProperty("instance")+prop.getProperty("dbName")+"?"+prop.getProperty("flags");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
    }

    public ArrayList<People> getAllPeople(int i)  {
        ArrayList<People> list = new ArrayList<People>();
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            if (i == 1){
                sql = "SELECT * FROM people";
            }
            else {
                sql = "SELECT * FROM people_reserve";
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                People p = new People(rs.getInt(1), rs.getString(3), rs.getDate(2));
                list.add(p);
            }
            con.close();
            st.close();
            rs.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
        return list;
    }

    public ArrayList<People> getPeopleByFIO(int i, String FIO){
        ArrayList<People> list = new ArrayList<People>();
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            if (i == 1){
                sql = "SELECT * FROM people WHERE fullName = ?";
            }
            else {
                sql = "SELECT * FROM people_reserve WHERE fullName = ?";
            }
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, FIO);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                People p = new People(rs.getInt(1), rs.getString(3), rs.getDate(2));
                list.add(p);
            }
            con.close();
            st.close();
            rs.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
        return list;
    }

    public ArrayList<People> getPeopleByBirth(int i, Date date) {
        ArrayList<People> list = new ArrayList<People>();
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            if (i == 1){
                sql = "SELECT * FROM people WHERE dateOfBirth = ?";
            }
            else {
                sql = "SELECT * FROM people_reserve WHERE dateOfBirth = ?";
            }
            PreparedStatement st = con.prepareStatement(sql);
            st.setDate(1, date);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                People p = new People(rs.getInt(1), rs.getString(3), rs.getDate(2));
                list.add(p);
            }
            con.close();
            st.close();
            rs.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
        return list;
    }

    public void addToMainTable(People p){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            sql = "INSERT INTO people (fullName, dateOfBirth) VALUES (?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, p.getFullName());
            st.setDate(2, p.getDateOfBirth());
            st.executeUpdate();
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }

    }
    public void deleteByFullName(String fullName){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            sql = "DELETE FROM people WHERE fullName = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, fullName);
            st.executeUpdate();
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }

    public void deleteByBirth(Date birth){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            sql = "DELETE FROM people WHERE dateOfBirth = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, birth.toString());
            st.executeUpdate();
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }

    public void updateFullName(String oldFullName, String newFullName){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            sql = "UPDATE people SET fullName = ? WHERE fullName = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, newFullName);
            st.setString(2, oldFullName);
            st.executeUpdate();
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }

    public void updateDateOfBirth(Date oldDate, Date newDate){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            sql = "UPDATE people SET dateOfBirth = ? WHERE dateOfBirth = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, newDate.toString());
            st.setString(2, oldDate.toString());
            st.executeUpdate();
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }

    public void copyToReserve(){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            clearTable(2);
            sql= "INSERT INTO people_reserve" +
                    " SELECT * FROM people";
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }

    public void clearTable(int i){
        try {
            Connection con = DriverManager.getConnection(connectionUrl, user, password);
            if (i == 1){
                sql = "DELETE FROM people ";
            }
            else {
                sql = "DELETE FROM people_reserve";
            }
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            con.close();
            st.close();
        }
        catch (SQLException sqlEX) {
            sqlEX.printStackTrace();
        }
    }
}
