#include "Sheep.h"

Sheep::Sheep(World* world, Cords cords)
{
	features.strength = 4;
	features.initiative = 4;
	features.age = 0;
	features.look = 'S';
	features.name = "Sheep";
	features.toKill = false;
	location = cords;
	this->world = world;
}
