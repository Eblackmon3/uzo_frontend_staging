package Model.DataObjects;

import java.util.Date;

public class Company {
    private int company_id;
    private String email;
    private String state;
    private String city;

    private String street;
    private String zip_code;
    private String website_link;

    private String company_name;
    private String password;
    private String description;
    private String UUID;

    private String date_insert;


    public String getDate_insert() {
        return date_insert;
    }

    public void setDate_insert(String date_insert) {
        this.date_insert = date_insert;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getStreet() {
        return street;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getWebsite_link() {
        return website_link;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    @Override
    public String toString() {
        return "Company Email: " + email+ " Company Name: " + company_name + " Company Website" + website_link;
    }

    @Override
    public boolean equals(Object obj) {
        Company comp = (Company) obj;
        if (((Company) obj).getCompany_id() == company_id) {
            return true;
        }
        return false;
    }

}
