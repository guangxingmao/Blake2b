//
// Created by mgx on 2020-04-21.
//

#include "mem.h"
#include <string.h>

void memzero(void *s, size_t n)
{
    memset(s, 0, n);
}