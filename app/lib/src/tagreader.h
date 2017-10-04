#ifndef TAGREADER_H
#define TAGREADER_H

#include <stdlib.h>
#include "taglib/tag_c.h"

typedef struct {
    TagLib_File *file;
    TagLib_Tag  *tag;
    char* artist;
    char* album;
    char* title;
    char* genre;
    char* comment;
    int year;
    int trackno;
} TrackInfo;

#ifdef __cplusplus
extern "C" {
#endif

int    tag_open_file       (const char *path);
void    tag_close_file      ();
char*   tag_get_artist      ();
char*   tag_get_album       ();
char*   tag_get_title       ();
char*   tag_get_genre       ();
char*   tag_get_comment     ();
int     tag_get_year        ();
int     tag_get_trackno     ();

#ifdef __cplusplus
}
#endif

#endif // TAGREADER_H