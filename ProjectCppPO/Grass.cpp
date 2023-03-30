#include "Grass.h"

Grass::Grass(World* world, Cords cords)
{
	features.strength = 0;
	features.age = 0;
	features.look = '"';
	features.name = "Grass";
	features.toKill = false;
	location = cords;
	this->world = world;
}
