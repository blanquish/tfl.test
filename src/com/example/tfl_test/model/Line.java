package com.example.tfl_test.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name="Line")
public class Line {

    @Attribute(name = "ID")
    private String id;

    @Attribute(name = "Name")
    private String name;

    public Line() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
