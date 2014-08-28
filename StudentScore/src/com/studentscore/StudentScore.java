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
        System.out.println("������ѧ��������");
        Scanner s = new Scanner(System.in);
        int number = s.nextInt();
        infoInput(number);
    }

    public void infoInput(int temp) {
        Scanner s = new Scanner(System.in);
        ArrayList<Student> stuList = new ArrayList<Student>();
        for (int i = 1; i <= temp; i++) {
            System.out.println("�������" + i + "λͬѧ��������");
            String name = s.next();
            System.out.println("�������" + i + "λͬѧ�ĳɼ���");
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
        System.out.println("�������ѯ�ֶ����ޣ�");
        float upLine = s.nextFloat();
        System.out.println("�������ѯ�ֶ����ޣ�");
        float downLine = s.nextFloat();
        scanScore(upLine, downLine, stuList);
    }

    public void output(ArrayList stuList) {
        Iterator it = stuList.iterator();
        System.out.println("ѧ�����Ƽ����������������򣩣�");
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
        System.out.println("����ѧ������ƽ����Ϊ��" + i / temp);
    }

    public void scanScore(float up, float down, ArrayList stuList) {
        Iterator it = stuList.iterator();
        System.out.println("��ѯ����ѧ��Ϊ��");
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

    DbAccess() {  //���췽�����г�ʼ��
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

    public void insert() {  //��������
        try {
            sql = "insert into table1(stuName,score)values('abc','100')";
            int i = stat.executeUpdate(sql);
            if(i > 0) {    //i��ֵ��ʾ�������ļ�¼������i=0��ʾδ�������ݿ����
                System.out.println("��ӳɹ���");
            }
        } catch(SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("���ʧ�ܣ�");
            //            e.printStackTrace();
        }
    }

}
