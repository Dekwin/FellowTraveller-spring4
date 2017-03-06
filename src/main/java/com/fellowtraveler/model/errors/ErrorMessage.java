package com.fellowtraveler.model.errors;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by igorkasyanenko on 05.03.17.
 */

public class ErrorMessage {

    private List<String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(List<String> errors) {
        this.errors = errors;
    }

    public ErrorMessage(String error) {
        this(Collections.singletonList(error));
    }

    public ErrorMessage(String ... errors) {
        this(Arrays.asList(errors));
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}