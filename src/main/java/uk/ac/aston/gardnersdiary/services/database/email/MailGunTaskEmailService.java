package uk.ac.aston.gardnersdiary.services.database.email;

import uk.ac.aston.gardnersdiary.models.Task;
import uk.ac.aston.gardnersdiary.services.property.ConfigFilePropertyService;
import uk.ac.aston.gardnersdiary.services.property.PropertyService;
import uk.ac.aston.gardnersdiary.services.rest.RestClient;
import uk.ac.aston.gardnersdiary.services.rest.UniRestClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MailGunTaskEmailService implements TaskEmailService {

    private static PropertyService propertyService = ConfigFilePropertyService.getInstance();

    private static final String MAILGUN_API_KEY = propertyService.getProperty("api.mailgun.key");
    private static final String MAILGUN_URL = propertyService.getProperty("api.mailgun.url");
    private static final String TASK_REMINDER_EMAIL = propertyService.getProperty("task.reminder.email");

    private RestClient restClient;


    public MailGunTaskEmailService() {
        restClient = new UniRestClient();
    }

    @Override
    public void sendEmail(Task task, String taskTypeName, String plantName) {
        Map<String, Object> mailGunFields = createMailGunFields(task.getName(), task.getDueDate(), taskTypeName, plantName);
        restClient.postWithAPIKey(MAILGUN_URL, MAILGUN_API_KEY, mailGunFields);
    }

    private Map<String, Object> createMailGunFields(String taskName, LocalDate dueDate, String taskTypeName, String plantName) {
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("from", "Gardner's Diary <postmaster@sandbox336af6ef33b74a289da39bda907c2187.mailgun.org>");
        fields.put("to", "Christina <" + TASK_REMINDER_EMAIL + ">");
        fields.put("subject", "Task reminder for task "+ taskName);
        fields.put("html", setupTaskReminderText(taskName, dueDate, taskTypeName, plantName));
//        fields.put("o:deliverytime", createDeliveryTime(dueDate));
        return fields;
    }

    private String createDeliveryTime(LocalDate dueDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E, d MMM y");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime time = LocalTime.now();
        String formattedDate = dueDate.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);
        String formattedDateTime = formattedDate + " " + formattedTime + " GMT";
        return formattedDateTime;
    }

    private String setupTaskReminderText(String taskName, LocalDate dueDate, String taskTypeName, String plantName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<b>Task name</b>: " + taskName + "<br>");
        stringBuilder.append("<b>Due Date</b>: " + dueDate + "<br>");
        stringBuilder.append("<b>Task type name</b>: " + taskTypeName + "<br>");
        stringBuilder.append("<b>Plant assigned to</b>: " + plantName + "<br>");
        return stringBuilder.toString();
    }
}
