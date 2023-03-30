#pragma once
#include "Organism.h"
#include <list>
#define REPORT_NUMBER 20

class Organism;
using namespace std;

//one field struct
struct Field {
	Organism* organism;
	bool occupied;
};

class World
{
private:
	Field** organismsBoard;
	Field* organismsToKill;
	list<Organism*> organismsList;
	string reports[REPORT_NUMBER];
	int reportsNumber;
	int height;
	int width;
	int turnNum;
	int population;
	int deathsNumber;
public:
	//constructors
	World();
	World(int height, int width, int percent);
	void createOrganismsBoard(int x, int y);
	//setters
	void addReport(string report);
	void setReportsNumber(int reportsNumber);
	void setHeight(int height);
	void setWidth(int width);
	void setPopulation(int population);
	void setDeathsNumber(int deathsNumber);
	void setTurnNum(int turnNum);
	//getters
	Field** getOrganismsBoard();
	Field* getOrganismsToKill();
	list<Organism*>* getOrganismsList();
	string* getReports(); 
	int getReportsNumber();
	int getHeight();
	int getWidth();
	int getPopulation();
	int getDeathsNumber();
	int getTurnNum();
	//world primary
	void doRound(int direction);
	void initWorld();
	void drawWorld();
	void showReports();
	void killOrganisms();
	void deallocateMemory();
	//organisms primary
	Organism* createOrganism(string name, Cords cords);
	Organism* createOrganism(int number, Cords cords);
	void startingOrganismsPositions();
	void startingHumanPosition();
	Cords startingCords();
	void addOrganismToBoard(Organism* organism);
	void addOrganismToKill(Organism* organism);
	void addOrganismToList(Organism* organism);
	//organisms other
	void cleanBoardField(Cords fieldCords);
	bool isSomethingThere(Cords cords);
	Organism* whatIsThere(Cords cords);
	list<Organism*>::iterator getIterator(Cords cords);
	list<Organism*>::iterator getIteratorToKill(Cords cords, Organism* organism);
	bool specialAbilityIsItSet();
	//saving to the file
	void saveWorld();
	void initWorldFromFile();
};

