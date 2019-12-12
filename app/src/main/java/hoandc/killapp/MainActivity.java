package hoandc.killapp;

import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppAdapter.IKillApp {

    private ActivityManager activityManager;
    private AppAdapter mAdapter;
    private RecyclerView mAppRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppRecyclerView = findViewById(R.id.rc_apps);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAppRecyclerView.setLayoutManager(layoutManager);
        setAppData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAppData();
    }

    /**
     * Update list app info
     */
    private void setAppData() {
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        PackageManager packageManager = getApplicationContext().getPackageManager();
        if (activityManager == null || packageManager == null) return;
        List<AppInfo> appInfoList = new ArrayList<>();
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
            String processName = processInfo.processName;
            String packageName;
            String appName = "";
            if (processName.contains("system") || processName.contains("phone")
                    || processName.contains("com.android") || processName.contains("hoandc.killapp")
                    || processName.contains("com.google.process") || processName.contains("com.google.android")) {
                continue;
            }
            if (processInfo.pkgList == null) {
                packageName = processName;
            } else {
                packageName = processInfo.pkgList[0];
            }
            try {
                ApplicationInfo info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                appName = (String) packageManager.getApplicationLabel(info);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (packageName.isEmpty() || appName.isEmpty()) {
                continue;
            }
            AppInfo appInfo = new AppInfo(appName, packageName);
            if (!isExistedApp(appInfoList, appInfo)) {
                appInfoList.add(appInfo);
            }
        }
        if (mAdapter == null) {
            mAdapter = new AppAdapter(this, appInfoList, this);
            mAppRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(appInfoList);
        }
    }

    /**
     * Checking if existed on list.
     *
     * @param appInfoList list app.
     * @param appInfo     app to check.
     * @return true if existed.
     */
    private boolean isExistedApp(List<AppInfo> appInfoList, AppInfo appInfo) {
        for (AppInfo info : appInfoList) {
            if (info.getAppName().equals(appInfo.getAppName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void OnKill(AppInfo appInfo) {
        if (activityManager != null) {
            activityManager.killBackgroundProcesses(appInfo.getAppPackage());
            Toast.makeText(this, appInfo.getAppName() + "has been killed!", Toast.LENGTH_LONG).show();
            setAppData();
        }
    }
}
