#include "Cords.h"

Cords::Cords()
{
}

Cords::Cords(int x, int y)
{
	this->x = x;
	this->y = y;
}

Cords Cords::getCords()
{
	return Cords(x, y);
}

int Cords::getX()
{
	return this->x;
}

int Cords::getY()
{
	return this->y;
}

void Cords::setCords(Cords cords)
{
	this->x = cords.x;
	this->y = cords.y;
}

void Cords::setX(int x)
{
	this->x = x;
}

void Cords::setY(int y)
{
	this->y = y;
}

void Cords::operator=(Cords& cords)
{
	this->x = cords.x;
	this->y = cords.y;
}

bool Cords::operator==(Cords& cords)
{
	if (this->x == cords.x && this->y == cords.y)
		return true;
	else
		return false;
}

bool Cords::operator!=(Cords& cords)
{
	if (this->x == cords.x && this->y == cords.y)
		return false;
	else
		return true;
}
