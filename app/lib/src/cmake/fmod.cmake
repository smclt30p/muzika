include(ExternalProject)

# fmod configuration
set(FMOD_VERSION 11000)
set(FMOD_CHECKSUM "154308800ea4e21649b70120d999fe7a")

ExternalProject_Add(
    libfmod
    URL ${CMAKE_CURRENT_SOURCE_DIR}/external/fmodstudioapi${FMOD_VERSION}android.tar.gz
    URL_MD5 ${FMOD_CHECKSUM}
    CONFIGURE_COMMAND ""
    BUILD_COMMAND ""
    INSTALL_COMMAND ${CMAKE_COMMAND} -E copy
                    ${CMAKE_CURRENT_BINARY_DIR}/libfmod/src/libfmod/api/lowlevel/lib/${ANDROID_ABI}/libfmod.so
                    ${CMAKE_SUPERBUILD_OUT}/${ANDROID_ABI}/libfmod.so
                    &&
                    ${CMAKE_COMMAND} -E copy
                    ${CMAKE_CURRENT_BINARY_DIR}/libfmod/src/libfmod/api/lowlevel/lib/fmod.jar
                    ${CMAKE_SUPERBUILD_OUT}/fmod.jar
    PREFIX libfmod
)

add_library(fmod SHARED IMPORTED)
set_target_properties(fmod PROPERTIES IMPORTED_LOCATION ${CMAKE_SUPERBUILD_OUT}/${ANDROID_ABI}/libfmod.so)
include_directories(${CMAKE_CURRENT_BINARY_DIR}/libfmod/src/libfmod/api/lowlevel/inc)