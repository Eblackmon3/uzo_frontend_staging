package Model.DataObjects;

public class StudentWorkAbility {

    private int student_id;
    private boolean bar;
    private boolean cashier;
    private boolean cleaning;
    private boolean data_entry;
    private boolean desk_assistant;
    private boolean driving_delivery;
    private boolean event_security;
    private boolean setup_breakdown;
    private boolean food_service;
    private boolean moving;

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public boolean isBar() {
        return bar;
    }

    public boolean isCleaning() {
        return cleaning;
    }

    public boolean isCashier() {
        return cashier;
    }

    public boolean isDesk_assistant() {
        return desk_assistant;
    }

    public boolean isData_entry() {
        return data_entry;
    }

    public boolean isDriving_delivery() {
        return driving_delivery;
    }

    public boolean isEvent_security() {
        return event_security;
    }

    public boolean isFood_service() {
        return food_service;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isSetup_breakdown() {
        return setup_breakdown;
    }

    public void setBar(boolean bar) {
        this.bar = bar;
    }

    public void setCashier(boolean cashier) {
        this.cashier = cashier;
    }

    public void setCleaning(boolean cleaning) {
        this.cleaning = cleaning;
    }

    public void setData_entry(boolean data_entry) {
        this.data_entry = data_entry;
    }

    public void setDesk_assistant(boolean desk_assistant) {
        this.desk_assistant = desk_assistant;
    }

    public void setDriving_delivery(boolean driving_delivery) {
        this.driving_delivery = driving_delivery;
    }

    public void setEvent_security(boolean event_security) {
        this.event_security = event_security;
    }

    public void setFood_service(boolean food_service) {
        this.food_service = food_service;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setSetup_breakdown(boolean setup_breakdown) {
        this.setup_breakdown = setup_breakdown;
    }

    @Override
    public String toString() {
        return student_id+"";
    }
}
