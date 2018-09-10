package com.pek.doni.musicbox;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;


import com.pek.doni.musicbox.Models.Album;
import com.pek.doni.musicbox.Models.Artist;
import com.pek.doni.musicbox.Models.Song;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class ReadAllSongs extends ListActivity implements Parcelable{

    private static ArrayList<Song> songsList = new ArrayList<>();
    private static ArrayList<Album> albumsList = new ArrayList<>();

    private SortSongs s;

    ContentResolver c;



    public ReadAllSongs(ContentResolver contentR) {


        c = contentR;


        getAllSong();


    }


    public void getAllSong(){

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
        };




        ContentResolver contentResolver = c;
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, projection, null, null, null);

        s = new SortSongs();

        if(songCursor != null && songCursor.moveToFirst())
        {

            do {

                Song song = new Song("",null,songCursor.getString(6),Integer.parseInt(songCursor.getString(0)),Integer.parseInt(songCursor.getString(1)),Integer.parseInt(songCursor.getString(2)),songCursor.getString(3),songCursor.getString(5),songCursor.getString(4),0,0);

                songsList.add(song);

            } while(songCursor.moveToNext());

            Collections.sort(songsList,s);
        }

        HashSet<Song> h = new HashSet<Song>(songsList);



        songsList.clear();

        songsList.addAll(h);

        Collections.sort(songsList,s);



    }


    protected ReadAllSongs(Parcel in) {
    }

    public static final Creator<ReadAllSongs> CREATOR = new Creator<ReadAllSongs>() {
        @Override
        public ReadAllSongs createFromParcel(Parcel in) {
            return new ReadAllSongs(in);
        }

        @Override
        public ReadAllSongs[] newArray(int size) {
            return new ReadAllSongs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }


    public ArrayList<Album> getAllAlbums(){

        String[] projection = {
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.FIRST_YEAR
        };

        ContentResolver contentResolver = c;
        Uri songUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, projection, null, null, null);

        SortAlbums a = new SortAlbums();

        if(songCursor != null && songCursor.moveToFirst())
        {

            do {

                Album album = new Album(Integer.parseInt(songCursor.getString(4)),songCursor.getString(2),songCursor.getString(1),Integer.parseInt(songCursor.getString(3)),0);

                albumsList.add(album);

            } while(songCursor.moveToNext());

            Collections.sort(songsList,s);
        }


        HashSet<Album> h = new HashSet<Album>(albumsList);

        albumsList.clear();

        albumsList.addAll(h);

        Collections.sort(albumsList,a);

        return albumsList;

    }

    public ArrayList<Artist> getAllArtists(){

        ArrayList<Artist> artistsList = new ArrayList<>();

        String[] projection = {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ARTIST,



        };




        ContentResolver contentResolver = c;
        Uri songUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, projection, null, null, null);

        SortArtists b = new SortArtists();

        if(songCursor != null && songCursor.moveToFirst())
        {

            do {

                Artist artist = new Artist("","",Integer.parseInt(songCursor.getString(0)),Integer.parseInt(songCursor.getString(0)),songCursor.getString(1),0,0);

                artistsList.add(artist);

            } while(songCursor.moveToNext());

            Collections.sort(songsList,s);
        }



        HashSet<Artist> h = new HashSet<Artist>(artistsList);



        artistsList.clear();

        artistsList.addAll(h);

        Collections.sort(artistsList,b);

        return artistsList;

    }


    public ArrayList<Song> getAllSongs() {


        return songsList;
    }


    public ArrayList<Album> getAlbums(String title) {

        ArrayList<Album> albumsList1 = new ArrayList<>();

        SortAlbums a = new SortAlbums();

        for (int i = 0; i < this.albumsList.size(); i++) {

            Album album = new Album(albumsList.get(i).getId(), albumsList.get(i).getTitle(), albumsList.get(i).getArtistName(), albumsList.get(i).getSongCount(), 0);

            Log.d("AAa",title + "   " +  album.getArtistName());

            if (title.equals(album.getArtistName())) {


                if (albumsList1.contains(album)) {

                } else {
                    Collections.sort(albumsList1, a);
                    albumsList1.add(album);

                }

            }
        }


        HashSet<Album> h = new HashSet<Album>(albumsList1);



        albumsList1.clear();

        albumsList1.addAll(h);

        Collections.sort(albumsList1,a);

        // return songs list array
        return albumsList1;
    }

    public ArrayList<Song> getSongs(String title) {

        ArrayList<Song> songsList1 = new ArrayList<>();

        SortSongs s = new SortSongs();

        for (int i = 0; i < this.songsList.size(); i++) {




            Song song = new Song(songsList.get(i).getImage(),songsList.get(i).getFile(), songsList.get(i).getPath(), songsList.get(i).getId(), songsList.get(i).getAlbumId(), songsList.get(i).getArtistId(), songsList.get(i).getTitle(), songsList.get(i).getArtist(), songsList.get(i).getAlbum(), 0, 0);

            Log.d("AAa",title + "   " +  song.getTitle());

            if (title.equals(song.getAlbum())) {


                    if (songsList1.contains(song)) {

                    } else {

                        songsList1.add(song);
                        Collections.sort(songsList1, s);

                    }

                }
            }

        HashSet<Song> h = new HashSet<Song>(songsList1);



        songsList1.clear();

        songsList1.addAll(h);

        Collections.sort(songsList1,s);



        // return songs list array
        return songsList1;
    }



    /**
     * Class to filter files which are having .mp3 extension
     */
    //you can choose the filter for me i put .mp3
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".flac"));
        }
    }


    class SortSongs implements Comparator<Song> {

        public int compare(Song a, Song b) {
            return a.getTitle().compareTo(b.getTitle());
        }

    }

    class SortArtists implements Comparator<Artist> {

        public int compare(Artist a, Artist b) {
            return a.getName().compareTo(b.getName());
        }

    }

    class SortAlbums implements Comparator<Album> {

        public int compare(Album a, Album b) {
            return a.getTitle().compareTo(b.getTitle());
        }

    }

    class Contains implements Comparator<Song>{


        public int compare(Song a, Song b) {

            return a.getAlbum().compareTo(b.getAlbum());
        }
    }
}


