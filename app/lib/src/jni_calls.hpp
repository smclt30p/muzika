
// These are all the messy JNI call-names here, through-calling
// the actual methods

#ifdef __cplusplus
extern "C" {
#endif

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
    const char *path = env->GetStringUTFChars(path_, 0);
    AudioService_playStream(path);
    env->ReleaseStringUTFChars(path_, path);
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

#ifdef __cplusplus
}
#endif

