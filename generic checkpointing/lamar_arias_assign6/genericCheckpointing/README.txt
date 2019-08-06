Assuming you are in the same directory as this README 

#CLEAN
run:
ant -buildfile genericCheckpointing/src/build.xml clean


_____________________________________________________________________________
#COMPILE
run:
ant -buildfile genericCheckpointing/src/build.xml all


_____________________________________________________________________________
#RUN with command line arguments  LOCAL INPUT	
	Note: replace "src/input2.txt src/output.txt" with your input file and a
	output file name. also replace "banana,quick,fox" with words you want to delete. 
run: ant -buildfile src/build.xml run -Dargs='serdeser 5 src/input.txt'


_____________________________________________________________________________
#Data Structures 
Vector: 
	compare : O(n)

The vector is for saving and comparing the Serializable objects to each other using their hash values.

_____________________________________________________________________________




"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating I will have to sign an
official form that I have cheated and that this form will be stored in
my official university record. I also understand that I will receive a
grade of 0 for the involved assignment for my first offense and that I
will receive a grade of F for the course for any additional
offense.â€

[Date: 12/11/2017] -- Please add the date here
Lamar Arias. 
_____________________________________________________________________________
#Notes
My hashcode function just takes all the parameters and adds them together and for the sstring part, it just gets the hashcode of that.
For some reason my set methods for the classes kept messing up even though I had the class names and methods and parameters right. I found a workaround but I'm not sure if it was what you guys were looking for but it works.
