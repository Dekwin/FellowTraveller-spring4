package com.fellowtraveler.model.jwt2;

/**
 * Created by igorkasyanenko on 04.03.17.
 */
public class AccountCredentials {

    private String ssoId;
    private String password;

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}