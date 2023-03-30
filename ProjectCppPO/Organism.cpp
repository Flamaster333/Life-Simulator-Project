#include "Organism.h"
#include "OtherTools.h"
#include <iostream>
#define DIRECTIONS 4

using namespace std;

Organism::Organism()
{
}

void Organism::action(int button)
{
}

//metoda rysuje organizm w odpowiednim kolorze
void Organism::drawOrganism()
{
	switch (features.look)
	{
		case 'W':
			SetConsoleTextAttribute(con, DARKGRAY);
			break;
		case 'S':
			SetConsoleTextAttribute(con, WHITE);
			break;
		case 'F':
			SetConsoleTextAttribute(con, RED);
			break;
		case 'T':
			SetConsoleTextAttribute(con, LIGHTGREEN);
			break;
		case 'A':
			SetConsoleTextAttribute(con, YELLOW);
			break;
		case '"':
			SetConsoleTextAttribute(con, GREEN);
			break;
		case '*':
			SetConsoleTextAttribute(con, BROWN);
			break;
		case '@':
			SetConsoleTextAttribute(con, LIGHTRED);
			break;
		case '%':
			SetConsoleTextAttribute(con, BLUE);
			break;
		case '#':
			SetConsoleTextAttribute(con, LIGHTMAGENTA);
			break;
		case 'H':
			SetConsoleTextAttribute(con, LIGHTGRAY);
			break;
	}
	cout << features.look;
	SetConsoleTextAttribute(con, WHITE);
}

Features Organism::getFeatures()
{
	return features;
}

Cords Organism::getLocation()
{
	return location;
}

void Organism::setLocation(Cords cords)
{
	location = cords;
}

void Organism::setToKill()
{
	this->features.toKill = true;
}

void Organism::setIsDead()
{
	features.isDead = true;
}

void Organism::setStrength(int strength)
{
	this->features.strength = strength;
}

void Organism::setAge(int age)
{
	this->features.age = age;
}

void Organism::gainStrength()
{
	this->features.strength += 3;
}

void Organism::getOlder()
{
	features.age++;
}

//Metoda sprawdza czy zamierozny ruch nie wychodzi poza plansze
bool Organism::checkMove(Cords cords)
{
	if ((cords.getX() >= world->getWidth()) || (cords.getX() < 0) ||
		(cords.getY() >= world->getHeight()) || (cords.getY() < 0))
		return false;
	else
		return true;
}

//Metoda sprawdza czy isnieje jakiekowliek wolne meijsce dooko³a podanych cordów
bool Organism::checkFreeFieldsNear(Cords cords)
{
	if (((cords.getX() + 1) < (world->getWidth() - 1)) && 
		(world->getOrganismsBoard()[cords.getY()][cords.getX() + 1].occupied == false))
		return true;
	else if (((cords.getX() - 1) >= 0) &&
		(world->getOrganismsBoard()[cords.getY()][cords.getX() - 1].occupied == false))
		return true;
	else if (((cords.getY() + 1) < (world->getHeight() - 1)) &&
		(world->getOrganismsBoard()[cords.getY() + 1][cords.getX()].occupied == false))
		return true;
	else if (((cords.getY() - 1) >= 0) &&
		(world->getOrganismsBoard()[cords.getY() - 1][cords.getX()].occupied == false))
		return true;
	else 
		return false;
}

//Metoda losuje cordy w zakresie ruchu
Cords Organism::randomNewCords(Cords cords, int moveRange)
{
	int direction = rand() % DIRECTIONS;
	switch (direction)
	{
		case 0:
			return Cords(cords.getX() + moveRange, cords.getY());
			break;
		case 1:
			return Cords(cords.getX() - moveRange, cords.getY());
			break;
		case 2:
			return Cords(cords.getX(), cords.getY() + moveRange);
			break;
		case 3:
			return Cords(cords.getX(), cords.getY() - moveRange);
			break;
	}
}

//Metoda losuje wolne pole dooko³a podanych cordów 
Cords Organism::randomFreeFieldsNear(Cords cords)
{
	int moveRange = 1;
	Cords newCords(-1, -1);
	while ((!checkMove(newCords)) || (world->getOrganismsBoard()[newCords.getY()][newCords.getX()].occupied == true))
	{
		newCords.setCords(randomNewCords(cords, moveRange));
	}
	return newCords;
}

//Metoda odpwoiada za przemieszczenie organizmuz  jednego pola na drugie
void Organism::move(Organism* organism, Cords newCords, Cords currentCords)
{
	organism->setLocation(newCords);
	world->addOrganismToBoard(organism);
	world->cleanBoardField(currentCords);
}

//Metoda mówi nam czy podany organizm jest zwierzêciem
bool Organism::isItAnimal()
{
	return false;
}

//Metoda zwraca nam cordy przemieszczone o jedno pole w którymœ kierunku
Cords Organism::movePossibilities(Cords cords, int option)
{
	int x = cords.getX();
	int y = cords.getY();
	switch (option)
	{
	case 0:
		return Cords(x + 1, y);
		break;
	case 1:
		return Cords(x - 1, y);
		break;
	case 2:
		return Cords(x, y + 1);
		break;
	case 3:
		return Cords(x, y - 1);
		break;
	}
}
