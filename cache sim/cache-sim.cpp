#include <iostream>
#include <vector>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <algorithm> 
#include <math.h>
#include <sstream>
#include <string>
#include <iostream>

using namespace std;

struct instr{
	unsigned long long num;
	string type;
};

struct lruslot{
	unsigned long long addr;
	int place;
};

vector <instr> list;
int instr_count = 0;


int dmap(int size, int shift){
	int acs = size / 32;
	//acs is size
	unsigned long long table[acs]; //change to acs
	int hits = 0;
	for (int i = 0; i < acs; i++){
		table[i] = -1;
	}
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long index = list[p].num >> 5;
		int ind = index % acs;
		unsigned long long tag = index >> shift;
		if (table[ind] != -1){
			if (table[ind] == tag){
				hits++;
			}
			else{
				table[ind] = tag;
			}
		}
		else{
			table[ind] = tag;
		}
	}
	return hits;
}

int setAss(int size, int shift, int assoc){
	int acs = size / (32 * assoc);
	vector <vector <lruslot> > tables;
	int maxy = assoc - 1;
	int hits = 0;
	for (int i = 0; i < acs; i++){//make default values for table slots
		vector <lruslot> arr;
		for (int p = 0; p < assoc; p++){
			lruslot tmp;
			tmp.addr = -1;
			tmp.place = p;
			arr.push_back(tmp);
		}
		tables.push_back(arr);
	}
	//outfile << "passed fill" << endl;
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long index = list[p].num >> 5;//shift off the offset bits
		int ind = index % acs;//get the index bits
		unsigned long long tag = index >> shift;//get the tag bits by shifting off the index bits
		int check = 17;
		for (int c = 0; c < assoc; c++){//looks for a tag match and records slot if it is successful
			if (tables[ind][c].addr == tag){
				check = c;//records way where match is found
				c = assoc;
			}
		}
		if (check != 17){// a match was found and the first match slot is recorded from left to right
			hits++;
			int was = tables[ind][check].place;//old placing of the way where the match was found
			tables[ind][check].place = 0;//changes place to 0
			for (int w = 0; w < assoc; w++){//adjust places for lru
				if (w != check){//not the changed slot
					if (tables[ind][w].place < was)tables[ind][w].place++;//if placing was lower than where the match was found, move them down one place
				}
				//if(tables[ind][w].place>3)outfile << "hit placing " << tables[ind][w].place << endl;
			}
		}
		else{// no match found so replace
			int save = 0;
			int neg = 17;
			for (int n = 0; n < assoc; n++){// look for negative 1s to replace
				if (tables[ind][n].addr == -1){
					neg = n;//saves first negative one slot
					n = assoc;
				}
			}

			if (neg == 17){//no negatives found
				for (int v = 0; v < assoc; v++){
					if (tables[ind][v].place == maxy){//look for least recent
						tables[ind][v].addr = tag;
						tables[ind][v].place = 0;
						save = v;//way of the replacement
					}
				}
				for (int m = 0; m < assoc; m++){//move up placing
					if (m != save)tables[ind][m].place++;
				}
			}
			else{//negatives found
				tables[ind][neg].addr = tag;//sets tag for negative slot
				int old = tables[ind][neg].place;//lru place of replaced slot
				tables[ind][neg].place = 0;//sets to most recent

				for (int r = 0; r < assoc; r++){
					if (r != neg){
						if (tables[ind][r].place < old)tables[ind][r].place++;
					}
				}
			}
		}
	}
	return hits;
}

int falru(int size){
	int acs = size/32;
	int hits = 0;
	vector <lruslot> table;
	for (int i = 0; i < acs; i++){
		lruslot tmp;
		tmp.addr = -1;
		tmp.place = i;
		table.push_back(tmp);
	}
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long tag = list[p].num >> 5;//shift off the offset bits and this is the tag
		//int ind = index % acs;//get the index bits
		int check = size;//arbitrarily large value
		for (int c = 0; c < acs; c++){
			if (table[c].addr == tag){
				check = c;
				c = size;
			}

		}
		if (check != size){//match found
			hits++;
			int old = table[check].place;
			table[check].place = 0;
			for (int w = 0; w < acs; w++){
				if (w != check){
					if (table[w].place < old)table[w].place++;
				}
			}
		}
		else{//no matches found
			int save = 0;
			int neg = size;
			for (int n = 0; n < acs; n++){
				if (table[n].addr == -1){
					neg = n;
					n = acs;
				}
			}
			if (neg == size){//no negatives found
				for (int f = 0; f < acs; f++){
					if (table[f].place == (acs - 1)){
						save = f;
						table[f].place = 0;
						table[f].addr = tag;
					}
				}
				for (int s = 0; s < acs; s++){
					if (s != save)table[s].place++;
				}
			}

			else{
				int chang = table[neg].place;
				table[neg].addr = tag;
				table[neg].place = 0;

				for (int r = 0; r < acs; r++){
					if (r != neg){
						if (table[r].place < chang)table[r].place++;
					}
				}
			}


		}
	}
	return hits;
}

int fahc(int size){
	int hc[1023];
	for(int i = 0; i < 1023; i++){
		hc[i] = 1;
	}
	int acs = size/32;
	int hits = 0;
	vector <lruslot> table;
	for (int i = 0; i < acs; i++){
		lruslot tmp;
		tmp.addr = -1;
		tmp.place = i;
		table.push_back(tmp);
	}
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long tag = list[p].num >> 5;//shift off the offset bits and this is the tag
		//int ind = index % acs;//get the index bits
		int check = size;//arbitrarily large value
		for (int c = 0; c < acs; c++){
			if (table[c].addr == tag){
				check = c;
				c = size;
			}

		}
		if (check != size){//match found
			hits++;
			int go = check + 511;
			for(int h = 0; h < 9; h++){
				if(go % 2 == 1){// left child
					go = (go - 1) / 2;
					if(hc[go] == 1)hc[go] = 0;
				}
				else{//right child
					go = (go - 2) / 2;
					if(hc[go] == 0)hc[go] = 1;
				}
			}
		}
		else{//no matches found
		int slot = 0;
			for(int b = 0; b < 9; b++){
				if(hc[slot] == 1){//move left on the tree
					hc[slot] = 0;
					slot = (2 * slot + 1);
				}
				else{// move right
					hc[slot] = 1;
					slot = (2 * slot + 2);
				}
			}
		slot = slot - 511;
		table[slot].addr = tag;
		}
	}
	return hits;
}

int setpre(int size, int shift, int assoc){
	int acs = size / (32 * assoc);
	vector <vector <lruslot> > tables;
	int maxy = assoc - 1;
	int hits = 0;
	for (int i = 0; i < acs; i++){//make default values for table slots
		vector <lruslot> arr;
		for (int p = 0; p < assoc; p++){
			lruslot tmp;
			tmp.addr = -1;
			tmp.place = p;
			arr.push_back(tmp);
		}
		tables.push_back(arr);
	}
	//outfile << "passed fill" << endl;
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long index = list[p].num >> 5;//shift off the offset bits
		//int gs = (p + 1) % list.size();
		//unsigned long long indexs = list[gs].num >> 5;//shift off the offset bits
		int ind = index % acs;//get the index bits
		int inds = (ind+1);
		//int indt = (index+1)%acs;
		unsigned long long tag = index >> shift;//get the tag bits by shifting off the index bits
		unsigned long long tags = tag + 1;//get the tag bits by shifting off the index bits
		
		int check = 17;
		for (int c = 0; c < assoc; c++){//looks for a tag match and records slot if it is successful
			if (tables[ind][c].addr == tag){
				check = c;//records way where match is found
				c = assoc;
			}
		}
		if (check != 17){// a match was found and the first match slot is recorded from left to right (HIT)
			hits++;
			int was = tables[ind][check].place;//old placing of the way where the match was found
			tables[ind][check].place = 0;//changes place to 0
			for (int w = 0; w < assoc; w++){//adjust places for lru
				if (w != check){//not the changed slot
					if (tables[ind][w].place < was)tables[ind][w].place++;//if placing was lower than where the match was found, move them down one place
				}
				//if(tables[ind][w].place>3)outfile << "hit placing " << tables[ind][w].place << endl;
			}
		}
		else{// no match found so replace (MISS)
			int save = 0;
			int neg = 17;
			for (int n = 0; n < assoc; n++){// look for negative 1s to replace
				if (tables[ind][n].addr == -1){
					neg = n;//saves first negative one slot
					n = assoc;
				}
			}
			if (neg == 17){//no negatives found for first line
				for (int v = 0; v < assoc; v++){
					if (tables[ind][v].place == maxy){//look for least recent
						tables[ind][v].addr = tag;
						tables[ind][v].place = 0;
						save = v;//way of the replacement
					}
				}
				for (int m = 0; m < assoc; m++){//move up placing
					if (m != save)tables[ind][m].place++;
				}
			}
			else{//negatives found
				tables[ind][neg].addr = tag;//sets tag for negative slot
				int old = tables[ind][neg].place;//lru place of replaced slot
				tables[ind][neg].place = 0;//sets to most recent

				for (int r = 0; r < assoc; r++){
					if (r != neg){
						if (tables[ind][r].place < old)tables[ind][r].place++;
					}
				}
			}
			
		}
		if(inds >= acs){
			inds = inds%acs;
			tag = tags;
		}
		check = 17;
		for (int c = 0; c < assoc; c++){//looks for a tag match and records slot if it is successful
			if (tables[inds][c].addr == tag){
				check = c;//records way where match is found
				c = assoc;
			}
		}
		
		if (check != 17){// a match was found and the first match slot is recorded from left to right (HIT)
			int was = tables[inds][check].place;//old placing of the way where the match was found
			tables[inds][check].place = 0;//changes place to 0
			for (int w = 0; w < assoc; w++){//adjust places for lru
				if (w != check){//not the changed slot
					if (tables[inds][w].place < was)tables[inds][w].place++;//if placing was lower than where the match was found, move them down one place
				}
				//if(tables[ind][w].place>3)outfile << "hit placing " << tables[ind][w].place << endl;
			}
		}
		else{	
			int saves = 0;
			int negs = 17;
			for (int s = 0; s < assoc; s++){// look for neg in fetch line
				if (tables[inds][s].addr == -1){
					negs = s;
					s = assoc;
				}
			}
			if (negs == 17){
//			if (negs == 17){//no negatives found for second line
				for (int v = 0; v < assoc; v++){
					if (tables[inds][v].place == maxy){//look for least recent
						tables[inds][v].addr = tag;
						tables[inds][v].place = 0;
						saves = v;//way of the replacement
					}
				}
				for (int m = 0; m < assoc; m++){//move up placing
					if (m != saves)tables[inds][m].place++;
				}
			}
			else{//negatives found
				tables[inds][negs].addr = tag;//sets tag for negative slot
				int old = tables[inds][negs].place;//lru place of replaced slot
				tables[inds][negs].place = 0;//sets to most recent
				for (int r = 0; r < assoc; r++){
					if (r != negs){
						if (tables[inds][r].place < old)tables[inds][r].place++;
					}
				}
			}
		}
	}
	return hits;
}

int setm(int size, int shift, int assoc){
	int acs = size / (32 * assoc);
	vector <vector <lruslot> > tables;
	int maxy = assoc - 1;
	int hits = 0;
	for (int i = 0; i < acs; i++){//make default values for table slots
		vector <lruslot> arr;
		for (int p = 0; p < assoc; p++){
			lruslot tmp;
			tmp.addr = -1;
			tmp.place = p;
			arr.push_back(tmp);
		}
		tables.push_back(arr);
	}
	//outfile << "passed fill" << endl;
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long index = list[p].num >> 5;//shift off the offset bits
		int ind = index % acs;//get the index bits
		unsigned long long tag = index >> shift;//get the tag bits by shifting off the index bits
		int check = 17;
		for (int c = 0; c < assoc; c++){//looks for a tag match and records slot if it is successful
			if (tables[ind][c].addr == tag){
				check = c;//records way where match is found
				c = assoc;
			}
		}
		if (check != 17){// a match was found and the first match slot is recorded from left to right
			hits++;
			int was = tables[ind][check].place;//old placing of the way where the match was found
			tables[ind][check].place = 0;//changes place to 0
			for (int w = 0; w < assoc; w++){//adjust places for lru
				if (w != check){//not the changed slot
					if (tables[ind][w].place < was)tables[ind][w].place++;//if placing was lower than where the match was found, move them down one place
				}
				//if(tables[ind][w].place>3)outfile << "hit placing " << tables[ind][w].place << endl;
			}
		}
		else if (check == 17 && list[p].type != "S"){// no match found so replace
			int save = 0;
			int neg = 17;
			for (int n = 0; n < assoc; n++){// look for negative 1s to replace
				if (tables[ind][n].addr == -1){
					neg = n;//saves first negative one slot
					n = assoc;
				}
			}

			if (neg == 17){//no negatives found
				for (int v = 0; v < assoc; v++){
					if (tables[ind][v].place == maxy){//look for least recent
						tables[ind][v].addr = tag;
						tables[ind][v].place = 0;
						save = v;//way of the replacement
					}
				}
				for (int m = 0; m < assoc; m++){//move up placing
					if (m != save)tables[ind][m].place++;
				}
			}
			else{//negatives found
				tables[ind][neg].addr = tag;//sets tag for negative slot
				int old = tables[ind][neg].place;//lru place of replaced slot
				tables[ind][neg].place = 0;//sets to most recent

				for (int r = 0; r < assoc; r++){
					if (r != neg){
						if (tables[ind][r].place < old)tables[ind][r].place++;
					}
				}
			}
		}
	}
	return hits;
}

int setprefm(int size, int shift, int assoc){
	int acs = size / (32 * assoc);
	vector <vector <lruslot> > tables;
	int maxy = assoc - 1;
	int hits = 0;
	for (int i = 0; i < acs; i++){//make default values for table slots
		vector <lruslot> arr;
		for (int p = 0; p < assoc; p++){
			lruslot tmp;
			tmp.addr = -1;
			tmp.place = p;
			arr.push_back(tmp);
		}
		tables.push_back(arr);
	}
	//outfile << "passed fill" << endl;
	for (int p = 0; p < list.size(); p++){//go through each instruction
		unsigned long long index = list[p].num >> 5;//shift off the offset bits
		int ind = index % acs;//get the index bits
		int inds = (ind+1);
		//int indt = (index+1)%acs;
		unsigned long long tag = index >> shift;//get the tag bits by shifting off the index bits
		unsigned long long tags = tag + 1;//get the tag bits by shifting off the index bits
		int check = 17;
		for (int c = 0; c < assoc; c++){//looks for a tag match and records slot if it is successful
			if (tables[ind][c].addr == tag){
				check = c;//records way where match is found
				c = assoc;
			}
		}
		if (check != 17){// a match was found and the first match slot is recorded from left to right
			hits++;
			int was = tables[ind][check].place;//old placing of the way where the match was found
			tables[ind][check].place = 0;//changes place to 0
			for (int w = 0; w < assoc; w++){//adjust places for lru
				if (w != check){//not the changed slot
					if (tables[ind][w].place < was)tables[ind][w].place++;//if placing was lower than where the match was found, move them down one place
				}
				//if(tables[ind][w].place>3)outfile << "hit placing " << tables[ind][w].place << endl;
			}
		}
		else{// no match found so replace
			int save = 0;
			int neg = 17;
			for (int n = 0; n < assoc; n++){// look for negative 1s to replace
				if (tables[ind][n].addr == -1){
					neg = n;//saves first negative one slot
					n = assoc;
				}
			}

			if (neg == 17){//no negatives found
				for (int v = 0; v < assoc; v++){
					if (tables[ind][v].place == maxy){//look for least recent
						tables[ind][v].addr = tag;
						tables[ind][v].place = 0;
						save = v;//way of the replacement
					}
				}
				for (int m = 0; m < assoc; m++){//move up placing
					if (m != save)tables[ind][m].place++;
				}
			}
			else{//negatives found
				tables[ind][neg].addr = tag;//sets tag for negative slot
				int old = tables[ind][neg].place;//lru place of replaced slot
				tables[ind][neg].place = 0;//sets to most recent

				for (int r = 0; r < assoc; r++){
					if (r != neg){
						if (tables[ind][r].place < old)tables[ind][r].place++;
					}
				}
			}
			
			if(inds >= acs){
			inds = inds%acs;
			tag = tags;
		}
		check = 17;
		for (int c = 0; c < assoc; c++){//looks for a tag match and records slot if it is successful
			if (tables[inds][c].addr == tag){
				check = c;//records way where match is found
				c = assoc;
			}
		}
		
		if (check != 17){// a match was found and the first match slot is recorded from left to right (HIT)
			int was = tables[inds][check].place;//old placing of the way where the match was found
			tables[inds][check].place = 0;//changes place to 0
			for (int w = 0; w < assoc; w++){//adjust places for lru
				if (w != check){//not the changed slot
					if (tables[inds][w].place < was)tables[inds][w].place++;//if placing was lower than where the match was found, move them down one place
				}
				//if(tables[ind][w].place>3)outfile << "hit placing " << tables[ind][w].place << endl;
			}
		}
		else{	
			int saves = 0;
			int negs = 17;
			for (int s = 0; s < assoc; s++){// look for neg in fetch line
				if (tables[inds][s].addr == -1){
					negs = s;
					s = assoc;
				}
			}
			if (negs == 17){
//			if (negs == 17){//no negatives found for second line
				for (int v = 0; v < assoc; v++){
					if (tables[inds][v].place == maxy){//look for least recent
						tables[inds][v].addr = tag;
						tables[inds][v].place = 0;
						saves = v;//way of the replacement
					}
				}
				for (int m = 0; m < assoc; m++){//move up placing
					if (m != saves)tables[inds][m].place++;
				}
			}
			else{//negatives found
				tables[inds][negs].addr = tag;//sets tag for negative slot
				int old = tables[inds][negs].place;//lru place of replaced slot
				tables[inds][negs].place = 0;//sets to most recent
				for (int r = 0; r < assoc; r++){
					if (r != negs){
						if (tables[inds][r].place < old)tables[inds][r].place++;
					}
				}
			}
		}
			
		}
	}
	return hits;
}

int main(int argc, char *argv[]) {
	// Temporary variables
	string in = argv[1];
	//string in = "trace1.txt";
	string out = argv[2];
	unsigned long long addr;
	string behavior, line;
	ifstream infile(in);
	ofstream outfile(out);


	while (getline(infile, line)) {
		stringstream s(line);
		s >> behavior >> std::hex >> addr;
		
		instr exe;
		exe.num = addr;
		exe.type = behavior;
		list.push_back(exe);
		instr_count++;

	}

	infile.close();

	if(outfile.is_open()){
	
		outfile << dmap(1024, 5) << "," << instr_count << "; ";
		outfile << dmap(4096, 7) << "," << instr_count << "; ";
		outfile << dmap(16384, 9) << "," << instr_count << "; ";
		outfile << dmap(32768, 10) << "," << instr_count << ";" << endl;
	
		outfile << setAss(16384, 8, 2) << "," << instr_count << "; ";
		outfile << setAss(16384, 7, 4) << "," << instr_count << "; ";
		outfile << setAss(16384, 6, 8) << "," << instr_count << "; ";
		outfile << setAss(16384, 5, 16) << "," << instr_count << ";" << endl;;
	
		outfile << falru(16384) << "," << instr_count << ";" << endl;
	
		outfile << fahc(16384) << "," << instr_count << ";" << endl;
	
		outfile << setm(16384, 8, 2) << "," << instr_count << "; ";
		outfile << setm(16384, 7, 4) << "," << instr_count << "; ";
		outfile << setm(16384, 6, 8) << "," << instr_count << "; ";
		outfile << setm(16384, 5, 16) << "," << instr_count << ";" << endl;
	
		outfile << setpre(16384, 8, 2) << "," << instr_count << "; ";
		outfile << setpre(16384, 7, 4) << "," << instr_count << "; ";
		outfile << setpre(16384, 6, 8) << "," << instr_count << "; ";
		outfile << setpre(16384, 5, 16) << "," << instr_count << ";" << endl;
	
		outfile << setprefm(16384, 8, 2) << "," << instr_count << "; ";
		outfile << setprefm(16384, 7, 4) << "," << instr_count << "; ";
		outfile << setprefm(16384, 6, 8) << "," << instr_count << "; ";
		outfile << setprefm(16384, 5, 16) << "," << instr_count << ";" << endl;
	}
	
	
	
	return 0;
}
