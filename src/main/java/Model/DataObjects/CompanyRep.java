package Model.DataObjects;


public class CompanyRep {
    private int company_id;
    private String position;
    private String position_details;
    private String found_uzo;
    private String uzo_help;
    private String first_name;
    private String last_name;
    private String phone_number;

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getUzo_help() {
        return uzo_help;
    }

    public void setUzo_help(String uzo_help) {
        this.uzo_help = uzo_help;
    }

    public String getFound_uzo() {
        return found_uzo;
    }

    public String getPosition() {
        return position;
    }

    public String getPosition_details() {
        return position_details;
    }



    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public void setFound_uzo(String found_uzo) {
        this.found_uzo = found_uzo;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPosition_details(String position_details) {
        this.position_details = position_details;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        return company_id+"";
    }
}
