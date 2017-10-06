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

#ifndef LIST_H
#define LIST_H

#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>

typedef struct list_node_t list_node_t;
typedef struct list_t list_t;

struct list_node_t {
    void **data;
    list_node_t *next;
    list_node_t *previous;
};

struct list_t {
    char* name;
    list_node_t *head;
    list_node_t *tail;
    uint64_t size;
};

list_t*     list_new            ();
void	    list_add            (list_t* list, void* object);
bool	    list_contains       (list_t* list, void* object);
bool	    list_remove         (list_t* list, void* object);
void*       list_get            (list_t* list, uint64_t index);
void	    list_add_all        (list_t* list, list_t* list_another);
bool	    list_contains_all   (list_t* list, list_t* list_another);
void	    list_remove_all     (list_t* list, list_t* list_another);
bool	    list_is_empty       (list_t* list);
void	    list_clear          (list_t* list);
void        list_free           (list_t* list);

#endif // LIST_H