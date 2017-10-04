#include <jni.h>

#include "audio.h"
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
