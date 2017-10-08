package org.muzika.filesystem;

import java.io.Closeable;

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
