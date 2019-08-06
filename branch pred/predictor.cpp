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
	string choice;

};
vector <instr> list;
int taken_count = 0;
int not_taken = 0;
int instr_count = 0;


int bimodal_single(int size, int cnt){
	int table[size];
	int acc_count = 0;
	for (int i = 0; i < size; i++){
		table[i] = 1;
	}
	for (int i = 0; i < cnt; i++){
		unsigned long long index = list[i].num;

		if (table[(index%size)] == 1){
			if (list[i].choice == "T")acc_count++;
			else if (list[i].choice == "NT"){
				table[(index%size)] = 0;
			}
		}
		else if (table[(index%size)] == 0){
			if (list[i].choice == "T")table[(index%size)] = 1;
			else if (list[i].choice == "NT"){
				acc_count++;
			}
		}
	}
	//cout << acc_count << ", " << cnt << "; ";
	return acc_count;
}

int bimodal_two(int size, int cnt){
	int table[size];
	int acc_count = 0;
	for (int i = 0; i < size; i++){
		table[i] = 3;
	}
	for (int i = 0; i < cnt; i++){
		unsigned long long index = list[i].num;
		if (table[(index%size)] == 3){
			if (list[i].choice == "T") acc_count++;
			else{
				table[(index%size)]--;
			}
		}
		else if (table[(index%size)] == 2){
			if (list[i].choice == "T"){
				acc_count++;
				table[(index%size)]++;
			}
			else{
				table[(index%size)]--;
			}
		}
		else if (table[(index%size)] == 1){
			if (list[i].choice == "NT"){
				acc_count++;
				table[(index%size)]--;
			}
			else{
				table[(index%size)]++;
			}
		}
		else{
			if (list[i].choice == "NT"){
				acc_count++;
			}
			else{
				table[(index%size)]++;
			}
		}
	}
	//cout << acc_count << ", " << cnt << "; ";
	return acc_count;
}


int gshare(int bits, int cnt){
	int table[2048];
	int gtable = 0;
	int acc_count = 0;
	int correct = 1;
	for (int i = 0; i < 2048; i++){
		table[i] = 3;
	}
	for (int i = 1; i <= bits; i++){
		correct *= 2;
	}

	for (int i = 0; i < cnt; i++){
		int index = list[i].num % 2048;
		//int correct = gtable%pow(2,bits);
		//cout <<to_string(index^gtable)<<endl;
		//cout << "bits num " << gtable%correct << endl;
		gtable = gtable%correct;
		if (table[(index ^ (gtable%correct))] == 3){
			if (list[i].choice == "T"){
				acc_count++;
				gtable = gtable << 1;
				gtable += 1;
			}
			else{
				table[(index ^ (gtable%correct))]--;
				gtable = gtable << 1;
			}
		}

		else if (table[(index ^ (gtable%correct))] == 2){
			if (list[i].choice == "T"){
				acc_count++;
				table[(index ^ (gtable%correct))]++;
				gtable = gtable << 1;
				gtable += 1;

			}
			else{
				table[(index ^ (gtable%correct))]--;
				gtable = gtable << 1;
				//acc_count++;
			}
		}

		else if (table[(index ^ (gtable%correct))] == 1){
			if (list[i].choice == "T"){
				table[(index ^ (gtable%correct))]++;
				gtable = gtable << 1;
				gtable += 1;
				//acc_count++;
			}
			else{
				acc_count++;
				table[(index ^ (gtable%correct))]--;
				gtable = gtable << 1;
			}
		}

		else if (table[(index ^ (gtable%correct))] == 0){
			if (list[i].choice == "T"){
				table[(index ^ (gtable%correct))]++;
				gtable = gtable << 1;
				gtable += 1;

			}
			else{
				acc_count++;
				gtable = gtable << 1;

			}
		}

	}
	cout << acc_count << ", " << cnt << "; ";
	return acc_count;

}

int tournament(int cnt){
	int bimtable[2048];
	int gstable[2048];
	int tourntable[2048];// XD
	int gtable = 0;
	int acc_count = 0;
	int modder = 2048;
	for (int i = 0; i< 2048; i++){
		bimtable[i] = 3;
		gstable[i] = 3;
		tourntable[i] = 0;
	}
	for (int i = 0; i < cnt; i++){
		unsigned long long index = list[i].num;
		int gindex = list[i].num % 2048;
		int bi_count = 0;
		int gs_count = 0;

		if (tourntable[index % 2048] == 3){// part 1, strong bimodal
			if (bimtable[(index % 2048)] == 3){
				if (list[i].choice == "T"){
					acc_count++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					bi_count--;
					//tourntable[(index % 2048)]--;
				}
			}
			else if (bimtable[(index % 2048)] == 2){
				if (list[i].choice == "T"){
					acc_count++;
					bimtable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 1){
				if (list[i].choice == "NT"){
					acc_count++;
					bimtable[(index % 2048)]--;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else{
				if (list[i].choice == "NT"){
					acc_count++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			//end of bimodal


			gtable = gtable % 2048;
			if (gstable[(gindex ^ (gtable % 2048))] == 3){
				if (list[i].choice == "T"){
					//acc_count++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					gs_count--;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 2){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;

				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					gs_count--;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 1){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					gs_count--;
				}
				else{
					//acc_count++;
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 0){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					gs_count--;

				}
				else{
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;

				}
			}
			if (bi_count - gs_count == 2)tourntable[index % 2048] = 3;
			else if (bi_count - gs_count == -2)tourntable[index % 2048] = 2;
			//else if (bi_count - gs_count == 0)tourntable[index % 2048] = 3;
			//end of gshare
			//if (tourntable[(index % 2048)] > 3) tourntable[(index % 2048)] = 3;
		}//end of part 1


		else if (tourntable[index % 2048] == 2){//start part2, weak bimodal
			if (bimtable[(index % 2048)] == 3){
				if (list[i].choice == "T"){
					acc_count++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 2){
				if (list[i].choice == "T"){
					acc_count++;
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 1){
				if (list[i].choice == "NT"){
					acc_count++;
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]++;
					bi_count++;

				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else{
				if (list[i].choice == "NT"){
					acc_count++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			//end of bimodal


			gtable = gtable % 2048;
			if (gstable[(gindex ^ (gtable % 2048))] == 3){
				if (list[i].choice == "T"){
					//acc_count++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 2){
				if (list[i].choice == "T"){
					//acc_count++;
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;

				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
					//acc_count++;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 1){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
					//acc_count++;
				}
				else{
					//acc_count++;
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 0){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]++;
					gs_count--;

				}
				else{
					//acc_count++;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;

				}
			}
			if (bi_count - gs_count == 2)tourntable[index % 2048] = 3;
			else if (bi_count - gs_count == -2)tourntable[index % 2048] = 1;
			//else if (bi_count - gs_count == 0)tourntable[index % 2048] = 2;
			//end of gshare
			//if (tourntable[(index % 2048)] > 3) tourntable[(index % 2048)] = 3;
		}//end of part2

		else if (tourntable[index % 2048] == 1){//start part 3, weak gshare
			if (bimtable[(index % 2048)] == 3){
				if (list[i].choice == "T"){
					//acc_count++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					//acc_count++;
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 2){
				if (list[i].choice == "T"){
					//acc_count++;
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 1){
				if (list[i].choice == "NT"){
					//acc_count++;
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]++;
					bi_count++;

				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else{
				if (list[i].choice == "NT"){
					//acc_count++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			//end of bimodal


			gtable = gtable % 2048;
			if (gstable[(gindex ^ (gtable % 2048))] == 3){
				if (list[i].choice == "T"){
					acc_count++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 2){
				if (list[i].choice == "T"){
					acc_count++;
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;

				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
					//acc_count++;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 1){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
					//acc_count++;
				}
				else{
					acc_count++;
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 0){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]++;
					gs_count--;

				}
				else{
					acc_count++;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;

				}
			}
			if (bi_count - gs_count == 2)tourntable[index % 2048] = 2;
			else if (bi_count - gs_count == -2)tourntable[index % 2048] = 0;
			//else if (bi_count - gs_count == 0)tourntable[index % 2048] = 1;
			//end of gshare
			//if (tourntable[(index % 2048)] > 3) tourntable[(index % 2048)] = 3;
		}//end of pt 3

		else if (tourntable[index % 2048] == 0){//strong gshare
			if (bimtable[(index % 2048)] == 3){
				if (list[i].choice == "T"){
					//acc_count++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 2){
				if (list[i].choice == "T"){
					//acc_count++;
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else if (bimtable[(index % 2048)] == 1){
				if (list[i].choice == "NT"){
					//acc_count++;
					bimtable[(index % 2048)]--;
					//tourntable[(index % 2048)]++;
					bi_count++;

				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			else{
				if (list[i].choice == "NT"){
					//acc_count++;
					//tourntable[(index % 2048)]++;
					bi_count++;
				}
				else{
					bimtable[(index % 2048)]++;
					//tourntable[(index % 2048)]--;
					bi_count--;
				}
			}
			//end of bimodal


			gtable = gtable % 2048;
			if (gstable[(gindex ^ (gtable % 2048))] == 3){
				if (list[i].choice == "T"){
					acc_count++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 2){
				if (list[i].choice == "T"){
					acc_count++;
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					gs_count++;

				}
				else{
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 1){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]++;
					gs_count--;
					//acc_count++;
				}
				else{
					acc_count++;
					gstable[(gindex ^ (gtable % 2048))]--;
					gtable = gtable << 1;
					//tourntable[(index % 2048)]--;
					gs_count++;
				}
			}

			else if (gstable[(gindex ^ (gtable % 2048))] == 0){
				if (list[i].choice == "T"){
					gstable[(gindex ^ (gtable % 2048))]++;
					gtable = gtable << 1;
					gtable += 1;
					//tourntable[(index % 2048)]++;
					gs_count--;

				}
				else{
					acc_count++;
					gtable = gtable << 1;
					gs_count++;

				}
			}
			if (bi_count - gs_count == 2)tourntable[index % 2048] = 1;
			else if (bi_count - gs_count == -2)tourntable[index % 2048] = 0;
			//else if (bi_count - gs_count == 0)tourntable[index % 2048] = 0;
			//if (tourntable[(index % 2048)] < 0) tourntable[(index % 2048)] = 0;
		}//end of part 4
	}//end of for loop
	//cout << acc_count << ", " << cnt << "; " << endl;
	return acc_count;
}

int main(int argc, char *argv[]) {
	// Temporary variables
	string in = argv[1];
	string out = argv[2];
	unsigned long long addr;
	string behavior, line;

	// Open file for reading
	ifstream infile(in);
	ofstream outfile(out);
//	float taken_count = 0;
//	float not_taken = 0;
//	float instr_count;

	// The following loop will read a line at a time
	while (getline(infile, line)) {
		// Now we have to parse the line into it's two pieces
		stringstream s(line);
		s >> std::hex >> addr >> behavior;
		instr exe;
		exe.num = addr;
		exe.choice = behavior;
		list.push_back(exe);
		// cout << behavior << endl;
		// Now we can output it
		//cout << addr;
		instr_count++;
		if (behavior == "T") {
			taken_count++;
			//cout << " -> taken" << endl;
		}
		else {
			not_taken++;
			//cout << " -> not taken" << endl;
		}
	}
	infile.close();
	//outfile(out);
	if (outfile.is_open()){
		outfile << taken_count << ", " << instr_count << ": " << endl;
		outfile << not_taken << ", " << instr_count << ": " << endl;
		//single bit
		int prnt;
		prnt = bimodal_single(16, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_single(32, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_single(128, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt  = bimodal_single(256, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_single(512, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_single(1024, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_single(2048, instr_count);
		outfile << prnt << ", " << instr_count << ": " << endl;
		//cout << endl;
		// 2 bit 
		prnt = bimodal_two(16, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_two(32, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_two(128, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_two(256, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_two(512, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_two(1024, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = bimodal_two(2048, instr_count);
		outfile << prnt << ", " << instr_count << ": " << endl;
		//cout << endl;
		//gshare
		prnt = gshare(3, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(4, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(5, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(6, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(7, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(8, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(9, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(10, instr_count);
		outfile << prnt << ", " << instr_count << ": ";
		prnt = gshare(11, instr_count);
		outfile << prnt << ", " << instr_count << ": " << endl;
		//cout << endl;

		prnt = tournament(instr_count);
		outfile << prnt << ", " << instr_count << ":";
	}
	return 0;
}
