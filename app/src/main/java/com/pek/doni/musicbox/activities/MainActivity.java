package com.pek.doni.musicbox.activities;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.fragments.AlbumsFragment;
import com.pek.doni.musicbox.fragments.ArtistsFragment;
import com.pek.doni.musicbox.fragments.SongsFragment;
import com.sothree.slidinguppanel.ScrollableViewHelper;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity{

    DrawerLayout drawerLayout1;
    Toolbar toolbar;
    ActionBar actionBar;
    NavigationView view;
    TabLayout tabLayout;
    SongsFragment songsFragment;
    AlbumsFragment albumsFragment;
    ArtistsFragment artistsFragment;
    ImageView iv;
    static ReadAllSongs r;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            requestStoragePermission();

        }





        r = new ReadAllSongs(getContentResolver());


        Bundle b = new Bundle();

        b.putParcelable("ReadAllSongs", r);


        songsFragment = new SongsFragment();
        songsFragment.setArguments(b);
        albumsFragment = new AlbumsFragment();
        albumsFragment.setArguments(b);

        artistsFragment = new ArtistsFragment();
        artistsFragment.setArguments(b);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        iv = findViewById(R.id.image);

        drawerLayout1 = findViewById(R.id.drawerL);

        view = (NavigationView) findViewById(R.id.nav_view);

        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                menuItem.setChecked(true);

                drawerLayout1.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.layout.activity_downloads:

                        Intent i = new Intent(getApplicationContext(), DownloadsActivity.class);
                        startActivity(i);


                }
                return true;

            }


        });


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public boolean setImage(String s){


        Picasso.with(getApplicationContext()).load(s).fit().into(iv);

        return true;
    }


    public void requestStoragePermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Storage")
                    .setMessage("Access storage to play music files")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();

        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                r = new ReadAllSongs(getContentResolver());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                drawerLayout1.openDrawer(GravityCompat.START);

        }

        return super.onOptionsItemSelected(item);
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {




        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public int p = -1;
        @Override
        public Fragment getItem(int position) {

            p = position;



            switch (position) {

                case 0:

                    return songsFragment;
                case 1:

                    return albumsFragment;
                case 2:

                    return artistsFragment;

                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }



    }



    public interface onClickListener{
        void onClick(View v, int position);
        void onLongClick(View v, int position);

}



}







