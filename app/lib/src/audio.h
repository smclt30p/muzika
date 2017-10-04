#ifndef AUDIO_H
#define AUDIO_H

#include "fmod_common.h"
#include "fmod_errors.h"

typedef struct {
    FMOD_SYSTEM *system;
    FMOD_SOUND *stream;
    FMOD_CHANNEL *channel;
    bool stream_is_valid;
    bool channel_is_valid;
    int32_t cards_num;
    uint32_t fmod_version;
} Player;

void        audio_init                      ();
void        audio_set_paused                (bool state);
void        audio_play                      (const char* path);
int64_t     audio_get_current_position      ();
void        audio_set_current_position      (int64_t position);
int64_t     audio_get_stream_length         ();

#endif // AUDIO_H