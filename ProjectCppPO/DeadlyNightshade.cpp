#include "DeadlyNightshade.h"

DeadlyNightshade::DeadlyNightshade(World* world, Cords cords)
{
	features.strength = 99;
	features.age = 0;
	features.look = '%';
	features.name = "Deadly Nightshade";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby wilcze jagody zabija³y tego kto je zje
void DeadlyNightshade::collision(Organism* occupyingOrganism)
{
	world->addOrganismToKill(this);
	world->addOrganismToKill(occupyingOrganism);
	world->cleanBoardField(location);
	world->cleanBoardField(occupyingOrganism->getLocation());
	//dodanie raportu
	string report = features.name + " killed " + occupyingOrganism->getFeatures().name + " while they was eating it";
	world->addReport(report);
}
