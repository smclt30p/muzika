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

package org.muzika.views.fragments.nowplaying;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.muzika.R;
import org.muzika.model.Track;
import org.muzika.views.services.media.MediaPlayerService;
import org.muzika.views.services.media.MediaPlayerState;

import static org.muzika.core.Logger.info;


/**
 * The NowPlaying fragment inside the app, the main view.
 * TODO: Implement
 */
public class NowPlayingFragment extends Fragment implements
        MediaPlayerService.OnPlaybackInfoBroadcast,
        NowPlayingSeekBar.OnChangeListener,
        NowPlayingButtonBar.OnClickListener {

    private MediaPlayerService mediaPlayerService = MediaPlayerService.getInstance();

    private TextView album;
    private TextView artist;
    private TextView title;
    private NowPlayingSeekBar seekBar;
    private NowPlayingButtonBar buttonBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_now_playing, container, false);
        mediaPlayerService.addPlaybackListener(this);
        assignViews(ret);
        return ret;
    }

    private void assignViews(View parent) {
        album = parent.findViewById(R.id.nowplaying_album);
        artist = parent.findViewById(R.id.nowplaying_artist);
        title = parent.findViewById(R.id.nowplaying_title);
        seekBar = parent.findViewById(R.id.nowplaying_seek_bar);
        seekBar.addOnSeekChangeListener(this);
        buttonBar = parent.findViewById(R.id.nowplaying_button_bar);
        buttonBar.addOnClickListener(this);
    }

    @Override
    public void updateInfo(final MediaPlayerState state, final MediaPlayerService service, final Track track, final long length, final long position) {

        if (getActivity() == null) {
            info(this, "Activity in now playing is null, returning");
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUi(state, service, track, length, position);
            }
        });
    }

    private void updateUi(MediaPlayerState state, MediaPlayerService service, Track track, long length, long position) {

        switch(state) {
            case NEW_TRACK:

                // set the info display
                album.setText(track.getAlbumString());
                title.setText(track.getTitleString());
                artist.setText(track.getArtistString());

                // set the seek bar range
                seekBar.reset();
                seekBar.setMaximum(track.getLength());

                break;

            case PLAYING:
                seekBar.setPositionSeconds(position / 1000);
        }



    }

    @Override
    public void seekSong(long position) {
        mediaPlayerService.seek(position * 1000);
    }

    @Override
    public void playPressed(NowPlayingButtonBar.PlayStatus status) {

    }

    @Override
    public void randomPressed(NowPlayingButtonBar.RandomStatus status) {

    }

    @Override
    public void repeatPressed(NowPlayingButtonBar.RepeatStatus status) {

    }

    @Override
    public void nextPressed() {

    }

    @Override
    public void previousPressed() {

    }
}
