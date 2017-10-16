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
 * This class represents the core music data library of Muzika.
 */
public class Library {

    private ArrayList<Track> tracks;
    private ArrayList<Album> albums;
    private ArrayList<Artist> artists;
    private ArrayList<Track> invalidTags;

    public Library() {
        this.tracks = new ArrayList<>();
        this.albums = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.invalidTags = new ArrayList<>();
    }

    /**
     * Get ALL the albums in the library
     * @return all albums in the library
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Get ALL the artists in the library
     * @return all artists in the library
     */
    public ArrayList<Artist> getArtists() {
        return artists;
    }

    /**
     * Get ALL the tracks in the library
     * @return all the tracks in the library
     */
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    /**
     * Get ALL the tracks in the library that had invalid
     * metadata
     * @return all tracks with invalid metadata
     */
    public ArrayList<Track> getInvalidTags() {
        return invalidTags;
    }

}
