package com.rohg007.android.innovaccerassignment.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohg007.android.innovaccerassignment.R;
import com.rohg007.android.innovaccerassignment.model.Host;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddHostActivity extends AppCompatActivity {

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_host);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Add Host");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        final EditText hostNameEdt = findViewById(R.id.add_hostName_edt);
        final EditText hostEmailEdt = findViewById(R.id.add_hostEmail_edt);
        final EditText hostPhoneEdt = findViewById(R.id.add_hostPhone_edt);
        final EditText hostAddressEdt = findViewById(R.id.add_hostAddress_edt);

        final Button addHostButton = findViewById(R.id.host_submit_button);
        Button clearButton = findViewById(R.id.host_clear_button);

        addHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidity(hostNameEdt.getText().toString(),hostEmailEdt.getText().toString(),hostPhoneEdt.getText().toString(),hostAddressEdt.getText().toString())){
                    String id = databaseReference.push().getKey();
                    Host host = new Host();
                    host.setHostId(id);
                    host.setHostName(hostNameEdt.getText().toString());
                    host.setHostEmail(hostEmailEdt.getText().toString());
                    host.setHostPhone(hostPhoneEdt.getText().toString());
                    host.setHostAddress(hostAddressEdt.getText().toString());

                    databaseReference.child("hosts").child(id).setValue(host).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Snackbar.make(hostNameEdt,"Host Added",Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(hostNameEdt,"Host couldn't be Added",Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Snackbar.make(addHostButton,"One or More Fields aren't valid",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostNameEdt.setText("");
                hostEmailEdt.setText("");
                hostPhoneEdt.setText("");
                hostAddressEdt.setText("");
            }
        });
    }

    private boolean checkValidity(String name, String email, String phonenumber, String address){
        return (isValidEmail(email)&&isValidMobile(phonenumber)&&!name.isEmpty()&&!address.isEmpty());
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
