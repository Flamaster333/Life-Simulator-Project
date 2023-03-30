#pragma once
#include "Plant.h"
class SosnowskyHogweed : public Plant
{
public:
	SosnowskyHogweed(World* world, Cords cords);
	void action() override;
	void collision(Organism* occupyingOrganism);
	void killingNear();
};

