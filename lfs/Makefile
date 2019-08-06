all:	program2

program2:	drive.o lfs.o
	g++ -g -std=c++11 drive.o -o drive
	g++ -g -std=c++11 lfs.o -o lfs

drive.o:	drive.cpp
	g++ -g -std=c++11 -c drive.cpp
	
lfs.o:	lfs.cpp
	g++ -g -std=c++11 -c lfs.cpp

clean:
	rm -rf *.o *~ drive lfs
	
undrive:
	rm -rf drive.o drive DRIVE
