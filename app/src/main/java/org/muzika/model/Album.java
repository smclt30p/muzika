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

package org.muzika.model;

import java.util.ArrayList;

/**
 * This class represents an album inside the library.
 */
public class Album {

    private Artist artist;
    private String name;
    private String description;
    private int year;
    private ArrayList<Track> tracks;

    public Album() {
        this.tracks = new ArrayList<>();
    }

    /**
     * Get the name of the album
     * @return the name of the album
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the album
     * @param name the name to set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the album
     * @return the album description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the album
     * @param description the descriptio to set to
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The year of the album
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the year of the album
     * @param year the year of the album
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Get the tracks that this album contains
     * @return the track list
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * Get the artist of the album
     * @return the album artists
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Set the artist of the album
     * @param artist the artist to set to
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", tracks=" + tracks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return name.equals(album.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
