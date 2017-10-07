/*
 * Copyright (c) 2017 Ognjen Galić
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
*/

#include "library.h"
#include "list.h"
#include "log.h"

list_t *songs;

bool file_is_supported(const char *fpath) {

    /* get the index of the last byte of the string */
    size_t len_path = strlen(fpath) - 1;

    /* traverse the string from the back to find the '.' character */
    const char* temp = fpath;
    while (len_path != 0) {
        if (*(temp + len_path) == '.') {
            break;
        }
        len_path--;
    }

    /* reached start of string, no extension */
    if (len_path == 0) {
        return false;
    }

    /* pointer to the file extension */
    const char *extension = temp + ++len_path;

    /* check if file is supported */
    char** ptr = supported_formats;
    while (*ptr != NULL) {
        if (strcmp(extension, *ptr) == 0) {
            return true;
        }
        ptr++;
    }

    return false;

}

int tree_file_process(const char *fpath, const struct stat *sb, int typeflag) {

    if (typeflag != FTW_F || !file_is_supported(fpath)) {
        return SCAN_CONTINUE;
    }

    /* copy temporary path to the heap */
    void* song = malloc((strlen(fpath) + 1) * sizeof(char*));
    strcpy((char*) song, fpath);
    list_add(songs, song);

    return SCAN_CONTINUE;
}

void library_scan(const char* path) {
    log_info("Starting library scan...");
    songs = list_new();
    ftw("/storage", tree_file_process, 50);

    for (uint64_t i = 0; i < songs->size; i++) {
        char* song = (char*) list_get(songs, i);
        log_info("song found: %s", song);
        free(song);
    }

}