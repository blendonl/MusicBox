package com.pek.doni.musicbox.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.pek.doni.musicbox.Methods;
import com.pek.doni.musicbox.Models.Artist;
import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.RecyclerTouchListener;
import com.pek.doni.musicbox.activities.ArtistDetailsActivity;
import com.pek.doni.musicbox.activities.MainActivity;
import com.pek.doni.musicbox.adapters.ArtistsListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2/8/2018.
 */

public class ArtistsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    Context context;
    List<Artist> artists;
    RecyclerView recyclerView;
    View rootView;
    Intent i;

    GridLayoutManager layoutManager;

    ReadAllSongs a;

    RecyclerView list;

    ArtistsListAdapter la;

    private final Methods m = new Methods();




    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Bundle b = getArguments();

        a = b.getParcelable("ReadAllSongs");

        artists = a.getAllArtists();
        context = getActivity().getApplicationContext();

        la = new ArtistsListAdapter(artists,context);
        i = new Intent(getActivity().getApplicationContext(), ArtistDetailsActivity.class);





    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerList);


       layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity().getApplicationContext()) {

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

        layoutManager.setInitialPrefetchItemCount(2);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(la);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                recyclerView, new MainActivity.onClickListener() {
            @Override
            public void onClick(View view, int position) {



                i.putExtra("Artist", la.getArtistList().get(position).getName());
                i.putExtra("Image", String.valueOf(la.getArtistList().get(position).getAlbumID()));
                i.putExtra("ReadAllSongs",a);
                getActivity().getApplicationContext().startActivity(i);

                Log.d("AAa",la.getArtistList().get(position).getName() + "    aa");


            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        list = recyclerView;
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

        List<Artist> filteredValues = new ArrayList<>(artists);


        for (Artist value : artists) {
            if (!value.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        la = new ArtistsListAdapter(filteredValues,context);
        list.setAdapter(la);

        return false;
    }

    public void resetSearch() {
        la = new ArtistsListAdapter(artists,context);
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

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }







}


