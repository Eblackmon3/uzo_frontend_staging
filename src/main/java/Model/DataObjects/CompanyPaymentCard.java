package Model.DataObjects;

import org.json.JSONArray;

public class CompanyPaymentCard {
    private int company_id;
    private String token_id;

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    @Override
    public boolean equals(Object obj) {
        CompanyPaymentCard account= (CompanyPaymentCard) obj;
        if(token_id.equals(account.getToken_id())&&
                company_id==account.getCompany_id()){
            return true;
        }

        return false;
    }

}
