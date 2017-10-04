cmake_minimum_required(VERSION 3.6)

project(libmuzika)

# unpack/link to FMOD
include(fmod.cmake)

# build taglib
include(taglib.cmake)

set(libmuzika_srcs
    audio.c
    jni_abstract.cpp
    tagreader.c
)

set(libmuzika_hdrs
    audio.h
    jni_abstract.hpp
    jni_calls.hpp
    log.h
    tagreader.h
)

add_library(muzika SHARED ${libmuzika_srcs} ${libmuzika_hdrs})

add_dependencies(fmod libfmod)
add_dependencies(muzika fmod)

target_link_libraries(muzika fmod android log tag)

install(TARGETS muzika DESTINATION ${CMAKE_SUPERBUILD_OUT}/${ANDROID_ABI})