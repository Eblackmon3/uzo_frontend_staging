package StripeController;

import Model.DataManagers.CompanyManager;
import Model.DataManagers.StudentManager;
import Model.DataObjects.*;
import com.stripe.Stripe;
import com.stripe.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class StripeController {


    public JSONObject getKey(){
        JSONObject key= new JSONObject();
        try{
            key.put("key",System.getenv("STRIPE_PUBLISH_KEY"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return key;

    }

    public static String createCustomer(CompanyPaymentCard card ){
        Stripe.apiKey=System.getenv("STRIPE_SECRET_KEY");
        JSONObject companyInfo= new JSONObject();
        Company company = new Company();
        try {
            CompanyManager manager= new CompanyManager();
            company.setCompany_id(card.getCompany_id());
            companyInfo= manager.getCompanyById(company);
            // Create a Customer:
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("source", card.getToken_id());
            chargeParams.put("email", companyInfo.get("email"));
            chargeParams.put("description", companyInfo.get("description"));
            Customer customer = Customer.create(chargeParams);
            return customer.getId();

        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public static String chargeCustomer(CompanyCharge card ){
        Stripe.apiKey=System.getenv("STRIPE_SECRET_KEY");
        JSONObject companyInfo= new JSONObject();
        Company company = new Company();
        try {
            CompanyManager manager= new CompanyManager();
            System.out.println("Company ID: "+card.getCompany_id() + "Amount: "+ card.getIntAmount());
            company.setCompany_id(card.getCompany_id());
            companyInfo= manager.getCompanyToken(company);
            System.out.println("Token ID: "+card.getIntAmount());
            // Create a Customer:
            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("amount", card.getIntAmount());
            customerParams.put("currency", "usd");
            customerParams.put("customer", companyInfo.get("token"));
            Charge charge = Charge.create(customerParams);
            return charge.toJson();

        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public static String createStudentAccount(StudentAcctTokens studentAcct ){
        Stripe.apiKey=System.getenv("STRIPE_SECRET_KEY");
        JSONObject studentInfo= new JSONObject();
        Student student = new Student();
        try {
            StudentManager manager= new StudentManager();
            student.setStudent_id(studentAcct.getStudent_id());
            studentInfo= manager.getStudentById(student);
            // Create a Customer:
            Map<String, Object> accountParams = new HashMap<String, Object>();
            accountParams.put("type", "standard");
            accountParams.put("country", "US");
            accountParams.put("email",studentInfo.get("email"));
            return Account.create(accountParams).getId();

        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }

    public static String transferToStudent(StudentTransfer studentTrans ){
        Stripe.apiKey=System.getenv("STRIPE_SECRET_KEY");
        JSONObject studentInfo= new JSONObject();
        Student student = new Student();
        try {
            StudentManager manager= new StudentManager();
            student.setStudent_id(studentTrans.getStudent_id());
            studentInfo=manager.getStudentAccount(student);
            Map<String, Object> transferParams = new HashMap<String, Object>();
            transferParams.put("amount", studentTrans.getIntAmount());

            System.out.println(Balance.retrieve());
            transferParams.put("currency", "usd");
            transferParams.put("destination", studentInfo.get("token"));

            Transfer.create(transferParams);

        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;
    }
}
