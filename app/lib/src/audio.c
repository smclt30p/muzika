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

#include <stdint.h>
#include <stdbool.h>

#include "audio.h"
#include "log.h"


Player player;

void audio_check_error(const char* tag, FMOD_RESULT result) {
    if (result != FMOD_OK) {
        log_info("error %s: %s",tag, FMOD_ErrorString(result))
    }
}

void audio_close() {
    audio_check_error("audio_close", FMOD_System_Close(player.system));
}

void audio_init() {
    player.stream_is_valid = false;
    player.channel_is_valid = false;
    audio_check_error("system_create", FMOD_System_Create(&player.system));
    audio_check_error("set_software_format", FMOD_System_SetSoftwareFormat(player.system, 44100, FMOD_SPEAKERMODE_STEREO, 2));
    audio_check_error("init", FMOD_System_Init(player.system, 10, FMOD_INIT_NORMAL, 0));
    audio_check_error("get_version", FMOD_System_GetVersion(player.system, &player.fmod_version));
    log_info("audio: initializing FMOD version %d", player.fmod_version);
    log_info("audio: running sound card detection");
    audio_check_error("get_numdrivers", FMOD_System_GetNumDrivers(player.system, &player.cards_num));
    if (player.cards_num == 0) {
        log_error("audio: fatal error: no sound cards detected");
        audio_close();
        return;
    }
    log_info("audio: detected %d sound card(s)", player.cards_num);
    log_info("audio: FMOD initialized");
}

void audio_set_paused(bool state) {
    if (!player.channel_is_valid) {
        log_error("audio_set_paused: can't pause invalid channel");
        return;
    }
    log_info("audio_set_paused: channel is valid")
    FMOD_BOOL playing;
    audio_check_error("audio_set_paused_is_playing", FMOD_Channel_IsPlaying(player.channel, &playing));
    if (!playing && !state) {
        log_error("audio_set_paused: channel is not playing, can't pause");
        return;
    }
    log_info("audio_set_paused: setting channel pause state: %d", state);
    audio_check_error("audio_set_paused_set_paused", FMOD_Channel_SetPaused(player.channel, state));
}

void audio_play(const char *path) {
    log_info("audio_play: playing %s", path);
    if (player.channel_is_valid) {
        audio_check_error("audio_play_channel_stop", FMOD_Channel_Stop(player.channel));
        player.channel_is_valid = false;
    }
    if (player.stream_is_valid) {
        audio_check_error("audio_play_sound_releae", FMOD_Sound_Release(player.stream));
        player.stream_is_valid = false;
    }
    audio_check_error("audio_play_system_createstream", FMOD_System_CreateStream(player.system, path, FMOD_CREATESTREAM, NULL, &player.stream));
    audio_check_error("audio_system_playsound", FMOD_System_PlaySound(player.system, player.stream, NULL, false, &player.channel));
    player.stream_is_valid = true;
    player.channel_is_valid = true;
}

int64_t audio_get_current_position() {
    if (!player.channel_is_valid) {
        return -1;
    }
    uint32_t position;
    FMOD_RESULT result = FMOD_Channel_GetPosition(player.channel, &position, FMOD_TIMEUNIT_MS);
    if (result != FMOD_OK) {
        log_error("audio_get_playing_offset: fmod error: %s", FMOD_ErrorString(result));
        return -1;
    }
    return (int64_t) position;
}

void audio_set_current_position(int64_t position) {
    if (!player.channel_is_valid || !player.stream_is_valid) {
        return;
    }
    log_info("audio_set_current_position: seeking to %lld", position);
    audio_check_error("audio_set_current_position_channel_setposition", FMOD_Channel_SetPosition(player.channel, (uint32_t) position, FMOD_TIMEUNIT_MS));
}

int64_t audio_get_stream_length() {
    if (!player.stream_is_valid) {
        return 0;
    }
    unsigned int length;
    audio_check_error("audio_get_stream_length", FMOD_Sound_GetLength(player.stream, &length, FMOD_TIMEUNIT_MS));
    return (int64_t) length;
}
