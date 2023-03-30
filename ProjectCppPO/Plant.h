#pragma once
#include "Organism.h"
#define PROBABILITY 5

class Plant : public Organism
{
public:
	virtual void action() override;
	virtual void collision(Organism* occupyingOrganism) override;
	virtual void defense(Organism* attacker) override;
	bool isItAnimal() override;
};

