/*
 *  Copyright (c) 2017 Ognjen Galić
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package org.muzika.views.services.media;

import org.muzika.audio.AudioService;
import org.muzika.model.Track;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.muzika.core.Logger.*;

public class MediaPlayerService extends TimerTask {

    private static final MediaPlayerService instance = new MediaPlayerService();
    public static MediaPlayerService getInstance() {
        return instance;
    }

    private final ArrayList<OnPlaybackInfoBroadcast> listeners;
    private final AudioService audioService;

    private MediaPlayerState mediaPlayerState;
    private Track currentTrack;
    private Timer playbackTimer;
    private MediaPlayerTimerTask timerTask;
    private long currentPosition = 0;
    private long totalLength = 0;

    private MediaPlayerService() {

        listeners = new ArrayList<>();
        audioService = new AudioService();
        playbackTimer = new Timer();
        mediaPlayerState = MediaPlayerState.INVALID;
        timerTask = new MediaPlayerTimerTask();

        audioService.init();

    }

    @Override
    public void run() {
        pingListeners();
    }

    public void addPlaybackListener(OnPlaybackInfoBroadcast listener) {
        listeners.add(listener);
    }

    private void restartTimer() {
        cancelTimer();
        this.playbackTimer.schedule(timerTask, 0, 500);
    }

    public void playTrack(Track track) {
        this.audioService.playStream(track.getFile().getAbsolutePath());
        this.currentTrack = track;
        this.totalLength = this.audioService.getStreamLength();
        this.mediaPlayerState = MediaPlayerState.NEW_TRACK;
        pingListeners();
        this.mediaPlayerState = MediaPlayerState.PLAYING;
        restartTimer();
    }

    public void seek(long currentPosition) {
        this.currentPosition = currentPosition;
        if (mediaPlayerState != MediaPlayerState.INVALID) {
            audioService.setCurrentPosition(currentPosition);
        }
        pingListeners();
    }

    private void pingListeners() {
        if (mediaPlayerState != MediaPlayerState.INVALID) {
            currentPosition = audioService.getCurrentPosition();
            if (currentPosition == totalLength) {
                mediaPlayerState = MediaPlayerState.PLAYBACK_END;
                cancelTimer();
            }
        }
        for (OnPlaybackInfoBroadcast listener : listeners) {
            listener.updateInfo(mediaPlayerState, this, currentTrack, totalLength, currentPosition);
        }
    }

    private void cancelTimer() {
        this.timerTask.cancel();
        this.timerTask = new MediaPlayerTimerTask();
        this.playbackTimer.cancel();
        this.playbackTimer.purge();
        this.playbackTimer = new Timer();
    }

    public void setPaused(boolean paused) {

        switch (mediaPlayerState) {
            case PAUSED:

                if (paused) {
                    info(this, "Can't pause a track that's paused already");
                    return;
                }

                this.audioService.setPaused(false);
                mediaPlayerState = MediaPlayerState.PLAYING;
                restartTimer();
                pingListeners();

                break;
            case PLAYING:

                if (!paused) {
                    info(this, "Can't play a track that's already playing");
                    return;
                }

                this.playbackTimer.cancel();
                mediaPlayerState = MediaPlayerState.PAUSED;
                this.audioService.setPaused(true);
                pingListeners();

                break;
            case INVALID:
                info(this, "Can't pause an invalid player");
                break;
        }

    }

    public MediaPlayerState getMediaPlayerState() {
        return mediaPlayerState;
    }

    private class MediaPlayerTimerTask extends TimerTask {
        @Override
        public void run() {
            pingListeners();
        }
    }

    public interface OnPlaybackInfoBroadcast {
        void updateInfo(MediaPlayerState state, MediaPlayerService service, Track track, long length, long position);
    }

}
