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

JNIEXPORT void JNICALL
Java_org_muzika_audio_AudioService_init(JNIEnv *env, jobject instance) {
    AudioService_init();
}

JNIEXPORT jlong JNICALL
Java_org_muzika_audio_AudioService_getCurrentPosition(JNIEnv *env,
                                                                     jobject instance) {
    return AudioService_getCurrentPosition();
}

JNIEXPORT jobject JNICALL
Java_org_muzika_audio_AudioService_setPaused(JNIEnv *env, jobject instance,
                                                       jboolean state) {
    AudioService_setPaused(state);
    return NULL;
}

JNIEXPORT jobject JNICALL
Java_org_muzika_audio_AudioService_playStream(JNIEnv *env, jobject instance,
                                                        jstring path_) {
    const char *path = (*env)->GetStringUTFChars(env, path_, 0);
    AudioService_playStream(path);
    (*env)->ReleaseStringUTFChars(env, path_, path);
    return NULL;
}

JNIEXPORT jobject JNICALL
Java_org_muzika_audio_AudioService_setCurrentPosition(JNIEnv *env, jobject instance,
                                                                jlong position) {
    AudioService_setCurrentPosition(position);
    return NULL;
}

JNIEXPORT jlong JNICALL
Java_org_muzika_audio_AudioService_getStreamLength(JNIEnv *env, jobject instance) {
    return AudioService_getStreamLength();
}

JNIEXPORT jint JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_openFile(JNIEnv *env, jobject instance, jstring path) {

    const char *_path = (*env)->GetStringUTFChars(env, path, 0);
    int status = TrackMetadataReader_openFile(_path);
    (*env)->ReleaseStringUTFChars(env, path, _path);
    return status;
}

JNIEXPORT jobject JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_closeFile(JNIEnv *env, jobject instance) {

    TrackMetadataReader_closeFile();
    return NULL;
}

JNIEXPORT jstring JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getArtist(JNIEnv *env, jobject instance) {

    char* dat_ptr = TrackMetadataReader_getArtist();
    jstring str_data = (*env)->NewStringUTF(env, dat_ptr);
    return str_data;
}

JNIEXPORT jstring JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getAlbum(JNIEnv *env, jobject instance) {
    char* dat_ptr = TrackMetadataReader_getAlbum();
    jstring str_data = (*env)->NewStringUTF(env, dat_ptr);
    return str_data;
}

JNIEXPORT jstring JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getTitle(JNIEnv *env, jobject instance) {
    char* dat_ptr = TrackMetadataReader_getTitle();
    jstring str_data = (*env)->NewStringUTF(env, dat_ptr);
    return str_data;
}

JNIEXPORT jstring JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getGenre(JNIEnv *env, jobject instance) {
    char* dat_ptr = TrackMetadataReader_getGenre();
    jstring str_data = (*env)->NewStringUTF(env, dat_ptr);
    return str_data;
}

JNIEXPORT jstring JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getComment(JNIEnv *env, jobject instance) {
    char* dat_ptr = TrackMetadataReader_getComment();
    jstring str_data = (*env)->NewStringUTF(env, dat_ptr);
    return str_data;
}

JNIEXPORT jint JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getYear(JNIEnv *env, jobject instance) {
    return TrackMetadataReader_getYear();
}

JNIEXPORT jint JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getTrackNumber(JNIEnv *env, jobject instance) {
    return TrackMetadataReader_getTrackNumber();
}

JNIEXPORT jint JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getBitRate(JNIEnv *env, jobject instance) {
    return TrackMetadataReader_getBitRate();
}

JNIEXPORT jint JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getSampleRate(JNIEnv *env, jobject instance) {
    return TrackMetadataReader_getSampleRate();
}

JNIEXPORT jint JNICALL
Java_org_muzika_filesystem_TrackMetadataReader_getLength(JNIEnv *env, jobject instance) {
    return TrackMetadataReader_getLength();
}