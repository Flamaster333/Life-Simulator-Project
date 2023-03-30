#pragma once

class Cords
{
private:
	int x;
	int y;
public:
	Cords();
	Cords(int x, int y);
	Cords getCords();
	int getX();
	int getY();
	void setCords(Cords cords);
	void setX(int x);
	void setY(int y);
	//operations on coordinates
	void operator =(Cords& cords);
	bool operator ==(Cords& cords);
	bool operator !=(Cords& cords);
};

