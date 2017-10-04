package org.muzika;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.muzika.filesystem.MediaScanner;
import org.muzika.filesystem.MediaScannerFinishedListener;
import org.muzika.model.Track;
import org.muzika.views.fragments.NowPlayingFragment;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    private ViewPager pager;
    private MasterFragmentAdapter masterPagerAdapter;
    private MediaScanner mediaScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masterPagerAdapter = new MasterFragmentAdapter(getSupportFragmentManager());

        pager = findViewById(R.id.master_view_pager);
        pager.setAdapter(masterPagerAdapter);
        pager.setCurrentItem(MasterFragmentAdapter.PAGE_NOW_PLAYING);

        mediaScanner = new MediaScanner("/storage");
        mediaScanner.addFinishListener(new MediaScannerFinishedListener() {
            @Override
            public void publishMedia(ArrayList<Track> tracks) {

                for (Track track : tracks) {
                    File origin = track.getFile();
                    System.out.println(origin.getAbsolutePath());
                }

            }
        });

        mediaScanner.execute();

    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == MasterFragmentAdapter.PAGE_NOW_PLAYING) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(MasterFragmentAdapter.PAGE_NOW_PLAYING, true);
        }
    }
}

class MasterFragmentAdapter extends FragmentPagerAdapter {

    private static final int NUMBER_OF_PAGES = 3;

    static final int PAGE_BROWSE = 0;
    static final int PAGE_NOW_PLAYING = 1;
    static final int PAGE_PLAYLIST = 2;

    MasterFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new NowPlayingFragment();
    }

    @Override
    public int getCount() {
        return NUMBER_OF_PAGES;
    }
}

