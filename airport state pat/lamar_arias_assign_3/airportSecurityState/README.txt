Assuming you are in the directory containing this README:

## To clean:
ant -buildfile src/build.xml clean

-----------------------------------------------------------------------
## To compile: 
ant -buildfile src/build.xml all

-----------------------------------------------------------------------
## To run by specifying arguments from command line 
## We will use this to run your code
## the output file will be put the in src folder and the input files should also be there else the program will create the output file or an error will be thrown for not finding the input file. 

ant -buildfile src/build.xml run -Darg0=src/input.txt -Darg1=src/output.txt -Darg2=0

-----------------------------------------------------------------------

## To create tarball for submission
ant -buildfile src/build.xml tarzip

-----------------------------------------------------------------------

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating I will have to sign an
official form that I have cheated and that this form will be stored in
my official university record. I also understand that I will receive a
grade of 0 for the involved assignment for my first offense and that I
will receive a grade of F for the course for any additional
offense.â€

[Date:10/16/17 ] -- 

-----------------------------------------------------------------------
I implemented the state pattern using a context class that handled all the operation calls. After the averages were calculated everytime a new person was added the state was changed based on the values claculated by the avgs class. Once the state was changed based on the criteria given it would call the function to do the operations and adds the output to the results object.

I used ArrayLists to store the numbers of people and prohibited items in the days so that I could properly recalculate the average after every new person was added else the values may be inaccurrate. I chose to store the values instead of just storing the sum in case they would need to be used for other records although inefficient in the assignment.
It is O(n) for space since the essential info for each person is stored.



-----------------------------------------------------------------------
https://www.tutorialspoint.com/design_pattern/state_pattern.htm












