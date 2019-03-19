package org.ivd.weather.tools.service;

public class CheckService {
    public boolean isNullOrEmpty(String str) {
        if (str != null && !str.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
