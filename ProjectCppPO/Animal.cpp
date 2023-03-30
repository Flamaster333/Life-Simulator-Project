#include "Animal.h"
#include <iostream>

//Metoda odpowiedzialna za poruszanie sie zwierz¹t podczas tury
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

//Zwieze chce siê przemieœciæ na pole na którym jest inny organizm - kolizja
void Animal::collision(Organism* occupyingOrganism)
{
	if (occupyingOrganism->getFeatures().name != this->getFeatures().name) //jeœli organizmy nie s¹ takie same bêd¹ walczyæ
	{
		if (occupyingOrganism->getFeatures().name == "Deadly Nightshade") //zwierze które zjad³o wilcze jagody ginie
			occupyingOrganism->collision(this);
		else if (occupyingOrganism->getFeatures().strength <= this->getFeatures().strength)
		{
			if (occupyingOrganism->getFeatures().name == "Turtle") //¿ó³w mo¿e odbiæ atak
				occupyingOrganism->defense(this);
			else if (occupyingOrganism->getFeatures().name == "Antylope") //antylopa mo¿e odskoczyæ
				occupyingOrganism->defense(this);
			else if(occupyingOrganism->getFeatures().name == "Guarana") //guarana daje +3 do si³y
				occupyingOrganism->collision(this);
			else if (occupyingOrganism->getFeatures().name == "Sosnowsky Hogweed") //zwierze które zjad³o barszcz sosnowskeigo ginie
				occupyingOrganism->collision(this);
			else
			{
				world->addOrganismToKill(occupyingOrganism);
				//dodanie raportu
				string report = features.name + " ate " + occupyingOrganism->getFeatures().name;
				world->addReport(report);
				//przesuniêcie ¿ywego organizmu na pole zabitego
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
			//nie przesuwamy ¿adnego organizmu bo obecny nie da³ rady zabiæ organizmu na polu na które chcia³ przejœæ
		}
	}
	else //jeœli sa takie same bêd¹ siê rozmna¿aæ
	{
		//Sprawdzamy czy plansza nie jest pe³na
		if (world->getOrganismsList()->size() < (world->getHeight() * world->getWidth())) {		
			breeding(this, occupyingOrganism->getLocation());
		}
	}
}

//metoda odpowiedzialna za rozmna¿anie zwierz¹t
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





