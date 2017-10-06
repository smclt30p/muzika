#ifndef MUZIKA_LIBRARY_H
#define MUZIKA_LIBRARY_H

#include<stdint.h>

typedef struct {
    char *name;
} artist_t;

typedef struct {
    char* name;
    uint16_t year;
    char *description;
    artist_t *artist;
} album_t;

typedef struct {
    char *name;
    char *file;
    uint8_t bitrate;
    uint16_t samplerate;
    uint64_t length;
    artist_t *artist;
    album_t *album;
} track_t;

#endif //MUZIKA_LIBRARY_H
