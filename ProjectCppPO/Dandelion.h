#pragma once
#include "Plant.h"
class Dandelion : public Plant
{
public:
	Dandelion(World* world, Cords cords);
	void action() override;
};

