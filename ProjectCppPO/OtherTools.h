#pragma once
#include <windows.h>

//colors
HANDLE static con = GetStdHandle(STD_OUTPUT_HANDLE);

enum {
	BLACK, 
	BLUE, 
	GREEN, 
	CYAN, 
	RED, 
	MAGENTA,
	BROWN, 
	LIGHTGRAY, 
	DARKGRAY, 
	LIGHTBLUE, 
	LIGHTGREEN,
	LIGHTCYAN, 
	LIGHTRED, 
	LIGHTMAGENTA, 
	YELLOW, 
	WHITE
};

//buttons
#define KEY_ESC	27
#define KEY_UP 72
#define KEY_LEFT 75
#define KEY_RIGHT 77
#define KEY_DOWN 80
#define KEY_SPACE 32
#define KEY_s 115