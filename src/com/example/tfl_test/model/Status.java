package com.example.tfl_test.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Status")
public class Status {

    @Attribute(name = "ID")
    private String id;

    @Attribute(name = "CssClass")
    private String cssClass;

    @Attribute(name = "Description")
    private String description;

    @Attribute(name = "IsActive")
    private boolean isActive;

    @Element(name = "StatusType")
    private StatusType statusType;

    public Status() {
    }

    public String getId() {
        return id;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public StatusType getStatusType() {
        return statusType;
    }
}

