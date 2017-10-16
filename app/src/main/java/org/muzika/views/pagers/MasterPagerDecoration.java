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

package org.muzika.views.pagers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.muzika.R;

/**
 * This is the small 3-icon decoration on the bottom of the page
 * that fades icons in and out in respect to the pager position, and
 * thus indicates where the pager is currently.
 */
public class MasterPagerDecoration extends FrameLayout implements ViewPager.OnPageChangeListener {

    private final Context context;
    private LayoutInflater inflater;

    private ImageView home;
    private ImageView nowPlaying;
    private ImageView playlist;

    public MasterPagerDecoration(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MasterPagerDecoration(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.master_pager_decor, this, true);

        this.home = this.findViewById(R.id.home_icon_decor);
        this.nowPlaying = this.findViewById(R.id.play_icon_decor);
        this.playlist = this.findViewById(R.id.playlist_icon_decor);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset == 0) return;

        switch (position) {
            case 0:
                nowPlaying.setAlpha(positionOffset + 0.33f);
                home.setAlpha((1 - positionOffset) + 0.33f);
                break;
            case 1:
                playlist.setAlpha(positionOffset + 0.33f);
                nowPlaying.setAlpha((1 - positionOffset) + 0.33f);
                break;
        }

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
