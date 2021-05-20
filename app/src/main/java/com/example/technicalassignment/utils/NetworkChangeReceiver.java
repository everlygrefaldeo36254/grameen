package com.example.technicalassignment.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private NetworkChangeCallback callback;

    public NetworkChangeReceiver() {

    }

    public NetworkChangeReceiver(NetworkChangeCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean status = isNetworkAvailable(context);

        if (callback != null) {
            callback.onNetworkChanged(status);
        }

    }

    public boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            return (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting());
        } catch (NullPointerException e) {
            return false;
        }
    }

}

