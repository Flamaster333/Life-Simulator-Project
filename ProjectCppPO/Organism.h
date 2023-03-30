#pragma once
#include <string>
#include "Cords.h"
#include "World.h"

class World;
using namespace std;

struct Features {
	int strength;
	int initiative;
	int age;
	char look;
	string name;
	bool toKill;
	bool isDead;
};

class Organism
{
protected:
	Features features;
	Cords location;
	World *world;
public:
	Organism();
	//virtual
	virtual void action() = 0;
	virtual void action(int button);
	virtual void collision(Organism* occupyingOrganism) = 0;
	virtual void drawOrganism();
	virtual void defense(Organism* attacker) = 0;
	//getters
	Features getFeatures();
	Cords getLocation();
	//setters
	void setLocation(Cords cords);
	void setToKill();
	void setIsDead();
	void setStrength(int strength);
	void setAge(int age);
	//adding
	void gainStrength();
	void getOlder();
	//actions
	bool checkMove(Cords cords);
	bool checkFreeFieldsNear(Cords cords);
	Cords randomNewCords(Cords cords, int moveRange);
	Cords randomFreeFieldsNear(Cords cords);
	void move(Organism* organism, Cords newCords, Cords currentCords);
	virtual bool isItAnimal();
	Cords movePossibilities(Cords cords, int option);
};

