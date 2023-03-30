#include "Antelope.h"
#define LUCKY 0

Antelope::Antelope(World* world, Cords cords)
{
	features.strength = 4;
	features.initiative = 4;
	features.age = 0;
	features.look = 'A';
	features.name = "Antelope";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby antylopa mog³a sie ruszac o 2 pola
void Antelope::action()
{
	int moveRange = 2;
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

//Metoda obs³uguj¹ca mo¿liwoœc odskoczenia antylopy gdy ktoœ chce j¹ zaatakowaæ
void Antelope::defense(Organism* attacker)
{
	int chance = rand() % 2;
	if (chance == LUCKY && checkFreeFieldsNear(location))
	{
		Cords newCords = randomFreeFieldsNear(location);
		move(this, newCords, location);
		//dodanie raportu
		string report = features.name + " escaped from " + attacker->getFeatures().name;
		world->addReport(report);
	}
	else
	{
		world->addOrganismToKill(this);
		move(attacker, location, attacker->getLocation());
		//dodanie raportu
		string report = attacker->getFeatures().name + " ate " + features.name;
		world->addReport(report);
	}
}
