package com.pek.doni.musicbox.Models;

import android.graphics.Bitmap;

/**
 * Created by USER on 2/12/2018.
 */

public class Album {

    public final String artistName;
    public final long id;
    public final int songCount;
    public String title;
    public final int year;

    public Album() {
        this.id = -1;
        this.title = "";
        this.artistName = "";
        this.songCount = -1;
        this.year = -1;

    }

    public Album(long _id, String _title, String _artistName, int _songCount, int _year) {
        this.id = _id;
        this.title = _title;
        this.artistName = _artistName;
        this.songCount = _songCount;
        this.year = _year;
    }


    public String getArtistName() {
        return artistName;
    }
    public long getId()
    {
        return id;
    }
    public int getSongCount()
    {
        return songCount;
    }
    public String getTitle(){
        return title;
    }
    public int getYear(){
        return year;
    }



    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Album){

            return ((Album) obj).getTitle().equals(title);

        }
        return false;
    }
}
