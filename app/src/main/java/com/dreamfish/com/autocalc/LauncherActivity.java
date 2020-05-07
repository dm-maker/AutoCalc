package com.dreamfish.com.autocalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import com.dreamfish.com.autocalc.dialog.CommonDialogs;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        testAgreementAllowed();
    }

    private void testAgreementAllowed() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("app_agreement_allowed", false)) {
            CommonDialogs.showPrivacyPolicyAndAgreement(this, (allowed) -> {
                if(allowed) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("app_agreement_allowed", true);
                    editor.apply();
                    startMain(false);
                }else finish();
            });
        }else startMain(true);
    }
    private void startMain(Boolean agreementAllowed) {
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(agreementAllowed ? 200 : 1000);
                    Intent it = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(it);
                    sleep(800);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }
}
