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

#include <jni.h>
#include <stdbool.h>

#include "audio.h"
#include "tagreader.h"
#include "jnilib.h"

JNI_CALL void AudioService_init() {
    audio_init();
}

JNI_CALL int64_t AudioService_getCurrentPosition() {
    return audio_get_current_position();
}

JNI_CALL void AudioService_setPaused(bool state) {
    audio_set_paused(state);
}

JNI_CALL void AudioService_playStream(const char *path) {
    audio_play(path);
}

void AudioService_setCurrentPosition(int64_t position) {
    audio_set_current_position(position);
}

int64_t AudioService_getStreamLength() {
    return audio_get_stream_length();
}

int TrackMetadataReader_openFile(const char *path) {
    return tag_open_file(path);
}

void TrackMetadataReader_closeFile() {
    tag_close_file();
}

char* TrackMetadataReader_getArtist() {
    return tag_get_artist();
}

char* TrackMetadataReader_getAlbum() {
    return tag_get_album();
}

char* TrackMetadataReader_getTitle() {
    return tag_get_title();
}

char* TrackMetadataReader_getGenre() {
    return tag_get_genre();
}

char* TrackMetadataReader_getComment() {
    return tag_get_comment();
}

int32_t TrackMetadataReader_getYear() {
    return tag_get_year();
}

int32_t TrackMetadataReader_getTrackNumber() {
    return tag_get_trackno();
}