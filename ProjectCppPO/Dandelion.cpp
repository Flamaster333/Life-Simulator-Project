#include "Dandelion.h"

Dandelion::Dandelion(World* world, Cords cords)
{
	features.strength = 0;
	features.age = 0;
	features.look = '*';
	features.name = "Dandelion";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby Mlecz mia³ 3 próby rozprzestrzeniania sie w jednej turze
void Dandelion::action()
{
	int chance = -1;
	int count = 0;
	while (chance && count != 3)
	{
		chance = rand() % (100 / PROBABILITY);
		count++;
	}
	if (!chance)
	{
		Cords seedCords(-1, -1);
		if (checkFreeFieldsNear(this->location))
			seedCords.setCords(randomFreeFieldsNear(this->location));
		if ((seedCords.getX() != -1) && (seedCords.getY() != -1))
		{
			Organism* tmpOrganism = world->createOrganism(features.name, seedCords);
			world->addOrganismToBoard(tmpOrganism);
			world->addOrganismToList(tmpOrganism);
			string report = "New " + tmpOrganism->getFeatures().name + " seed was sown on field ("
				+ to_string(seedCords.getX()) + ", " + to_string(seedCords.getY()) + ")";
			world->addReport(report);
		}
	}
}
