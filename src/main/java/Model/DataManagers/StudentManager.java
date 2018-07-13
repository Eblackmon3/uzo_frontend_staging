package Model.DataManagers;

import AmazonController.s3Operations;
import Model.DataObjects.*;
import Model.DbConn;
import StripeController.StripeController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class StudentManager {



    public JSONObject getStudentById(Student student){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String first="";String last="";
        String university=""; String phone_number=""; String state="";
        String street=""; String city=""; String apt=""; String zipcode="";
        String date_of_birth= ""; String major=""; int year=0;
        String description="";
        JSONObject studentObj= new JSONObject();
        ResultSet rs=null;
        try {

            if(student.getStudent_id()==0){
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
            pstmt.setInt(1,student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                email=rs.getString("email");
                first=rs.getString("first_name");
                last=rs.getString("last_name");
                university=rs.getString("university");
                phone_number= rs.getString("phone_number");
                state=rs.getString("state");
                street=rs.getString("street");
                city=rs.getString("city");
                apt=rs.getString("apt");
                date_of_birth=rs.getString("date_of_birth");
                major=rs.getString("major");
                year= rs.getInt("year");
                description=rs.getString("description");
                zipcode= rs.getString("zipcode");
            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            studentObj.put("email",email);
            studentObj.put("first_name",first);
            studentObj.put("last_name", last);
            studentObj.put("university",university);
            studentObj.put("phone_number",phone_number);
            studentObj.put("state",state);
            studentObj.put("street",street);
            studentObj.put("city",city);
            studentObj.put("apt",apt);
            studentObj.put("date_of_birth", date_of_birth);
            studentObj.put("major",major);
            studentObj.put("year",year);
            studentObj.put("description",description);
            studentObj.put("zipcode",zipcode);



        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return studentObj;
    }

    public ArrayList<String> getAllStudentsNumbers(){
        Twilio.init(System.getenv("TWILIO_ACCOUNT"),System.getenv("TWILIO_TOKEN"));
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info";
        DbConn jdbcObj = new DbConn();
        ArrayList<String> phoneNumbers= new ArrayList<>();
        String phone_number="";
        ResultSet rs=null;
        try {
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            rs= pstmt.executeQuery();
            while(rs.next()){
                phone_number= rs.getString("phone_number");
                if(phone_number!=null &&phone_number!="" &&phone_number!=" "){
                    phone_number = phone_number.replaceAll("\\D+","");
                    if(phone_number.length()==10){
                        phoneNumbers.add(phone_number);
                        try {
                            Message.creator(new PhoneNumber(phone_number),
                                    new PhoneNumber("6787265534"),
                                    "New Job Posted At: https://uzo-frontend-dev.herokuapp.com/index.html").create();
                        }catch(Exception e ){
                            continue;
                        }

                    }

                }

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();




        } catch (Exception e) {
            e.printStackTrace();
            try {

            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return phoneNumbers;
    }


    public String getStudentsNumber(int student, String forWhat){
        Twilio.init(System.getenv("TWILIO_ACCOUNT"),System.getenv("TWILIO_TOKEN"));
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        ArrayList<String> phoneNumbers= new ArrayList<>();
        String phone_number="";
        ResultSet rs=null;
        try {
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,student);
            rs= pstmt.executeQuery();
            while(rs.next()){
                phone_number= rs.getString("phone_number");
                if(phone_number!=null &&phone_number!="" &&phone_number!=" "){
                    phone_number = phone_number.replaceAll("\\D+","");
                    if(phone_number.length()==10){
                        try {
                            if (forWhat.equals("HKA")){
                                Message.creator(new PhoneNumber(phone_number),
                                        new PhoneNumber("6787265534"),
                                        "You have been accepted as an UZO Student! logon to the app to start searching for jobs!").create();
                        }else if(forWhat.equals("job")){
                                Message.creator(new PhoneNumber(phone_number),
                                        new PhoneNumber("6787265534"),
                                        "You have been accepted for a new job! logon to UZO now to see what it is!").create();

                            } else{
                                Message.creator(new PhoneNumber(phone_number),
                                        new PhoneNumber("6787265534"),
                                        "Your verification code  is: "+forWhat).create();

                            }
                        }catch(Exception e ){
                            continue;
                        }

                    }

                }else{
                    return "student number not in DB";
                }

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();




        } catch (Exception e) {
            e.printStackTrace();
            try {

            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return phone_number;
    }



    public JSONObject getLastInsertedStudent(){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select max(student_id) from t_student_info";
        DbConn jdbcObj = new DbConn();
        int student_id=0;
        JSONObject studentObj= new JSONObject();
        ResultSet rs=null;
        try {
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            rs= pstmt.executeQuery();
            while(rs.next()){
                student_id= rs.getInt("max");
            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            studentObj.put("student_id",student_id);


        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return studentObj;
    }


    public JSONObject insertStudent(Student student){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        ResultSet lastStudent=null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_info(email, password, first_name, last_name, university," +
                "phone_number, date_of_birth, major, year, description, state, street, city, apt, zipcode) Values(?,?,?, ?,?,?, ?,?,?, ?,?, ?,  ? ,?, ?) RETURNING student_id;";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(student.getEmail()==null || student.getPassword()==null ||student.getFirst_name()==null ||
                    student.getLast_name()==null || student.getUniversity()==null || student.getDate_of_birth()==null
                    ||student.getState()==null|| student.getMajor()==null ||student.getYear()==0
                    ||student.getCity()==null || student.getStreet()==null || student.getZipcode()==null){
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
            pstmt.setString(1, student.getEmail());
            pstmt.setString(2,student.getPassword());
            pstmt.setString(3,student.getFirst_name());
            pstmt.setString(4,student.getLast_name());
            pstmt.setString(5,student.getUniversity());
            pstmt.setString(6,student.getPhone_number());
            pstmt.setString(7,student.getDate_of_birth());
            pstmt.setString(8,student.getMajor());
            pstmt.setInt(9,student.getYear());
            pstmt.setString(10,student.getDescription());
            pstmt.setString(11,student.getState());
            pstmt.setString(12,student.getStreet());
            pstmt.setString(13,student.getCity());
            pstmt.setString(14,student.getApt());
            pstmt.setString(15,student.getZipcode());
            boolean didItWork;
             pstmt.execute();
            lastStudent= pstmt.getResultSet();
            int student_id=0;
            while(lastStudent.next()){
                student_id= lastStudent.getInt("student_id");
            }
            pstmt.close();
            lastStudent.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("student_id",student_id);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(lastStudent!=null){
                try {
                    lastStudent.close();
                }catch (Exception e){
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
        return insertedStudent;
    }

    public JSONObject updateStudent(String studentData, String category,int student_id){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_info set " + StringEscapeUtils.escapeJava(category)+ " =? where student_id= "+ student_id+";";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(student_id==0){
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
            if(category.equals("year")) {
                pstmt.setInt(1, Integer.parseInt(studentData));
            }else{
                pstmt.setString(1, studentData);
            }
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            }catch(Exception f){
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
        return insertedStudent;
    }


    public JSONObject registerStudentEvent(StudentEvent studEvent){
        JSONObject insertedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String sql="select * from t_student_event_map where student_id=? and company_id=? and event_id= ?";
        String sql2="insert into t_student_event_map(student_id,company_id, event_id) " +
                "Values(?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if (studEvent.getCompany_id()==0||studEvent.getEvent_id()==0 || studEvent.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, studEvent.getStudent_id());
            pstmt.setInt(2, studEvent.getCompany_id());
            pstmt.setInt(3,studEvent.getEvent_id());
            rs= pstmt.executeQuery();
            if(rs.next()) {
                insertedStudentJob.put("result", "Already assigned to this job");
                return insertedStudentJob;
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, studEvent.getStudent_id());
            pstmt.setInt(2, studEvent.getCompany_id());
            pstmt.setInt(3,studEvent.getEvent_id());
            affectedRows = pstmt.executeUpdate();
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudentJob.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedStudentJob.put("error",e.toString());
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
        return insertedStudentJob;



    }


    public JSONObject assignStudentJob(StudentJob studJob){
        JSONObject insertedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String sql="select * from t_student_job_map where student_id=? and company_id=? and job_id= ?";
        String sql2="insert into t_student_job_map(student_id,company_id, job_id) " +
                "Values(?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if (studJob.getCompany_id()==0||studJob.getJob_id()==0 || studJob.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2, studJob.getCompany_id());
            pstmt.setInt(3,studJob.getJob_id());
            rs= pstmt.executeQuery();
            if(rs.next()) {
                insertedStudentJob.put("result", "Already assigned to this job");
                return insertedStudentJob;
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2, studJob.getCompany_id());
            pstmt.setInt(3,studJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudentJob.put("affected_rows",affectedRows);
            if(affectedRows>0){
                System.out.println("Texting Student "+getStudentsNumber(studJob.getStudent_id(),"job"));
            }

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedStudentJob.put("error",e.toString());
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
        return insertedStudentJob;



    }



    public JSONObject removeStudentJob(StudentJob studJob){
        JSONObject deletedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="delete from t_student_job_map where student_id =? and job_id =?";
        String sql2="update t_job_info set students_on= students_on-1 where job_id=?;";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(studJob.getStudent_id()==0|| studJob.getJob_id()==0){
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
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2,studJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            if(affectedRows>0) {
                pstmt = conn.prepareStatement(sql2);
                pstmt.setInt(1, studJob.getJob_id());
                affectedRows = pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                jdbcObj.closePool();
            }
            deletedStudentJob.put("affected_rows", affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                deletedStudentJob.put("error",e.toString());

            }catch(Exception f){
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
        return deletedStudentJob;

    }


    public JSONObject unregisterStudentEvent(StudentEvent studEvent){
        JSONObject deletedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="delete from t_student_event_map where student_id =? and event_id =?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(studEvent.getStudent_id()==0|| studEvent.getEvent_id()==0){
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
            pstmt.setInt(1, studEvent.getStudent_id());
            pstmt.setInt(2,studEvent.getEvent_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            deletedStudentJob.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                deletedStudentJob.put("error",e.toString());

            }catch(Exception f){
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
        return deletedStudentJob;

    }


    public JSONArray getStudentJobList(Student student){
        JSONObject selectedStudentJob= new JSONObject();
        JSONArray selectedJobs= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        ArrayList<Integer> studentsJobs= new ArrayList<>();
        int job_id;
        String company_name;
        String city;
        String street;
        String state;
        int studentJobID;
        String date;
        String rate;
        String dress_code;
        double duration;
        boolean open;
        String job_title;
        String start_time;
        String end_time;
        int company_id;
        String description;
        String sql2= "select * from t_job_info inner join t_company_info on t_job_info.company_id=t_company_info.company_id where job_id=?";
        String sql="select * from t_student_job_map where student_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(student.getStudent_id()==0){
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
            pstmt.setInt(1, student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                studentJobID=rs.getInt("job_id");
                studentsJobs.add(studentJobID);
            }

            pstmt = conn.prepareStatement(sql2);
            for(int i=0;i<studentsJobs.size();i++){
                pstmt.setInt(1, studentsJobs.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){

                    job_id=rs.getInt("job_id");
                    date=rs.getString("date");
                    rate=rs.getString("rate");
                    dress_code= rs.getString("dress_code");
                    duration = rs.getDouble("duration");
                    open= rs.getBoolean("open");
                    job_title= rs.getString("job_title");
                    company_id=rs.getInt("company_id");
                    start_time=rs.getString("start_time");
                    end_time=rs.getString("end_time");
                    description=rs.getString("description");
                    company_name=rs.getString("company_name");
                    state=rs.getString("state");
                    city=rs.getString("city");
                    street=rs.getString("street");
                    selectedStudentJob.put("job_id",job_id);
                    selectedStudentJob.put("date",date);
                    selectedStudentJob.put("rate",rate);
                    selectedStudentJob.put("dress_code",dress_code);
                    selectedStudentJob.put("duration",duration);
                    selectedStudentJob.put("open", open);
                    selectedStudentJob.put("job_title", job_title);
                    selectedStudentJob.put("company_id",company_id);
                    selectedStudentJob.put("start_time", start_time);
                    selectedStudentJob.put("end_time", end_time);
                    selectedStudentJob.put("description", description);
                    selectedStudentJob.put("company_name",company_name);
                    selectedStudentJob.put("address", street+ " "+ city+ " "+ state);
                    selectedJobs.put(selectedStudentJob);
                    selectedStudentJob=new JSONObject();
                }

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedStudentJob.put("error", e.toString());
                System.out.println(selectedStudentJob.toString());
                selectedJobs= new JSONArray();
                selectedJobs.put(selectedStudentJob);
                return selectedJobs;

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
        return selectedJobs;
    }



    public JSONArray getStudentEventList(Student student){
        JSONObject selectedStudentEvents= new JSONObject();
        JSONArray selectedEvents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        ArrayList<Integer> studentsEvents= new ArrayList<>();
        ArrayList<Boolean> studentsComplete= new ArrayList<>();
        int event_id;
        int studentEventID;
        boolean completed;
        String date;
        String dress_code;
        double duration;
        boolean open;
        String event_title;
        int time;
        int company_id;
        String description;
        String sql2= "select * from t_event_info where event_id=?";
        String sql="select * from t_student_event_map where student_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(student.getStudent_id()==0){
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
            pstmt.setInt(1, student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                studentEventID=rs.getInt("event_id");
                completed=rs.getBoolean("completed");
                studentsEvents.add(studentEventID);
                studentsComplete.add(completed);
            }

            pstmt = conn.prepareStatement(sql2);
            for(int i=0;i<studentsEvents.size();i++){
                pstmt.setInt(1, studentsEvents.get(i));
                completed= studentsComplete.get(i);
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
                    description=rs.getString("description");
                    selectedStudentEvents.put("event_id",event_id);
                    selectedStudentEvents.put("completed",completed);
                    selectedStudentEvents.put("date",date);
                    selectedStudentEvents.put("dress_code",dress_code);
                    selectedStudentEvents.put("duration",duration);
                    selectedStudentEvents.put("open", open);;
                    selectedStudentEvents.put("event_title", event_title);
                    selectedStudentEvents.put("company_id",company_id);
                    selectedStudentEvents.put("time", time);
                    selectedStudentEvents.put("description", description);
                    selectedEvents.put(selectedStudentEvents);
                    selectedStudentEvents=new JSONObject();
                }

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedStudentEvents.put("error", e.toString());
                System.out.println(selectedStudentEvents.toString());
                selectedEvents= new JSONArray();
                selectedEvents.put(selectedStudentEvents);
                return selectedEvents;

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
        return selectedEvents;
    }


    public JSONObject insertStudentOnCall(JobOnCall onCall){
        JSONObject insertedOncall= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_job_on_call(student_id, job_id) Values(?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(onCall.getJob_id()==0|| onCall.getStudent_id()==0){
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
            pstmt.setInt(1, onCall.getStudent_id());
            pstmt.setInt(2,onCall.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedOncall.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedOncall.put("error", e.toString());

            }catch(Exception f){
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
        return insertedOncall;
    }
    public JSONArray getSudentsOncallJobs(Student student){
        JSONObject selectedStudentJob= new JSONObject();
        JSONArray selectedJobs= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        ArrayList<Integer> studentsJobs= new ArrayList<>();
        int job_id;
        int studentJobID;
        String date;
        String rate;
        String dress_code;
        double duration;
        boolean open;
        String job_title;
        String start_time;
        String end_time;
        int company_id;
        String description;
        String sql2= "select * from t_job_info where job_id=?";
        String sql="select * from t_job_on_call where student_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(student.getStudent_id()==0){
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
            pstmt.setInt(1, student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                studentJobID=rs.getInt("job_id");
                studentsJobs.add(studentJobID);
            }

            pstmt = conn.prepareStatement(sql2);
            for(int i=0;i<studentsJobs.size();i++){
                pstmt.setInt(1, studentsJobs.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){
                    job_id=rs.getInt("job_id");
                    date=rs.getString("date");
                    rate=rs.getString("rate");
                    dress_code= rs.getString("dress_code");
                    duration = rs.getDouble("duration");
                    open= rs.getBoolean("open");
                    job_title= rs.getString("job_title");
                    company_id=rs.getInt("company_id");
                    start_time=rs.getString("start_time");
                    end_time=rs.getString("end_time");
                    description= rs.getString("description");
                    selectedStudentJob.put("job_id",job_id);
                    selectedStudentJob.put("date",date);
                    selectedStudentJob.put("rate",rate);
                    selectedStudentJob.put("dress_code",dress_code);
                    selectedStudentJob.put("duration",duration);
                    selectedStudentJob.put("open", open);
                    selectedStudentJob.put("job_title", job_title);
                    selectedStudentJob.put("company_id",company_id);
                    selectedStudentJob.put("description",description);
                    selectedStudentJob.put("start_time", start_time);
                    selectedStudentJob.put("end_time", end_time);
                    selectedJobs.put(selectedStudentJob);
                    selectedStudentJob= new JSONObject();

                }

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedJobs= new JSONArray();
                selectedStudentJob.put("error", e.toString());
                selectedJobs.put(selectedStudentJob);

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
        return selectedJobs;
    }



    public JSONObject updateStudentUniversity(Student student ){
        JSONObject updateUniversity= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_info set university=? where student_id=?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{

            if(student.getStudent_id()==0 ||student.getUniversity()==null){
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
            pstmt.setString(1, student.getUniversity());
            pstmt.setInt(2,student.getStudent_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            updateUniversity.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                updateUniversity.put("error", e.toString());

            }catch(Exception f){
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
        return updateUniversity;
    }



    public JSONObject updateStudentAccepted(Student student ){
        JSONObject updateUniversity= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_info set student_accepted=? where email=? and first_name=? and last_name=? RETURNING student_id; ";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{

            if(student.getFirst_name()==null||student.getLast_name()==null||student.getEmail()==null){
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
            pstmt.setBoolean(1, student.isStudent_accepted());
            pstmt.setString(2,student.getEmail());
            pstmt.setString(3,student.getFirst_name());
            pstmt.setString(4,student.getLast_name());
            pstmt.execute();
            rsObj = pstmt.getResultSet();
            if(rsObj.next()) {
                updateUniversity.put("affected_rows",1);
                affectedRows=1;
                if (affectedRows > 0) {
                    System.out.println("Texting Student " + getStudentsNumber(rsObj.getInt("student_id"), "HKA"));
                }
            }else{
                System.err.print("Something went wront ");
            }
            rsObj.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();


        }catch(Exception e){
            e.printStackTrace();
            try{
                updateUniversity.put("error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rsObj!=null){
                try {
                    rsObj.close();
                }catch (Exception e){
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
        return updateUniversity;
    }


    public JSONObject insertStudentLostNumberRecord(Student student ){
        JSONObject updateUniversity= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String uuid=UUID.randomUUID().toString().substring(0,4);
        String sql2="select student_id from t_student_info where email=? and first_name=? and last_name=?";
        String sql="insert into t_student_lost_password(email, first_name, last_name, student_id, uuid)" +
                " Values(?,?,?,?,?)";
        String sql3="update t_student_lost_password set uuid=? where email=? and first_name=? and last_name=?";
        String sql4="select student_id from t_student_lost_password where student_id=?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{

            if(student.getFirst_name()==null||student.getLast_name()==null||student.getEmail()==null){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt= conn.prepareStatement(sql2);
            pstmt.setString(1, student.getEmail());
            pstmt.setString(2,student.getFirst_name());
            pstmt.setString(3,student.getLast_name());
            pstmt.executeQuery();
            rsObj=pstmt.getResultSet();

            if(rsObj.next()){
                student.setStudent_id(rsObj.getInt("student_id"));

            }else{
                updateUniversity.put("result","information incorrect");
                return updateUniversity;
            }

            pstmt = conn.prepareStatement(sql4);
            pstmt.setInt(1,student.getStudent_id());
            pstmt.executeQuery();
            rsObj=pstmt.getResultSet();
            if(rsObj.next()) {
                pstmt = conn.prepareStatement(sql3);
                pstmt.setString(2, student.getEmail());
                pstmt.setString(3,student.getFirst_name());
                pstmt.setString(4,student.getLast_name());
                pstmt.setString(1,uuid);
                affectedRows= pstmt.executeUpdate();
                updateUniversity.put("affected_rows",affectedRows);

                rsObj.close();
                pstmt.close();
                conn.close();
                jdbcObj.closePool();

            }else {

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, student.getEmail());
                pstmt.setString(2, student.getFirst_name());
                pstmt.setString(3, student.getLast_name());
                pstmt.setString(4, uuid);
                affectedRows = pstmt.executeUpdate();
                updateUniversity.put("affected_rows", affectedRows);
            }

            rsObj.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();

            if(affectedRows>0) {
                getStudentsNumber(student.getStudent_id(), uuid);
            }



        }catch(Exception e){
            e.printStackTrace();
            try{


                    updateUniversity.put("error", e.toString());


            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rsObj!=null){
                try {
                    rsObj.close();
                }catch (Exception e){
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
        return updateUniversity;
    }


    public JSONObject getStudentLostPasswordHash(Student student ){
        JSONObject updateUniversity= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql2="select * from t_student_lost_password where uuid=? and email=? and first_name=? and last_name=?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{

            if(student.getEmail()==null||student.getFirst_name()==null ||student.getLast_name()==null||
                    student.getUUID()==null){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt= conn.prepareStatement(sql2);
            pstmt.setString(1, student.getUUID());
            pstmt.setString(2, student.getEmail());
            pstmt.setString(3, student.getFirst_name());
            pstmt.setString(4, student.getLast_name());

            pstmt.executeQuery();
            rsObj=pstmt.getResultSet();
            if(rsObj.next()){
                updateUniversity.put("result", "correct");
                updateUniversity.put("student_id",rsObj.getInt("student_id"));

            }else{
                updateUniversity.put("result","information incorrect");

            }
            rsObj.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();


        }catch(Exception e){
            e.printStackTrace();
            try{
                updateUniversity.put("error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rsObj!=null){
                try {
                    rsObj.close();
                }catch (Exception e){
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
        return updateUniversity;
    }

    public JSONObject addRating(Student student){
        JSONObject updateUniversity= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        int previousRating=0;
        int previousTimesRated=0;
        String sql="select * from t_student_info where student_id =?";
        String sql2="update t_student_info set total_rating=?, times_rated=? where student_id=?;";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(student.getStudent_id()==0|| student.getTotal_rating()==0){
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
            pstmt.setInt(1, student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                previousRating=rs.getInt("total_rating");
                previousTimesRated=rs.getInt("times_rated");
            }

            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, previousRating+student.getTotal_rating());
            pstmt.setInt(2, previousTimesRated+1);
            pstmt.setInt(3, student.getStudent_id());
            affectedRows=pstmt.executeUpdate();

            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            updateUniversity.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                updateUniversity.put("error", e.toString());

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
        return updateUniversity;

    }


    public JSONObject getStudentAvgRating(Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        int total_rating=1;
        int times_rated=1;
        ResultSet rs=null;
        double averageRating;
        JSONObject studentObj= new JSONObject();
        try {
            if(student.getStudent_id()==0){
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
            pstmt.setInt(1,student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                total_rating=rs.getInt("total_rating");
                times_rated=rs.getInt("times_rated");
            }
            averageRating=(double)total_rating/(double)times_rated;
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            studentObj.put("average_rating",averageRating);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return studentObj;

    }



    public JSONObject uploadStudentResume(MultipartFile file, int student_id){
        String resume_location= s3Operations.uploadStudentFile(student_id,file);
        JSONObject uploadeResume= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_work_history set resume_location=? where student_id=?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(file==null||student_id==0|| file.isEmpty()){
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
            pstmt.setString(1, resume_location);
            pstmt.setInt(2,student_id);
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            uploadeResume.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                uploadeResume.put("error", e.toString());

            }catch(Exception f){
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
        return uploadeResume;

    }

    public JSONObject uploadStudentResource(MultipartFile file, int student_id){
        String reesource_location= s3Operations.uploadStudentFile(student_id,file);
        JSONObject uploadeResource= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_resources(resource_location,student_id, file_name) Values(?,?,?)";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(file==null||student_id==0|| file.isEmpty()){
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
            pstmt.setString(1, reesource_location);
            pstmt.setInt(2,student_id);
            pstmt.setString(3, file.getOriginalFilename());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            uploadeResource.put("affectd_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                uploadeResource.put("error", e.toString());

            }catch(Exception f){
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
        return uploadeResource;
    }

    public JSONObject checkStudentEmail( Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where email=?";
        DbConn jdbcObj = new DbConn();
        ResultSet rs= null;

        JSONObject studentObj= new JSONObject();
        try {
            if(student.getEmail()==null){
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
            pstmt.setString(1,student.getEmail());
            rs= pstmt.executeQuery();
            if(rs.next()){
                studentObj.put("student_email","Student email exist");

            }else{
                studentObj.put("student_email","Student email does not exist ");

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();

        } catch (Exception e) {
            e.printStackTrace();
            try{
                studentObj.put("error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                   rs.close();
                }catch (Exception e){
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

        return studentObj;

    }

    public JSONObject checkStudentLogin( Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String sql="select * from t_student_info where email=? and password=?;";
        DbConn jdbcObj = new DbConn();

        JSONObject studentObj= new JSONObject();
        try {
            if(student.getPassword()==null|| student.getEmail()==null){
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
            pstmt.setString(1,student.getEmail());
            pstmt.setString(2,student.getPassword());
            rs= pstmt.executeQuery();
            if(rs.next()){
                studentObj.put("student_id",rs.getInt("student_id"));
                studentObj.put("email",rs.getString("email"));
                studentObj.put("first_name",rs.getString("first_name"));
                studentObj.put("last_name", rs.getString("last_name"));
                studentObj.put("university",rs.getString("university"));
                studentObj.put("phone_number",rs.getString("phone_number"));
                studentObj.put("state",rs.getString("state"));
                studentObj.put("street",rs.getString("street"));
                studentObj.put("city", rs.getString("city"));
                studentObj.put("apt",rs.getString("apt"));
                studentObj.put("date_of_birth", rs.getString("date_of_birth"));

                studentObj.put("major",rs.getString("major"));
                studentObj.put("year",rs.getString("year"));
                studentObj.put("description",rs.getString("description"));
                studentObj.put("zipcode",rs.getString("zipcode"));


            }else{
                studentObj.put("student_login","Does not exist");

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();



        } catch (Exception e) {
            e.printStackTrace();
            try{
                studentObj.put("error", e.toString());

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

        return studentObj;

    }

    public JSONObject insertStudentAvailability(StudentAvailabilitySlot studentAvail){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        DbConn jdbcObj=null;

        try{
            if(studentAvail.getStudent_id()==0||studentAvail.getDay()==null||studentAvail.getTime()==null){
                throw new Exception( "Missing Parameters");
            }
            String tableName= StringEscapeUtils.escapeJava("t_"+studentAvail.getDay().toLowerCase());
            String sql= "select * from "+tableName+" where student_id=?";
            studentAvail.setTime("\""+StringEscapeUtils.escapeJava(studentAvail.getTime())+"\"");
            String sql2="insert into " +tableName+"("+studentAvail.getTime()+",student_id) Values(?,?);";
            jdbcObj = new DbConn();
            int affectedRows=0;
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt= conn.prepareStatement(sql);
            pstmt.setInt(1,studentAvail.getStudent_id());
            rsObj=pstmt.executeQuery();
            if(rsObj.next()){
                insertedStudent.put(""+studentAvail.getStudent_id(),"student already inserted please use update_student_availability");
                return insertedStudent;
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setBoolean(1,studentAvail.isAvailable());
            pstmt.setInt(2,studentAvail.getStudent_id());
            System.out.print(pstmt.toString());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            }catch(Exception f){
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
        return insertedStudent;
    }


    public JSONObject updateStudentAvailability(StudentAvailabilitySlot studentAvail){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        DbConn jdbcObj=null;
        try{
            if(studentAvail.getStudent_id()==0||studentAvail.getDay()==null||studentAvail.getTime()==null){
                throw new Exception( "Missing Parameters");
            }
            String tableName= StringEscapeUtils.escapeJava("t_"+studentAvail.getDay().toLowerCase());
            studentAvail.setTime("\""+StringEscapeUtils.escapeJava(studentAvail.getTime())+"\"");
            //﻿﻿update t_friday set "0000"=true where student_id =1;
            String sql2="update  " +tableName+" set "+studentAvail.getTime()+"=? where student_id=?";
            jdbcObj = new DbConn();
            int affectedRows=0;
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql2);
            pstmt.setBoolean(1,studentAvail.isAvailable());
            pstmt.setInt(2,studentAvail.getStudent_id());
            System.out.print(pstmt.toString());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            }catch(Exception f){
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
        return insertedStudent;
    }


    public JSONObject insertInterestedStudent(InterestedStudent interestedStudent){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_interested_students_jobs(student_id, job_id) Values(?,?);";
        String sql2= "select * from t_interested_students_jobs where student_id= ? and job_id= ?;";
        String sql3="select student_accepted from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(interestedStudent.getJob_id()==0|| interestedStudent.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, interestedStudent.getStudent_id());
            pstmt.setInt(2, interestedStudent.getJob_id());
            rsObj = pstmt.executeQuery();
            if(rsObj.next()){
                return  insertedStudent.put("result", "student already inserted");
            }
            pstmt = conn.prepareStatement(sql3);
            pstmt.setInt(1, interestedStudent.getStudent_id());
            rsObj = pstmt.executeQuery();
            //System.out.println("Is something returned:"+rsObj.next());
            if(rsObj.next()){
                System.out.println("Is student accepted:"+rsObj.getBoolean("student_accepted"));
                if(!rsObj.getBoolean("student_accepted")) {
                    return insertedStudent.put("result", "student not accepted");
                }
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, interestedStudent.getStudent_id());
            pstmt.setInt(2, interestedStudent.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedStudent.put("error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rsObj!=null){
                try {
                    rsObj.close();
                }catch (Exception e){
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
        return insertedStudent;
    }


    public JSONObject removeInterestedStudent(InterestedStudent interestedStudent){
        JSONObject deletedInterestedStudent= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="delete from t_interested_students_jobs where student_id =? and job_id =?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(interestedStudent.getStudent_id()==0|| interestedStudent.getJob_id()==0){
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
            pstmt.setInt(1, interestedStudent.getStudent_id());
            pstmt.setInt(2,interestedStudent.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            deletedInterestedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                deletedInterestedStudent.put("error",e.toString());

            }catch(Exception f){
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
        return deletedInterestedStudent;

    }


    public JSONObject setStudentJobPreference(StudentJobPreference studentJobPreference){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_job_preferences(student_id, uzo_reason, lift_ability,bike, car, bus) Values(?,?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(studentJobPreference.getStudent_id()==0 || studentJobPreference.getUzo_reason()==null){
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
            pstmt.setInt(1, studentJobPreference.getStudent_id());
            pstmt.setString(2,studentJobPreference.getUzo_reason());
            pstmt.setBoolean(3,studentJobPreference.isLift_ability());
            pstmt.setBoolean(4,studentJobPreference.getBike());
            pstmt.setBoolean(5,studentJobPreference.getCar());
            pstmt.setBoolean(6,studentJobPreference.getBus());

            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            }catch(Exception f){
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
        return insertedStudent;

    }

    public JSONObject getStudentJobPreference(StudentJobPreference student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_job_preferences where student_id=?";
        DbConn jdbcObj = new DbConn();
        String uzo_reason=""; boolean lift_ability=false ;Boolean car=false;
        Boolean bus=false; Boolean bike=false;
        JSONObject studentObj= new JSONObject();
        ResultSet rs= null;
        try {
            if(student.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations heremobility
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                uzo_reason=rs.getString("uzo_reason");
                lift_ability=rs.getBoolean("lift_ability");
                car=rs.getBoolean("car");
                bike=rs.getBoolean("bike");
                bus=rs.getBoolean("bus");

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            studentObj.put("uzo_reason",uzo_reason);
            studentObj.put("lift_ability",lift_ability);
            studentObj.put("car", car);
            studentObj.put("bike", bike);
            studentObj.put("bus", bus);



        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return studentObj;

    }

    public JSONObject insertStudentWorkAbility(StudentWorkAbility studentWorkAbility) {
        JSONObject insertedStudent = new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into t_student_work_ability(student_id, bar, cashier,cleaning,data_entry,desk_assistant" +
                ",driving_delivery,event_security,setup_breakdown,food_service,moving) Values(?,?,?,?,?  ,?," +
                "?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows = 0;
        try {
            if (studentWorkAbility.getStudent_id() == 0) {
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
            pstmt.setInt(1, studentWorkAbility.getStudent_id());
            pstmt.setBoolean(2, studentWorkAbility.isBar());
            pstmt.setBoolean(3, studentWorkAbility.isCashier());
            pstmt.setBoolean(4, studentWorkAbility.isCleaning());
            pstmt.setBoolean(5, studentWorkAbility.isData_entry());
            pstmt.setBoolean(6, studentWorkAbility.isDesk_assistant());
            pstmt.setBoolean(7, studentWorkAbility.isDriving_delivery());
            pstmt.setBoolean(8, studentWorkAbility.isEvent_security());
            pstmt.setBoolean(9, studentWorkAbility.isSetup_breakdown());
            pstmt.setBoolean(10, studentWorkAbility.isFood_service());
            pstmt.setBoolean(11, studentWorkAbility.isMoving());


            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows", affectedRows);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            } catch (Exception f) {
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
        return insertedStudent;
    }

    public JSONObject getStudentWorkAbility(StudentWorkAbility studentWorkAbility) {
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs= null;
        String sql="select * from t_student_work_ability where student_id=?";
        DbConn jdbcObj = new DbConn();
        boolean bar= false; boolean cashier=false; boolean cleaning=false;
        boolean data_entry=false; boolean desk_assistant=false; boolean driving_delivery=false;
        boolean event_security=false; boolean setup_breakdown=false; boolean food_service=false;
        boolean moving=false;
        JSONObject studentObj= new JSONObject();
        try {
            if(studentWorkAbility.getStudent_id()==0){
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
            pstmt.setInt(1,studentWorkAbility.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                bar=rs.getBoolean("bar");
                cashier=rs.getBoolean("cashier");
                cleaning=rs.getBoolean("cleaning");
                data_entry=rs.getBoolean("data_entry");
                desk_assistant= rs.getBoolean("desk_assistant");
                driving_delivery=rs.getBoolean("driving_delivery");
                event_security=rs.getBoolean("event_security");
                setup_breakdown=rs.getBoolean("setup_breakdown");
                food_service= rs.getBoolean("food_service");
                food_service= rs.getBoolean("moving");
            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            studentObj.put("bar",bar);
            studentObj.put("cashier",cashier);
            studentObj.put("cleaning", cleaning);
            studentObj.put("data_entry",data_entry);
            studentObj.put("desk_assistant",desk_assistant);
            studentObj.put("driving_delivery",driving_delivery);
            studentObj.put("event_security", event_security);
            studentObj.put("setup_breakdown",setup_breakdown);
            studentObj.put("moving",moving);
            studentObj.put("food_service",food_service);


        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return studentObj;
    }

    public JSONObject insertStudentWorkHistory(StudentWorkHistory studentWorkHistory) {
        JSONObject insertedStudent = new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into t_student_work_history(student_id, work_reference_1, work_reference_2,work_reference_3,crime,hear_uzo) Values(?,?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows = 0;
        try {
            if (studentWorkHistory.getStudent_id() == 0 || studentWorkHistory.getWork_reference_1()==null ||
                    studentWorkHistory.getWork_reference_2()==null) {
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
            pstmt.setInt(1, studentWorkHistory.getStudent_id());
            pstmt.setString(2, studentWorkHistory.getWork_reference_1());
            pstmt.setString(3, studentWorkHistory.getWork_reference_2());
            pstmt.setString(4, studentWorkHistory.getWork_reference_3());
            pstmt.setBoolean(5, studentWorkHistory.isCrime());
            pstmt.setString(6,studentWorkHistory.getHear_uzo());


            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            insertedStudent.put("affected_rows", affectedRows);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
            } catch (Exception f) {
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
        return insertedStudent;
    }



    public JSONObject getStudentWorkHistory(StudentWorkHistory studentWorkHistory) {
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_work_history where student_id=?";
        DbConn jdbcObj = new DbConn();
        String work_reference_1= ""; String work_reference_2=""; String work_reference_3= "";
        boolean crime=false; String hear_uzo=""; String resume_location="";
        ResultSet rs= null;

        JSONObject studentObj= new JSONObject();
        try {
            if(studentWorkHistory.getStudent_id()==0){
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
            pstmt.setInt(1,studentWorkHistory.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                work_reference_1=rs.getString("work_reference_1");
                work_reference_2=rs.getString("work_reference_2");
                work_reference_3=rs.getString("work_reference_3");
                crime=rs.getBoolean("crime");
                hear_uzo=rs.getString("hear_uzo");
                resume_location=rs.getString("resume_location");
            }
            studentObj.put("work_reference_1",work_reference_1);
            studentObj.put("work_reference_2",work_reference_2);
            studentObj.put("work_reference_3", work_reference_3);
            studentObj.put("crime",crime);
            studentObj.put("hear_uzo",hear_uzo);
            studentObj.put("resume_location",resume_location);
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();


        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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
                if(jdbcObj!=null) {
                    jdbcObj.closePool();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return studentObj;
    }




    public JSONObject insertStudentAccount(StudentAcctTokens account){
        String customer_token= StripeController.createStudentAccount(account);
        JSONObject insertedStudent= new JSONObject();
        ResultSet rs=null;
        int affectedRows=0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_account_tokens(student_id,token) Values(?,?)";
        DbConn jdbcObj = new DbConn();

        try{
            if(account.getStudent_id()==0){
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
            pstmt.setInt(1, account.getStudent_id());
            pstmt.setString(2,customer_token);
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
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
        return insertedStudent;
    }


    public JSONObject getStudentAccount(Student student){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_account_tokens where student_id=?";
        DbConn jdbcObj = new DbConn();
        String account="";
        JSONObject studentObj= new JSONObject();
        ResultSet rs=null;
        try {

            if(student.getStudent_id()==0){
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
            pstmt.setInt(1,student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                account=rs.getString("token");
            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();
            studentObj.put("token",account);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
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

        return studentObj;
    }

}
