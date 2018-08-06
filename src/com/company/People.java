package com.company;
import java.sql.Date;

public class People {

    private int id_people;
    private String fullName;
    private Date dateOfBirth;

    public int getId() {
        return id_people;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(int aid) {
        id_people = aid;
    }

    public void setDateOfBirth(Date adateofBirth) {
       dateOfBirth = adateofBirth;
    }

    public void setFullName(String afullName) {
       fullName = afullName;
    }

    public People(int aid , String afullName, Date adateofBirth ){
        id_people = aid;
        fullName = afullName;
        dateOfBirth = adateofBirth;
    }

    public People(){

    }

    public void  print(){
        System.out.println("id: "+ id_people );
        System.out.println("fullName: "+ fullName );
        System.out.println("dateOfBirth: "+ dateOfBirth.toString() );
    }
}
