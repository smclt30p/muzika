/*
 *  Copyright (c) 2017 Ognjen Galić
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package org.muzika.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.muzika.R;
import org.muzika.model.Track;

import java.util.ArrayList;

/**
 * This is an adapter to display @see Tracks inside
 * listviews. This is the most basic adapter there is.
 */
public class TrackArrayAdapter extends BaseAdapter {

    private final ArrayList<Track> tracks;
    private final LayoutInflater inflater;

    public TrackArrayAdapter(Context ctx, ArrayList<Track> data) {
        this.tracks = data;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return tracks.size();
    }

    @Override
    public Object getItem(int i) {
        return tracks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return -1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Track track = tracks.get(position);
        View master = convertView == null ? inflater.inflate(R.layout.list_item_track, parent, false) : convertView;

        TextView artist = master.findViewById(R.id.list_item_artist);
        TextView title = master.findViewById(R.id.list_item_title);
        TextView length = master.findViewById(R.id.list_item_length);

        artist.setText(String.format("%s • %s", track.getArtistString(), track.getAlbumString()));
        title.setText(track.getTitleString());
        length.setText(getStringFromSeconds(track.getLength()));

        return master;
    }

    private String getStringFromSeconds(long seconds) {
        float factor = (seconds % 60) / 10.0f;
        if (factor >= 1.0f) {
            return String.format("%d:%d", seconds / 60, seconds % 60);
        } else {
            return String.format("%d:0%d", seconds / 60, seconds % 60);
        }

    }
}
