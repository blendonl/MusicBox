package com.pek.doni.musicbox.Models;



/**
 * Created by USER on 2/12/2018.
 */

public class Artist extends Object {

    public final int albumCount;
    public final int id;
    public String name;
    public final int songCount;
    public final String path;
    public final String bitmap;
    public int albumID;

    public Artist() {
        this.id = -1;
        this.name = "";
        this.songCount = -1;
        this.albumCount = -1;
        this.path = "";
        this.bitmap = null;
    }

    public Artist(String bitmap, String path,int _id,int albumID, String _name, int _albumCount, int _songCount) {
        this.id = _id;
        this.name = _name;
        this.songCount = _songCount;
        this.albumCount = _albumCount;
        this.path = path;
        this.bitmap = bitmap;
        this.albumID = albumID;
    }


    public int getAlbumCount()
    {

        return albumCount;
    }

    public long getId()
    {

        return id;
    }

    public int getAlbumID()
    {

        return albumID;
    }

    public String getName()
    {

        return name;
    }

    public int getSongCount()
    {
        return songCount;
    }

    public String getPath()
    {

        return path;
    }

    public String getBitmap()
    {

        return bitmap;
    }


    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Artist) {
            return ((Artist)obj).getName().equals(name);
        }
        return false;
    }








}
