package com.android.proteam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateModel
{

    @SerializedName("state_list")
    @Expose
    private List<StateList> stateList = null;

    public List<StateList> getStateList() {
        return stateList;
    }

    public void setStateList(List<StateList> stateList) {
        this.stateList = stateList;
    }


    public class StateList {

        @SerializedName("state_id")
        @Expose
        private Integer stateId;
        @SerializedName("state_name")
        @Expose
        private String stateName;

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

    }

}
