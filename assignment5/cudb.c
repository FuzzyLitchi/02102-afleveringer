// It's not actually 4 characters it's 4 bytes/chars.
// Nørd isn't a valid name even though it should be

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NAME_SIZE 5

typedef struct {
    char name[NAME_SIZE];
    int data;
} student_t;

// Returns -1 if there is no input. Don't use this function if you need -1 as input.
int read_int() {
    // Ok so.
    // We read a whole line, and only after reading the line do we parse it.
    // This way we don't leave characters in the "input buffer". Which is to
    // say, if a user input "10\n" we read the newline also. If we did scanf
    // that would leave the newline character in there. I don't know if there
    // is a better way to do this. Maybe I should read the book.

    // Well that was no help at all. I mean parsing numbers based on just
    // reading until it is no longer a number is pretty foolish, we should
    // be checking that the integer is immediately proceeded by either EOF
    // or a newline and then an EOF. Or as regex /^[0-9]\n?$/
    char input[100];
    fgets(input, 100, stdin);

    int choice = -1;
    sscanf(input, "%d", &choice);

    return choice;
}

void print_students(student_t database[], int student_count) {
    // If there are no students, display a helpful message.
    if (student_count == 0) {
        printf("\nThere are no students in the database.\n\n");
        return;
    }
    // Otherwise, display a user friendly table

    printf("\nNumber Name Year Start  GPA\n");

    for (int i = 0; i < student_count; i++) {
        student_t* student = &database[i];

        int start_year = (student->data & 0b11111) + 2009;
        int spring_start = student->data >> 5 & 0b1;
        int gpa = student->data >> 6 & 0xff;

        char* start = spring_start ? "Spring" : "Autumn";

        printf("s%04d  %s %d %s %d\n", i, student->name, start_year, start, gpa);
    }
    
    printf("\n");
}

void add_student(student_t database[], int* student_count) {
    // In order to parse input correctly we read way more than we need.
    // We assume that the user won't type a line longer than 100 characters,
    // which means the entire line (followed by a newline and then a null byte)
    // is going to appear in name. We can then give the user useful feedback
    // about their input (did they input too much/too little) and assure there's
    // no charcaters in the input buffer that'll leak to the next input handling.
    printf("Enter a name (4 characters only): ");

    char name[100];
    fgets(name, 100, stdin);
    // Shorten the string to end at the newline. Basically transforms "John\n" to "John".
    // Any garbage after the newline is also discarded.
    for (int i = 0;; i++) {
        if (name[i] == 0) {
            break;
        } else if (name[i] == '\n') {
            name[i] = 0;
            break;
        }
    }

    // Validated name
    if (strlen(name) > 4) {
        printf("The students name has to be 4 characters or shorter.\n\n");
        return;
    } else if (strlen(name) == 0) {
        printf("You have to enter a name.\n\n");
        return;
    }

    // Read year
    printf("Enter start year (2009-2040): ");
    int start_year = read_int() - 2009;
    // Validate year
    if (start_year < 0 || start_year > 31) {
        printf("The start year has to be between 2009 and 2040 (inclusive).\n\n");
        return;
    }

    // Read semester
    printf("Enter start semester (0=Autumn/1=Spring): ");
    int spring = read_int();
    // Validate semester
    if (spring != 0 && spring != 1) {
        printf("Start semester has to be 0 (Autumn) or 1 (Spring).\n\n");
        return;
    }

    // Read GPA
    printf("Enter GPA (0-255): ");
    int gpa = read_int();
    // Validate GPA
    if (gpa < 0 || gpa > 255) {
        printf("GPA must be between 0 and 255 (inclusive).\n\n");
        return;
    }

    // We have all the data needed to store the student.
    student_t student = {
        // GPA | spring or autumn | start år efter 2009
        .data = (gpa << 6) | (spring << 5) | start_year
    };
    strcpy(student.name, name);

    database[*student_count] = student;
    (*student_count)++;

    printf("Student added.\n\n");
}

void print_menu_options() {
    printf(
        "0: Halt\n"
        "1: List all students\n"
        "2: Add a new student\n"
        "\n"
    );
}

int main() {
    puts("Welcome to CUDB - The C University Data Base");

    // Initliaze the "database"
    student_t database[10000]; // We have to be able to store 10_000 students
    int student_count = 0;

    print_menu_options();

    while (1) {
        printf("Enter action: ");

        // Ugh idk how I'm supposed to do this.
        int choice = read_int();

        switch (choice) {
            case 0:
                puts("Dropping the database, persistence is for chumps anyway.");
                // We can't break out of the switch statement and the while loop.
                // In a better language, switch statements wouldn't need "break" to
                // not "fall" into the next option. In a better language, you would
                // be able to break out of nested loops. Unfortunately, we are not
                // in a better language. So we simply call exit(0). This makes the
                // return statement at the end of main pointless. We could use a
                // goto statement, but it's such a footgun I almost don't want to
                // even mention it. It's like the aura of goto is going to make my
                // code unsafe.
                // ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⢿⣿⣿⣿⠟⠻⣿⣿⣿⣿⠟⢿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⡏⠀⠀⣿⣿⣿⣿⠀⠀⢿⣇⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣧⣤⣴⣿⣿⣿⣿⣷⣤⣾⣿⢸⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡞⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⢿⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⡿⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣸⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⣿⠃⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⣿⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀
                // ⠀⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⢹⣿⣿⣿⣿⣿⡀⠀⠀⠀⠀
                // ⠀⠀⠀⢠⣿⣿⣿⣿⣽⣿⡇⢸⢷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠸⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀
                // ⠀⠀⢠⣿⣿⣿⣿⣇⣿⣿⠁⣿⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠇⣿⣿⣿⣿⣿⣿⣇⠀⠀⠀
                // ⠀⢀⣿⣿⣿⠏⣿⣾⣿⡟⢀⠏⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⢹⣿⣿⣿⣿⣿⣿⡆⠀⠀
                // (make sure to decode this file as utf-8 and not windows-1252)
                exit(0);

            case 1:
                print_students(database, student_count);
                break;

            case 2:
                add_student(database, &student_count);
                break;

            default: 
                printf("Invalid action. Select a valid option:\n\n");
                print_menu_options();
                break;
        }
    }

    return 0;
}
