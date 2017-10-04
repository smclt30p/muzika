package org.muzika.filesystem;

public class TrackMetadataReader implements TablibReaderInterface {
    @Override public native int openFile(String path);
    @Override public native void closeFile();
    @Override public native String getArtist();
    @Override public native String getAlbum();
    @Override public native String getTitle();
    @Override public native String getGenre();
    @Override public native String getComment();
    @Override public native int getYear();
    @Override public native int getTrackNumber();
}
