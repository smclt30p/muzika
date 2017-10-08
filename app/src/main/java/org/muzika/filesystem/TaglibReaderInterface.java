package org.muzika.filesystem;

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
