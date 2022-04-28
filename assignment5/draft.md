# Letters

In order to count the amount of "letters"\footnote{The assignment specifies that we're counting characters actually.} using strlen, we simply have to call it on every argument passed to our program. There are many edge cases when counting characters.

```
$ letters Hello World
10
$ letters "Hello World"
11
$ letters a
1
$ letters
0
$ letters Punctuation!
12
$ letters Punctuation
11
$ letters Ø
2
$ letters 🤯
4
```

As we can see, parsing \verb!Hello World! as two arguments or one argument surrounded by quotes changes how it's interpreted\footnote{Arguably, this behavior stems from bash, but similar noteworthy edge cases occur in almost every command line interface to run executables.}. Not giving the program any characters results in it simply counting to 0 (We might prefer an error, depending on our use case). We note that the program counts chracters, including spaces or exclamation points, not letters. Except that it doesn't quite count characters, it counts chars. Which just means that it counts how many bytes the strings we give are encoded as, which is not the same as what a user might expect a character to be. You might think only giving it ASCII would resolve this as we don't run into emoji, latin (but non-english) letters which are all encoded as more than 1 byte, however even ASCII has some spooky characters. All of \verb!0x00-0x1F! in ASCII are control characters, which are valid ASCII but nonetheless will not be understood by users.

# 