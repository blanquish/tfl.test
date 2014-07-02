package com.example.tfl_test.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="LineStatus")
public class LineStatus {

    @Attribute(name = "ID", required = false)
    private String id;

    @Attribute(name = "StatusDetails", required = false)
    private String statusDetails;

    @Element(name = "BranchDisruptions", required = false)
    private BranchDisruptions branchDisruptions;

    @Element(name = "Line", required = false)
    private Line line;

    @Element(name = "Status", required = false)
    private Status status;

    public LineStatus() {
    }

    public String getId() {
        return id;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public BranchDisruptions getBranchDisruptions() {
        return branchDisruptions;
    }

    public Line getLine() {
        return line;
    }

    public Status getStatus() {
        return status;
    }
}
