package com.rohg007.android.innovaccerassignment.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EntryAdapter extends ArrayAdapter<Entry> {

    private Context context;
    private List<Entry> entryList;
    private int callingFragment;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

    public EntryAdapter(Context context, List<Entry> entryList, int callingFragment){
        super(context,0,entryList);
        this.callingFragment=callingFragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem=convertView;
        if(listViewItem==null){
            listViewItem= LayoutInflater.from(getContext()).inflate(R.layout.meeting_card,null,false);
        }

        final Entry currentEntry = getItem(position);

        TextView vistorNameTv = listViewItem.findViewById(R.id.visitor_name_tv);
        String visitorName = currentEntry.getVisitorName();
        vistorNameTv.setText(visitorName);

        TextView hostNameTv = listViewItem.findViewById(R.id.host_name_tv);
        String hostName = currentEntry.getHost().getHostName();
        hostNameTv.setText(hostName);

        TextView checkInTimeTv = listViewItem.findViewById(R.id.check_in_time_tv);
        String checkInTime = currentEntry.getCheckInTime();
        checkInTimeTv.setText(checkInTime);

        TextView checkOutTimeTv = listViewItem.findViewById(R.id.check_out_time_tv);
        String checkOutTime = currentEntry.getCheckOutTime();
        checkOutTimeTv.setText(checkOutTime);

        Button checkoutButton = listViewItem.findViewById(R.id.checkout_button);
        final ImageView leftIcon = listViewItem.findViewById(R.id.left_arrow_icon);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkOutStamp = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());
                currentEntry.setCheckOutTime(checkOutStamp);
                databaseReference.child("entries").child(currentEntry.getEntryId()).setValue(currentEntry).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Snackbar.make(leftIcon, "Checked Out", Snackbar.LENGTH_SHORT).show();
                        }
                        else
                            Snackbar.make(leftIcon,"Error Checking Out",Snackbar.LENGTH_SHORT).show();
                    }
                });
                String message = "Here are your meeting details:\n"+"\n"+
                        "Name: "+currentEntry.getVisitorName()+"\n"+
                        "Phone: "+currentEntry.getVisitorPhone()+"\n"+
                        "Check-In Time: "+currentEntry.getCheckInTime()+"\n"+
                        "Check-Out Time: "+currentEntry.getCheckOutTime()+"\n"+
                        "Host Name: "+currentEntry.getHost().getHostName()+"\n"+
                        "Address Visited: "+currentEntry.getHost().getHostAddress();
                SendMail sendMail = new SendMail(getContext(),currentEntry.getVistorEmail(),"Your Meeting Details",message);
                sendMail.execute();
            }
        });

        if(callingFragment==100) {
            checkOutTimeTv.setVisibility(View.GONE);
            leftIcon.setVisibility(View.GONE);
        } else {
            checkoutButton.setVisibility(View.GONE);
        }

        return listViewItem;
    }

}
