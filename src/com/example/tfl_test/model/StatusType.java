package com.example.tfl_test.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="StatusType")
public class StatusType {

    @Attribute(name = "ID")
    private String id;

    @Attribute(name = "Description")
    private String description;

    public StatusType() {
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
