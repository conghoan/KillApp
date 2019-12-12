package hoandc.killapp;

import java.util.List;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2019. All rights reserved
 *  Author HoanDC. Create on 12/12/2019.
 * ******************************************************************************
 */
public class AppInfo {
    private String appName;
    private String appPackage;

    AppInfo(String appName, String appPackage) {
        this.appName = appName;
        this.appPackage = appPackage;
    }

    String getAppName() {
        return appName;
    }

    void setAppName(String appName) {
        this.appName = appName;
    }

    String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }
}
