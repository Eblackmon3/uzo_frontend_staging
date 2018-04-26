package AmazonController;

import java.io.*;

import Model.DataObjects.Student;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;


@Configuration
@PropertySource("classpath:application.properties")
public class s3Operations {

    @Autowired
    private static Environment env;
    private static String accessKeyId=System.getenv("S3_KEY");
    private static String accessSecret=System.getenv("S3_SECRET");
    private static String bucketName = System.getenv("S3_BUCKET");
    private static String region=System.getenv("REGION");
    private static AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, accessSecret);

    private static AmazonS3 s3client = new AmazonS3Client(credentials);

    public static void uploadResume(String resume) throws IOException {


    }

    //use this method to create a new folder on our s3 bucket to store students resumes
    public static  JSONObject createFolder(Student student) {
        JSONObject ret=new JSONObject();
        try {
            // create meta-data for your folder and set content-length to 0
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            // create empty content
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    "" + student.getStudent_id()+"/", emptyContent, metadata);
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);
            ret.put("Student:" + student.getStudent_id(), "Folder created");
            ret.put("Put result", result.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    //https://s3.us-east-2.amazonaws.com/uzo-s3-bucket/1/Resume
    public static String uploadStudentFile(int studentID, MultipartFile file){
        String fileName =  "studentfolder/"+studentID+"/"+file.getOriginalFilename();
        String resume_location="https://s3.us-east-2.amazonaws.com/uzo-s3-bucket/studentfolder/"+studentID+"/"+file.getOriginalFilename();
        File convFile;
        try {
            convFile=multipartToFile(file);
            System.out.println(convFile.length());
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                    convFile).withCannedAcl(CannedAccessControlList.PublicRead);
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);

        }catch(Exception e){
            e.printStackTrace();
        }
        return resume_location;

    }

    //https://s3.us-east-2.amazonaws.com/uzo-s3-bucket/1/Resume
    public static String uploadCompanyFile(int company_id, MultipartFile file){
        String fileName =  "companyfolder/"+company_id+"/"+file.getOriginalFilename();
        String resource_location="https://s3.us-east-2.amazonaws.com/uzo-s3-bucket/companyfolder/"+company_id+"/"+file.getOriginalFilename();
        File convFile;
        try {
            convFile=multipartToFile(file);
            System.out.println(convFile.length());
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                    convFile).withCannedAcl(CannedAccessControlList.PublicRead);
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);

        }catch(Exception e){
            e.printStackTrace();
        }
        return resource_location;

    }

    //https://s3.us-east-2.amazonaws.com/uzo-s3-bucket/1/Resume
    public static String uploadJobFile(int job_id, MultipartFile file){
        System.out.println("Checking where error is ");
        String fileName =  "jobfolder/"+job_id+"/"+file.getOriginalFilename();
        String resource_location="https://s3.us-east-2.amazonaws.com/uzo-s3-bucket/jobfolder/"+job_id+"/"+file.getOriginalFilename();
        File convFile;
        try {
            convFile=multipartToFile(file);
            System.out.println(convFile.length());
            // create a PutObjectRequest passing the folder name suffixed by /
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName,
                    convFile).withCannedAcl(CannedAccessControlList.PublicRead);
            // send request to S3 to create folder
            PutObjectResult result = s3client.putObject(putObjectRequest);

        }catch(Exception e){
            e.printStackTrace();
        }
        return resource_location;

    }



    public static File multipartToFile(MultipartFile file)
    {
        File convFile=null;
        try {
            System.out.println(file.getOriginalFilename());
            convFile = new File(file.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(convFile);
        return convFile;
    }


}
