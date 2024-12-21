package com.rbmjltd.uddoktapay;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {
    public static boolean isConnectedToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    // Remove the semicolon here
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;  // Return true if any network is connected
                    }
                }
            }
        }
        return false;  // Return false if no connected network was found
    }
}
