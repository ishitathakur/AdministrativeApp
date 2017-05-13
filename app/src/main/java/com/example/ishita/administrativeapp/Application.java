package com.example.ishita.administrativeapp;

import java.io.Serializable;

public class Application implements Serializable {

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getDeparture_Date() {
        return Departure_Date;
    }

    public void setDeparture_Date(String departure_Date) {
        Departure_Date = departure_Date;
    }

    public String getDeparture_Time() {
        return Departure_Time;
    }

    public void setDeparture_Time(String departure_Time) {
        Departure_Time = departure_Time;
    }

    public String getArrival_Date() {
        return Arrival_Date;
    }

    public void setArrival_Date(String arrival_Date) {
        Arrival_Date = arrival_Date;
    }

    public String getArrival_Time() {
        return Arrival_Time;
    }

    public void setArrival_Time(String arrival_Time) {
        Arrival_Time = arrival_Time;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Boolean getAcceptedWarden() {
        return AcceptedWarden;
    }

    public void setAcceptedWarden(Boolean acceptedWarden) {
        AcceptedWarden = acceptedWarden;
    }

    public Boolean getAcceptedAttendant() {
        return AcceptedAttendant;
    }

    public void setAcceptedAttendant(Boolean acceptedAttendant) {
        AcceptedAttendant = acceptedAttendant;
    }
    public String getFirstName() {
        return First_Name;
    }

    public void setFirstame(String firstname) {
        First_Name = firstname;
    }

    public String getLastName() {
        return Last_Name;
    }

    public void setLastame(String lastname) {
        Last_Name = lastname;
    }


    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getRollNo() {
        return Roll_No;
    }

    public void setRollNo(String rollNo) {
        Roll_No = rollNo;
    }

    public String getRoomNo() {
        return Room_No;
    }

    public void setRoomNo(String roomNo) {
        Room_No = roomNo;
    }

    public String getHostel() {
        return Hostel;
    }

    public void setHostel(String hostel) {
        Hostel = hostel;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPhoneNo() {
        return Phone_No;
    }

    public void setPhoneNo(String phoneNo) {
        Phone_No = phoneNo;
    }

    public String getParentPhoneNo() {
        return Parent_Phone_No;
    }

    public void setParentPhoneNo(String parentPhoneNo) {
        Parent_Phone_No = parentPhoneNo;
    }
    private String Place;
    private String Purpose;
    private String Departure_Date;
    private String Departure_Time;
    private String Arrival_Date;
    private String Arrival_Time;
    private String Id;
    private Boolean AcceptedWarden;
    private Boolean AcceptedAttendant;
    private String First_Name;
    private String Last_Name;
    private String  Roll_No;
    private String  Room_No;
    private String Hostel;
    private String Phone_No;
    private String Parent_Phone_No;
    private String Department;
    private String Semester;
}
