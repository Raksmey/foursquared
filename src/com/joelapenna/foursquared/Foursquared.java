/**
 * Copyright 2009 Joe LaPenna
 */

package com.joelapenna.foursquared;

import com.joelapenna.foursquare.Foursquare;
import com.joelapenna.foursquared.error.FoursquaredCredentialsError;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Joe LaPenna (joe@joelapenna.com)
 */
public class Foursquared extends Application {
    public static final String TAG = "Foursquared";
    public static final boolean DEBUG = true;

    public static final String EXTRAS_VENUE_KEY = "venue";

    public static final String PREFERENCE_PHONE = "phone";
    public static final String PREFERENCE_PASSWORD = "password";

    // Hidden preferences
    public static final String PREFERENCE_EMAIL = "email";

    public Foursquare mFoursquare;

    public void onCreate() {
        try {
            loadCredentials();
        } catch (FoursquaredCredentialsError e) {
            startPreferences();
        }
    }

    public Foursquare getFoursquare() {
        return mFoursquare;
    }

    public void loadCredentials() throws FoursquaredCredentialsError {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String phoneNumber = settings.getString(Foursquared.PREFERENCE_PHONE, null);
        String password = settings.getString(Foursquared.PREFERENCE_PASSWORD, null);

        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password)) {
            throw new FoursquaredCredentialsError(
                    "Phone number or password not set in preferences.");
        }
        mFoursquare = new Foursquare(phoneNumber, password);
    }

    public void startPreferences() {
        startActivity(new Intent(Foursquared.this, PreferenceActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
