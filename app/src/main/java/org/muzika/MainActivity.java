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

package org.muzika;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.muzika.audio.AudioService;
import org.muzika.filesystem.MediaScanner;
import org.muzika.filesystem.MediaScannerFinishedListener;
import org.muzika.model.Library;
import org.muzika.model.Track;
import org.muzika.views.adapters.TrackArrayAdapter;
import org.muzika.views.fragments.LibraryFragment;
import org.muzika.views.fragments.NowPlayingFragment;
import org.muzika.views.fragments.PlaylistFragment;
import org.muzika.views.pagers.MasterPagerAdapter;
import org.muzika.views.pagers.MasterPagerBackground;
import org.muzika.views.pagers.MasterPagerDecoration;
import org.muzika.views.pagers.MasterPagerDrawer;

import java.util.Collections;

/**
 * This is the master activity of the app, aka. the "Main" activity.
 * It's layout is defined in activity_main.xml and is inflated
 * on the creation of the activity.
 */
public class MainActivity extends FragmentActivity {

    /*
     * Load the "libmuzika" shared library that handles file
     * tags and the sound engine. This is the "core" of Muzika
     * basically, written in C.
     */
    static {
        System.loadLibrary("muzika");
    }

    private ViewPager pager;
    private MasterPagerAdapter masterPagerAdapter;
    private MasterPagerDrawer drawer;
    private MediaScanner mediaScanner;
    private ListView songs;
    private TrackArrayAdapter adapter;
    private Library library;
    private AudioService audio;
    private MasterPagerBackground tint;
    private MasterPagerDecoration decoration;

    /**
     * The onCreate method of the activity. This does a few things:
     *  1) Sets the PagerAdapter on the ViewPager
     *  2) Configures the hamburger menu for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audio = new AudioService();
        audio.init();

        tint = findViewById(R.id.pager_background);
        pager = findViewById(R.id.master_view_pager);
        drawer = findViewById(R.id.master_drawer);
        decoration = findViewById(R.id.master_drawer_decor);

        masterPagerAdapter = new MasterPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(masterPagerAdapter);
        pager.setCurrentItem(MasterPagerAdapter.PAGE_NOW_PLAYING);

        pager.addOnPageChangeListener(tint);
        pager.addOnPageChangeListener(drawer);
        pager.addOnPageChangeListener(decoration);

        mediaScanner = new MediaScanner("/storage");
        mediaScanner.addFinishListener(new MediaScannerFinishedListener() {
            @Override
            public void publishMedia(Library ne) {
                library = ne;
                Collections.sort(library.getTracks());
                songs = findViewById(R.id.song_list);
                adapter = new TrackArrayAdapter(getApplicationContext(), library.getTracks());
                songs.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Track track = library.getTracks().get(i);
                        audio.playStream(track.getFile().getAbsolutePath());
                    }
                });
            }
        });

        mediaScanner.execute();



    }

    /**
     * This handles the back pressed key. By design, if you press the back
     * key once you shaould be taken to the Now Playing screen if you are not
     * on another screen. Pressing back again closes the activity.
     */
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == MasterPagerAdapter.PAGE_NOW_PLAYING) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(MasterPagerAdapter.PAGE_NOW_PLAYING, true);
        }
    }
}

