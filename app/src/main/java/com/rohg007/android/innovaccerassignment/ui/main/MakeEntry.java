package com.rohg007.android.innovaccerassignment.ui.main;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rohg007.android.innovaccerassignment.R;
import com.rohg007.android.innovaccerassignment.model.Entry;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeEntry extends Fragment {

    String phonenumber;
    String message="Hi Buccha!";


    public MakeEntry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_make_entry, container, false);
        final EditText visitorNameEdt = v.findViewById(R.id.visitor_name_edt);
        final EditText visitorEmailEdt = v.findViewById(R.id.visitor_email_edt);
        final EditText visitorPhoneEdt = v.findViewById(R.id.visitor_phone_edt);
        Button checkInButton = v.findViewById(R.id.checkin_button);
        Button resetButton = v.findViewById(R.id.reset_button);
        phonenumber = visitorPhoneEdt.getText().toString();

        final Entry entry = new Entry();

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidity(visitorNameEdt.getText().toString(),visitorEmailEdt.getText().toString(),visitorPhoneEdt.getText().toString())) {
                    entry.setVisitorName(visitorNameEdt.getText().toString());
                    entry.setVistorEmail(visitorEmailEdt.getText().toString());
                    entry.setVisitorPhone(visitorPhoneEdt.getText().toString());
                    Intent intent = new Intent(getActivity(), HostListActivity.class);
                    intent.putExtra("ENTRY", entry);
                    startActivity(intent);
                    visitorNameEdt.setText("");
                    visitorEmailEdt.setText("");
                    visitorPhoneEdt.setText("");
                } else {
                    Toast.makeText(getActivity(),"One or More fields entered aren't valid",Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitorNameEdt.setText("");
                visitorEmailEdt.setText("");
                visitorPhoneEdt.setText("");
            }
        });

        return v;
    }

    private boolean checkValidity(String name, String email, String phonenumber){
        return (isValidEmail(email)&&isValidMobile(phonenumber)&&!name.isEmpty());
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private Boolean isValidMobile(String number){
        String num_Pattern = "^[1-9]{1}[0-9]{9}$";
        Pattern pattern = Pattern.compile(num_Pattern);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
