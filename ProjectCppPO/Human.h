#pragma once
#include "Animal.h"

class Human : public Animal
{
private:
	bool boostIsActive;
	bool boostIsAvaliable;
	bool isAlive;
	int moveRange;
	int turnsToBoost;
	int boostTurns;
public:
	Human(World* world, Cords cords);
	void action(int button);
	//boost
	void boostLoading();
	void boostManagement();
	void boostActivate();
	//getters
	int getTurnsToBoost();
	int getBoostTurns();
	bool getBoostIsActive();
	//setters
	void setBoostIsActive(bool boostIsActive);
	void setBoostIsAvaliable(bool boostIsAvaliable);
	void setTurnsToBoost(int turnsToBoost);
	void setBoostTurns(int boostTurns);
};

