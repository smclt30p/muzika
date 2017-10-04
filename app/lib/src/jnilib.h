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

#ifndef JNI_ABSTRACT_H
#define JNI_ABSTRACT_H

#include <stdbool.h>

#include "jni.h"

#define JNI_CALL

/* These are all the JNI calls called in this applications. The interface goes like this:
 *
 * JNI_CALL <return type> ClassName_MethodName
*/

/* org.muzika.audio.AudioService */
JNI_CALL    void        AudioService_init                       ();
JNI_CALL    int64_t     AudioService_getCurrentPosition         ();
JNI_CALL    void        AudioService_setCurrentPosition         (int64_t position);
JNI_CALL    int64_t     AudioService_getStreamLength            ();
JNI_CALL    void        AudioService_setPaused                  (bool state);
JNI_CALL    void        AudioService_playStream                 (const char *path);

/* org.muzika.filesystem.TrackMetadataReader */
JNI_CALL    int         TrackMetadataReader_openFile            (const char *path);
JNI_CALL    void        TrackMetadataReader_closeFile           ();
JNI_CALL    char*       TrackMetadataReader_getArtist           ();
JNI_CALL    char*       TrackMetadataReader_getAlbum            ();
JNI_CALL    char*       TrackMetadataReader_getTitle            ();
JNI_CALL    char*       TrackMetadataReader_getGenre            ();
JNI_CALL    char*       TrackMetadataReader_getComment          ();
JNI_CALL    int32_t     TrackMetadataReader_getYear             ();
JNI_CALL    int32_t     TrackMetadataReader_getTrackNumber      ();

#include "jnireceiver.h"
#endif // JNI_ABSTRACT_H