
// These are all the messy JNI call-names here, through-calling
// the actual methods

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


