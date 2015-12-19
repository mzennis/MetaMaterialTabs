package com.mzennis.tab;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int TABS = 3;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pagerAdapter = new DCViewPagerAdapter(getSupportFragmentManager());

        onViewCreated();
    }

    protected void onViewCreated() {

        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Material Tab");

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(TABS);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        if (tabLayout != null) {
            tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.ColorPrimary));
            tabLayout.setTabTextColors(ContextCompat.getColor(getApplicationContext(), R.color.textColorPrimary), ContextCompat.getColor(getApplicationContext(), R.color.ColorPrimaryDark));
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
            tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition(), true);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        pagerAdapter.notifyDataSetChanged();
    }

    private class DCViewPagerAdapter extends FragmentStatePagerAdapter {

        public DCViewPagerAdapter(final FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(final int position) {
            return DummyFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(final int position) {
            return ((DummyFragment) getItem(position)).title();
        }

        @Override
        public int getCount() {
            return TABS;
        }

    }

    public static class DummyFragment extends Fragment {

        public static DummyFragment newInstance(final int position) {
            final DummyFragment dummyFragment = new DummyFragment();
            final Bundle bundle = new Bundle();
            bundle.putInt(POSITION, position);
            dummyFragment.setArguments(bundle);
            return dummyFragment;
        }

        private static final String POSITION = "position";

        private ArrayAdapter<String> mAdapter;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            title();
            final LayoutInflater layoutInflater = LayoutInflater.from(getActivity().getApplicationContext());
            mAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.listview_item) {
                @Override
                public View getView(final int position, View convertView, final ViewGroup parent) {
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
                    }
                    ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
                    return convertView;
                }

                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }

                @Override
                public boolean isEnabled(final int position) {
                    return false;
                }

            };
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_listview, container, false);
        }

        @Override
        public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ((ListView) view.findViewById(R.id.listView)).setAdapter(mAdapter);
        }

        public String title() {
            return String.format("Page #%d", getArguments().getInt(POSITION)).toUpperCase();
        }

        @Override
        public void onResume() {
            super.onResume();

            final int position = getArguments().getInt(POSITION);
            final int n = 16;
            mAdapter.clear();
            for (int i = 0; i < n; i++) {
                mAdapter.add("Meyta Zenis Taliti");
            }
            mAdapter.notifyDataSetChanged();
        }

    }
}
