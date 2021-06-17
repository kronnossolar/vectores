package com.app.vector.vectores.view;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.app.vector.vectores.R;
import com.app.vector.vectores.dao.ConfigurationDao;
import com.app.vector.vectores.entity.Configuration;

public class Help extends AppCompatActivity {

    private ViewPager mViewPager;
    private FloatingActionButton btnNextArrow, btnBackArrow;
    private Configuration configuration;
    private ConfigurationDao configurationDao;
    private static final int PAGE_COUNT = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        HelpPageAdapter adapter = new HelpPageAdapter(getSupportFragmentManager());
        adapter.addFragment(HelpFragment.newInstance("Page 1", R.drawable.logo));
        adapter.addFragment(HelpFragment.newInstance("Page 2",R.drawable.logo));
        adapter.addFragment(HelpFragment.newInstance("Page 3",R.drawable.logo));
        adapter.addFragment(HelpFragment.newInstance("Page 4",R.drawable.logo));
        adapter.addFragment(HelpFragment.newInstance("Page 5",R.drawable.logo));
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        btnNextArrow = (FloatingActionButton) findViewById(R.id.btnNext);
        btnBackArrow = (FloatingActionButton) findViewById(R.id.btnBack);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);
        btnNextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int actualItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(actualItem+1);
                Toast.makeText(Help.this, "aaaaa", Toast.LENGTH_SHORT).show();
                if(mViewPager.getCurrentItem() > 0){
                    btnBackArrow.setVisibility(View.VISIBLE);
                }
                if(mViewPager.getCurrentItem()+1 == PAGE_COUNT){
                    btnNextArrow.setVisibility(View.GONE);
                }
            }
        });
        btnBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Help.this, "aaaaa", Toast.LENGTH_SHORT).show();
                int actualItem = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(actualItem - 1);
                if(actualItem == 1){
                    btnBackArrow.setVisibility(View.GONE);
                    btnNextArrow.setVisibility(View.VISIBLE);
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    btnBackArrow.setVisibility(View.GONE);
                    btnNextArrow.setVisibility(View.VISIBLE);
                }else{
                    btnBackArrow.setVisibility(View.VISIBLE);
                    if(position + 1 == PAGE_COUNT){
                        btnNextArrow.setVisibility(View.GONE);
                    }else{
                        btnNextArrow.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
