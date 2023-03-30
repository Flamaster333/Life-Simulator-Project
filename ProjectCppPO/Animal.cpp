#include "Animal.h"
#include <iostream>

//Metoda odpowiedzialna za poruszanie sie zwierz�t podczas tury
void Animal::action()
{
	int moveRange = 1;
	Cords currentCords = this->getLocation();
	Cords newCords(-1, -1);
	while (!checkMove(newCords))
		newCords.setCords(randomNewCords(currentCords, moveRange));
	if (checkMove(newCords))
	{
		if (world->isSomethingThere(newCords))
		{
			Organism* occupyingOrganism = world->whatIsThere(newCords);
			collision(occupyingOrganism);
		}
		else
			move(this, newCords, currentCords);
	}
}

//Zwieze chce si� przemie�ci� na pole na kt�rym jest inny organizm - kolizja
void Animal::collision(Organism* occupyingOrganism)
{
	if (occupyingOrganism->getFeatures().name != this->getFeatures().name) //je�li organizmy nie s� takie same b�d� walczy�
	{
		if (occupyingOrganism->getFeatures().name == "Deadly Nightshade") //zwierze kt�re zjad�o wilcze jagody ginie
			occupyingOrganism->collision(this);
		else if (occupyingOrganism->getFeatures().strength <= this->getFeatures().strength)
		{
			if (occupyingOrganism->getFeatures().name == "Turtle") //��w mo�e odbi� atak
				occupyingOrganism->defense(this);
			else if (occupyingOrganism->getFeatures().name == "Antylope") //antylopa mo�e odskoczy�
				occupyingOrganism->defense(this);
			else if(occupyingOrganism->getFeatures().name == "Guarana") //guarana daje +3 do si�y
				occupyingOrganism->collision(this);
			else if (occupyingOrganism->getFeatures().name == "Sosnowsky Hogweed") //zwierze kt�re zjad�o barszcz sosnowskeigo ginie
				occupyingOrganism->collision(this);
			else
			{
				world->addOrganismToKill(occupyingOrganism);
				//dodanie raportu
				string report = features.name + " ate " + occupyingOrganism->getFeatures().name;
				world->addReport(report);
				//przesuni�cie �ywego organizmu na pole zabitego
				move(this, occupyingOrganism->getLocation(), this->getLocation());
			}
		}
		else
		{
			world->addOrganismToKill(this);
			//dodanie raportu
			string report = occupyingOrganism->getFeatures().name + " ate " + features.name;
			world->addReport(report);
			world->cleanBoardField(this->getLocation());
			//nie przesuwamy �adnego organizmu bo obecny nie da� rady zabi� organizmu na polu na kt�re chcia� przej��
		}
	}
	else //je�li sa takie same b�d� si� rozmna�a�
	{
		//Sprawdzamy czy plansza nie jest pe�na
		if (world->getOrganismsList()->size() < (world->getHeight() * world->getWidth())) {		
			breeding(this, occupyingOrganism->getLocation());
		}
	}
}

//metoda odpowiedzialna za rozmna�anie zwierz�t
void Animal::breeding(Organism* organism, Cords breederCords)
{
	Cords offspringCords(-1, -1);
	if (checkFreeFieldsNear(breederCords))
		offspringCords.setCords(randomFreeFieldsNear(breederCords));
	else if(checkFreeFieldsNear(organism->getLocation()))
		offspringCords.setCords(randomFreeFieldsNear(organism->getLocation()));

	if ((offspringCords.getX() != -1) && (offspringCords.getY() != -1))
	{
		Organism* tmpOrganism = world->createOrganism(organism->getFeatures().name, offspringCords);
		world->addOrganismToBoard(tmpOrganism);
		world->addOrganismToList(tmpOrganism);
		string report = "New offspring of " + tmpOrganism->getFeatures().name + " was born on field ("
			+ to_string(offspringCords.getX()) + ", " + to_string(offspringCords.getY()) + ")";
		world->addReport(report);
	}
}

void Animal::defense(Organism* attacker)
{
}

bool Animal::isItAnimal()
{
	return true;
}





