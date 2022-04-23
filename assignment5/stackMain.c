// stackMain.c

#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

int main() {
    stack_t * myStack = newStack();
    for (int i = 0; i < 1<<20; i++) {
        push(myStack, i);
    }

    printf("    Size: %d\nCapacity: %d\n", myStack->size, myStack->capacity);
    
    while (!empty(myStack)) {
        int value = pop(myStack);
    }

    // We free the used memory
    free(myStack->array);
    free(myStack);

    return 0;
}
