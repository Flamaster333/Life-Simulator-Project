#include "World.h"
#include "Init.h"
#include "OtherTools.h"
#include <iostream>
#include <stdlib.h>  /* srand, rand */
#include <time.h>  /* time */
#include <conio.h> /* keyboard */

using namespace std;

int main()
{
	srand(time(NULL));
	World world;
	Init symulator(&world);
	symulator.initWorld();
	int button = 0;
	//pêtla gry
	while (button != KEY_ESC)
	{
		button = _getch();
		button = _getch();
		if (button == KEY_s)
			world.saveWorld(); // zapisanie œwiata
		else
			world.doRound(button); //kolejna runda
	}
	world.deallocateMemory();
	return 0;
}