#pragma once
#include "Plant.h"
class Guarana : public Plant
{
public:
	Guarana(World* world, Cords cords);
	void collision(Organism* occupyingOrganism);
};

