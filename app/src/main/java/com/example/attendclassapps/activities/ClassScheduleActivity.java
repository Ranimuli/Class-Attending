package com.example.attendclassapps.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.attendclassapps.fragment.FriFragment;
import com.example.attendclassapps.fragment.MonFragment;
import com.example.attendclassapps.fragment.SatFragment;
import com.example.attendclassapps.fragment.ThuFragment;
import com.example.attendclassapps.fragment.TueFragment;
import com.example.attendclassapps.fragment.WedFragment;
import com.example.attendclassapps.R;
import com.example.attendclassapps.SectionsPageAdapter;

public class ClassScheduleActivity extends AppCompatActivity {

    SectionsPageAdapter mSectionsPageAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);
        setTitle(getString(R.string.label_class_shedule));

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonFragment(), "Mon");
        adapter.addFragment(new TueFragment(), "Tue");
        adapter.addFragment(new WedFragment(), "Wed");
        adapter.addFragment(new ThuFragment(), "Thu");
        adapter.addFragment(new FriFragment(), "Fri");
        adapter.addFragment(new SatFragment(), "Sat");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
