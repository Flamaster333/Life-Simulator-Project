#include "Turtle.h"
#define DEFENSE 5

Turtle::Turtle(World* world, Cords cords)
{
	features.strength = 2;
	features.initiative = 1;
	features.age = 0;
	features.look = 'T';
	features.name = "Turtle";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj�ca metod� z klasy rodzica �eby ��w mia� tlyko 75% szans na ruch w turze
void Turtle::action()
{
	int moveOrNot = rand() % 4;
	if (moveOrNot == 0)
	{
		int moveRange = 1;
		Cords currentCords = this->getLocation();
		Cords newCords(-1, -1);
		while (!checkMove(newCords))
			newCords.setCords(randomNewCords(currentCords, moveRange));
		if (checkMove(newCords))
		{
			if (world->isSomethingThere(newCords))
			{
				Organism* occupyingOrganism = world->whatIsThere(newCords);
				collision(occupyingOrganism);
			}
			else
				move(this, newCords, currentCords);
		}
	}
}

//Metoda umo�lwiaj�ca ��wiowi odbicie ataku organizmu z si�� <5
void Turtle::defense(Organism* attacker)
{
	if (attacker->getFeatures().strength < DEFENSE) 
	{
		//dodanie raportu
		string report = features.name + " repelled the attack from " + attacker->getFeatures().name;
		world->addReport(report);
	}
	else
	{
		world->addOrganismToKill(this);
		move(attacker, location, attacker->getLocation());
		//dodanie raportu
		string report = attacker->getFeatures().name + " ate " +  features.name;
		world->addReport(report);
	}
}
