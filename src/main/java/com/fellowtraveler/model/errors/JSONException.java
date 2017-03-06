package com.fellowtraveler.model.errors;

/**
 * Created by igorkasyanenko on 05.03.17.
 */
public class JSONException {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
