package com.rohg007.android.innovaccerassignment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rohg007.android.innovaccerassignment.ui.main.AddHostActivity;
import com.rohg007.android.innovaccerassignment.ui.main.LoginActivity;
import com.rohg007.android.innovaccerassignment.ui.main.MakeEntry;
import com.rohg007.android.innovaccerassignment.ui.main.PastEntryFragment;
import com.rohg007.android.innovaccerassignment.ui.main.PlaceholderFragment;
import com.rohg007.android.innovaccerassignment.ui.main.RunningEntryFragment;
import com.rohg007.android.innovaccerassignment.ui.main.SectionsPagerAdapter;
import com.rohg007.android.innovaccerassignment.utils.MailSender;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        sectionsPagerAdapter.addFrag(new MakeEntry());
        sectionsPagerAdapter.addFrag(new RunningEntryFragment());
        sectionsPagerAdapter.addFrag(new PastEntryFragment());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddHostActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton powerButton= findViewById(R.id.logout_button);
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_logout:
                Toast.makeText(MainActivity.this,"Add Host Clicked",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}