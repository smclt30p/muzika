package org.muzika.model;

import java.util.ArrayList;

public class Album {
    private String name;
    private ArrayList<Track> tracks;
    private Artist parentArtist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public Artist getParentArtist() {
        return parentArtist;
    }

    public void setParentArtist(Artist parentArtist) {
        this.parentArtist = parentArtist;
    }
    // TODO: Albumart
}
