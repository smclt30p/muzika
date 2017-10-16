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

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.muzika.R;

import java.util.ArrayList;

public class NowPlayingSeekBar extends LinearLayout implements SeekBar.OnSeekBarChangeListener {

    private static final int STEP_MAX = 1024;

    private TextView totalLabel;
    private TextView positionLabel;
    private SeekBar seekBar;

    private long totalSeconds;
    private long positionSecondsNew;
    private boolean startTrack;

    private ArrayList<OnChangeListener> listeners;

    public NowPlayingSeekBar(Context context) {
        super(context);
        init(context);
    }

    public NowPlayingSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View master = inflater.inflate(R.layout.view_seek_bar, this, false);
        listeners = new ArrayList<>();

        addView(master);
        populateViews(master);
        reset();
    }

    private void populateViews(View master) {
        totalLabel = master.findViewById(R.id.view_seek_total);
        positionLabel = master.findViewById(R.id.view_seek_position);
        seekBar = master.findViewById(R.id.view_seek_seekbar);

        seekBar.setMax(0);
        seekBar.setOnSeekBarChangeListener(this);

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!fromUser) return;
        float factor = progress / 1024.0f;
        double pos = factor * totalSeconds;
        positionSecondsNew = (long) pos;
        positionLabel.setText(getStringFromSeconds(positionSecondsNew));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        startTrack = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        startTrack = false;
        for (OnChangeListener listener : listeners) {
            listener.seekSong(positionSecondsNew);
        }
    }

    public void setMaximum(long maximum) {
        this.totalSeconds = maximum;
        this.seekBar.setMax(STEP_MAX);
        totalLabel.setText(getStringFromSeconds(maximum));
    }

    public void reset() {
        totalSeconds = 0;
        seekBar.setProgress(0);
        positionLabel.setText("00:00");
        totalLabel.setText("00:00");
    }

    public void setPositionSeconds(long positionSeconds) {
        if (totalSeconds == 0) return;
        if (!startTrack) {
            positionLabel.setText(getStringFromSeconds(positionSeconds));
            setSeekBarFromSeconds(positionSeconds, totalSeconds);
        }
    }

    private void setSeekBarFromSeconds(long positionSeconds, long totalSeconds) {
        float seekFactor = ((float) positionSeconds) / ((float) totalSeconds);
        int seek = (int) (1024.0f * seekFactor);
        seekBar.setProgress(seek);
    }

    private String getStringFromSeconds(long seconds) {
        float factor = (seconds % 60) / 10.0f;
        if (factor >= 1.0f) {
            return String.format("%d:%d", seconds / 60, seconds % 60);
        } else {
            return String.format("%d:0%d", seconds / 60, seconds % 60);
        }

    }

    public void addOnSeekChangeListener(OnChangeListener listener) {
        listeners.add(listener);
    }

    public  interface OnChangeListener {
        void seekSong(long position);
    }

}
