package com.fellowtraveler.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;

@Configuration("storage")
public class StorageProperties {

    @Autowired
    ServletContext servletContext;
    /**
     * Folder location for storing files
     */

    private String location = "/static/uploads/images";

    public String getLocation() {
        return location;
    }

    public String getPathToStore(String location) {
        return servletContext.getRealPath(location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
