import java.util.*;

public class Report {
    private static int counter = 0;
    private final int id;
    private final String action;
    private final String details;
    private final boolean success;
    private final Date date;

    public Report(String action, String details, boolean success) {
        this.id = ++counter;
        this.action = action;
        this.details = details;
        this.success = success;
        this.date = TimeManager.getCurrentDate();
        System.out.println(this); 
    }

    public int getId(){ return id; }
    public String getAction(){ return action; }
    public boolean isSuccess(){ return success; }
    public Date getDate(){ return date; }

    @Override
    public String toString() {
        return "Report #" + id +
                " | Action: " + action +
                " | Status: " + (success ? "✅ Success" : "❌ Failed") +
                " | Details: " + details +
                " | Date: " + date;
    }
}
