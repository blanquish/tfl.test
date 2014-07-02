package com.example.tfl_test.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="BranchDisruptions")
public class BranchDisruptions {

    @ElementList(required = false, inline = true, type = BranchDisruption.class)
    private List<BranchDisruption> branchDisruptionList;
}
