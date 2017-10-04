
/* Copyright (c) 2017 Ognjen Galić
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
*/

#include "tagreader.h"
#include "log.h"

TrackInfo info;

int tag_open_file(const char *path) {

    info.file = taglib_file_new(path);
    if (info.file == NULL) {
        LOG("Error opening file for tag reading: %s", path);
        return -1;
    }
    info.tag = taglib_file_tag(info.file);
    info.artist = taglib_tag_artist(info.tag);
    info.album = taglib_tag_album(info.tag);
    info.title = taglib_tag_title(info.tag);
    info.comment = taglib_tag_comment(info.tag);
    info.genre = taglib_tag_genre(info.tag);
    info.year = (int) taglib_tag_year(info.tag);
    info.trackno = (int) taglib_tag_track(info.tag);
    return 0;

}

void tag_close_file() {
    taglib_tag_free_strings();
    taglib_file_free(info.file);
}

char* tag_get_artist() {
    return info.artist;
}

char* tag_get_album() {
    return info.album;
}

char* tag_get_title() {
    return info.title;
}

char* tag_get_genre() {
    return info.genre;
}

char* tag_get_comment() {
    return info.comment;
}

int tag_get_year() {
    return info.year;
}

int tag_get_trackno() {
    return info.trackno;
}