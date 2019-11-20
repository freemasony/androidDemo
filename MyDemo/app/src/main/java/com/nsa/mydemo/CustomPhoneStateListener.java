package com.nsa.mydemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2019/11/18.
 */

public class CustomPhoneStateListener extends PhoneStateListener  {
    private Context mContext;

    public CustomPhoneStateListener(Context context) {
        mContext = context;
    }

    @Override
    public void onServiceStateChanged(ServiceState serviceState) {
        super.onServiceStateChanged(serviceState);
        Log.d(PhoneListenService.TAG, "CustomPhoneStateListener onServiceStateChanged: " + serviceState);
    }
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        Log.d(PhoneListenService.TAG, "CustomPhoneStateListener state: "
                + state + " incomingNumber: " + incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:      // 电话挂断
                break;
            case TelephonyManager.CALL_STATE_RINGING:   // 电话响铃
                Toast.makeText(mContext, "吐司一下", Toast.LENGTH_LONG).show();
                if(incomingNumber.equals("15910529830")){
                    openDing(mContext);
                    sendEMail();
                }
//                HangUpTelephonyUtil.endCall(mContext);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:   // 来电接通 或者 去电，去电接通  但是没法区分
                break;
        }
    }


    private void openDing(Context context) {
        try {
            openDing("com.alibaba.android.rimet", context);//吊起钉钉
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public static void openDing(String packageName,Context context) {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo pi = null;
            try {
                pi = packageManager.getPackageInfo("com.alibaba.android.rimet", 0);
            } catch (PackageManager.NameNotFoundException e) {
            }
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
            List<ResolveInfo> apps = packageManager.queryIntentActivities(resolveIntent, 0);
            ResolveInfo resolveInfo = apps.iterator().next();
            if (resolveInfo != null ) {
                String className = resolveInfo.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        }

    private void sendEMail() {
        MailManager.getInstance().sendMail("打卡邮件", "app运行正常");
    }
}
