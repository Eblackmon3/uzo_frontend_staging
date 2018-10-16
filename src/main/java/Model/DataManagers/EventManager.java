package Model.DataManagers;

import Model.DataObjects.Event;
import Model.DataObjects.Job;
import Model.DataObjects.JobInsert;
import Model.DataObjects.StudentEvent;
import Model.DbConn;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class EventManager {

    public JSONObject insertEvent(Event event){
        JSONObject insertedJob= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_event_info(company_id, dress_code, duration, open, event_title, date, time , description) " +
                "Values(?,?, ?,  ?,?,?, ?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(event.getDate()==null  || event.getDress_code()==null
                    ||  event.getEvent_title()==null|| event.getCompany_id()==0 || event.getDescription()==null){
                throw new Exception("Missing Parameter");
            }

            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, event.getCompany_id());
            pstmt.setString(2, event.getDress_code());
            pstmt.setDouble(3, event.getDuration());
            pstmt.setBoolean(4, event.isOpen());
            pstmt.setString(5, event.getEvent_title());
            pstmt.setString(6, event.getDate());
            pstmt.setInt(7, event.getTime());
            pstmt.setString(8, event.getDescription());


            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedJob.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedJob.put("error", e.toString());
            }catch (Exception f){
                f.printStackTrace();
            }

        }finally{
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return insertedJob;

    }


    public JSONArray getEventStudentList(Event event){
        //One of the students that are associated with a job
        JSONObject selectedEventStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String phone_number=""; String state="";  String street="";  String city ="";  String apt="";
        String date_of_birth= ""; String major=""; int year=0;
        String description=""; String zipcode="";
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_student_event_map where event_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(event.getEvent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, event.getEvent_id());
            rs= pstmt.executeQuery();

            while(rs.next()){
                jobStudentID=rs.getInt("student_id");
                jobsStudents.add(jobStudentID);
            }

            pstmt = conn.prepareStatement(sql2);
            System.out.println(jobsStudents.size());
            for(int i=0;i<jobsStudents.size();i++){
                pstmt.setInt(1, jobsStudents.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){
                    student_id=rs.getInt("student_id");
                    email=rs.getString("email");
                    first_name=rs.getString("first_name");
                    last_name=rs.getString("last_name");
                    university=rs.getString("university");
                    phone_number= rs.getString("phone_number");
                    state=rs.getString("state");
                    street=rs.getString("street");
                    city=rs.getString("city");
                    apt=rs.getString("apt");
                    date_of_birth=rs.getString("date_of_birth");
                    major=rs.getString("major");
                    year= rs.getInt("year");
                    zipcode= rs.getString("zipcode");
                    description=rs.getString("description");
                    selectedEventStudent.put("student_id",student_id);
                    selectedEventStudent.put("email",email);
                    selectedEventStudent.put("first_name",first_name);
                    selectedEventStudent.put("last_name",last_name);
                    selectedEventStudent.put("university",university);
                    selectedEventStudent.put("phone_number",phone_number);
                    selectedEventStudent.put("street",street);
                    selectedEventStudent.put("state",state);
                    selectedEventStudent.put("city",city);
                    selectedEventStudent.put("apt",apt);
                    selectedEventStudent.put("date_of_birth",date_of_birth);
                    selectedEventStudent.put("major",major);
                    selectedEventStudent.put("year",year);
                    selectedEventStudent.put("description",description);
                    selectedEventStudent.put("zipcode",zipcode);
                    selectedStudents.put(selectedEventStudent);
                    selectedEventStudent=new JSONObject();

                }

            }

            pstmt.close();
            conn.close();
            rs.close();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedEventStudent.put("error", e.toString());
                selectedStudents=new JSONArray();
                selectedStudents.put(selectedEventStudent);

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return selectedStudents;
    }



    public JSONObject getEventById(Event event){
        JSONObject selectedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int event_id;
        String date;
        String dress_code;
        double duration;
        boolean open;
        String event_title;
        int time;
        int company_id;
        String description;
        DbConn jdbcObj = new DbConn();
        String sql= "select * from t_event_info where event_id=?";
        try {
            if(event.getEvent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, event.getEvent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                event_id=rs.getInt("event_id");
                date=rs.getString("date");
                dress_code= rs.getString("dress_code");
                duration = rs.getDouble("duration");
                open= rs.getBoolean("open");
                event_title= rs.getString("event_title");
                company_id=rs.getInt("company_id");
                time=rs.getInt("time");
                description= rs.getString("description");
                selectedStudentJob.put("event_id",event_id);
                selectedStudentJob.put("date",date);;
                selectedStudentJob.put("dress_code",dress_code);
                selectedStudentJob.put("duration",duration);
                selectedStudentJob.put("open", open);
                selectedStudentJob.put("event_title", event_title);
                selectedStudentJob.put("company_id",company_id);
                selectedStudentJob.put("time", time);
                selectedStudentJob.put("description",description);
            }
            pstmt.close();
            conn.close();
            rs.close();

        }catch( Exception e){
            e.printStackTrace();
            try {
                selectedStudentJob.put("error", e.toString());
            }catch( Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }


        return  selectedStudentJob;
    }

    /*

    TODO: Add a call using student_id and company_id to determine if the student has actually been to an event
     */


    public JSONObject DetermineStudentCompleteEvent(StudentEvent studentEvent){
        JSONObject selectedStudentEvents= new JSONObject();
        JSONArray selectedEvents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        boolean completed;
        JSONObject studentsComplete= new JSONObject();
        String sql="select completed from t_student_event_map where student_id =? and company_id=?";
        DbConn jdbcObj = new DbConn();
        try{
            if(studentEvent.getStudent_id()==0 ||studentEvent.getCompany_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentEvent.getStudent_id());
            pstmt.setInt(2, studentEvent.getCompany_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                completed=rs.getBoolean("completed");
                studentsComplete.put("completed",completed);
            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();

        }catch(Exception e){
            e.printStackTrace();
            try{
                studentsComplete.put("completed",false);
                return studentsComplete;

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return studentsComplete;
    }

}
