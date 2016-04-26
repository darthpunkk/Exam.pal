package com.example.android.exampal;

/**
 * Created by Vedant on 4/23/2016.
 */
public class FacultyInfo {

    private String facultyName;
    private String facultyDept;
    private  String facultyNum;
    private String facultyRno;

    public  FacultyInfo(){
    }

    public FacultyInfo(String facultyName,String facultyDept,String facultyNum,String facultyRno){
        this.facultyName=facultyName;
        this.facultyDept=facultyDept;
        this.facultyNum=facultyNum;
        this.facultyRno=facultyRno;
    }

    public String getFacultyNum() {
        return facultyNum;
    }

    public void setFacultyNum(String facultyNum) {
        this.facultyNum = facultyNum;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyDept() {
        return facultyDept;
    }

    public void setFacultyDept(String facultyDept) {
        this.facultyDept = facultyDept;
    }

    public String getFacultyRno() {
        return facultyRno;
    }

    public void setFacultyRno(String facultyRno) {
        if(!(facultyRno.equals("null")||facultyRno.equals("")))
        this.facultyRno = facultyRno;
        else
            this.facultyRno = "000";
    }
}
