#include "SosnowskyHogweed.h"
#define POSSIBLEMOVES 4

SosnowskyHogweed::SosnowskyHogweed(World* world, Cords cords)
{
	features.strength = 10;
	features.age = 0;
	features.look = '#';
	features.name = "Sosnowsky Hogweed";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby barszcz sosnowskiego zabija³ ka¿dego kto stoi obok niego
void SosnowskyHogweed::action()
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
	killingNear();
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby marszcz zabija³ ka¿de zbierze które go zje
void SosnowskyHogweed::collision(Organism* occupyingOrganism)
{
	world->addOrganismToKill(this);
	world->addOrganismToKill(occupyingOrganism);
	world->cleanBoardField(location);
	world->cleanBoardField(occupyingOrganism->getLocation());
	//dodanie raportu
	string report = features.name + " killed " + occupyingOrganism->getFeatures().name + " while they was eating it";
	world->addReport(report);
}

//Metoda obs³uguj¹ca zabijanie pobliskich organizmów przez barszcz
void SosnowskyHogweed::killingNear()
{
	int x = location.getX();
	int y = location.getY();
	for (int i = 0; i < POSSIBLEMOVES; i++)
	{
		if (checkMove(movePossibilities(location, i)) && world->isSomethingThere(movePossibilities(location, i)))
		{
			Organism* organism = world->whatIsThere(movePossibilities(location, i));
			if (organism->isItAnimal())
			{
				world->addOrganismToKill(organism);
				world->cleanBoardField(organism->getLocation());
				//dodanie raportu
				string report = features.name + " killed " + organism->getFeatures().name;
				world->addReport(report);
			}
		}
	}
}
