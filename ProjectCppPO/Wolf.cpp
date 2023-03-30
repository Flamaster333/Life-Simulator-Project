#include "Wolf.h"

Wolf::Wolf(World* world, Cords cords)
{
	features.strength = 9;
	features.initiative = 5;
	features.age = 0;
	features.look = 'W';
	features.name = "Wolf";
	features.toKill = false;
	location = cords;
	this->world = world;
}
