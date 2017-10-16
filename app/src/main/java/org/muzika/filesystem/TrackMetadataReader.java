package org.muzika.filesystem;

import java.io.Closeable;

/**
 * This is the metadata reader (IDv1, IDv2, OGG, FLAC etc.) for the tracks on disk.
 * Uses the native library "taglib" and is implemented inside the tagreader.c file
 * and defined in the tagreader.h header.
 */
class TrackMetadataReader implements TaglibReaderInterface, Closeable {

    @Override public native int openFile(String path);
    @Override public native void closeFile();
    @Override public native String getArtist();
    @Override public native String getAlbum();
    @Override public native String getTitle();
    @Override public native String getGenre();
    @Override public native String getComment();
    @Override public native int getYear();
    @Override public native int getTrackNumber();
    @Override public native int getBitRate();
    @Override public native int getSampleRate();
    @Override public native int getLength();

    @Override
    public void close() {
        closeFile();
    }
}
