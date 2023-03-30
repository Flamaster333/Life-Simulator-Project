#pragma once
#include "Animal.h"
class Fox : public Animal
{
public:
	Fox(World* world, Cords cords);
	void action() override;
};

