package com.example.tfl_test.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class ArrayOfLineStatus {

    @ElementList(inline = true, type = LineStatus.class)
    private List<LineStatus> lineStatusList = new ArrayList<LineStatus>();

    public ArrayOfLineStatus() {
    }

    public List<LineStatus> getLineStatusList() {
        return lineStatusList;
    }
}
