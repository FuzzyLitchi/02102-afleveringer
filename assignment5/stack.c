// stack.c

#include <stdlib.h>
#include <stdio.h>
#include "stack.h"

#define STACK_START_SIZE 1

stack_t* newStack(void) {
    int* array = malloc(sizeof(int) * STACK_START_SIZE);

    if (array == NULL) {
        printf("Couldn't allocate array.\n");
        exit(112);
    }

    stack_t* stack = malloc(sizeof(stack_t));
    if (array == NULL) {
        printf("Couldn't allocate stack struct.\n");
        exit(112);
    }

    stack->capacity = 1;
    stack->array = array;
    stack->size = 0;

    return stack;
}

int pop(stack_t* stack) {
    if (stack->size == 0) {
        // There's nothing to pop!! We gotta crash
        printf("pop() ran on an empty stack! Aborting!\n");
        exit(112);
    }

    stack->size--;
    return stack->array[stack->size];
}

void push(stack_t* stack, int value) {
    // If our array is full, we have to reallocate.
    if (stack->capacity == stack->size) {
        // We double the capacity
        stack->capacity *= 2;
        stack->array = realloc(stack->array, sizeof(int) * stack->capacity);

        if (stack->array == NULL) {
            printf("Couldn't reallocate array.\n");
            exit(112);
        }
    }

    stack->array[stack->size] = value;
    stack->size++;
}

int top(stack_t* stack) {
    return stack->array[stack->size-1];
}

int empty(stack_t * stack) {
    return stack->size == 0;
}

