package com.e.weatherapp.apicall;

import com.e.weatherapp.model.Weather;

import java.util.ArrayList;

public interface Calback {

    public void callBackListener(ArrayList<Weather> dataList);
}
