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

package org.muzika.views.fragments.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.muzika.R;
import org.muzika.filesystem.MediaScanner;
import org.muzika.filesystem.MediaScannerFinishedListener;
import org.muzika.model.Library;
import org.muzika.model.Track;
import org.muzika.views.adapters.TrackArrayAdapter;
import org.muzika.views.services.accentcolor.AccentColor;
import org.muzika.views.services.accentcolor.AccentColorService;
import org.muzika.views.services.media.MediaPlayerService;

import java.util.Collections;

public class LibraryFragment extends Fragment {

    private MediaScanner mediaScanner;
    private ListView songs;
    private TrackArrayAdapter adapter;
    private Library library;
    private MediaPlayerService mediaPlayerService = MediaPlayerService.getInstance();

    public LibraryFragment() {
        System.out.println("creating library");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View inflate = inflater.inflate(R.layout.fragment_library, container, false);

        AccentColorService.getAccentColorService().setCurrentAccentColor(AccentColor.fromString("0xdd7a6d61"));

        mediaScanner = new MediaScanner("/sdcard");
        mediaScanner.addFinishListener(new MediaScannerFinishedListener() {
            @Override
            public void publishMedia(Library ne) {
                if (ne == null) return;
                library = ne;
                Collections.sort(library.getTracks());
                songs = inflate.findViewById(R.id.song_list);
                adapter = new TrackArrayAdapter(getContext(), library.getTracks());
                songs.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Track track = library.getTracks().get(i);
                        mediaPlayerService.playTrack(track);
                    }
                });
            }
        });

        mediaScanner.execute();


        return inflate;
    }
}

