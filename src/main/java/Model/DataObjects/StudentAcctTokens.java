package Model.DataObjects;

public class StudentAcctTokens {
    int student_id;
    String token;

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StudentAcctTokens){
            StudentAcctTokens stud= (StudentAcctTokens) obj;
            if(((StudentAcctTokens) obj).getStudent_id()==student_id){
                return true;
            }
            return false;

        }
        return false;
    }
}

