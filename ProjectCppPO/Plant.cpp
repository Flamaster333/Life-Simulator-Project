#include "Plant.h"

//Metoda daj¹ca szansê ka¿dej roœlinie na rozprzestrzenienie siê
void Plant::action()
{
	int chance = rand() % (100 / PROBABILITY);
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

void Plant::collision(Organism* occupyingOrganism)
{
}

void Plant::defense(Organism* attacker)
{
}

bool Plant::isItAnimal()
{
	return false;
}
