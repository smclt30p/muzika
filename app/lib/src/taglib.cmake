include(ExternalProject)

# taglib configuration
set(TAGLIB_VERSION 1.11.1)
set(TAGLIB_CHECKSUM "8624219ba50c77f17889af15e1ca5f7d")

ExternalProject_Add(
    taglib
    URL ${CMAKE_CURRENT_SOURCE_DIR}/external/taglib-${TAGLIB_VERSION}.zip
    URL_MD5 ${TAGLIB_CHECKSUM}
    CONFIGURE_COMMAND ${CMAKE_COMMAND}
                      ${CMAKE_CURRENT_BINARY_DIR}/taglib/src/taglib
                      -DCMAKE_SYSTEM_NAME=Android
                      -DCMAKE_ANDROID_API=${ANDROID_NATIVE_API_LEVEL}
                      -DCMAKE_ANDROID_ARCH_ABI=${ANDROID_ABI}
                      -DANDROID_ABI=${ANDROID_ABI}
                      -DCMAKE_TOOLCHAIN_FILE=${CMAKE_TOOLCHAIN_FILE}
                      -GUnix\ Makefiles
                      -DCMAKE_MAKE_PROGRAM=${CMAKE_MAKE_PROGRAM}
                      -DBUILD_SHARED_LIBS=ON
                      -DCMAKE_INSTALL_PREFIX=${CMAKE_CURRENT_BINARY_DIR}/build/taglib/${ANDROID_ABI}

    PREFIX taglib
    INSTALL_COMMAND ${CMAKE_MAKE_PROGRAM} install
                    &&
                    ${CMAKE_COMMAND} -E copy
                    ${CMAKE_CURRENT_BINARY_DIR}/build/taglib/${ANDROID_ABI}/lib/libtag.so
                    ${CMAKE_SUPERBUILD_OUT}/${ANDROID_ABI}/libtag.so
)

add_library(tag SHARED IMPORTED)
set_target_properties(tag PROPERTIES IMPORTED_LOCATION ${CMAKE_CURRENT_BINARY_DIR}/build/taglib/${ANDROID_ABI}/lib/libtag.so)
include_directories(${CMAKE_CURRENT_BINARY_DIR}/build/taglib/${ANDROID_ABI}/include)