#pragma once
#include "World.h"

class Init
{
private:
	World* world;
public:
	Init(World* world);
	void initWorld();
};

