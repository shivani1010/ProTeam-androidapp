package com.android.proteam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityModel {

    @SerializedName("city_list")
    @Expose
    private List<CityList> cityList = null;

    public List<CityList> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityList> cityList) {
        this.cityList = cityList;
    }

    public class CityList {

        @SerializedName("city_id")
        @Expose
        private Integer cityId;
        @SerializedName("city_name")
        @Expose
        private String cityName;

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

    }
}
