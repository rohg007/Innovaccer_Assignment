package com.rohg007.android.innovaccerassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rohg007.android.innovaccerassignment.R;
import com.rohg007.android.innovaccerassignment.model.Host;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HostAdapter extends ArrayAdapter<Host> {

    private Context context;
    private List<Host> hostList=null;

    public HostAdapter(Context context,List<Host> hostList){
        super(context,0,hostList);}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem==null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.host_item, null, false);
        }

        Host currHost = getItem(position);

        TextView hostNameTv = listViewItem.findViewById(R.id.host_item_name);
        String hostName = currHost.getHostName();
        hostNameTv.setText(hostName);

        TextView hostEmailTv = listViewItem.findViewById(R.id.host_item_email);
        String hostEmail = currHost.getHostEmail();
        hostEmailTv.setText(hostEmail);

        TextView hostPhoneTv = listViewItem.findViewById(R.id.host_item_phone);
        String hostPhone = currHost.getHostPhone();
        hostPhoneTv.setText(hostPhone);

        TextView hostAddressTv = listViewItem.findViewById(R.id.host_item_address);
        String hostAddress = currHost.getHostAddress();
        hostAddressTv.setText(hostAddress);

        return listViewItem;
    }
}
