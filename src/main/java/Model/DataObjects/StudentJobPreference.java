package Model.DataObjects;

public class StudentJobPreference {

    private int student_id;
    private String uzo_reason;
    private boolean lift_ability;
    private Boolean car;
    private Boolean bus;
    private Boolean bike;

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public boolean isLift_ability() {
        return lift_ability;
    }

    public String getUzo_reason() {
        return uzo_reason;
    }

    public void setLift_ability(boolean lift_ability) {
        this.lift_ability = lift_ability;
    }

    public Boolean getBike() {
        return bike;
    }

    public Boolean getCar() {
        return car;
    }

    public Boolean getBus() {
        return bus;
    }

    public void setBike(Boolean bike) {
        this.bike = bike;
    }

    public void setCar(Boolean car) {
        this.car = car;
    }

    public void setBus(Boolean bus) {
        this.bus = bus;
    }

    public void setUzo_reason(String uzo_reason) {
        this.uzo_reason = uzo_reason;
    }

    @Override
    public String toString() {
        return "Student id: " +student_id;
    }
}
