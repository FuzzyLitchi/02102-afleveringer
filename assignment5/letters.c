#include <stdio.h>
#include <string.h>

int main(int argc, char * argv[]) {
    int letter_count = 0;

    for (int i = 1; i < argc; i++) {
        letter_count += strlen(argv[i]);
    }
    
    printf("%d\n", letter_count);

    return 0;
}
