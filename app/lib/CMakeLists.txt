cmake_minimum_required(VERSION 3.6)

project(Muzika NONE)

include(ExternalProject)

# Set up Android SDK and NDK
set(ANDROID_BUILD_HOST_ARCH "windows-x86_64")
set(ANDROID_NATIVE_API_LEVEL 17)

set(ANDROID_SDK "C:/Android/sdk")
set(ANDROID_NDK "${ANDROID_SDK}/ndk-bundle")
set(ANDROID_TOOLCHAIN "${ANDROID_NDK}/build/cmake/android.toolchain.cmake")
set(ANDROID_MAKE_PROGRAM "${ANDROID_NDK}/prebuilt/${ANDROID_BUILD_HOST_ARCH}/bin/make.exe")

set(ANDROID_TARGET_ABI armeabi-v7a)

foreach(ANDROID_ABI ${ANDROID_TARGET_ABI})

    ExternalProject_Add(
        libmuzika_${ANDROID_ABI}
        SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/src
        CONFIGURE_COMMAND ${CMAKE_COMMAND}
                          ${CMAKE_CURRENT_SOURCE_DIR}/src
                          -DCMAKE_SYSTEM_NAME=Android
                          -DCMAKE_ANDROID_API=${ANDROID_NATIVE_API_LEVEL}
                          -DANDROID_NATIVE_API_LEVEL=${ANDROID_NATIVE_API_LEVEL}
                          -DCMAKE_ANDROID_ARCH_ABI=${ANDROID_ABI}
                          -DANDROID_ABI=${ANDROID_ABI}
                          -DCMAKE_TOOLCHAIN_FILE=${ANDROID_TOOLCHAIN}
                          -GUnix\ Makefiles
                          -DCMAKE_MAKE_PROGRAM=${ANDROID_MAKE_PROGRAM}
                          -DBUILD_SHARED_LIBS=ON
                          -DCMAKE_SUPERBUILD_OUT=${CMAKE_CURRENT_SOURCE_DIR}/build/out

        PREFIX libmuzika
    )

endforeach(ANDROID_ABI ${ANDROID_TARGET_ABI})

