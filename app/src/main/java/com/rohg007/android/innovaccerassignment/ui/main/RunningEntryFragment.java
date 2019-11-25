package com.rohg007.android.innovaccerassignment.ui.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rohg007.android.innovaccerassignment.R;
import com.rohg007.android.innovaccerassignment.adapters.EntryAdapter;
import com.rohg007.android.innovaccerassignment.model.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunningEntryFragment extends Fragment {

    private ListView runningEntryListView;
    private EntryAdapter adapter;
    private DatabaseReference databaseReference;
    private List<Entry> entryList = new ArrayList<>();

    private int FRAGMENT_CODE = 100;

    public RunningEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_running_entry, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());

        runningEntryListView = v.findViewById(R.id.running_entry_list_view);
        TextView empty_case = v.findViewById(android.R.id.empty);
        runningEntryListView.setEmptyView(empty_case);
        adapter = new EntryAdapter(getActivity(),entryList,FRAGMENT_CODE);
        runningEntryListView.setAdapter(adapter);
        getRunningEntriesFromFirebase();

        runningEntryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entry currEntry = entryList.get(position);
                Intent intent = new Intent(getActivity(),EntryDetails.class);
                intent.putExtra("ENTRY",currEntry);
                startActivity(intent);
            }
        });

        return v;
    }

    private void getRunningEntriesFromFirebase(){
        databaseReference.child("entries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entryList.clear();
                for(DataSnapshot entryDataSnapshot : dataSnapshot.getChildren()){
                    Entry entry = entryDataSnapshot.getValue(Entry.class);
                    if(entry.getCheckOutTime().equals(""))
                        entryList.add(entry);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(runningEntryListView,"Couldn't retrieve entries",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
