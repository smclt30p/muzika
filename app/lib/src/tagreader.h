/*
 * Copyright (c) 2017 Ognjen Galić
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

#ifndef TAGREADER_H
#define TAGREADER_H

#include <stdlib.h>
#include <stdbool.h>
#include "taglib/tag_c.h"

typedef struct {
    TagLib_File *file;
    TagLib_Tag  *tag;
    char* artist;
    char* album;
    char* title;
    char* genre;
    char* comment;
    int year;
    int trackno;
    int samplerate;
    int bitrate;
    int length;
    bool file_open;
} TrackInfo;


int    tag_open_file       (const char *path);
void    tag_close_file      ();
char*   tag_get_artist      ();
char*   tag_get_album       ();
char*   tag_get_title       ();
char*   tag_get_genre       ();
char*   tag_get_comment     ();
int     tag_get_year        ();
int     tag_get_trackno     ();
int     tag_get_bitrate     ();
int     tag_get_samplerate  ();
int     tag_get_length      ();


#endif // TAGREADER_H