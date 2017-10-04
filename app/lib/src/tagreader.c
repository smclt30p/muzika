#include "tagreader.h"

#include "log.h"

TrackInfo info;

void tag_open_file(const char *path) {

    info.file = taglib_file_new(path);
    if (info.file == NULL) {
        LOG("Error opening file for tag reading: %s", path);
    }
    info.tag = taglib_file_tag(info.file);
    info.artist = taglib_tag_artist(info.tag);
    info.album = taglib_tag_album(info.tag);
    info.title = taglib_tag_title(info.tag);
    info.comment = taglib_tag_comment(info.tag);
    info.genre = taglib_tag_genre(info.tag);
    info.year = (int) taglib_tag_year(info.tag);
    info.trackno = (int) taglib_tag_track(info.tag);

}

void tag_close_file() {
    taglib_tag_free_strings();
    taglib_file_free(info.file);
}

char* tag_get_artist() {
    return info.artist;
}

char* tag_get_album() {
    return info.album;
}

char* tag_get_title() {
    return info.title;
}

char* tag_get_genre() {
    return info.genre;
}

char* tag_get_comment() {
    return info.comment;
}

int tag_get_year() {
    return info.year;
}

int tag_get_trackno() {
    return info.trackno;
}