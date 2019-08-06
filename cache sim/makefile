all: cache-sim

cache-sim: cache-sim.o
	g++ -o cache-sim cache-sim.o
	

cache-sim.o: cache-sim.cpp
	g++ -g -std=c++11 -c cache-sim.cpp

clean:
	rm -rf *.o *~ cache-sim
