package com.example.attendclassapps.ui.classSchedule;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule.FriFragment;
import com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule.MonFragment;
import com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule.SatFragment;
import com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule.ThuFragment;
import com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule.TueFragment;
import com.example.attendclassapps.ui.classSchedule.Fragment_classSchedule.WedFragment;
import com.example.attendclassapps.R;

public class ClassScheduleActivity extends AppCompatActivity {

    SectionsPageAdapter mSectionsPageAdapter;
    ViewPager mViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_schedule);
        setTitle(getString(R.string.label_class_shedule));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

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
