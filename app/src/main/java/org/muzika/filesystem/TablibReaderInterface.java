package org.muzika.filesystem;

public interface TablibReaderInterface {
    int openFile(String path);
    void closeFile();
    String getArtist();
    String getAlbum();
    String getTitle();
    String getGenre();
    String getComment();
    int getYear();
    int getTrackNumber();
}
