package Model.DataObjects;

public class JobResource {
    private int job_id;
    private String resource_location;

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public String getResource_location() {
        return resource_location;
    }

    public void setResource_location(String resource_location) {
        this.resource_location = resource_location;
    }

    @Override
    public String toString() {
        return "Job ID:"+job_id+ " Resource Location:"+resource_location;
    }
}
