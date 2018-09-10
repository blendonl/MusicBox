package com.pek.doni.musicbox;

import android.content.Context;
import android.net.Uri;

public class getAlbumImage {


    public Uri getUri(Context mContext, String album_id) {
        if (mContext != null) {
            Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
            Uri imageUri = Uri.withAppendedPath(sArtworkUri, String.valueOf(album_id));
            return imageUri;
        }
        return null;
    }
}
