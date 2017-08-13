package com.example.alex.nasapp.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.example.alex.nasapp.R;
import com.example.alex.nasapp.model.menuFeatures.MenuFeatures;

import butterknife.BindView;
import butterknife.ButterKnife;

//MainActivity which holds FeatureFragments in view pager
public class MainActivity extends FragmentActivity {

    @BindView (R.id.viewPager) ViewPager viewPager;
    @BindView (R.id.tabDots)  TabLayout tabDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentPagerAdapter pagerAdapter =
                new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                    return getFeatureFragment(0);

                    case 1:
                    return getFeatureFragment(1);

                    case 2:
                    return getFeatureFragment(2);

                    default:
                    return getFeatureFragment(0);
                }

            }

            @Override
            public int getCount() {
                return 3;
            }
        };

        viewPager.setAdapter(pagerAdapter);
        tabDots.setupWithViewPager(viewPager, true);
        }

    private Fragment getFeatureFragment(int i) {
        return FeatureFragment.newInstance(MenuFeatures.getMenuFeatures(i));
    }
}
