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

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import org.muzika.audio.AudioService;
import org.muzika.views.pagers.MasterPager;
import org.muzika.views.pagers.MasterPagerAdapter;
import org.muzika.views.pagers.MasterPagerBackground;
import org.muzika.views.pagers.MasterPagerBackgroundGradient;
import org.muzika.views.pagers.MasterPagerDecoration;
import org.muzika.views.pagers.MasterPagerDrawer;

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

    private MasterPager pager;
    private MasterPagerAdapter masterPagerAdapter;
    private MasterPagerDrawer drawer;
    private MasterPagerBackground tint;
    private MasterPagerDecoration decoration;
    private MasterPagerBackgroundGradient gradient;

    private AudioService audio;


    /**
     * The onCreate method of the activity. This does a few things:
     *  1) Sets the PagerAdapter on the ViewPager
     *  2) Configures the hamburger menu for the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAnimation();

        tint = findViewById(R.id.pager_background);
        pager = findViewById(R.id.master_view_pager);
        drawer = findViewById(R.id.master_drawer);
        decoration = findViewById(R.id.master_drawer_decor);
        gradient = findViewById(R.id.master_drawer_gradient);

        masterPagerAdapter = new MasterPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(masterPagerAdapter);
        pager.setCurrentItem(MasterPagerAdapter.PAGE_NOW_PLAYING);

        pager.addOnPageChangeListener(tint);
        pager.addOnPageChangeListener(drawer);
        pager.addOnPageChangeListener(decoration);

    }

    /**
     * Animate the splash screen. There is a
     * couple of animations started at different times.
     *
     * TODO: This should be disable-able in the future.
     */
    private void startAnimation() {

        View splash = findViewById(R.id.splash);
        View logo = findViewById(R.id.muzika_logo);
        View fmod = findViewById(R.id.fmod_logo);

        ObjectAnimator splashAnimator = ObjectAnimator.ofFloat(splash, "alpha", 1.0f, 0.0f);
        splashAnimator.setStartDelay(2300);
        splashAnimator.setDuration(500);
        splashAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        splashAnimator.start();

        ObjectAnimator logoAnimator = ObjectAnimator.ofFloat(logo, "alpha", 0.0f, 1.0f);
        logoAnimator.setDuration(800);
        logoAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        logoAnimator.start();

        ObjectAnimator fmodAnimator = ObjectAnimator.ofFloat(fmod, "alpha", 0.0f, 1.0f);
        fmodAnimator.setStartDelay(1000);
        fmodAnimator.setDuration(500);
        fmodAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        fmodAnimator.start();

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

