package com.android.proteam;

import java.util.ArrayList;
import java.util.List;

public class Record {

    String stateName;
    String countConfirmed;
    String countQuarantine;

    public Record(String stateName, String countConfirmed, String countQuarantine) {
        this.stateName = stateName;
        this.countConfirmed = countConfirmed;
        this.countQuarantine = countQuarantine;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountConfirmed() {
        return countConfirmed;
    }

    public void setCountConfirmed(String countConfirmed) {
        this.countConfirmed = countConfirmed;
    }

    public String getCountQuarantine() {
        return countQuarantine;
    }

    public void setCountQuarantine(String countQuarantine) {
        this.countQuarantine = countQuarantine;
    }

    public static List<Record> testData() {
        List<Record> sample = new ArrayList<>();
        sample.add(new Record("Karnataka","23","12"));
        sample.add(new Record("Chhattisgarh","10","2"));
        sample.add(new Record("Assam","3","1"));
        sample.add(new Record("Goa","4","2"));
        sample.add(new Record("Delhi","31","10"));
        sample.add(new Record("Punjab","19","15"));
        sample.add(new Record("Kerela","17","14"));
        return sample;
    }
}
