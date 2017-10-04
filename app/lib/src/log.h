#include <android/log.h>
#define LOG(...) __android_log_print(ANDROID_LOG_INFO, "log" , __VA_ARGS__);
