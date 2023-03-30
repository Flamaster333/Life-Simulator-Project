#pragma once
#include "Plant.h"
class DeadlyNightshade : public Plant
{
public:
	DeadlyNightshade(World* world, Cords cords);
	void collision(Organism* occupyingOrganism);
};

