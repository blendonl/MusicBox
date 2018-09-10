package com.pek.doni.musicbox.fragments;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.pek.doni.musicbox.Constants;
import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.NotificationService;
import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.RecyclerTouchListener;
import com.pek.doni.musicbox.activities.MainActivity;
import com.pek.doni.musicbox.activities.NowPlayingActivity;
import com.pek.doni.musicbox.adapters.SongsListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2/8/2018.
 */

public class SongsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener  {


    public RecyclerView list;
    RecyclerView l;

    LinearLayoutManager layoutManager;

    ReadAllSongs s;

    Intent serviceIntent;
    Intent i;

    public List<Song> songs;

    private  View rootView;
    public Context context;

    public static SongsListAdapter la;


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        Bundle b = getArguments();

        s = b.getParcelable("ReadAllSongs");

        songs = s.getAllSongs();

        context = getActivity().getApplicationContext();



        la = new SongsListAdapter(songs,context);
        serviceIntent = new Intent(getActivity().getApplicationContext(), NotificationService.class);

        i = new Intent(context, NowPlayingActivity.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        l = (RecyclerView) rootView.findViewById(R.id.recyclerList);




        layoutManager = new LinearLayoutManager(context) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {

                    private static final float SPEED = 1000f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);


            }

        };

        l.setHasFixedSize(true);

        l.setLayoutManager(layoutManager);


        l.setAdapter(la);

        l.addOnItemTouchListener(new RecyclerTouchListener(context,
                l, new MainActivity.onClickListener() {
            @Override
            public void onClick(View view, int position) {

                serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                getActivity().getApplicationContext().startService(serviceIntent);
                serviceIntent.putExtra("Position",position);


                i.putExtra("Position",position);
                i.putExtra("Album","All");
                i.putExtra("ReadAllSongs",s);


                ((MainActivity)getActivity()).setImage(String.valueOf(la.getSonglist().get(position).getAlbumId()));


                startActivity(i);



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




        l.setHasFixedSize(true);

        list = l;


        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<Song> filteredValues = new ArrayList<Song>(songs);

        for (Song value : songs) {
            if (!value.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        la = new SongsListAdapter(filteredValues,context);
        list.setAdapter(la);

        return false;
    }

    public void resetSearch() {
        la = new SongsListAdapter(songs,context);
        list.setAdapter(la);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    public void startService(View v) {
        Intent serviceIntent = new Intent(getActivity().getApplicationContext(), NotificationService.class);
        serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        getActivity().getApplicationContext().startService(serviceIntent);
    }

}

