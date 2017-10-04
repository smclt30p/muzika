package org.muzika.filesystem;

/* Copyright (c) 2017 Ognjen Galić
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
*/

import android.os.AsyncTask;
import org.muzika.model.Track;
import java.io.File;
import java.util.ArrayList;

/**
 * The MediaScanner scans the storage media and returns supported files
 * as Tracks.
 */
public class MediaScanner extends AsyncTask<Void, Void, ArrayList<Track>> {

    /**
     * According to WikiPedia, FMOD supports  AIFF, ASF, ASX, DLS, FLAC,
     * FSB (FMOD's sample bank format), IT, M3U, MIDI, MOD, MP2, MP3,
     * Ogg Vorbis, PLS, S3M, VAG (PS2/PSP format), WAV,
     * WAX (Windows Media Audio Redirector), WMA, XM, XMA (only on the Xbox 360),
     * as well as raw audio data.
     */
    private static String[] supportedFormats = {
            "aiff","aif","aifc","asf",
            "wma","asx","dls","flac",
            "fsb","it","mid","midi",
            "mod","mp2","mpeg2","mp3",
            "mpeg3","ogg","oga","pls",
            "s3m","vag","wav","wax",
            "xm"
    };

    private ArrayList<Track> walkedFiles = new ArrayList<>();
    private MediaScannerFinishedListener listener;
    private TrackMetadataReader metadataReader = new TrackMetadataReader();

    private final String root;

    /**
     * Initialize the media scanner to scan in the provided root.
     * @param root the root to start scanning in
     */
    public MediaScanner(String root) {
        this.root = root;
    }

    /**
     * This is the worker thread entry point.
     */
    @Override
    protected ArrayList<Track> doInBackground(Void... voids) {
        walk(new File(root));
        return walkedFiles;
    }

    /**
     * This executes after the worker thread has finished
     * walking the directory.
     * @param tracks the supported tracks found
     */
    @Override
    protected void onPostExecute(ArrayList<Track> tracks) {
        listener.publishMedia(tracks);
    }

    /**
     * The fs walk method. This is a recursive method.
     * @param root the directory to walk to
     */
    private void walk(File root) {
        if (!root.isDirectory() || !root.canRead()) return;
        File[] files = root.listFiles();
        for (File file : files) {
            if (!file.canRead()) continue;
            if (file.isDirectory()) {
                walk(file);
            }
            System.out.println(file.getAbsolutePath());
            if (!isFileSupported(file)) continue;
            Track track = new Track();
            track.setFile(file);


            metadataReader.openFile(file.getAbsolutePath());

            System.out.println(String.format(
                    "Song: %s\n" +
                    "Artist: %s\n" +
                    "Album: %s\n",
                    metadataReader.getTitle(),
                    metadataReader.getArtist(),
                    metadataReader.getAlbum()
            ));


            metadataReader.closeFile();
            walkedFiles.add(track);
        }
    }

    /**
     * Checks if the provided file is supported.
     * This check is based on the file extension, in the
     * future I would like it to be 'file' based.
     * @param file the file to be checked
     * @return true if the file is supported
     */
    private boolean isFileSupported(File file) {
        String fileName = file.getName();
        String[] separated = fileName.split("\\.");
        if (separated.length < 1) return false;  // no file extension
        for (String extension : supportedFormats) {
            if (separated[separated.length - 1].equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attach a listener that gets the scanned tracks.
     * @param finishedListener the listener to attach
     */
    public void addFinishListener(MediaScannerFinishedListener finishedListener) {
        this.listener = finishedListener;
    }

}

