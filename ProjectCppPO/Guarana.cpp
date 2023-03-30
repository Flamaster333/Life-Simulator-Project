#include "Guarana.h"

Guarana::Guarana(World* world, Cords cords)
{
	features.strength = 0;
	features.age = 0;
	features.look = '@';
	features.name = "Guarana";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj�ca metod� z klasy rodzica �eby guarana dodawa�a +3 do si�y organizmowi kt�ry ja zjad�
void Guarana::collision(Organism* occupyingOrganism)
{
	occupyingOrganism->gainStrength();
	world->addOrganismToKill(this);
	move(occupyingOrganism, location, occupyingOrganism->getLocation());
	//dodanie raportu
	string report = occupyingOrganism->getFeatures().name + " gained +3 strength from " + features.name;
	world->addReport(report);
}
