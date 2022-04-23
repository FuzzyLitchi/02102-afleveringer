letters: letters.c
	gcc letters.c -o letters

stackMain: stackMain.c stack.o
	gcc stackMain.c -o stackMain stack.o

stack.o: stack.c
	gcc -c -o stack.o stack.c

cudb: cudb.c
	gcc cudb.c -o cudb

clean:
	rm stack.o stackMain letters