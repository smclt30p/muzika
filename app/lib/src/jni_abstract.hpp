#ifndef JNI_ABSTRACT_H
#define JNI_ABSTRACT_H

#include <stdbool.h>

#include "jni.h"

#define JNI_CALL

/* These are all the JNI calls called in this applications. The interface goes like this:
 *
 * JNI_CALL <return type> ClassName_MethodName
*/

JNI_CALL    void        AudioService_init                       ();
JNI_CALL    int64_t     AudioService_getCurrentPosition         ();
JNI_CALL    void        AudioService_setCurrentPosition         (int64_t position);
JNI_CALL    int64_t     AudioService_getStreamLength            ();
JNI_CALL    void        AudioService_setPaused                  (bool state);
JNI_CALL    void        AudioService_playStream                 (const char *path);

#include "jni_calls.hpp"
#endif // JNI_ABSTRACT_H