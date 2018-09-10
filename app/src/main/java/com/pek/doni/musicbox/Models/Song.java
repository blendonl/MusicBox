package com.pek.doni.musicbox.Models;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by USER on 2/12/2018.
 */

public class Song {

    public final long albumId;
    public final String albumName;
    public final int artistId;
    public final String artistName;
    public final int duration;
    public final long id;
    public final String title;
    public final int trackNumber;
    public final String path;
    public String file;
    public String bitmap;

    public Song() {
        this.id = -1;
        this.albumId = -1;
        this.artistId = -1;
        this.title = "";
        this.artistName = "";
        this.albumName = "";
        this.duration = -1;
        this.trackNumber = -1;
        this.path = "";
        this.file = null;
        this.bitmap = null;
    }

    public Song(String image,String file, String path, long _id, long _albumId, int _artistId, String _title, String _artistName, String _albumName, int _duration, int _trackNumber) {
        this.id = _id;
        this.albumId = _albumId;
        this.artistId = _artistId;
        this.title = _title;
        this.artistName = _artistName;
        this.albumName = _albumName;
        this.duration = _duration;
        this.trackNumber = _trackNumber;
        this.path = path;
        this.file = file;
        this.bitmap = image;
    }


    public String getAlbum()
    {


        return albumName;
    }

    public long getAlbumId()
    {


        return albumId;
    }

    public int getArtistId()
    {


        return artistId;
    }

    public String getArtist()
    {

        return artistName;
    }

    public long getId()
    {


        return id;
    }

    public String getTitle()
    {

        return title;
    }


    public int getTrackNumber()
    {

        return trackNumber;
    }

    public String getPath()
    {

        return path;
    }
    public String getFile()
    {

        return file;
    }

    public String getImage()
    {
        return bitmap;
    }









}
