all: predictor

predictor: predictor.o
	g++ -o predictor predictor.o
	

predictor.o: predictor.cpp
	g++ -g -std=c++11 -c predictor.cpp

clean:
	rm -rf *.o *~ predictor
	
