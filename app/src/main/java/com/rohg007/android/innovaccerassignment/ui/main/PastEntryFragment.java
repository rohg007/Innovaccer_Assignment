package com.rohg007.android.innovaccerassignment.ui.main;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
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
public class PastEntryFragment extends Fragment {

    private ListView pastEntryListView;
    private List<Entry> entryList;
    private DatabaseReference databaseReference;
    private EntryAdapter adapter;
    private int FRAGMENT_CODE = 99;
    private ValueEventListener eventListener;

    public PastEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        entryList.clear();
        getPastEntriesFromFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid());
        entryList = new ArrayList<>();
        View v = inflater.inflate(R.layout.fragment_past_entry, container, false);
        pastEntryListView = v.findViewById(R.id.past_entry_list_view);
        TextView empty_case = v.findViewById(android.R.id.empty);
        pastEntryListView.setEmptyView(empty_case);
        adapter = new EntryAdapter(getActivity(), entryList,FRAGMENT_CODE);
        pastEntryListView.setAdapter(adapter);
        getPastEntriesFromFirebase();

        pastEntryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entry currEntry = entryList.get(position);
                Intent intent = new Intent(getActivity(),EntryDetails.class);
                intent.putExtra("ENTRY",currEntry);
                intent.putExtra("FRAGMENT_CODE",FRAGMENT_CODE);
                startActivity(intent);
            }
        });



        return v;
    }

    private void getPastEntriesFromFirebase(){
        if (databaseReference != null && eventListener != null) {
            databaseReference.removeEventListener(eventListener);
        }
        eventListener = databaseReference.child("entries").orderByChild("checkOutTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                entryList.clear();
                for(DataSnapshot pastEntrySnapshot : dataSnapshot.getChildren()){
                    Entry entry = pastEntrySnapshot.getValue(Entry.class);
                    if(!entry.getCheckOutTime().equals(""))
                        entryList.add(entry);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Snackbar.make(pastEntryListView,"Couldn't Retrive Past Entries",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
