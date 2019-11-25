package com.rohg007.android.innovaccerassignment.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rohg007.android.innovaccerassignment.MainActivity;
import com.rohg007.android.innovaccerassignment.R;
import com.rohg007.android.innovaccerassignment.adapters.HostAdapter;
import com.rohg007.android.innovaccerassignment.model.Entry;
import com.rohg007.android.innovaccerassignment.model.Host;
import com.rohg007.android.innovaccerassignment.utils.MailSender;
import com.rohg007.android.innovaccerassignment.utils.SendMail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HostListActivity extends AppCompatActivity {

    ListView hostListView;
    private static final int ACTIVITY_CODE = 101;
    DatabaseReference databaseReference;
    List<Host> hostList = new ArrayList<>();
    HostAdapter adapter;
    private static final int MAX_SMS_LENGTH = 160;
    private final static int SEND_SMS_PERMISSION_REQ=1;
    String phonenumber;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_list);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Select Host to Check In");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if(!checkPermission(Manifest.permission.SEND_SMS))
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Intent intent = getIntent();
        final Entry currEntry = intent.getParcelableExtra("ENTRY");

        hostListView = findViewById(R.id.host_list_view);
        adapter = new HostAdapter(HostListActivity.this, hostList);
        hostListView.setAdapter(adapter);
        getHostListFromFirebase();

        hostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currEntry.setHost(hostList.get(position));
                addDetailsToFirebase(currEntry);
                phonenumber = currEntry.getHost().getHostPhone();
                String message = "You've a visitor!\n"+"\n"+
                        "Visitor's Name: "+ currEntry.getVisitorName() +"\n"+
                        "Visitor's Email: "+ currEntry.getVistorEmail() +"\n"+
                        "Visitor's Phone: "+ currEntry.getVisitorPhone() +"\n";
                sendSMS(message);
                SendMail sm = new SendMail(HostListActivity.this, currEntry.getHost().getHostEmail(), "Innovaccer Assignment", message);
                sm.execute();
            }
        });
    }

    private void sendEmail(){
        MailSender sender = new MailSender(getResources().getString(R.string.auth_email),getResources().getString(R.string.auth_password));
        try {
            sender.sendMail("Innovacer Assignment","Hey! This is a test mail",getResources().getString(R.string.auth_email),"Rohan Gupta","17dcs012@lnmiit.ac.in");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendSMS(String message){
        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            SmsManager.getDefault().sendTextMessage(phonenumber, null, message, null,null);
        }
        else{
            ActivityCompat.requestPermissions(HostListActivity.this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
            if(checkPermission(Manifest.permission.SEND_SMS))
            {
                SmsManager.getDefault().sendTextMessage(phonenumber, null, message, null,null);
            }
        }

    }

    private void addDetailsToFirebase(Entry currEntry){
        String id = databaseReference.push().getKey();
        currEntry.setEntryId(id);
        String checkInStamp = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
        currEntry.setCheckInTime(checkInStamp);

        databaseReference.child("entries").child(id).setValue(currEntry).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar.make(hostListView,"Successfully Checked-In", Snackbar.LENGTH_LONG).show();
                    finish();
                } else {
                    Snackbar.make(hostListView,"Can't Check In", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getHostListFromFirebase(){
        databaseReference.child("hosts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hostList.clear();
                for(DataSnapshot hostSnapshot : dataSnapshot.getChildren()){
                    hostList.add(hostSnapshot.getValue(Host.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(hostListView,"Error Retrieving Hosts", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkPermission(String sendSms) {
        int checkpermission= ContextCompat.checkSelfPermission(this,sendSms);
        return checkpermission== PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
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
