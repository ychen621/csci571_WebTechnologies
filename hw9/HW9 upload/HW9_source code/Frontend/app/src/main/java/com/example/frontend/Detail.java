package com.example.frontend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {

    private static final String SELECTED_ID = "selected_artist_id";
    private static final String SELECTED_NAME = "selected_artist_name";

    private String artistID;
    private String artistName;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        extractDataFromIntent();
        setTitle(artistName);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        //Testing
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("Details");
        titleList.add("Artwork");

        tabLayout.setupWithViewPager(viewPager);

        prepareViewerPager(viewPager, titleList);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    private void prepareViewerPager(ViewPager viewPager, ArrayList<String> titleList) {
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("params", artistID);
        Info myObj = new Info();
        Artwork myWork = new Artwork();
        myObj.setArguments(bundle);
        myWork.setArguments(bundle);

        adapter.addFragment(myObj, titleList.get(0));
        adapter.addFragment(myWork, titleList.get(1));

        viewPager.setAdapter(adapter);

    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        artistID = intent.getStringExtra(SELECTED_ID);
        artistName = intent.getStringExtra(SELECTED_NAME);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mybutton){
            return super.onOptionsItemSelected(item);
        }

        Intent intent = Search.makeIntent(Detail.this, artistName);
        startActivity(intent);
        this.finish();
        return true;
    }

    public static Intent makeDetailIntent(Context context, String artistName, String artistID){
        Intent intent = new Intent(context, Detail.class);
        intent.putExtra(SELECTED_NAME, artistName);
        intent.putExtra(SELECTED_ID, artistID);
        return intent;
    }

    private class MainAdapter extends FragmentPagerAdapter{

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        ArrayList<String> fragmentTitle = new ArrayList<>();
        int[] imageList = {R.drawable.alert_circle_outline, R.drawable.palette_outline};

        public void addFragment(Fragment fragment, String title){
            fragmentArrayList.add(fragment);
            fragmentTitle.add(title);
        }

        public MainAdapter(FragmentManager supportFragmentManager) {

            super(supportFragmentManager);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), imageList[position]);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

            SpannableString spannableString = new SpannableString("     " + fragmentTitle.get(position));
            ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);

            spannableString.setSpan(imageSpan,0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    }
}