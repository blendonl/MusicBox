package com.pek.doni.musicbox.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pek.doni.musicbox.Models.Album;
import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumsListAdapter extends RecyclerView.Adapter<AlbumsListAdapter.NumberViewHolder> {


        List<Album> albumList;
        Context c;
        NumberViewHolder n;
        View v;
        Context c1;
        LayoutInflater lf;
        int i;





        public AlbumsListAdapter(List<Album> list, Context context)
        {

            albumList = list;
            c = context;
        }

        @Override
        public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            c1 = parent.getContext();
            lf = LayoutInflater.from(c);
            i = R.layout.albums_list;

            v = lf.inflate(i,parent,false);

            n = new NumberViewHolder(v);


            return n;
        }

        @Override
        public void onBindViewHolder(NumberViewHolder holder, int position) {

            holder.artistName.setText(albumList.get(position).getArtistName());
            holder.albumTitle.setText(albumList.get(position).getTitle());

            Uri ImageUrl = getAlbumUri(c, String.valueOf(albumList.get(position).getId()));

            Picasso.with(c).load(ImageUrl).fit().into(holder.albumImage);
        }



    public Uri getAlbumUri(Context mContext, String album_id) {
        if (mContext != null) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
            return imageUri;
        }
        return null;
    }


    public List<Album> getAlbumList() {
        return albumList;
    }


    @Override
        public int getItemCount() {

            return albumList.size();
        }

    class NumberViewHolder extends RecyclerView.ViewHolder
        {


            TextView albumTitle;
            TextView artistName;
            ImageView albumImage;



                public NumberViewHolder(View itemView) {

                    super(itemView);

                    albumTitle = (TextView) itemView.findViewById(R.id.albumATitle);
                    artistName = (TextView) itemView.findViewById(R.id.artistAName);
                    albumImage = (ImageView) itemView.findViewById(R.id.albumAImage);

                }


        }
}






