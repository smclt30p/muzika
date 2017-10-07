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

#ifndef MUZIKA_LIBRARY_H
#define MUZIKA_LIBRARY_H

#include<stdint.h>
#include<ftw.h>
#include<string.h>
#include<stdbool.h>

#define SCAN_CONTINUE 0
#define SCAN_CANCEL 1

static char *supported_formats[] = {
    "aiff","aif","aifc","asf",
    "wma","asx","dls","flac",
    "fsb","it","mid","midi",
    "mod","mp2","mpeg2","mp3",
    "mpeg3","ogg","oga","pls",
    "s3m","vag","wav","wax",
    "xm", NULL
};

typedef struct {
    char *name;
} artist_t;

typedef struct {
    char* name;
    uint16_t year;
    char *description;
    artist_t *artist;
} album_t;

typedef struct {
    char *name;
    char *file;
    uint8_t bitrate;
    uint16_t samplerate;
    uint64_t length;
    artist_t *artist;
    album_t *album;
} track_t;


void    library_scan        (const char* path);

#endif //MUZIKA_LIBRARY_H
