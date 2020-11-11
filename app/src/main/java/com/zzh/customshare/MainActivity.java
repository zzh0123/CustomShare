package com.zzh.customshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_share = findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        getShareAppList();
    }

    private List<AppInfo> getShareAppList() {
        List<AppInfo> shareAppInfos = new ArrayList<AppInfo>();
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(this);
        if (null == resolveInfos) {
            return null;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.setAppPkgName(resolveInfo.activityInfo.packageName);
                Log.i("zzz1", "getAppPkgName " + appInfo.getAppPkgName());
//				showLog_I(TAG, "pkg>" + resolveInfo.activityInfo.packageName + ";name>" + resolveInfo.activityInfo.name);
                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);
                Log.i("zzz1", "ClassName " + appInfo.getAppLauncherClassName());
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                Log.i("zzz1", "AppName " + appInfo.getAppName());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appInfo);
            }
        }
        return shareAppInfos;
    }

    public List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
//		intent.setType("*/*");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent, 0);
        return mApps;
    }

    private void showDialog() {
        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

}
