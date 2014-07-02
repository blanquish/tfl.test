package com.example.tfl_test.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="BranchDisruption")
public class BranchDisruption {

    @Element(name = "StationTo")
    private Station stationTo;

    @Element(name = "StationFrom")
    private Station stationFrom;

    @Element(name = "Status")
    private Status status;
}
