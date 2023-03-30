#include "Fox.h"

Fox::Fox(World* world, Cords cords)
{
	features.strength = 3;
	features.initiative = 7;
	features.age = 0;
	features.look = 'F';
	features.name = "Fox";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby lis nigdy nie wszed³ na pole z organizmem silniejszym od siebie
void Fox::action()
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
			if(occupyingOrganism->getFeatures().strength <= this->features.strength)
				collision(occupyingOrganism);
		}
		else
			move(this, newCords, currentCords);
	}
}