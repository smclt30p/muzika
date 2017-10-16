package org.muzika.filesystem;

/**
 * This defines the methods available by TagLib, v1.11
 * written by Scott Wheeler et al. for the KDE project.
 */
interface TaglibReaderInterface {
    int openFile(String path);
    void closeFile();
    String getArtist();
    String getAlbum();
    String getTitle();
    String getGenre();
    String getComment();
    int getYear();
    int getTrackNumber();
    int getBitRate();
    int getSampleRate();
    int getLength();
}
