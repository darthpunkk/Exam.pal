package com.example.android.exampal;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements ActionBar.TabListener {
    private ViewPager  viewPager;
    private ActionBar actionBar;
    private TabPagerAdapter mAdapter;

    private String[] tabs ={"Faculty","Student"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager = (ViewPager)findViewById(R.id.pager);
        actionBar = getSupportActionBar();

        mAdapter = new TabPagerAdapter(getSupportFragmentManager());
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        viewPager.setAdapter(mAdapter);

        for(int i=0;i<2;i++)
        {
            actionBar.addTab(actionBar.newTab().setText(tabs[i]).setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if(tab.getPosition()==0)
            Toast.makeText(this,"you are a student",Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(this,"you are a Faculty",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
