package com.example.tfl_test.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "Station")
public class Station {

    @Attribute(name = "ID")
    private String id;

    @Attribute(name = "Name")
    private String name;
}
