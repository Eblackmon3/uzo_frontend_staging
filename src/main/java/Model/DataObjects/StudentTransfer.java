package Model.DataObjects;

public class StudentTransfer {
   private String token;
   private int student_id;
   private double amount;
   private int intAmount;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public int getIntAmount() {
        return intAmount;
    }

    public void setIntAmount(int intAmount) {
        this.intAmount = intAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StudentTransfer){
            StudentTransfer obTrans= (StudentTransfer) obj;
            if(obTrans.getToken().equals(token)){
                return true;
            }
            return false;

        }
        return false;
    }
}
