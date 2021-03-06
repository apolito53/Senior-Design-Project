package com.newtest.test.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private User user;
    private SharedPreferences sp;
    private String fingerprintPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        user = getIntent().getParcelableExtra("UserKey");
        TextView tv = (TextView) findViewById(R.id.user_label);
        tv.setText(user.getUsername());

        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        fingerprintPermission = sp.getString("useFingerprint", "");

        updateUI();
    }

    private void updateUI() {
        //to create a logout button
        Button logoutBtn = findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();

                ed.putString("username", "");
                ed.putString("password", "");
                ed.putString("locationPermission", "");
                ed.putString("useFingerprint", "");
                ed.apply();
                startActivity(new Intent(Settings.this, SignInSignUp.class));
            }
        });

        //to create a button to update your account info
        Button updateAccountInfoBtn = findViewById(R.id.update_account_info_button);
        updateAccountInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, AccountSettings.class);
                intent.putExtra("UserKey", user);
                startActivity(intent);
            }
        });

        //to create a button to update billing info
        Button updateBillingInfoBtn = findViewById(R.id.update_billing_info_button);
        updateBillingInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, BillingInformation.class);
                intent.putExtra("UserKey", user);
                startActivity(intent);
            }
        });

        //to create a button to update your identification activity_settings
        Button fingerprintSettingsBtn = findViewById(R.id.update_fingerprint_settings_button);
        if(fingerprintPermission.equals("")) {
            fingerprintSettingsBtn.setError("");
        }
        fingerprintSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, FingerprintSettings.class);
                intent.putExtra("UserKey", user);
                startActivity(intent);
                finish();
            }
        });

        //to create a button to go back to account page
        Button backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd = new ProgressDialog(Settings.this);
                pd.setMessage("Retrieving account information...");
                pd.show();
                Intent intent = new Intent(Settings.this, Account.class);
                intent.putExtra("UserKey", user);
                startActivity(intent);
            }
        });

        Button websiteBtn = findViewById(R.id.website_button);
        websiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://cardguard.azurewebsites.net/"));
                startActivity(browserIntent);
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, Account.class);
        intent.putExtra("UserKey", user);
        startActivity(intent);
    }
}
