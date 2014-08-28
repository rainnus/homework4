package com.studentscore;

import java.sql.*;
import java.util.*;

/**
 * Created by rainnus on 14-5-13.
 */
public class StudentScore {

    public static void main(String[] arg) {
        StudentScore student = new StudentScore();
        DbAccess DB = new DbAccess();
        DB.insert();
        student.getStuNum();

    }

    public void getStuNum() {
        System.out.println("请输入学生数量：");
        Scanner s = new Scanner(System.in);
        int number = s.nextInt();
        infoInput(number);
    }

    public void infoInput(int temp) {
        Scanner s = new Scanner(System.in);
        ArrayList<Student> stuList = new ArrayList<Student>();
        for (int i = 1; i <= temp; i++) {
            System.out.println("请输入第" + i + "位同学的姓名：");
            String name = s.next();
            System.out.println("请输入第" + i + "位同学的成绩：");
            float score = s.nextFloat();
            stuList.add(new Student(name, score));
        }
        methodStu(stuList, temp);

    }

    public void methodStu(ArrayList stuList, int temp) {
        ComparatorStu comparator = new ComparatorStu();
        Collections.sort(stuList, comparator);
        output(stuList);
        scoreEverage(stuList, temp);
        Scanner s = new Scanner(System.in);
        System.out.println("请输入查询分段上限：");
        float upLine = s.nextFloat();
        System.out.println("请输入查询分段下限：");
        float downLine = s.nextFloat();
        scanScore(upLine, downLine, stuList);
    }

    public void output(ArrayList stuList) {
        Iterator it = stuList.iterator();
        System.out.println("学生名称及分数（按分数排序）：");
        while (it.hasNext()) {
            Student usrTemp = (Student) it.next();
            String name = String.format("%6s", usrTemp.getName());
            String score = String.format("%6s", String.valueOf(usrTemp.getScore()));
            System.out.println(name + "   " + score);
        }
    }

    public void scoreEverage(ArrayList stuList, int temp) {
        Iterator it = stuList.iterator();
        float i = 0;
        while (it.hasNext()) {
            Student usrTemp = (Student) it.next();
            i += usrTemp.getScore();
        }
        System.out.println("所有学生分数平均数为：" + i / temp);
    }

    public void scanScore(float up, float down, ArrayList stuList) {
        Iterator it = stuList.iterator();
        System.out.println("查询到的学生为：");
        while (it.hasNext()) {
            Student usrTemp = (Student) it.next();
            int flag = usrTemp.getScore() >= down && usrTemp.getScore() <= up ? 1 : 0;
            if (flag == 1) {
                String name = String.format("%6s", usrTemp.getName());
                String score = String.format("%6s", String.valueOf(usrTemp.getScore()));
                System.out.println(name + "   " + score);
            }
        }
    }
}

class Student {
    private String name;
    private float score;

    Student(String a, float b) {
        name = a;
        score = b;
    }

    public String getName() {
        return name;
    }

    public float getScore() {
        return score;
    }
}

class ComparatorStu implements Comparator {

    public int compare(Object arg0, Object arg1) {
        Student stu0 = (Student) arg0;
        Student stu1 = (Student) arg1;
        int flag = stu0.getScore() > stu1.getScore() ? 1 : 0;
        if (flag == 0) {
            return stu0.getName().compareTo(stu1.getName());
        } else {
            return flag;
        }
    }

}

class DbAccess {

    Connection con;
    Statement stat;
    String sql;
    ResultSet rs;

    DbAccess() {  //构造方法进行初始化
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb,*.access)};DBQ= ../../homework4.mdb");
            stat = con.createStatement();
        } catch(SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void insert() {  //插入数据
        try {
            sql = "insert into table1(stuName,score)values('abc','100')";
            int i = stat.executeUpdate(sql);
            if(i > 0) {    //i的值表示语句操作的记录数量，i=0表示未进行数据库操作
                System.out.println("添加成功！");
            }
        } catch(SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("添加失败！");
            //            e.printStackTrace();
        }
    }

}
