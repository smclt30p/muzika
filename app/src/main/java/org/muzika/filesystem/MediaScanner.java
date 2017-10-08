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

import org.muzika.model.Album;
import org.muzika.model.Artist;
import org.muzika.model.Library;
import org.muzika.model.Track;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The MediaScanner scans the storage media and returns supported files
 * as Tracks.
 */
public class MediaScanner extends AsyncTask<Void, Void, Library> {

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

    private enum SearchType {
        ALBUM, ARTIST
    }

    private static final int FIND_NOT_FOUND = -2;

    private ArrayList<Track> walkedFiles = new ArrayList<>();
    private MediaScannerFinishedListener listener;
    private TrackMetadataReader metadataReader = new TrackMetadataReader();
    private volatile Library library = new Library();
    private final String root;

    /**
     * Initialize the media scanner to scan in the provided root.
     * @param root the root to start scanning in
     */
    public MediaScanner(String root) {
        this.root = root;
    }

    /**
     * Create a new album from a track
     * @param track the track to create the album for
     * @return the new album
     */
    private Album createAlbum(Track track) {
        Album album = new Album();
        album.setName(track.getAlbumString());
        album.setYear(track.getYear());
        album.setDescription(track.getAlbumString());
        return album;
    }

    /**
     * This is the worker thread entry point.
     */
    @Override
    protected Library doInBackground(Void... voids) {

        File root = new File(this.root);

        if (!root.exists() || !root.canRead()) return null;

        walk(root);

        for (Track track : walkedFiles) {

            library.getTracks().add(track);

            int artistIndex = find(library.getArtists(), track.getArtistString(), SearchType.ARTIST);
            if (artistIndex == FIND_NOT_FOUND) {
                /* artist was not found */
                Artist artist = new Artist();
                artist.setName(track.getArtistString());

                Album album = createAlbum(track);
                album.setArtist(artist);
                album.getTracks().add(track);

                artist.getAlbums().add(album);

                track.setArtist(artist);
                track.setAlbum(album);

                library.getArtists().add(artist);
                library.getAlbums().add(album);

            } else {
                /* artist was found */
                Artist artist = library.getArtists().get(artistIndex);
                int albumIndex = find(artist.getAlbums(), track.getAlbumString(), SearchType.ALBUM);

                if (albumIndex == FIND_NOT_FOUND) {
                    /* album not found */
                    Album album = createAlbum(track);
                    album.getTracks().add(track);
                    artist.getAlbums().add(album);
                    album.setArtist(artist);
                    track.setAlbum(album);
                    library.getAlbums().add(album);
                } else {
                    /* album found */
                    Album album = artist.getAlbums().get(albumIndex);
                    album.getTracks().add(track);
                    track.setAlbum(album);
                }
            }
        }

        return library;
    }

    /**
     * This executes after the worker thread has finished
     * walking the directory.
     * @param library the whole library that was scanned
     */
    @Override
    protected void onPostExecute(Library library) {
        listener.publishMedia(library);
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

            if (!isFileSupported(file)) continue;
            Track track = new Track();
            track.setFile(file);

            if (metadataReader.openFile(file.getAbsolutePath()) != 0) {
                System.err.println("Unsupported file by TagLib: " + file.getAbsolutePath());
                library.getInvalidTags().add(track);
                metadataReader.close();
                continue;
            }

            track.setTitleString(metadataReader.getTitle());
            track.setArtistString(metadataReader.getArtist());
            track.setAlbumString(metadataReader.getAlbum());
            track.setComment(metadataReader.getComment());
            track.setBitrate(metadataReader.getBitRate());
            track.setTrackNumber(metadataReader.getTrackNumber());
            track.setSamplerate(metadataReader.getSampleRate());
            track.setYear(metadataReader.getYear());
            track.setLength(metadataReader.getLength());

            metadataReader.close();

            if (track.getArtistString().equalsIgnoreCase("") ||
                    track.getTitleString().equalsIgnoreCase("") ||
                    track.getAlbumString().equalsIgnoreCase("")) {
                library.getInvalidTags().add(track);
                return;
            }

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

    /**
     * Search for an item in a list based on name
     * @param haystack the haystack of items
     * @param needle the needle to find
     * @param type the type of search
     * @return the index of the item in the list
     */
    private int find(List haystack, String needle, SearchType type) {

        for (int i = 0; i < haystack.size(); i++) {

            switch (type) {
                case ALBUM:
                    Album album = (Album) haystack.get(i);
                    if (album.getName().equalsIgnoreCase(needle)) {
                        return i;
                    }
                    break;
                case ARTIST:
                    Artist artist = (Artist) haystack.get(i);
                    if (artist.getName().equalsIgnoreCase(needle)) {
                        return i;
                    }
                    break;
                default:
                    throw new RuntimeException("Invalid search flag!" + type);
            }

        }

        return FIND_NOT_FOUND;

    }

}

