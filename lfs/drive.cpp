//
//  Lab7
//
//  Lamar Arias
//  Dylan Ferrara
//  John Poblador
//
#include <sys/stat.h>
#include <iostream>
#include <fstream>
#include <iostream>
#include <string>
#include <sstream>

#define BLOCK_SIZE  1024
#define SEGMENT_SIZE  1024 * BLOCK_SIZE
#define DRIVE_SIZE 32

using namespace std;

int main(int argc, char *argv[]){
	mkdir("./DRIVE", 0700);
	for(int i = 0; i < DRIVE_SIZE; i++){
		string segName = ".//DRIVE//SEGMENT";
		
		string num_to_str;
		ostringstream convert;
		convert << i+1;
		num_to_str = convert.str();
		segName += num_to_str;
		segName += ".txt";
		const char* cstr = segName.c_str();

		
		ofstream segFile;
		segFile.open(cstr);
		if(!segFile.is_open()){
			cout << "couldn't open output file" << endl;
			exit(0);
		}else{
			for(int j = 0; j < SEGMENT_SIZE; j++){
				segFile << '0';
			}
			//segFile << endl;
			//segFile << 5463761234;
			segFile.close();
		}
	}
}
