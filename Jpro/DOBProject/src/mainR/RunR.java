import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

//sql server start mysqld --console
//sql client start mysql -u myuser -p
/*
 * create database if not exists dobdata;
 * use dobdata;
 * drop table if exists bxapps;
 * create table bxapps(
     id int,
     name varchar(50),
     address varchar(50),
     primary key (id));
    
    drop table if exists mhapps;
    create table mhapps(
     id int,
     name varchar(50),
     address varchar(50),
     primary key (id));
 */
public class RunR {
	
	//method returns an Arraylist <string> to be used for insert statement into sql table
	public static ArrayList <String> sqlIns() {
		Scanner input = new Scanner(System.in);
		ArrayList <String> ins = new ArrayList <String>();
		System.out.println("Please enter your name." + "\n");
		ins.add(input.nextLine());
		//ins += ;
		System.out.println("Please enter the address of the property." + "\n");
		ins.add(input.nextLine());
		input.close();
		return ins;
	}
	
	public static ArrayList <String> trans(){
		ArrayList <String> tables = new ArrayList <String> ();
		Scanner in = new Scanner(System.in);
		String source;
		String dest;
		String idnum;
		boolean cont = false;
		while(cont == false) {
			System.out.println("Please enter the name of the database to transfer files from.");
			source = in.nextLine();
			if((source.equals("bxapps"))|| (source.equals("mhapps"))) {
				cont = true;
				tables.add(source);
			}
			else {
				System.out.println("Please enter a table that exists.");
			}
		}
		
		cont = false;
		while(cont == false) {
			System.out.println("Please enter the name of the database to transfer files to.");
			dest = in.nextLine();
			if(((dest.equals("bxapps"))|| (dest.equals("mhapps"))) && !(dest.equals(tables.get(0)))) {
				cont = true;
				tables.add(dest);
			}
			else {
				System.out.println("Please enter a table that exists and is not the same as the source.");
			}
		}
		System.out.println("Please enter the name under the application.");
		idnum = in.nextLine();
		tables.add(idnum);
		in.close();
		
		return tables;
	}
	
	//method returns the int of the id to be deleted
	public static int sqldel() {
		int del = 0;
		Scanner inp = new Scanner(System.in);
		System.out.println("Please enter the id of the item to be deleted.");
		del = inp.nextInt();
		inp.close();
		return del;
	}

	public static void main(String[] args) {
		System.out.println("testing bananas");
		try( 
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dobdata?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
		               "myuser", "xxxx");
			Statement stmt = conn.createStatement();	
		){
			String cmd = "";
//			boolean keepEnt = true;
			Scanner input = new Scanner(System.in);
			while(!(cmd.equals("quit"))) {
				System.out.println("Please enter the command." + "\n");
				cmd = input.nextLine();
				
				//insert statement switch
				if(cmd.equals("insert")) {
					int maxAdd = 0; //max entered id 
					ResultSet tmpq = stmt.executeQuery("Select MAX(id) from bxapps");//query to get max id from table bxapps
					
					//gets max id value in table
					if(tmpq.next()) {
						maxAdd = tmpq.getInt(1);
					}
					
					ArrayList <String> info = sqlIns();//list of information entered for insertion
					System.out.println(info);
					maxAdd += 1;//increment max value to get new value for new entry
					String strInsert = "Insert into bxapps values (" + maxAdd + ", " + "'" + info.get(0) + "', '" + info.get(1) + "')";
					System.out.println("The Sql Statement is: " + strInsert + "\n"); //dbg
					int countInserted = stmt.executeUpdate(strInsert);
					System.out.println(countInserted + " records inserted. \n"); //number of records inserted str
				}
				
				//Transfer statement switch
				else if(cmd.equals("transfer")) {
					ArrayList <String> tables = new ArrayList <String> (trans());
					String tab2ins = "Insert into " + tables.get(1) + " (id, name, address) "
							+ "Select id, name, address From " + tables.get(0) 
							+ " Where id = " + tables.get(2);
					String tab1del = "Delete From " + tables.get(0) + " Where id = " + tables.get(2);
					int countInserted = stmt.executeUpdate(tab2ins);
					System.out.println(countInserted + " records inserted. \n"); //number of records inserted str
					int countdel = stmt.executeUpdate(tab1del);
					System.out.println(countdel + " records deleted. \n"); //number of records inserted str
				}
				//exit statement switch
				else if(cmd.equals("quit"));
				
				//delete statement switch
				else if(cmd.equals("delete")) {
					int idDel = sqldel();
					String strDelete = "Delete from bxapps where id = " + idDel;
					System.out.println("The Sql Statement is: " + strDelete + "\n"); //dbg
					int countDeleted = stmt.executeUpdate(strDelete);
					System.out.println(countDeleted + " records deleted.\n");
				}
				
				//improper input switch
				else {
					System.out.println("incorrect input entered, please enter a valid request.");
				}
			}
			
			//input.close();
			
			//String strInsert = "Insert into apps values (1001, 'Bernardo Arias', '3318 White Plains Road')";
			//System.out.println("The Sql Statement is: " + strInsert + "\n"); //dbg
			//int countInserted = stmt.executeUpdate(strInsert);
			//System.out.println(countInserted + " records inserted. \n"); //number of recors inserted str
			
			String strSelect = "select * from bxapps";
			System.out.println("The Sql Statement is: " + strSelect + "\n"); //dbg
			
			ResultSet rset = stmt.executeQuery(strSelect);//result of query
			
			
			System.out.println("The records selected are: ");
			int rowcount = 0;//count of number of rows in table
			while(rset.next()) {
				//String name = rset.getString("name");
				//String address = rset.getString("address");
				//System.out.println(name + "," + address);
				
				System.out.println(rset.getInt("id") + ", " + 
									rset.getString("name") + ", " + 
									rset.getString("address"));
				
				++rowcount;
			}
			
			System.out.println("Total number of records = " + rowcount);
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

}

