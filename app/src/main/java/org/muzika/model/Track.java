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

import android.support.annotation.NonNull;

import java.io.File;

/**
 * This class represents a sound track on disk
 */
public class Track implements Comparable<Track> {

    private String titleString;
    private String artistString;
    private String albumString;
    private Album album;
    private Artist artist;
    private File file;
    private String comment;
    private int bitrate;
    private int trackNumber;
    private int samplerate;
    private int year;
    private long length;

    public Track() {
    }

    /**
     * Get the title string of the track
     * @return the title string
     */
    public String getTitleString() {
        return titleString;
    }

    /**
     * Set the title string of the track
     * @param titleString the title string to set
     */
    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    /**
     * Get the artist string
     * @return the artists string
     */
    public String getArtistString() {
        return artistString;
    }

    /**
     * Set the artists string in the track
     * @param artistString the string to set
     */
    public void setArtistString(String artistString) {
        this.artistString = artistString;
    }

    /**
     * Get the album string in the track
     * @return the string
     */
    public String getAlbumString() {
        return albumString;
    }

    /**
     * Set the album string inside the track
     * @param albumString the album string
     */
    public void setAlbumString(String albumString) {
        this.albumString = albumString;
    }

    /**
     * Get the track comment
     * @return the track comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the track comment
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get the track bitrate in kbps
     * @return the bitrate in kbps
     */
    public int getBitrate() {
        return bitrate;
    }

    /**
     * Sets the track bitrate in kbps
     * @param bitrate the bitrate to set
     */
    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    /**
     * Gets the track number inside the album
     * @return the track number inside the album
     */
    public int getTrackNumber() {
        return trackNumber;
    }

    /**
     * Set the track number in the album
     * @param trackNumber the number to set
     */
    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    /**
     * Get the sample rate in hertz
     * @return the sample rate in hertz
     */
    public int getSamplerate() {
        return samplerate;
    }

    /**
     * Set the sample rate (in hertz)
     * @param samplerate the sample rate to set (in hertz)
     */
    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
    }

    /**
     * Get the year of the track
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Set the year of the track
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Get the length of the track (in seconds)
     * WARNING: The Audio system's getLength returns the length
     * in miliseconds. This is to be used only for displaying purposes
     * @return the track length in seconds
     */
    public long getLength() {
        return length;
    }

    /**
     * Sets the length of the file in seconds
     * @param length the length
     */
    public void setLength(long length) {
        this.length = length;
    }

    /**
     * Gets the physical file object on disk
     * @return the file on disk
     */
    public File getFile() {
        return file;
    }

    /**
     * Set the physical file object on disk
     * @param file the file object
     */
    public void setFile(File file) {
        this.file = file;
    }


    /**
     * Get the artist object of the track
     * @return the artist object
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Set the artist object of the track
     * @param artist the artist object
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * Get the album object of the track
     * @return the album object
     */
    public Album getAlbum() {
        return album;
    }

    /**
     * Set the album object of the track
     * @param album the album object
     */
    public void setAlbum(Album album) {
        this.album = album;
    }


    @Override
    public String toString() {
        return "Track{" +
                "titleString='" + titleString + '\'' +
                ", artistString='" + artistString + '\'' +
                ", albumString='" + albumString + '\'' +
                ", comment='" + comment + '\'' +
                ", bitrate=" + bitrate +
                ", trackNumber=" + trackNumber +
                ", samplerate=" + samplerate +
                ", year=" + year +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        return file.equals(track.file);

    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }

    @Override
    public int compareTo(@NonNull Track track) {

        char local = getTitleString().charAt(0);
        char foreign = track.getTitleString().charAt(0);

        if (local == foreign) return 0;
        if (local > foreign) return 1;
        if (local < foreign) return -1;

        return 0;
    }
}
