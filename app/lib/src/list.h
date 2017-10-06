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