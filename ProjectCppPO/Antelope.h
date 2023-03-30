#pragma once
#include "Animal.h"
class Antelope : public Animal
{
public:
	Antelope(World* world, Cords cords);
	void action() override;
	void defense(Organism* attacker) override;
};

