#include <jni.h>

#include "audio.h"
#include "tagreader.h"
#include "jni_abstract.hpp"

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