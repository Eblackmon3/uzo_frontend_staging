package Model.DataObjects;

public class StudentEvent {
    private int student_id;
    private int company_id;
    private int event_id;
    private boolean completed;

    public int getStudent_id() {
        return student_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object obj) {
        StudentEvent studentEvent = (StudentEvent) obj;
        if (student_id == studentEvent.getStudent_id() && company_id == studentEvent.company_id) {
            return true;
        }

        return false;
    }

}

