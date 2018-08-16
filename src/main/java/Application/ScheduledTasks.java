package Application;


import Model.DataManagers.CompanyManager;
import Model.DataManagers.StudentManager;
import org.json.JSONArray;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private CompanyManager manager;
    private StudentManager studManager;

    @Scheduled(cron = "0 1 1 ? * *")
    public void sendTheBoystheEmail() {
        manager=new CompanyManager();
        studManager=new StudentManager();
        JSONArray array= studManager.getAllStudentsEmails();
        manager.generateAndSendEmailVariable(array);

    }
}
