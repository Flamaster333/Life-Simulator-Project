#include "Human.h"
#include "OtherTools.h"
#define BOOST_TIME 5
#define LOWERBOOST_TIME 3
#define LUCKY 0

Human::Human(World* world, Cords cords)
{
	boostIsAvaliable = true;
	boostIsActive = false;
	isAlive = true;
	turnsToBoost = 0;
	boostTurns = 0;
	moveRange = 1;
	features.strength = 5;
	features.initiative = 4;
	features.age = 0;
	features.look = 'H';
	features.name = "Human";
	features.toKill = false;
	location = cords;
	this->world = world;
}

//Metoda nadpisuj¹ca metodê z klasy rodzica ¿eby cz³owiek porusza³ sie za pomoca strza³ek
void Human::action(int button)
{
	boostLoading();
	Cords newCords = location;
	int x = location.getX();
	int y = location.getY();
	switch (button) {
	case KEY_UP:
		newCords.setY(y - moveRange);
		break;
	case KEY_DOWN:
		newCords.setY(y + moveRange);
		break;
	case KEY_RIGHT:
		newCords.setX(x + moveRange);
		break;
	case KEY_LEFT:
		newCords.setX(x - moveRange);
		break;
	case KEY_SPACE:
		boostActivate();
		break;
	}
	if (boostIsActive)
		boostManagement();
	if (newCords != location && checkMove(newCords))
	{
		if (world->isSomethingThere(newCords))
		{
			Organism* occupyingOrganism = world->whatIsThere(newCords);
			collision(occupyingOrganism);
		}
		else
			move(this, newCords, this->location);
	}
}

//Metoda laduje umiejetnosc specjalna czlowieka
void Human::boostLoading() {
	if (turnsToBoost > 0 && !boostIsActive) {
		--turnsToBoost;
	}
	else if (turnsToBoost == 0 && !boostIsActive) {
		boostIsAvaliable = true;
	}
}

//Metoda sprawdzaj¹ca czy specjalna umiejêtnoœæ jeszcze dzia³a i jeœli tak to jak
void Human::boostManagement()
{
	if (boostTurns < BOOST_TIME)
	{
		if (boostTurns < LOWERBOOST_TIME)
			moveRange = 2;
		else
		{
			int chance = rand() % 2;
			if (chance == LUCKY)
				moveRange = 2;
			else
				moveRange = 1;
		}
		++boostTurns;
	}
	else
	{
		boostIsActive = false;
		boostIsAvaliable = false;
		turnsToBoost = 5;
		boostTurns = 0;
		moveRange = 1;
	}

}

//Metoda aktywuje specjaln¹ umiejêtnoœæ
void Human::boostActivate()
{
	if (boostIsAvaliable)
	{
		boostIsAvaliable = false;
		boostIsActive = true;
		string report = "Human just activated special ability (Antelope speed)";
		world->addReport(report);
	}
	else
	{
		string report = "Human can't activate special ability! You have to wait: " + to_string(turnsToBoost) + " turns";
		world->addReport(report);
	}
}


int Human::getTurnsToBoost()
{
	return turnsToBoost;
}

int Human::getBoostTurns()
{
	return boostTurns;
}

bool Human::getBoostIsActive()
{
	return boostIsActive;
}

void Human::setBoostIsActive(bool boostIsActive)
{
	this->boostIsActive = boostIsActive;
}

void Human::setBoostIsAvaliable(bool boostIsAvaliable)
{
	this->boostIsAvaliable = boostIsAvaliable;
}

void Human::setTurnsToBoost(int turnsToBoost)
{
	this->turnsToBoost = turnsToBoost;
}

void Human::setBoostTurns(int boostTurns)
{
	this->boostTurns = boostTurns;
}

