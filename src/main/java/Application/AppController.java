package Application;

import AmazonController.s3Operations;
import Model.*;
import Model.DataManagers.CompanyManager;
import Model.DataManagers.EventManager;
import Model.DataManagers.JobManager;
import Model.DataManagers.StudentManager;
import Model.DataObjects.*;
import StripeController.StripeController;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;



import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.SysexMessage;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class  AppController {





    @RequestMapping("/")
    public String index() {
        return("Welcome to the UZO-API");
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/get_student_by_id
      {
       "student_id" :1
      }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value="/get_student_by_id")
    public String getStudentById(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        JSONObject result= manager.getStudentById(student);
        return result.toString();
    }
    /*
     * api call example https://uzo-web-app.herokuapp.com/get_last_inserted_student

     */

    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @GetMapping("/get_last_inserted_student")
    public String getLastInsertedStudent(){
        StudentManager manager= new StudentManager();
        JSONObject result= manager.getLastInsertedStudent();
        return result.toString();
    }


    /*
     * api call example https://uzo-web-app.herokuapp.com/insert_student
     * headers
     * {
         "email": "stephenoakala@gmail.com",
         "password": "281330800fB",
         "first_name": "Stephen",
         "last_name":"Okala",
         "university":"Georgia Tech",
         "phone_number":"571-344-9998",
          "state": "VA",
          "street": "hood avenue",
          "city": "Chantilly",
          "apt": "115",
         "date_of_birth":"1/27/1993",
         "major":" "Computer Science",
         "year":1,
         "description":"realest of the real"
        }
     * used a string as to not process the JSONOBJECT on response
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_student")
    public String insertStudent(@RequestBody Student insertStudent){
        StudentManager manager= new StudentManager();
        return manager.insertStudent(insertStudent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
  * api call example https://uzo-web-app.herokuapp.com/update_student_info
  * All of the paramters are optional
  * headers
  *   {
         "email": "stephenoakala@gmail.com",
         "password": "281330800fB",
         "first_name": "Stephen",
         "last_name":"Okala",
         "university":"Georgia Tech",
         "phone_numnber":"571-344-9998",
          "state": "VA",
          "street": "hood avenue",
          "city": "Chantilly",
          "apt": "115",
          "zipcode":"30326",
         "date_of_birth":"1/27/1993",
         "major": "Computer Science",
         "year":1,
         "description":"realest of the real"
        }
  * used a string as to not process the JSONOBJECT on response
  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_student_info")
    public String updateStudentInfo(@RequestBody Student insertStudent){
        JSONObject obj= new JSONObject();
        int everythingNull=1;
        StudentManager manager= new StudentManager();
        if(insertStudent.getStudent_id()==0) {
            return "{ \"Error\":\"Missing Parameter\"}";
        }
        if(insertStudent.getEmail()!=null){
            manager.updateStudent(insertStudent.getEmail(),"email",insertStudent.getStudent_id() ).toString();
            everythingNull=0;

        }if(insertStudent.getPassword()!=null){
            manager.updateStudent(insertStudent.getPassword(),"password", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getFirst_name()!=null){
            manager.updateStudent(insertStudent.getFirst_name(),"first_name", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getLast_name()!=null){
            manager.updateStudent(insertStudent.getLast_name(),"last_name", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getUniversity()!=null){
            manager.updateStudent(insertStudent.getUniversity(),"university", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getPhone_number()!=null){
            manager.updateStudent(insertStudent.getPhone_number(), "phone_numnber", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getStreet()!=null){
            manager.updateStudent(insertStudent.getStreet(),"street", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getState()!=null){
            manager.updateStudent(insertStudent.getState(),"state", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getApt()!=null){
            manager.updateStudent(insertStudent.getApt(),"apt", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getDate_of_birth()!=null){
            manager.updateStudent(insertStudent.getDate_of_birth(), "date_of_birth", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getMajor()!=null){
            manager.updateStudent(insertStudent.getMajor(), "major", insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getYear()!=0){
            manager.updateStudent(insertStudent.getYear()+"","year",insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getDescription()!=null){
           manager.updateStudent(insertStudent.getDescription(),"description",insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(insertStudent.getZipcode()!=null){
            manager.updateStudent(insertStudent.getZipcode(),"zipcode",insertStudent.getStudent_id()).toString();
            everythingNull=0;

        }if(everythingNull==1) {
            return "{ \"Error\":\"No student detail specified\"}";
        }else{
            return "{ \"" + insertStudent.getStudent_id()+" Updated\":\"affected Rows:1\"}";
        }

        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }




    /*
     * api call example https://uzo-web-app.herokuapp.com/get_company_by_id
     {
      company_id:22
     }
     * used a string as to not process the JSONOBJECT on response
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value="/get_company_by_id")
    public @ResponseBody String getCompanyById(@RequestBody Company getCompany){
        CompanyManager manager= new CompanyManager();
        return manager.getCompanyById(getCompany).toString();
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/get_company_rep
     {
      "company_id":6
     }
     * used a string as to not process the JSONOBJECT on response
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value="/get_company_rep")
    public @ResponseBody String getCompanyRep(@RequestBody CompanyRep getCompanyRep){
        CompanyManager manager= new CompanyManager();
        return manager.getCompanyRep(getCompanyRep).toString();
    }

    /*
      * api call example https://uzo-web-app.herokuapp.com/update_company_rep
      * headers
      * {
           "company_id": 6,
          "position":"CEO,
          "position_details": "get that cheddar",
          "found_uzo":"your mom",
          "uzo_help": "expand business,
          "last_name":"Blackmon",
          "first_name":"Eric"
         }
      * used a string as to not process the JSONOBJECT on response
      */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_company_rep")
    public String updateCompanyRep(@RequestBody CompanyRep companyRep){
        CompanyManager manager= new CompanyManager();
        return manager.updateCompanyRep(companyRep).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    /*
     * api call example https://uzo-web-app.herokuapp.com/insert_company
     * headers
     * {
         "email": "eric.blackmon@uzo.com",
         "city": "Chantilly",
         "street": "street",
         "zip_code": "30763",
         "state": "Va",
         "website_link": "uzo.com",
         "company_name": "UZO",
         "password":"281330800fB",
         "description": "chillen"
        }
     * used a string as to not process the JSONOBJECT on response
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_company")
    public String insertCompany(@RequestBody Company insertCompany){
          CompanyManager manager= new CompanyManager();
        return manager.insertCompany(insertCompany).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/insert_company_rep
     * headers
     * {
          "company_id": 6,
         "position":"CEO,
         "position_details": "get that cheddar",
         "found_uzo":"your mom",
         "uzo_help": "expand business,
         "last_name":"Blackmon",
         "first_name":"Eric"
        }
     * used a string as to not process the JSONOBJECT on response
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_company_rep")
    public String insertCompanyRep(@RequestBody CompanyRep insertCompanyRep){
        CompanyManager manager= new CompanyManager();
        return manager.insertCompanyRep(insertCompanyRep).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/insert_job
     * header
     * {
         "date": "2018-06-15",
         "rate": "40/hr",
         "dress_code": "Formal",
         "duration":2.5,
         "open": true,
         "job_title": "Janitor",
         "start_time":2300,
         "end_time":2300,
         "company_id":1,
         "description":"chillen"
         "important_quality": "idk",
         "preferred_quality": "idk",
         "num_employees": 7

}
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_job")
    public String insertJob(@RequestBody JobInsert insertJob){
        JobManager manager= new JobManager();
        return manager.insertJob(insertJob).toString();
    }

    /*
     * Example url: https://uzo-web-app.herokuapp.com/assign_student_job
     * example json
     * {
         "student_id": 1,
         "company_id": 19,
         "job_id":1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/assign_student_job")
    public String assignStudentJob(@RequestBody StudentJob studentJob){
        StudentManager manager= new StudentManager();
        return manager.assignStudentJob(studentJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* example url: https://uzo-web-app.herokuapp.com/delete_student_job
     * example json:
     * {
         "student_id": 1,
         "job_id":1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/delete_student_job")
    public String deleteStudentJob(@RequestBody StudentJob studentJob){
        StudentManager manager= new StudentManager();
        return manager.removeStudentJob(studentJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * example url:https://uzo-web-app.herokuapp.com/get_students_jobs_by_id
     * example header:
     * {
         "student_id": 1
        }
     *
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_students_jobs_by_id")
    public String getStudentJobList(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.getStudentJobList(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /*
     *example url:https://uzo-web-app.herokuapp.com/get_jobs_students_by_id
     * example header:
     * {
         "job_id": 1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_jobs_students_by_id")
    public String getJobStudentList(@RequestBody Job job) {
        JobManager manager= new JobManager();
        return manager.getJobStudentList(job).toString();
    }

    /*
        example url: https://uzo-web-app.herokuapp.com/insert_on_call_student
        header:
            {
             "student_id": 1,
             "job_id":1
            }

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_on_call_student")
    public String insertOnCallStudent(@RequestBody JobOnCall onCall){
        StudentManager manager= new StudentManager();
        return manager.insertStudentOnCall(onCall).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
     * example url:https://uzo-web-app.herokuapp.com/get_students_on_call_jobs_by_id
     * example header:
     * {
         "student_id": 1
        }
     *
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_students_on_call_jobs_by_id")
    public String getSudentsOncallJobs(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.getSudentsOncallJobs(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    *example url:https://uzo-web-app.herokuapp.com/get_jobs_on_call_students_by_id
    * example header:
    * {
        "job_id": 1
       }
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_jobs_on_call_students_by_id")
    public String getJobOnCallStudents(@RequestBody Job job) {
        JobManager manager= new JobManager();
        return manager.getJobsOnCallStudents(job).toString();
    }

    /*
    *example url:https://uzo-web-app.herokuapp.com/get_companys_past_students_by_id
    * example header:
    * {
       company_id": 1
       }
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_companys_past_students_by_id")
    public String getStudentsByCompany(@RequestBody Company company) {
        CompanyManager manager= new CompanyManager();
        return manager.getStudentsByCompany(company).toString();
    }


    /*
        example url: https://uzo-web-app.herokuapp.com/update_student_university
        header:
            {
             "student_id": 2,
             "university":"UVA"
            }

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_student_university")
    public String updateStudentUniversity(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.updateStudentUniversity(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
       example url: https://uzo-web-app.herokuapp.com/update_student_accepted
       header:
           {
            "student_id": 2,
            "student_accepted":true
           }

    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_student_accepted")
    public String updateStudentAccepted(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.updateStudentAccepted(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
      example url: https://uzo-web-app.herokuapp.com/lost_password_request
      header:
          {
           "email": "email,
           "first_name":"eric",
           "last_name":"blackmon"
          }

   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/lost_password_request")
    public String studentLostPasswordRequest(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.insertStudentLostNumberRecord(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     example url: https://uzo-web-app.herokuapp.com/lost_password_request
     header:
         {
          "email": "email,
          "first_name":"eric",
          "last_name":"blackmon",
          "uuid"= "asdfa-adfa-adfa"
         }

  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/allow_password_reset")
    public String allowPasswordReset(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.getStudentLostPasswordHash(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    /*
        example url: https://uzo-web-app.herokuapp.com/update_student_rating
        header:
            {
             "student_id": 2,
             "total_rating":5
            }

     */
     @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_student_rating")
    public String updateStudentRating(@RequestBody Student student){
         StudentManager manager= new StudentManager();
        return manager.addRating(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
        example url: https://uzo-web-app.herokuapp.com/get_student_rating
        header:
            {
             "student_id": 2
            }

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_student_rating")
    public String getStudentAvgRating(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.getStudentAvgRating(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

     /*
        example url: https://uzo-web-app.herokuapp.com/insert_job_captain
        header:
            {
             "student_id": 1,
             "job_id":1
            }

     */
     @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_job_captain")
    public String insertJobCaptain(@RequestBody StudentJob studentJob){
         JobManager manager= new JobManager();
        return manager.insertJobCaptain(studentJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
        example url: https://uzo-web-app.herokuapp.com/add_one_student_to_job
        header:
            {
             "job_id":1
            }

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/add_one_student_to_job")
    public String addStudentToJob(@RequestBody Job job){
        JobManager manager= new JobManager();
        return manager.addStudent(job).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

     /*
        example url: https://uzo-web-app.herokuapp.com/insert_job_co_captain
        header:
            {
             "student_id": 1,
             "job_id":1
            }

     */
     @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_job_co_captain")
    public String insertJobCoCaptain(@RequestBody StudentJob studentJob){
         JobManager manager= new JobManager();
        return manager.insertJobCoCaptain(studentJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
        example url: https://uzo-web-app.herokuapp.com/get_job_by_id
        header:
            {
             "job_id":1
            }

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_job_by_id")
    public String getJobById(@RequestBody Job job){
        JobManager manager= new JobManager();
        return manager.getJobById(job).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }



     /*
        example url: https://uzo-web-app.herokuapp.com/check_student_email
        header:
            {
             "email":ericblackmon52@yahoo.com
            }

     */
     @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/check_student_email")
    public String checkStudentEmail(@RequestBody Student student){
         StudentManager manager= new StudentManager();
        return manager.checkStudentEmail(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

     /*
        example url: https://uzo-web-app.herokuapp.com/check_student_login
        header:
            {
             "email":"ericblackmon52@yahoo.com",
             "password": "281330800fB"
            }

     */
     @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/check_student_login")
    public String checkStudentPassword(@RequestBody Student student){
         StudentManager manager= new StudentManager();
        return manager.checkStudentLogin(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
        example url: https://uzo-web-app.herokuapp.com/check_company_login
        header:
            {
             "email":"ericblackmon52@yahoo.com",
             "password": "281330800fB"
            }

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/check_company_login")
    public String checkCompanyLogin(@RequestBody Company company){
        CompanyManager manager= new CompanyManager();
        return manager.checkCompanyLogin(company).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

     /*
        example url: https://uzo-web-app.herokuapp.com/upload_student_resume
        MUST SEND THIS AS A FORM DATA WITH THE BELOW AS
        header:
        file - file selected
        student_id - <student id >


     */
     @CrossOrigin(origins = "*")
    @PostMapping(value = "/upload_student_resume", consumes = "multipart/form-data")
    public String uploadStudentResume(@RequestParam("file") MultipartFile file,  int student_id){
         StudentManager manager= new StudentManager();
        return manager.uploadStudentResume(file,student_id).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
      example url: https://uzo-web-app.herokuapp.com/upload_student_resource
      MUST SEND THIS AS A FORM DATA WITH THE BELOW AS
      header:
      file - file selected
      student_id - <student_id >


   */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/upload_student_resource", consumes = "multipart/form-data")
    public String uploadStudentFile(@RequestParam("file") MultipartFile file,  int student_id){
        StudentManager manager= new StudentManager();
        System.out.println("Actually Uploaded");
        return manager.uploadStudentResource(file,student_id).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
       example url: https://uzo-web-app.herokuapp.com/upload_company_resource
       MUST SEND THIS AS A FORM DATA WITH THE BELOW AS
       header:
       file - file selected
       company_id - <job id >


    */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/upload_company_resource", consumes = "multipart/form-data")
    public String uploadCompanyFile(@RequestParam("file") MultipartFile file,  int company_id){
        CompanyManager manager= new CompanyManager();
        return manager.insertCompanyResource(file,company_id).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    example url: https://uzo-web-app.herokuapp.com/upload_job_resource
    MUST SEND THIS AS A FORM DATA WITH THE BELOW AS
    header:
    file - file selected
    job_id - <job id >


 */
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/upload_job_resource", consumes = "multipart/form-data")
    public String uploadJobFile(@RequestParam("file") MultipartFile file,  int job_id){
        JobManager manager= new JobManager();
        return manager.insertJobResource(file,job_id).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    api call example https://uzo-web-app.herokuapp.com/get_companys_resources
      {
       "company_id" :50
     }


*/
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_companys_resources")
    public String getCompanysResources(@RequestBody Company company){
        CompanyManager manager= new CompanyManager();
        return manager.getCompanysResources(company).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
api call example https://uzo-web-app.herokuapp.com/get_job_resources
  {
   "job_id" :50
 }


*/
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_job_resources")
    public String getJobResource(@RequestBody Job job){
        JobManager manager= new JobManager();
        return manager.getJobsResources(job).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }





    /*
       example url: https://uzo-web-app.herokuapp.com/set_student_availibility
       header:
           {
            "student_id": 1,
            "available": true,
            "day":friday,
            "time":"1330"
           }

    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/set_student_availibility")
    public String setStudentAvailibility(@RequestBody StudentAvailabilitySlot student){
        StudentManager manager= new StudentManager();
        return manager.insertStudentAvailability(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     example url: https://uzo-web-app.herokuapp.com/set_student_availibility
     header:
         {
          "student_id": 1,
          "available": true,
          "day":friday,
          "time":"1330"
         }

  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_student_availibility")
    public String updateStudentAvailibility(@RequestBody StudentAvailabilitySlot student){
        StudentManager manager= new StudentManager();
        return manager.updateStudentAvailability(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
      example url: https://uzo-web-app.herokuapp.com/insert_interested_student
      header:
          {
           "student_id": 1,
           "job_id":1
          }

   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_interested_student")
    public String insertInterestedStudents(@RequestBody InterestedStudent interestedStudent){
        StudentManager manager= new StudentManager();
        return manager.insertInterestedStudent(interestedStudent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /* example url: https://uzo-web-app.herokuapp.com/delete_interested_student
     * example json:
     * {
         "student_id": 1,
         "job_id":1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/delete_interested_student")
    public String deleteInterestedStudent(@RequestBody InterestedStudent interestedStudent){
        StudentManager manager= new StudentManager();
        return manager.removeInterestedStudent(interestedStudent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /* example url: https://uzo-web-app.herokuapp.com/delete_interested_student
     * example json:
     * {
         "job_id":1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/delete_jobs_interested_student")
    public String deleteJobsInterestedStudent(@RequestBody InterestedStudent interestedStudent){
        JobManager manager= new JobManager();
        return manager.removeJobsInterestedStudent(interestedStudent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    *example url:https://uzo-web-app.herokuapp.com/get_jobs_interested_students_by_id
    * example header:
    * {
        "job_id": 1
       }
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_jobs_interested_students_by_id")
    public String getJobsInterestedtList(@RequestBody Job job) {
        JobManager manager= new JobManager();
        return manager.getJobInterestedList(job).toString();
    }

    /*
    * api call example https://uzo-web-app.herokuapp.com/insert_student_preferences
    * headers
    * {
        "student_id": 50,
        "uzo_reason": "For the racks",
        "lift_ability": true,
        "car":true,
        "bike":bike,
        "bus":bus
       }
    * used a string as to not process the JSONOBJECT on response
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_student_preferences")
    public String setStudentPreferences(@RequestBody StudentJobPreference pref){
        StudentManager manager= new StudentManager();
        return manager.setStudentJobPreference(pref).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
       * api call example https://uzo-web-app.herokuapp.com/get_student_job_preferences
        {
         "student_id" :50
        }
       */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value="/get_student_job_preferences")
    public String getStudentPreference(@RequestBody StudentJobPreference student){
        StudentManager manager= new StudentManager();
        JSONObject result= manager.getStudentJobPreference(student);
        return result.toString();
    }

    /*
   * api call example https://uzo-web-app.herokuapp.com/insert_student_work_ability
   * headers
   * {
      student_id: 50,
        bar:true,
       cashier:true,
       cleaning:true,
       data_entry:true,
       desk_assistant:true,
        driving_delivery:true,
       event_security:true,
       setup_breakdown:true,
       food_service:true,
        moving:true
      }
   * used a string as to not process the JSONOBJECT on response
   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_student_work_ability")
    public String setStudentWorkAbility(@RequestBody StudentWorkAbility workAbility){
        StudentManager manager= new StudentManager();
        return manager.insertStudentWorkAbility(workAbility).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    * api call example https://uzo-web-app.herokuapp.com/get_student_work_ability
     {
      "student_id" :50
     }
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value="/get_student_work_ability")
    public String getStudentPreference(@RequestBody StudentWorkAbility student){
        StudentManager manager= new StudentManager();
        JSONObject result= manager.getStudentWorkAbility(student);
        return result.toString();
    }

    /*
  * api call example https://uzo-web-app.herokuapp.com/insert_student_work_history
  * headers
  * {
     "﻿student_id": 50,
    "work_reference_1": "Bob Jones",
    "work_reference_2": "Bob Jones",
    work_reference_3 :"Bob Jones",
    "crime": true,
    "hear_uzo": "word of mouf"
     }
  * used a string as to not process the JSONOBJECT on response
  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_student_work_history")
    public String setStudentWorkHistory(@RequestBody StudentWorkHistory workHistory){
        StudentManager manager= new StudentManager();
        return manager.insertStudentWorkHistory(workHistory).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
    * api call example https://uzo-web-app.herokuapp.com/get_student_work_history
     {
      "student_id" :50
     }
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value="/get_student_work_history")
    public String getStudentPreference(@RequestBody StudentWorkHistory student){
        StudentManager manager= new StudentManager();
        JSONObject result= manager.getStudentWorkHistory(student);
        return result.toString();
    }


    /*
 * api call example https://uzo-web-app.herokuapp.com/insert_event
 * header
 * {
     "date": "2018-06-15",
     "dress_code": "Formal",
     "duration":2.5,
     "open": true,
     "event_title": "Janitor",
     "time":2300,
     "company_id":1,
     "description":"chillen"
}
 */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_event")
    public String insertEvent(@RequestBody Event insertEvent){
        EventManager manager= new EventManager();
        return manager.insertEvent(insertEvent).toString();
    }

    /*
 *example url:https://uzo-web-app.herokuapp.com/get_events_students_by_id
 * example header:
 * {
     "event_id": 1
    }
 */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_events_students_by_id")
    public String getEventStudentList(@RequestBody Event event) {
        EventManager manager= new EventManager();
        return manager.getEventStudentList(event).toString();
    }

    /*
      example url: https://uzo-web-app.herokuapp.com/get_event_by_id
      header:
          {
           "event_id":1
          }

   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_event_by_id")
    public String getEventById(@RequestBody Event event){
        EventManager manager= new EventManager();
        return manager.getEventById(event).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
     * Example url: https://uzo-web-app.herokuapp.com/register_student_for_event
     * example json
     * {
         "student_id": 1,
         "company_id": 19,
         "event_id":1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/register_student_for_event")
    public String registerStudentEvent(@RequestBody StudentEvent studentEvent){
        StudentManager manager= new StudentManager();
        return manager.registerStudentEvent(studentEvent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* example url: https://uzo-web-app.herokuapp.com/unregister_student_from_event
     * example json:
     * {
         "student_id": 1,
         "event_id":1
        }
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/unregister_student_from_event")
    public String unregisterStudent(@RequestBody StudentEvent studentEvent){
        StudentManager manager= new StudentManager();
        return manager.unregisterStudentEvent(studentEvent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
   * example url:https://uzo-web-app.herokuapp.com/get_students_events_by_id
   * example header:
   * {
       "student_id": 1
      }
   *
   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_students_events_by_id")
    public String getStudentEventsList(@RequestBody Student student){
        StudentManager manager= new StudentManager();
        return manager.getStudentEventList(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
    *example url:https://uzo-web-app.herokuapp.com/get_companys_completed_jobs
    * example header:
    * {
       company_id": 1
       }
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_companys_completed_jobs")
    public String getCompanysCompletedJobsById(@RequestBody Company company) {
        CompanyManager manager= new CompanyManager();
        return manager.getCompanysCompletedJobsById(company).toString();
    }

    /*
   *example url:https://uzo-web-app.herokuapp.com/get_companys_current_jobs
   * example header:
   * {
      company_id": 1
      }
   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_companys_current_jobs")
    public String getCompanysCurrentJobsById(@RequestBody Company company) {
        CompanyManager manager= new CompanyManager();
        return manager.getCompanysCurrentJobsById(company).toString();
    }

    /*
  *example url:https://uzo-web-app.herokuapp.com/set_completed_job
  * example header:
  * {
     company_id": 1
     "job_id" : 1,
     "completed" : true
     }
  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/set_completed_job")
    public String setStudentCompleted(@RequestBody StudentJob student) {
        JobManager manager= new JobManager();
        return manager.setStudentCompleted(student).toString();
    }

    /*
  *example url:https://uzo-web-app.herokuapp.com/clockin_student
  * example header:
  * {
     company_id": 1
     "job_id" : 1,
     "clock_in" : 1230
     }
  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/clockin_student")
    public String clockinStudent(@RequestBody StudentJob student) {
        JobManager manager= new JobManager();
        return manager.clockinStudent(student).toString();
    }

    /*
          *example url:https://uzo-web-app.herokuapp.com/clockout_student
          * example header:
          * {
             company_id": 1
             "job_id" : 1,
             "clock_out" : 1230
             }
  */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/clockout_student")
    public String clockoutStudents(@RequestBody StudentJob student) {
        JobManager manager= new JobManager();
        return manager.clockoutStudent(student).toString();
    }


    /*
*example url:https://uzo-web-app.herokuapp.com/complete_job
* example header:
* {
   "job_id" : 1,
   "completed" : true
   }
*/
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/complete_job")
    public String completeJob(@RequestBody Job job) {
        JobManager manager= new JobManager();
        return manager.completeJob(job).toString();
    }


    /*
         *example url:https://uzo-web-app.herokuapp.com/get_job_status
         * example header:
         *

            {
            "student_id": 1,
             "job_id": 1
            }
 */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_job_status")
    public String getJobStatus(@RequestBody StudentJob student) {
        JobManager manager= new JobManager();
        return manager.getJobStatus(student).toString();
    }


    /*
     * api call example https://uzo-web-app.herokuapp.com/get_stripe_key

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @RequestMapping(value="/get_stripe_key")
    public String getStripeKey(){
        StripeController key= new StripeController();
        return key.getKey().toString();
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/get_open_jobs

     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @RequestMapping(value="/get_open_jobs")
    public String getOpenJobs(){
        JobManager key= new JobManager();
        return key.getOpenJobs().toString();
    }

    /*
       example url: https://uzo-web-app.herokuapp.com/insert_company_card
       header:
           {
            "company_id": 1,
            "token_id":"adsfasdf"
           }

    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_company_card")
    public String insertCompanyCard(@RequestBody CompanyPaymentCard companyPaymentCard){
        CompanyManager manager= new CompanyManager();
        return manager.insertCompanyCard(companyPaymentCard).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
      example url: https://uzo-web-app.herokuapp.com/insert_student_account
      header:
          {
           "student_id": 1
          }

   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/insert_student_account")
    public String insertStudentAccount(@RequestBody StudentAcctTokens account){
        StudentManager manager= new StudentManager();
        return manager.insertStudentAccount(account).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /*
      example url: https://uzo-web-app.herokuapp.com/charge_company_card
      header:
          {
           "company_id": 1,
           "amount": .01
          }

   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/charge_company_card")
    public String chargeCompanyCard(@RequestBody CompanyCharge companyCharge){
        companyCharge.setIntAmount((int)Math.round(companyCharge.getAmount()*100));
        return StripeController.chargeCustomer(companyCharge).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * example url:https://uzo-web-app.herokuapp.com/get_company_job_list
     * example header:
     * {
         "company_id": 1
        }
     *
     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/get_company_job_list")
    public String getCompanyJobList(@RequestBody Company company){
        CompanyManager manager= new CompanyManager();
        return manager.getCompanyJobList(company).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
    * example url:https://uzo-web-app.herokuapp.com/delete_job_by_id
    * example header:
    * {
        "job_id": 1
       }
    *
    */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/delete_job_by_id")
    public String deleteJobById(@RequestBody Job job ){
        JobManager manager= new JobManager();
        return manager.deleteJob(job).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
 * api call example https://uzo-web-app.herokuapp.com/update_job_info
 * All of the paramters are optional
 * headers
 *   {
        "date": "2018-06-15",
         "rate": "40/hr",
         "job_title": "Janitor",
         "start_time":2300,
         "end_time":2300,
         "company_id":1,
         "description":"chillen"
         "num_employees": 7
       }
 * used a string as to not process the JSONOBJECT on response
 */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/update_job_info")
    public String updateJobInfo(@RequestBody Job insertJob){
        JSONObject obj= new JSONObject();
        int everythingNull=1;
        JobManager manager= new JobManager();
        if(insertJob.getJob_id()==0) {
            return "{ \"Error\":\"Missing Parameter\"}";
        }
        if(insertJob.getDate()!=null){
            manager.updateJob(insertJob.getDate(),"date",insertJob.getJob_id() ).toString();
            everythingNull=0;

        }if(insertJob.getRate()!=null){
            manager.updateJob(insertJob.getRate(),"rate", insertJob.getJob_id()).toString();
            everythingNull=0;

        }if(insertJob.getJob_title()!=null){
            manager.updateJob(insertJob.getJob_title(),"job_title", insertJob.getJob_id()).toString();
            everythingNull=0;

        }if(insertJob.getDescription()!=null){
            manager.updateJob(insertJob.getDescription(),"description", insertJob.getJob_id()).toString();
            everythingNull=0;

        }if(insertJob.getNum_employees()!=0){
            manager.updateJob(insertJob.getNum_employees()+"","num_employees", insertJob.getJob_id()).toString();
            everythingNull=0;

        }if(insertJob.getStart_time()!=null){
            manager.updateJob(insertJob.getStart_time(), "start_time", insertJob.getJob_id()).toString();
            everythingNull=0;

        }if(insertJob.getEnd_time()!=null){
            manager.updateJob(insertJob.getEnd_time(),"end_time", insertJob.getJob_id()).toString();
            everythingNull=0;

        }if(everythingNull==1) {
            return "{ \"Error\":\"No job detail specified\"}";
        }else{
            return "{ \"" + insertJob.getJob_id()+" Updated\":\"affected Rows:1\"}";
        }

        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
        example url: https://uzo-web-app.herokuapp.com/populate_student_job_list
        header:


     */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @GetMapping(value = "/populate_student_job_list")
    public String populateStudentsAndJobs(){
        JobManager manager= new JobManager();
        return manager.populateStudentsAndJobs().toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /*
      example url: https://uzo-web-app.herokuapp.com/transfer_funds_to_student
      header:
          {
           "student_id": 1,
           "amount": .01
          }

   */
    @CrossOrigin(origins = "https://uzo-frontend.herokuapp.com")
    @PostMapping(value = "/transfer_funds_to_student")
    public String transferToStudent(@RequestBody StudentTransfer studentTrans){
        studentTrans.setIntAmount((int)Math.round(studentTrans.getAmount()*100));
        return StripeController.transferToStudent(studentTrans).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
