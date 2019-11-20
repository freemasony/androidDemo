package com.nsa.mydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by popfisher on 2017/11/6.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d(PhoneListenService.TAG, "PhoneStateReceiver onReceive state: " + state);

        Intent  service  = new Intent(context, PhoneListenService.class);
        context.startService(service);
    }
}
