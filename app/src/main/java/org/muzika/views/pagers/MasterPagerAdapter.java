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

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.muzika.views.fragments.library.LibraryFragment;
import org.muzika.views.fragments.nowplaying.NowPlayingFragment;
import org.muzika.views.fragments.playlist.PlaylistFragment;

/**
 * This is the main pager adapter for the view pager
 * inside the main activity. This creates the 3 main pages
 * of the app. The app has 3 pages:
 *
 *  1) Library
 *  2) Now Playing
 *  3) Playlist
 *
 */
public class MasterPagerAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_PAGES = 3;

    public static final int PAGE_BROWSE = 0;
    public static final int PAGE_NOW_PLAYING = 1;
    public static final int PAGE_PLAYLIST = 2;

    public MasterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_BROWSE:
                return new LibraryFragment();
            case PAGE_NOW_PLAYING:
                return new NowPlayingFragment();
            case PAGE_PLAYLIST:
                return new PlaylistFragment();
            default:
                throw new RuntimeException("Unknown page listed");
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}
