package Model.DataObjects;

public class Student {
    private int student_id;
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String university;
    private int total_rating;
    private int times_rated;
    private String phone_number;
    private String state;
    private String city;
    private String street;
    private String apt;
    private String date_of_birth;
    private String major;
    private int year;
    private String description;
    private String zipcode;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getTimes_rated() {
        return times_rated;
    }

    public void setTimes_rated(int times_rated) {
        this.times_rated = times_rated;
    }

    public int getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(int total_rating) {
        this.total_rating = total_rating;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Student Email: "+ email+ " Student Name:"+ first_name+
                " "+ last_name+ " Student id "+ student_id;
    }

    @Override
    public boolean equals(Object obj) {
        Student compare= (Student) obj;
        if(this.student_id==((Student) obj).student_id){
            return true;
        }
        return false;
    }
}
