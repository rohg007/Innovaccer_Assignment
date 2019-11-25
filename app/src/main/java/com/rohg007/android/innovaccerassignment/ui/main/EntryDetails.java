package com.rohg007.android.innovaccerassignment.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rohg007.android.innovaccerassignment.R;
import com.rohg007.android.innovaccerassignment.model.Entry;
import com.rohg007.android.innovaccerassignment.utils.SendMail;

public class EntryDetails extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Entry Detail");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        TextView visitorNameTv = findViewById(R.id.visitor_name_detail);
        TextView visitorEmailTv = findViewById(R.id.visitor_email_detail);
        TextView visitorPhoneTv = findViewById(R.id.visitor_phone_detail);
        TextView hostNameTv = findViewById(R.id.host_name_detail);
        TextView hostEmailTv = findViewById(R.id.host_email_detail);
        TextView hostPhoneTv = findViewById(R.id.host_phone_detail);
        TextView hostAddressTv = findViewById(R.id.host_address_detail);
        TextView checkInTimeTv = findViewById(R.id.checkin_detail);
        TextView checkOutTimeTv = findViewById(R.id.checkout_detail);
       // TextView durationTv = findViewById(R.id.duration_detail);
        final Button checkOutButton = findViewById(R.id.detail_checkout_button);

        Intent intent = getIntent();
        final Entry entry = intent.getParcelableExtra("ENTRY");
        visitorNameTv.setText(entry.getVisitorName());
        visitorEmailTv.setText(entry.getVistorEmail());
        visitorPhoneTv.setText(entry.getVisitorPhone());
        hostNameTv.setText(entry.getHost().getHostName());
        hostEmailTv.setText(entry.getHost().getHostEmail());
        hostPhoneTv.setText(entry.getHost().getHostPhone());
        hostAddressTv.setText(entry.getHost().getHostPhone());
        checkInTimeTv.setText(entry.getCheckInTime());
        checkOutTimeTv.setText(entry.getCheckOutTime());

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkOutStamp = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
                entry.setCheckOutTime(checkOutStamp);
                databaseReference.child("entries").child(entry.getEntryId()).setValue(entry).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Snackbar.make(checkOutButton, "Checked Out", Snackbar.LENGTH_SHORT).show();
                        }
                        else
                            Snackbar.make(checkOutButton,"Error Checking Out",Snackbar.LENGTH_SHORT).show();
                    }
                });
                String message = "Here are your meeting details:\n"+"\n"+
                        "Name: "+entry.getVisitorName()+"\n"+
                        "Phone: "+entry.getVisitorPhone()+"\n"+
                        "Check-In Time: "+entry.getCheckInTime()+"\n"+
                        "Check-Out Time: "+entry.getCheckOutTime()+"\n"+
                        "Host Name: "+entry.getHost().getHostName()+"\n"+
                        "Address Visited: "+entry.getHost().getHostAddress();
                SendMail sendMail = new SendMail(EntryDetails.this,entry.getVistorEmail(),"Your Meeting Details",message);
                sendMail.execute();
                finish();
            }
        });
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
