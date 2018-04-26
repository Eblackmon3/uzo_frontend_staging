package Model.DataObjects;

public class InterestedStudent {
    private int student_id;
    private int job_id;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "Student:"+student_id+" is interested in job:"+job_id;
    }


}
