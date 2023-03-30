#include "World.h"
#include "OtherTools.h"
#include "Cords.h"
#include "Organism.h"
#include "Wolf.h"
#include "Sheep.h"
#include "Fox.h"
#include "Turtle.h"
#include "Antelope.h"
#include "Grass.h"
#include "Dandelion.h"
#include "Guarana.h"
#include "DeadlyNightshade.h"
#include "SosnowskyHogweed.h"
#include "Human.h"
#include <iostream>
#include <windows.h>
#include <fstream>
#define SPECIES 10

using namespace std;

//Konstruktor bezparametrowy
World::World(){
	this->width = 0;
	this->height = 0;
	this->population = 0;
	this->deathsNumber = 0;
	this->reportsNumber = 0;
	turnNum = 0;
}

//Konstruktor z podaniem wysokoœci szerokoœci i populacji
World::World(int height, int width, int percent){
	this->height = height;
	this->width = width;
	this->population = (width, height, percent)/100;
	this->deathsNumber = 0;
	this->reportsNumber = 0;
	turnNum = 0;
}

//Metoda tworzy puste tablice: planszê oraz toKill
void World::createOrganismsBoard(int x, int y){
	//tworzymy tablicê dwuwymiarowa planszy
	organismsBoard = new Field * [y];
	for (int i = 0; i < y; i++)
		organismsBoard[i] = new Field[x];
	//tworzymy tablicê to kill
	organismsToKill = new Field[x * y];
	for (int i = 0; i < x * y; ++i) {
		organismsToKill[i].organism = nullptr;
		organismsToKill[i].occupied = false;
	}
	for (int i = 0; i < y; ++i) {
		for (int j = 0; j < x; ++j) {
			organismsBoard[i][j].organism = nullptr;
			organismsBoard[i][j].occupied = false;
		}
	}
}

//Metoda dodaje raport do tablicy raportow
void World::addReport(string report) {
	if (reportsNumber < REPORT_NUMBER) {
		this->reports[reportsNumber] = report;
		++reportsNumber;
	}
}

void World::setReportsNumber(int reportsNumber) {
	this->reportsNumber = reportsNumber;
}

void World::setHeight(int height){
	this->height = height;
}

void World::setWidth(int width){
	this->width = width;
}

void World::setPopulation(int population){
	this->population = population;
}

void World::setDeathsNumber(int deathsNumber)
{
	this->deathsNumber = deathsNumber;
}

void World::setTurnNum(int turnNum){
	this->turnNum = turnNum;
}

Field** World::getOrganismsBoard(){
	return organismsBoard;
}

Field* World::getOrganismsToKill()
{
	return organismsToKill;
}

list<Organism*>* World::getOrganismsList()
{
	return &organismsList;
}

string* World::getReports() {
	return this->reports;
}

int World::getReportsNumber() {
	return this->reportsNumber;
}

int World::getHeight(){
	return height;
}

int World::getWidth(){
	return width;
}

int World::getPopulation(){
	return population;
}

int World::getDeathsNumber()
{
	return deathsNumber;
}

int World::getTurnNum(){
	return turnNum;
}

//Metoda obs³uguj¹ca jedn¹ rundê
void World::doRound(int button){
	//itrujemy po wsyztskich organizmach w liœcie posortowanychweg³ug inicjatywy lub wieku gdy inicjatywy s¹ równe
	for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++)
	{
		Organism* tmp = *tmpOrganism;
		if (!tmp->getFeatures().toKill)
		{
			tmp->getOlder();
			if (tmp->getFeatures().name == "Human")
				tmp->action(button);
			else
				tmp->action();
		}
	}
	killOrganisms();
	system("cls");
	drawWorld();
}

//Metoda inicjalizuj¹ca œwiat
void World::initWorld(){
	//tworzenie tablicy - planszy
	createOrganismsBoard(width, height);
	//tworzenie czlowieka
	startingHumanPosition();
	//reszta organizmow
	startingOrganismsPositions();
}

//Metoda rysuj¹ca œwiat
void World::drawWorld(){
	turnNum++;
	SetConsoleTextAttribute(con, LIGHTGREEN);
	cout << "------------------------------------------------------------------" << endl;
	cout << "|                Autor: Justyna Dabrowska, 185872                |" << endl;
	cout << "------------------------------------------------------------------" << endl << endl;
	SetConsoleTextAttribute(con, LIGHTMAGENTA);
	cout << "**************************** Controls ****************************" << endl;
	cout << "*               Next Turn = Human moves  -  arrows               *" << endl; 
	cout << "*                   Special Ability - 2x SPACE                   *" << endl;
	cout << "*                          Exit - 2x Esc                         *" << endl;
	cout << "*                           Save - 2x s                          *" << endl;
	cout << "******************************************************************" << endl << endl;
	SetConsoleTextAttribute(con, LIGHTRED);
	cout << "Board size: " << width << " x " << height << "     ";
	cout << "Turn Number: " << turnNum << "     ";
	cout << "Living organisms: " << organismsList.size() << endl;
	cout << "                       Special Ability: ";
	if (specialAbilityIsItSet())
	{
		SetConsoleTextAttribute(con, GREEN);
		cout << "YES";
	}	
	else
	{
		SetConsoleTextAttribute(con, RED);
		cout << "NO";
	}
	cout << "                      " << endl << endl;
	SetConsoleTextAttribute(con, CYAN);

	//gorna granica
	for (int i = 0; i < 2 * width + 3; ++i) {
		cout << "#";
	}
	cout << endl;
	
	for (int i = 0; i < height; ++i) {
		//lewa granica
		cout << "#";
		//plansza z organizmami
		for (int j = 0; j < width; ++j) {
			cout << " ";
			if (organismsBoard[i][j].occupied == true)
			{
				organismsBoard[i][j].organism->drawOrganism();
				SetConsoleTextAttribute(con, CYAN);
			}
			else
				cout << "-";
		}
		//prawa granica
		cout << " #" << endl;
	}
	//granica dolna
	for (int i = 0; i < 2 * width + 3; ++i) {
		cout << "#";
	}
	cout << endl << endl;
	showReports();
	SetConsoleTextAttribute(con, WHITE);
}

//Metoda pokazuj¹ca raporty
void World::showReports()
{
	//Górna krawêdŸ
	SetConsoleTextAttribute(con, LIGHTGREEN);
	cout << "============================ REPORTS ============================" << endl;
	SetConsoleTextAttribute(con, LIGHTMAGENTA);
	cout << "=================================================================" << endl;
	SetConsoleTextAttribute(con, WHITE);
	if (reportsNumber != 0) {
		for (int i = 0; i < reportsNumber; ++i)
			cout << to_string(i + 1) + ". " + reports[i] << endl;
	}
	else {
		SetConsoleTextAttribute(con, LIGHTRED);
		cout << "Currently no activity to report..." << endl;
	}
	//Dolna krawêdŸ
	SetConsoleTextAttribute(con, LIGHTMAGENTA);
	cout << "=================================================================" << endl;
	SetConsoleTextAttribute(con, LIGHTGREEN);
	cout << "=================================================================" << endl;
	setReportsNumber(0);
}

//Metoda usuwa wsyztskie organizmy znajduj¹ce siê w tablicy toKill
void World::killOrganisms()
{
	for (int i = 0; i < deathsNumber; i++)
	{
		Organism* tmp = organismsToKill[i].organism;
		organismsList.erase(getIteratorToKill(tmp->getLocation(), tmp)); // usuniecie z listy za pomoc¹ cordów oranizmu do zabicia
		organismsToKill[i].organism = nullptr;
		organismsToKill[i].occupied = false;
		delete tmp; //dealokacja pamieci
	}
	deathsNumber = 0;
}

void World::deallocateMemory()
{
	for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++) {
		Organism* tmp = *tmpOrganism;
		addOrganismToKill(tmp);
	}
	killOrganisms();
	for (int i = 0; i < height; ++i)
		delete[] organismsBoard[i];
	delete[] organismsToKill;
	cout << "All of the organisms are dead and board is clear, have a great day :)" << endl;
}

//Metoda tworz¹ca konkretny wskazany nowy organizm - np do rozmna¿ania, rozsiewania czy cz³owieka
Organism* World::createOrganism(string name, Cords cords){
	if (name == "Wolf")
		return new Wolf(this, cords);
	else if (name == "Sheep")
		return new Sheep(this, cords);
	else if (name == "Fox")
		return new Fox(this, cords);
	else if (name == "Turtle")
		return new Turtle(this, cords);
	else if (name == "Antelope")
		return new Antelope(this, cords);
	else if (name == "Grass")
		return new Grass(this, cords);
	else if (name == "Dandelion")
		return new Dandelion(this, cords);
	else if (name == "Guarana")
		return new Guarana(this, cords);
	else if (name == "Deadly Nightshade")
		return new DeadlyNightshade(this, cords);
	else if (name == "Sosnowsky Hogweed")
		return new SosnowskyHogweed(this, cords);
	else if (name == "Human")
		return new Human(this, cords);
}

//Metoda tworz¹ca nowy organizm za pomoc¹ modulo liczby gatunków
Organism* World::createOrganism(int number, Cords cords){
	if (number % SPECIES == 0)
		return new Wolf(this, cords);
	else if (number % SPECIES == 1)
		return new Sheep(this, cords);
	else if (number % SPECIES == 2)
		return new Fox(this, cords);
	else if (number % SPECIES == 3)
		return new Turtle(this, cords);
	else if (number % SPECIES == 4)
		return new Antelope(this, cords);
	else if (number % SPECIES == 5)
		return new Grass(this, cords);
	else if (number % SPECIES == 6)
		return new Dandelion(this, cords);
	else if (number % SPECIES == 7)
		return new Guarana(this, cords);
	else if (number % SPECIES == 8)
		return new DeadlyNightshade(this, cords);
	else if (number % SPECIES == 9)
		return new SosnowskyHogweed(this, cords);
}

//Metoda ustawiaj¹ca organizmy na wylosowanych pozycjach startowych
void World::startingOrganismsPositions(){
	for (int i = 0; i < population - 1; i++)
	{
		Cords tmpCords = startingCords();
		Organism* tmpOrganism = createOrganism(i, tmpCords);
		addOrganismToBoard(tmpOrganism);
		addOrganismToList(tmpOrganism);
		string report = "New " + tmpOrganism->getFeatures().name + " was born on field ("
			+ to_string(tmpCords.getX()) + ", " + to_string(tmpCords.getY()) + ")";
		addReport(report);
	}
}

//Metoda ustawiaj¹ca cz³owieka na wylosowanej pozycji startowej
void World::startingHumanPosition()
{
	Cords tmpCords = startingCords();
	Organism* human = createOrganism("Human", tmpCords);
	addOrganismToBoard(human);
	addOrganismToList(human);
}

//Metoda losuje pozycje startowe dla organizmów
Cords World::startingCords()
{
	while (true)
	{
		int x = rand() % width;
		int y = rand() % height;
		if (organismsBoard[y][x].occupied == false) 
			return Cords(x, y);
	}
}

//Metoda dodaj¹ca organizm do Tablicy organizmów - planszy
void World::addOrganismToBoard(Organism* organism){
	organismsBoard[organism->getLocation().getY()][organism->getLocation().getX()].organism = organism;
	organismsBoard[organism->getLocation().getY()][organism->getLocation().getX()].occupied = true;
}

//Metoda dodaje organizm do tablicy toKill, na koñcu tury zostan¹ usuniête wskazane organizmy
void World::addOrganismToKill(Organism* organism)
{
	organism->setToKill();
	organismsToKill[deathsNumber].organism = organism;
	organismsToKill[deathsNumber].occupied = true;
	deathsNumber++;
}

//Metoda dodaje nowy organizm do listy organizmów
void World::addOrganismToList(Organism* organism){
	if (!organismsList.empty())
	{
		bool organismIsSet = false;
		for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++) {
			Organism* tmp = *tmpOrganism;
			if ((organism->getFeatures().initiative > tmp->getFeatures().initiative) || 
				(organism->getFeatures().initiative == tmp->getFeatures().initiative && organism->getFeatures().age > tmp->getFeatures().age))
			{
				organismsList.insert(tmpOrganism, organism);
				organismIsSet = true;
				break;
			}
		}
		if (!organismIsSet) organismsList.push_back(organism);
	}
	else organismsList.push_front(organism);
}

//Metoda czyœci pole na planszy na podanych wspó³rzêdnych
void World::cleanBoardField(Cords fieldCords){
	organismsBoard[fieldCords.getY()][fieldCords.getX()].organism = nullptr;
	organismsBoard[fieldCords.getY()][fieldCords.getX()].occupied = false;
}

//Metoda mówi nam czy na podanych wspó³rzêdnych jest jakiœ organizm
bool World::isSomethingThere(Cords cords)
{
	if (organismsBoard[cords.getY()][cords.getX()].occupied == true)
		return true;
	else
		return false;
}

//Metoda zwraca organizm na podanych wspó³rzêdnych
Organism* World::whatIsThere(Cords cords)
{
	return organismsBoard[cords.getY()][cords.getX()].organism;
}

//Metoda zwraca iterator listy
list<Organism*>::iterator World::getIterator(Cords cords) {
	for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++) {
		Organism* tmp = *tmpOrganism;
		if (tmp->getLocation() == cords && tmp->getFeatures().toKill && !tmp->getFeatures().isDead) {
			tmp->setIsDead();
			return tmpOrganism;
		}
	}
}
//Metoda zwraca iterator listy
list<Organism*>::iterator World::getIteratorToKill(Cords cords, Organism* organism) {
	for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++) {
		Organism* tmp = *tmpOrganism;
		if (tmp->getLocation() == cords && tmp->getFeatures().toKill && !tmp->getFeatures().isDead
			&& tmp->getFeatures().name == organism->getFeatures().name) {
			tmp->setIsDead();
			return tmpOrganism;
		}
	}
}

//Metoda sprawdza czy cz³owiek ma w³¹czon¹ specjaln¹ umiejêtnoœæ
bool World::specialAbilityIsItSet()
{
	for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++) {
		Organism* tmp = *tmpOrganism;
		if (tmp->getFeatures().name == "Human") {

			Human* human = (Human*)tmp;
			return human->getBoostIsActive();
		}
	}
	return false;
}

//Metoda zapisuj¹ca œwiat do pliku
void World::saveWorld() {
	fstream file;
	string fileName, organismType;
	cout << "Enter file name:" << endl;
	cin >> fileName;
	fileName += ".txt";
	file.open(fileName, fstream::app);
	if (!file.is_open()) cout << "The file could not be created :(" << endl;
	else {
		list<Organism*>* listOrganisms = getOrganismsList();
		file << getWidth();
		file << " ";
		file << getHeight();
		file << " ";
		file << getTurnNum();
		file << "\n";
		for (list<Organism*>::iterator tmpOrganism = organismsList.begin(); tmpOrganism != organismsList.end(); tmpOrganism++) {
			Organism* tmp = *tmpOrganism;
			file << tmp->getFeatures().name;
			file << " ";
			file << tmp->getLocation().getX();
			file << " ";
			file << tmp->getLocation().getY();
			file << " ";
			file << tmp->getFeatures().strength;
			file << " ";
			file << tmp->getFeatures().age;
			if (tmp->getFeatures().name == "Human") {
				file << " ";
				Human* human = (Human*)tmp;
				file << human->getTurnsToBoost();
				file << " ";
				file << human->getBoostTurns();
			}
			file << "\n";
		}
		file.close();
		cout << "Saving the world to the file has been completed successfully!!!" << endl;
	}
}

//Metoda inicjalizuj¹ca zapisany œwiat z pliku
void World::initWorldFromFile(){
	fstream file;
	string fileName, organismName, tmpString;
	cout << "                  Enter file name:" << endl;
	cout << "                    ";
	cin >> fileName;
	fileName += ".txt";
	file.open(fileName);
	if (!file.is_open()) {
		cout << "Could not open file! The specified file does not exist." << endl;
	}
	else {
		string organismName;
		file >> this->width;
		file >> this->height;
		file >> this->turnNum;
		setWidth(this->width); 
		setHeight(this->height);
		setTurnNum(this->turnNum);
		Organism* tmpOrganism = nullptr;
		createOrganismsBoard(width, height);
		while (!file.eof()) {
			int x, y, strength, age, tmp;
			file >> organismName;
			if (organismName == "Deadly" || organismName == "Sosnowsky") {
				file >> tmpString;
				organismName = organismName + " " + tmpString;
			}
			file >> x;
			file >> y;
			file >> strength;
			file >> age;

			tmpOrganism = createOrganism(organismName, Cords(x, y));
			tmpOrganism->setStrength(strength);
			tmpOrganism->setAge(age);
			addOrganismToList(tmpOrganism);
			addOrganismToBoard(tmpOrganism);

			if (organismName == "Human") {
				Human* human = (Human*)tmpOrganism;
				file >> tmp;
				human->setTurnsToBoost(tmp);
				file >> tmp;
				human->setBoostTurns(tmp);
				if (human->getBoostTurns()) {
					human->setBoostIsActive(true);
					human->setBoostIsAvaliable(false);
				}
			}
		}
		file.close();
		string report = "The world was open from file: " + fileName;
		addReport(report);
	}
}
