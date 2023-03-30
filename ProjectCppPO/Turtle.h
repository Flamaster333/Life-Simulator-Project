#pragma once
#include "Animal.h"
class Turtle : public Animal
{
public:
	Turtle(World* world, Cords cords);
	void action() override;
	void defense(Organism* attacker) override;
};

