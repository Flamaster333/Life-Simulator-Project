#include <iostream>
#include "Init.h"
#include "World.h"
#include "OtherTools.h"

using namespace std;

Init::Init(World* world)
{
	this->world = world;
}

//Metoda pokazuje ekran startowy i dziêki niej wybieramy parametry nowej symulacji ¿ycia
void Init::initWorld()
{
	int h, w, p, population;
	char command = '0';
	bool wrongCommand = true;
	SetConsoleTextAttribute(con, LIGHTGREEN);
	cout << "****************** LIFE SYMULATOR ******************" << endl;
	cout << "*                                                  *" << endl;
	cout << "*                n - New Symulation                *" << endl;
	cout << "*              f - Load world from file            *" << endl;
	cout << "*                                                  *" << endl;
	cout << "****************************************************" << endl << endl;
	SetConsoleTextAttribute(con, LIGHTMAGENTA);
	cout << "                      Choose: ";
	cin >> command;
	cout << endl;
	SetConsoleTextAttribute(con, WHITE);
	while (wrongCommand)
	{
		if (command == 'n')
		{
			
			cout << "               Enter board dimensions" << endl;
			cout << "                      Height: ";
			cin >> h;
			world->setHeight(h);
			cout << "                      Width: ";
			cin >> w;
			world->setWidth(w);
			cout << "Enter the size of the population as a percentage of " << endl;
			cout << "             the fields on the board: ";
			cin >> p;
			population = (w * h * p) / 100;
			world->setPopulation(population);

			world->initWorld();
			wrongCommand = false;
		}
		else if (command == 'f')
		{
			world->initWorldFromFile();
			wrongCommand = false;
		}
		else
		{
			SetConsoleTextAttribute(con, LIGHTRED);
			cout << "            Wrong option! Choose again: ";
			wrongCommand = true;
			cin >> command;
			SetConsoleTextAttribute(con, WHITE);
		}
	}
	system("cls");
	world->drawWorld();

}
