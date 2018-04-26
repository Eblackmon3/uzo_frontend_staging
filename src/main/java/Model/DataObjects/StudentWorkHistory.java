package Model.DataObjects;

public class StudentWorkHistory {
    private int student_id;
    private String work_reference_1;
    private String work_reference_2;
    private String work_reference_3;
    private String  resume_location;
    private boolean crime;
    private String hear_uzo;

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public boolean isCrime() {
        return crime;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getHear_uzo() {
        return hear_uzo;
    }

    public String getResume_location() {
        return resume_location;
    }

    public String getWork_reference_1() {
        return work_reference_1;
    }

    public String getWork_reference_2() {
        return work_reference_2;
    }

    public String getWork_reference_3() {
        return work_reference_3;
    }

    public void setCrime(boolean crime) {
        this.crime = crime;
    }

    public void setResume_location(String resume_location) {
        this.resume_location = resume_location;
    }

    public void setHear_uzo(String hear_uzo) {
        this.hear_uzo = hear_uzo;
    }

    public void setWork_reference_1(String work_reference_1) {
        this.work_reference_1 = work_reference_1;
    }

    public void setWork_reference_2(String work_reference_2) {
        this.work_reference_2 = work_reference_2;
    }

    public void setWork_reference_3(String work_reference_3) {
        this.work_reference_3 = work_reference_3;
    }

    @Override
    public String toString() {
        return student_id+"";
    }
}
