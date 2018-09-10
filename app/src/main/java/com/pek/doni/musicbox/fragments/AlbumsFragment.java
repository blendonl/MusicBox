package com.pek.doni.musicbox.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.pek.doni.musicbox.Methods;
import com.pek.doni.musicbox.Models.Album;
import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.R;
import com.pek.doni.musicbox.ReadAllSongs;
import com.pek.doni.musicbox.RecyclerTouchListener;
import com.pek.doni.musicbox.activities.AlbumDetailsActivity;
import com.pek.doni.musicbox.activities.MainActivity;
import com.pek.doni.musicbox.adapters.AlbumsListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2/8/2018.
 */

public class AlbumsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {


    Context context;
    List<Album> albums;


    private ReadAllSongs a;
    private RecyclerView list;
    private AlbumsListAdapter la;
    private RecyclerView l;
    final Methods m = new Methods();
    private GridLayoutManager layoutManager;
    View rootView;
    Intent i;





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

        albums = a.getAllAlbums();
        context = getActivity().getApplicationContext();
        la = new AlbumsListAdapter(a.getAllAlbums(),context);
        i = new Intent(context, AlbumDetailsActivity.class);

        i.putExtra("ReadAllSongs",a);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_main, container, false);


        l = (RecyclerView) rootView.findViewById(R.id.recyclerList);


        layoutManager = new GridLayoutManager(context,2) {

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




        l.setLayoutManager(layoutManager);
        l.setHasFixedSize(true);
        l.setItemAnimator(new DefaultItemAnimator());
        l.addOnItemTouchListener(new RecyclerTouchListener(context,
                l, new MainActivity.onClickListener() {
            @Override
            public void onClick(View view, int position) {






                i.putExtra("Album", la.getAlbumList().get(position).getTitle());
                i.putExtra("Image", String.valueOf(la.getAlbumList().get(position).getId()));
                i.putExtra("ReadAllSongs",a);
                context.startActivity(i);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        l.setAdapter(la);
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

        List<Album> filteredValues = new ArrayList<Album>(albums);

        for (Album value : albums) {
            if (!value.getTitle().toLowerCase().contains(newText.toLowerCase())) {

                filteredValues.remove(value);
            }
        }

        la = new AlbumsListAdapter(filteredValues,context);
        list.setAdapter(la);

        return false;
    }

    public void resetSearch() {
        la = new AlbumsListAdapter(albums,context);
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
