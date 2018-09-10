package com.pek.doni.musicbox.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pek.doni.musicbox.Models.Artist;
import com.pek.doni.musicbox.Models.Song;
import com.pek.doni.musicbox.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by USER on 2/8/2018.
 */

public class ArtistsListAdapter extends RecyclerView.Adapter<ArtistsListAdapter.NumberViewHolder> {


    public List<Artist> artistList;
    Context c;

    Context c1;
    LayoutInflater lf;
    int i;

    View v;

    NumberViewHolder n;

    public ArtistsListAdapter(List<Artist> list,Context context)
    {

        artistList = list;
        c = context;

    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c1 = parent.getContext();
        lf = LayoutInflater.from(c);
        i = R.layout.artists_list;
        v = lf.inflate(i,parent,false);
        n = new NumberViewHolder(v);

        return n;
    }

//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
//                                                         int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
//
//
//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) > reqHeight
//                    && (halfWidth / inSampleSize) > reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }


    public List<Artist> getArtistList() {
        return artistList;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {

        holder.artistName.setText(artistList.get(position).getName());


        Uri ImageUrl = getAlbumUri(c, String.valueOf(artistList.get(position).getId()));


        Picasso.with(c).load(ImageUrl).fit().into(holder.artistImage);


       // Picasso.with(c).load(artistList.get(position).getBitmap()).centerCrop().resize(420,400).into(holder.artistImage);

    }


    public Uri getAlbumUri(Context mContext,String album_id) {
        if (mContext != null) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
            return imageUri;
        }
        return null;
    }





    @Override
    public int getItemCount() {
        return artistList.size();
    }


    class NumberViewHolder extends RecyclerView.ViewHolder
    {
        TextView artistName;
        ImageView artistImage;

        public NumberViewHolder(View itemView) {
            super(itemView);

            artistName = (TextView) itemView.findViewById(R.id.artistArName);
            artistImage = (ImageView) itemView.findViewById(R.id.artistImage);

        }


    }

}

