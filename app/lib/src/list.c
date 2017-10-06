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

#include "list.h"

list_t* list_new() {
    list_t* list = malloc(sizeof(list_t));
    list->size = 0;
    list->head = NULL;
    list->tail = NULL;
    list->name = "Generic List";
    return list;
}

void list_add(list_t* list, void* object) {

    /* Create a new node */
    list_node_t *data = malloc(sizeof(list_node_t));
    data->data = object;
    data->next = NULL;
    data->previous = NULL;

    /* Handle empty list */
    if (list->head == NULL) {
        list->head = data;
        list->tail = data;
        list->size = 1;
        return;
    }

    /* Handle n-number of elements list */
    list_node_t *old_tail = list->tail;

    list->tail = data;
    old_tail->next = data;
    list->tail->previous = old_tail;
    list->size++;

}

bool list_remove(list_t* list, void* object) {

    /*
     * If the list is empty return false.
     */
    if (list->head == NULL) {
        return false;
    }

    /* Temporary values for housekeeping */
    list_node_t *pointer = list->head;
    list_node_t *list_item = NULL;

    /* check flags for the item */
    int8_t IS_HEAD = list->head->data == object;
    int8_t IS_TAIL = list->tail->data == object;

    /* There is only one item in the list*/
    if (IS_TAIL && IS_HEAD) {
        list_node_t *old_item = list->head;
        list->head = NULL;
        list->tail = NULL;
        list->size = 0;
        free(old_item);
        return true;
    }

    /* object is tail */
    if (IS_TAIL) {
        list->tail = list_item->previous;
        list->tail->next = NULL;
        list->size--;
        free(list_item);
        return true;
    }

    /* object is head */
    if (IS_HEAD) {
        list->head = list->head->next;
        list->head->previous = NULL;
        list->size--;
        free(list_item);
        return true;
    }

    /* object is n-th, remove it */
    while (pointer->next != NULL) {
        if (pointer->data == object) {
            pointer->previous->next = pointer->next;
            pointer->next->previous = pointer->previous;
            list->size--;
            free(pointer);
            return true;
        }
        pointer = pointer->next;
    }

    return false;

}

bool list_contains(list_t* list, void* object) {

    /* list is empty */
    if (list->head == NULL) {
        return false;
    }

    /* check flags for the item */
    int8_t IS_HEAD = list->head->data == object;
    int8_t IS_TAIL = list->tail->data == object;

    /* the item is a head or tail */
    if (IS_HEAD || IS_TAIL) {
        return true;
    }

    list_node_t *pointer = list->head;

    while (pointer->next != NULL) {
        if (pointer->data == object) {
            return true;
        }
        pointer = pointer->next;
    }

    return false;

}

void* list_get(list_t* list, uint64_t index) {

    /* prevent index-out-of-bounds */
    if (index >= list->size) {
        return NULL;
    }

    /* if the index is 0 return the head */
    if(index == 0 && list->head != NULL) {
        return list->head->data;
    }

    /*
     * if the list has more than one item, the tail is not null and the index is
     * the last item in the list, return the tail
     */
    if (list->size > 1 && list->tail != NULL && index == list->size - 1) {
        return list->tail->data;
    }

    list_node_t *pointer = list->head;
    uint64_t index_temp = 0;

    while(pointer->next != NULL) {
        if (index == index_temp) {
            return pointer->data;
        }
        index_temp++;
        pointer = pointer->next;
    }

    return NULL;

}


void list_add_all(list_t* list, list_t* list_another) {
    for (uint64_t i = 0; i < list->size; i++) {
        void* data = list_get(list, i);
        list_add(list_another, data);
    }
}

bool list_contains_all(list_t* list, list_t* list_another) {

    /* list of different sizes; impossible for both to contain the same */
    if (list->size != list_another->size) {
        return false;
    }

    for (uint64_t i = 0; i < list->size; i++) {
        void *a = list_get(list, i);
        void *b = list_get(list, i);
        if (a != b) {
            return false;
        }
    }

    return true;
}

void list_remove_all(list_t* list, list_t* list_another) {
    for (uint64_t i = 0; i < list_another->size; i++) {
        void* data = list_get(list_another, i);
        if (list_contains(list, data)) {
            list_remove(list, data);
        }
    }
}

bool list_is_empty(list_t* list) {
    return list->head == NULL;
}

void list_clear(list_t* list) {

    if (list->size == 0) {
        return;
    }

    list_node_t *pointer = list->head;
    list_node_t *pointer_save = NULL;

    while (pointer->next != NULL) {
        pointer_save = pointer->next;
        free(pointer);
        pointer = pointer_save;
    }

    free(pointer_save);

    list->size = 0;
    list->head = NULL;
    list->tail = NULL;

}

void list_free(list_t* list) {
    list_clear(list);
    free(list);
}