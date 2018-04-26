package Model.DataObjects;

public class CompanyResource {

    private int company_id;
    private String resource_location;

    public void setResource_location(String resource_location) {
        this.resource_location = resource_location;
    }

    public String getResource_location() {
        return resource_location;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    @Override
    public String toString() {
        return ":Company ID:"+ company_id+ " Resource Location";
    }
}
