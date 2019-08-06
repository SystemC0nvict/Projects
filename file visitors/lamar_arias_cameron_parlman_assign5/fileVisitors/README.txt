Assuming you are in the same directory as this README 

#CLEAN
run:
ant -buildfile wordTree/src/build.xml clean


_____________________________________________________________________________
#COMPILE
run:
ant -buildfile wordTree/src/build.xml all


_____________________________________________________________________________
#RUN with command line arguments  LOCAL INPUT	
	Note: replace "src/input2.txt src/output.txt" with your input file and a
	output file name. also replace "banana,quick,fox" with words you want to delete. 
run: ant -buildfile src/build.xml run -Dargs='src/input.txt src/output.txt 0'


_____________________________________________________________________________
#Data Structures 
Binary Search Tree of Nodes. 
	Running time:Search: O(logN) 
	Running time:insert: O(logN)

ArrayList of Nodes.
	Running Time:insert: O(N)
	Running Time:sort:   O(Nlog(N))
The BST compares the lexicographical ordering of words. 
the lexicographical ordering of the the tree makes a BST ideal for searching and inserting words.  
The ArrayList of Nodes is for sorting the Nodes based on the alphanumeric values of the words.
_____________________________________________________________________________
#DEBUG VALUES 

DEBUG_VALUE=4 [Print to stdout everytime a constructor is called]
DEBUG_VALUE=2 [prints out the sum of characters of words in the tree]
DEBUG_VALUE=1 [prints out method calls from Treebuilder]
DEBUG_VALUE=0 [No output should be printed from the applicatio to stdout. It is ok to write to the output file though" ]



_____________________________________________________________________________




"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating I will have to sign an
official form that I have cheated and that this form will be stored in
my official university record. I also understand that I will receive a
grade of 0 for the involved assignment for my first offense and that I
will receive a grade of F for the course for any additional
offense.â€

[Date: 11/22/2017] -- Please add the date here
Cameron Parlman.
Lamar Arias. 


