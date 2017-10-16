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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.muzika.R;
import org.muzika.views.basic.SquareBitmapButton;

import java.util.ArrayList;

public class NowPlayingButtonBar extends RelativeLayout implements Button.OnClickListener {

    private SquareBitmapButton playButton;
    private SquareBitmapButton repeatButton;
    private SquareBitmapButton randomButton;
    private SquareBitmapButton nextButton;
    private SquareBitmapButton previousButton;

    /**
     * Button click inside navigation bar
     */
    @Override
    public void onClick(View view) {

        if (view == playButton) {
            playPressed();
        } else if (view == repeatButton) {
            repeatPressed();
        } else if (view == randomButton) {
            randomPressed();
        } else if (view == nextButton) {
            nextPressed();
        } else if (view == previousButton) {
            previousPressed();
        }

    }

    private void playPressed() {

        switch (playStatus) {
            case PAUSED:
                playStatus = PlayStatus.PLAYING;
                playButton.setBackgroundResource(R.drawable.icons8pause96);
                break;
            case PLAYING:
                playButton.setBackgroundResource(R.drawable.icons8play96);
                playStatus = PlayStatus.PAUSED;
                break;
        }

        for (OnClickListener listener : listeners) {
            listener.playPressed(playStatus);
        }

    }

    private void repeatPressed() {

        switch (repeatStatus) {
            case NO_REPEAT:
                repeatStatus = RepeatStatus.REPEAT_PLAYLIST;
                repeatButton.setBackgroundResource(R.drawable.icons8repeat96);
                repeatButton.setAlpha(1);
                break;
            case REPEAT_PLAYLIST:
                repeatStatus = RepeatStatus.REPEAT_ONE;
                repeatButton.setBackgroundResource(R.drawable.icons8repeatone96);
                break;
            case REPEAT_ONE:
                repeatStatus = RepeatStatus.NO_REPEAT;
                repeatButton.setBackgroundResource(R.drawable.icons8repeat96);
                repeatButton.setAlpha(0.5f);
                break;
        }

        for (OnClickListener listener : listeners) {
            listener.repeatPressed(repeatStatus);
        }
        
    }

    private void randomPressed() {

        switch (randomStatus) {
            case SEQUENTIAL:
                randomStatus = RandomStatus.RANDOM;
                randomButton.setAlpha(1);
                break;
            case RANDOM:
                randomStatus = RandomStatus.SEQUENTIAL;
                randomButton.setAlpha(0.5f);
                break;
        }

        for (OnClickListener listener : listeners) {
            listener.randomPressed(randomStatus);
        }

    }

    private void nextPressed() {
        for (OnClickListener listener : listeners) {
            listener.nextPressed();
        }
    }

    private void previousPressed() {
        for (OnClickListener listener : listeners) {
            listener.previousPressed();
        }
    }

    public enum PlayStatus {
        PLAYING, PAUSED
    }

    public enum RepeatStatus {
        NO_REPEAT, REPEAT_PLAYLIST, REPEAT_ONE
    }

    public enum RandomStatus {
        SEQUENTIAL, RANDOM
    }

    private PlayStatus playStatus = PlayStatus.PAUSED;
    private RepeatStatus repeatStatus = RepeatStatus.NO_REPEAT;
    private RandomStatus randomStatus = RandomStatus.SEQUENTIAL;
    private final ArrayList<OnClickListener> listeners = new ArrayList<>();

    public void addOnClickListener(OnClickListener listener) {
        listeners.add(listener);
    }

    public void setPlayingStatus(PlayStatus status) {
        this.playStatus = status;
    }

    public void setRepeatStatus(RepeatStatus status) {
        this.repeatStatus = status;

        switch (status) {

            case NO_REPEAT:
                break;
            case REPEAT_PLAYLIST:
                break;
            case REPEAT_ONE:
                break;


        }

    }

    public NowPlayingButtonBar(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NowPlayingButtonBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_button_bar, this, false);
        addView(view);
        populateViews(view);

    }

    private void populateViews(View parent) {
        this.playButton = parent.findViewById(R.id.bar_button_play);
        this.repeatButton = parent.findViewById(R.id.bar_button_repeat);
        this.randomButton = parent.findViewById(R.id.bar_button_random);
        this.nextButton = parent.findViewById(R.id.bar_button_next);
        this.previousButton = parent.findViewById(R.id.bar_button_previous);
        this.playButton.setOnClickListener(this);
        this.repeatButton.setOnClickListener(this);
        this.randomButton.setOnClickListener(this);
        this.nextButton.setOnClickListener(this);
        this.previousButton.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float height = getMeasuredWidth() / 8.5f;
        getLayoutParams().height = Math.round(height);
    }

    public interface OnClickListener {
        void playPressed(PlayStatus status);
        void randomPressed(RandomStatus status);
        void repeatPressed(RepeatStatus status);
        void nextPressed();
        void previousPressed();
    }

}
