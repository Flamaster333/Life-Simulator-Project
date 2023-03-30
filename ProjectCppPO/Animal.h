#pragma once
#include "Organism.h"
#include "Animal.h"

class Animal : public Organism
{
private:

public:
	virtual void action();
	virtual void collision(Organism* occupyingOrganism);
	void breeding(Organism* organism, Cords breederCords);
	virtual void defense(Organism* attacker);
	bool isItAnimal() override;
};

