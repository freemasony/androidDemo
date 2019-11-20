package com.nsa.mydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerPhoneStateListener();
        findViewById(R.id.register_service).setOnClickListener(this);
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState){
        Log.d(TAG,"onPostCreate");
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart");
        super.onStart();
        registerPhoneStateListener();
    }


    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        switch (viewId) {
            case R.id.register_service:
                registerPhoneStateListener();
                break;
        }
    }

    private void registerPhoneStateListener() {
        Log.d(TAG,"registerPhoneStateListener");
        Intent intent = new Intent(this, PhoneListenService.class);
        intent.setAction(PhoneListenService.ACTION_REGISTER_LISTENER);
        startService(intent);
    }
}
