#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
void generate_fibonacci(int n) {
    int a = 0, b = 1;
    int i;
    for (i = 0; i < n; i++) {
        printf("%d ", a);
        int next = a + b;
        a = b;
        b = next;
    }
    printf("\n");
}
int main(int argc, char* argv[]) {
    if (argc != 2) {
        printf("Usage: %s <non-negative_integer>\n", argv[0]);
        return 1;
    }
    int n = atoi(argv[1]);
    if (n < 0) {
        printf("Error: The input should be a non-negative integer.\n");
        return 1;
    }
    pid_t pid = fork();
    if (pid < 0) {
        printf("Error: Forking failed.\n");
        return 1;
    }
    else if (pid == 0) {
    // Child process
        printf("Child process (PID: %d) Fibonacci Series: ", getpid());
        generate_fibonacci(n);
    }
    else {
    // Parent process
        printf("Parent process (PID: %d) waiting for the child process (PID: %d) to finish...\n", getpid(), pid);
        wait(NULL);
        printf("Parent process: Child process has finished.\n");
        }
    return 0;
}