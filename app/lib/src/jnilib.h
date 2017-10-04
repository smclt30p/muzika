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