package io.swagger.util;

import java.util.ArrayList;
import java.util.HashMap;
public class StableMatch 
{
	//Create an ArrayList of ArrayLists. This is the preference table.
	//The first index of each array list is the evaluatee and 
	//the values from 1-n are the evaluatee's preference list
	ArrayList <ArrayList<String>> preferenceTable = null;
	
	//This is the number of matches to be made. For example admins may want three matches
	int numOfMatchesToBeMade;
	
	//Constructor for the object which contains the preference table of all tutors
	public StableMatch(int n) 
	{
		preferenceTable = buildPreferenceTable();
		numOfMatchesToBeMade = n;
	}

	//This method is used to build the preference table containing all of the tutors on a team
	//and their preference lists. This method queries the database
	public ArrayList <ArrayList<String>> buildPreferenceTable()
	{
		//Create an ArrayList of ArrayLists. This is the preference table.
		//The first index of each array list is the evaluatee and 
		//the values from 1-n are the evaluatee's preference list
		ArrayList <ArrayList<String>> preferenceTable = new ArrayList <ArrayList<String>>();
		
		//Query the database here to get all the preferences and store it into a table t
		//Table t = database query
		
		
		/*//Populate the HashMap containing the evaluatee as the key and the preference lists as values.
		//This is done by looping through the t table and extracting the data
		 * for(int i = 0; i < t.size; i++)
		 * {
		 * 	ArrayList<String> prefs = new ArrayList<String>()
		 * 	for(int j = 0; j < t[i].size; j++)
		 *  {
		 *	 	prefs.add(t[i][j]);
		 *	}
		 *	preferenceTable.add(prefs);
		 * }*/
		
		//hard coded values for testing without the database
		preferenceTable.add(buildRow());
		preferenceTable.add(buildRow2());
		preferenceTable.add(buildRow3());
		preferenceTable.add(buildRow4());
		preferenceTable.add(buildRow5());
		preferenceTable.add(buildRow6());
		
		
		
		return preferenceTable;
	}
	
	//This method does irvings matching algorithm. It should return an ArrayList of ArrayList of Strings
	//containing the matches ex return type: [(1, 2), (3, 4), (5, 6), (2, 1), (4, 3), (6, 5)]. 
	//This means 1 and 2 are matched, 3 and 4 are matched, and 5 and 6 are matched
	public ArrayList<ArrayList<String>> matchAlgorithm()
	{
		HashMap<String, String> matches = new HashMap <String, String>();
		
		//Create an arrayList of the unmatched evaluatees and a length variable
		ArrayList<String> unmatchedEvaluatees = new ArrayList <String> ();
		int unmatchedEvaluateesLength = 0;
		

		//populate unmatched arraylist and also make all tutors not matched to anyone
		for(int i = 0; i < preferenceTable.size(); i++)
		{
			unmatchedEvaluatees.add(preferenceTable.get(i).get(0));
			unmatchedEvaluateesLength += 1;
			matches.put(preferenceTable.get(i).get(0), null);
		}
		
		//create a copy of the preferenceTable to work with as to not modify the table
		//The preference table should only be modified in updateTable() method
		ArrayList <ArrayList<String>> phase1Table = buildPreferenceTable();
		
		ArrayList <String> possibleEvaluators = new ArrayList <String>();
		
		//phase 1 of the algorithm///////////////////////////////////////////////////
		//while there are unmatched evaluatees
		while(unmatchedEvaluateesLength > 0)
		{
			//get an evaluatee
			String evaluatee = unmatchedEvaluatees.get(0);
			
			//populate the possible evaluators for the evaluatee
			for(int i = 0; i < phase1Table.size(); i++)
			{
				if(phase1Table.get(i).get(0).equals(evaluatee))
				{
					for(int j=1; j < phase1Table.get(i).size(); j++)
					{
						possibleEvaluators.add(phase1Table.get(i).get(j));
					}
				}
			}
			
			//no stable match possible if a list is empty (no possible evaluators) so return null
			if(possibleEvaluators.size() < 1)
			{
				return null;
			}
			else
			{	
				//Now loop through all possible evaluators for the evaluatee
				for(int i = 0; i < possibleEvaluators.size(); i++)
				{
					String evaluator = possibleEvaluators.get(i);
					
					//If the evaluator does not have a match yet
					if(matches.get(evaluator) == null)
					{
						//match the evaluator with the evaluatee

						matches.put(evaluator, evaluatee);
						
						//remove the evaluatee from the unmatched evaluatee list and decrement the length
						unmatchedEvaluatees.remove(evaluatee);
						unmatchedEvaluateesLength -= 1;
						break;
					}
					//The evaluator does have a match
					else
					{
						
						
						int newEvaluateeIndex = searchForRow(phase1Table, evaluator).indexOf(evaluatee);
						int currentEvaluateeIndex = searchForRow(phase1Table, evaluator).indexOf(matches.get(evaluator));
						
						//The evaluator prefers a different evaluatee (LHS) over its current matched evaluatee (RHS)
						//and the new evaluatee exists on the evaluators pref list
						if(newEvaluateeIndex != -1 && newEvaluateeIndex < currentEvaluateeIndex)		
						{
						//Find out who the old evaluatee was
						String old_evaluatee = matches.get(evaluator);
										
						//match the new evaluatee with the evaluator
						matches.put(evaluator,evaluatee);
						
						//remove the current evaluatee from the unmatched list
						unmatchedEvaluatees.remove(evaluatee);
						
						//Add the old evaluatee to the unmatched list
						unmatchedEvaluatees.add(0, old_evaluatee);
						
						//remove the old_evaluatee's evaluator from their preference list
						//and remove the evalutor's old evaluatee from their preference list
						searchForRow(phase1Table, evaluator).remove(old_evaluatee);
						searchForRow(phase1Table, old_evaluatee).remove(evaluator);			
						break;
						}
						else //they do not prefer the new match
						{
							//remove the current evaluatee from the evaluators pref list since
							//the prefer somebody else. Also remove the evaluator from the evaluatee
							//pref list since they are less preferred than another evaluatee
							//iterate to next possible evaluator on evaluatee pref list
							searchForRow(phase1Table, evaluator).remove(evaluatee);
							searchForRow(phase1Table, evaluatee).remove(evaluator);
						}
					}
				}
				//clear the evaluators list and move on to next evaluatee
				possibleEvaluators.clear();
				
			}
		}
		////end of phase 1//////////////////////////////////////////////////////////////////
		
		//get the size of the total number of matches and get all of the evaluators
		int numMatches = matches.size();
		ArrayList <String> evaluators = new ArrayList <String> ();
		
		//populate evaluators list from the table
		for(int i = 0; i < preferenceTable.size(); i++)
		{
			evaluators.add(preferenceTable.get(i).get(0));
		}
		
		//phase 2 of the algorithm/////////////////////////////////////////////////////////
		for(int i = 0; i < numMatches; i++)
		{
			//get the evaluator from the evaluators list
			String evaluator = evaluators.get(i);
			
			//get the evaluatee from the matches hashmap
			String evaluatee = matches.get(evaluator);
			
			//find the index of evaluatee in evaluator's pref list then add one to it
			int startIndex = searchForRow(phase1Table, evaluator).indexOf(evaluatee) + 1;
			int counter = startIndex;
			int endIndex = searchForRow(phase1Table, evaluator).size();
			
			//while there are worse evaluatees on the evaluator's pre list, remove them
			//from evaluators pref list and symmetrictly remove the evaluator from the evaluatee's
			//pref list
			while(counter < endIndex)
			{
				String e2 = searchForRow(phase1Table, evaluator).get(startIndex);
				searchForRow(phase1Table, evaluator).remove(startIndex);
				searchForRow(phase1Table, e2).remove(evaluator);
				counter++;
			}
			
			//check for any empty lists. If true there is no stable match
			if(searchForRow(phase1Table, evaluatee).isEmpty())
			{
				return null;
			}
		}
		////end of phase 2////////////////////////////////////////////////////////
		
		//begin phase 3//////////////////////////////////////////////////////////
		//create a boolean variable to loop until only one match left on everybody's list
		boolean flag = true;
		
		while(flag)
		{
			//Create a table consisting of all rows in phase 1 table that are at least size 2
			//It is 3 instead of 2 because index 0 is used to store the evaluator
			ArrayList<ArrayList<String>> phase3Table = new ArrayList();
			for(int i=0; i < phase1Table.size(); i++)
			{
				if(phase1Table.get(i).size() > 2)
				{
					ArrayList <String> row = new ArrayList<String> ();
					for(int j=0; j < phase1Table.get(i).size();j++)
					{
						row.add(phase1Table.get(i).get(j));
					}
					phase3Table.add(row);
				}
			}
			
			//if the phase 3 table is not empty, two rows must have 3 or more elements in them
			if(phase3Table.isEmpty() == false)
			{
				//Create the p and q arrays
				ArrayList <String> p = new ArrayList <String> ();
				ArrayList <String> q = new ArrayList <String> ();
				
				//add the initial values
				String pElement = phase3Table.get(0).get(0);
				p.add(pElement);
				
				String qElement = phase3Table.get(0).get(2);
				q.add(qElement);
			
				
				//while the p array does not contain any duplicates build the cycles
				while(!containsDuplicate(p, pElement))
				{
					//get the row with Q and add the last element of the preference list to the p array
					ArrayList<String> rowWithQ = searchForRow(phase1Table, qElement);
					pElement = rowWithQ.get(rowWithQ.size()-1);
					p.add(pElement);
					
					//if the element just added cause a duplicate break
					if(containsDuplicate(p,pElement)) 
					{
						break;
					}
					
					//add the second preffered from p to the q array
					qElement = searchForRow(phase1Table, pElement).get(2);
					q.add(qElement);
				}
				
				
				//loop to delete the cycles generated in the p and q array lists
				for(int i =0; i <p.size()-1; i++)
				{
					if(searchForRow(phase1Table, p.get(i)).size() > 2)
					{
						for(int j= 0; j < p.size()-1; j++)
						{
							String x = q.get(j);
							String y = p.get(j + 1);
							if(searchForRow(phase1Table, q.get(j)).size() > 2)
							{
								searchForRow(phase1Table, q.get(j)).remove(p.get(j+1));
							}
							if(searchForRow(phase1Table, p.get(j+1)).size() > 2)
							{
								searchForRow(phase1Table, p.get(j+1)).remove(q.get(j));
							}
						}
					}
				}
			}
			else
			{
				flag = false;
			}
		}
		//end of phase 3//////////////////////////////////////////////
		return phase1Table;
	}
	
	//This method will update the preference table once matches are made. This is done by 
	//looping through the table and removing the matched evaluator from the tutor's preference list
	//Ex: tutor 1: 2 3 4 and tutor 1 and 3 are matched then the new row would be 1: 2 4.
	//This will allow for the algorithm to be run multiple times
	public void updateTable(HashMap <String, String> matches)
	{
		//TO DO: remove the match from the table
	}
	
	//This method is responsible for running the match algorithm numOfMatchesToBeMade times and
	//update the preference table
	public void makeMatches()
	{
		for(int i = 0; i < numOfMatchesToBeMade; i++)
		{
			ArrayList<ArrayList<String>> matches = matchAlgorithm();
			if(matches == null)
			{
				System.out.println("no stable match possible");
				return;
			}
			else
			{
				System.out.println("Debug: The matches are " + matches.toString());
				//updateTable(matches);
			}
		}
	}
	
	//create a custom search to return a row of the preferencetable
	public ArrayList <String> searchForRow(ArrayList<ArrayList<String>> table, String key)
	{
		for(int i = 0; i < table.size(); i++)
		{
			if(table.get(i).get(0) == key)
			{
				return table.get(i);
			}
		}
		return null;
	}
	
	public boolean containsDuplicate(ArrayList<String> arr, String key)
	{
		int count = 0;
		for(int i = 0; i < arr.size(); i++)
		{
			if(arr.get(i).equals(key))
			{
				count++;
			}
		}
		if(count >= 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//This method should query the database to get the preference lists of each tutor
	public ArrayList<String> buildRow()
	{
		//Create the preference list 
		ArrayList <String> preferenceList = new ArrayList <String>();				
		
		/*preferenceList.add("Ralph");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("Penny");
		preferenceList.add("Boris");
		preferenceList.add("Oliver");
		preferenceList.add("Tammy");
		preferenceList.add("Ginny");*/
		
		preferenceList.add("A");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("B");
		preferenceList.add("D");
		preferenceList.add("F");
		preferenceList.add("C");
		preferenceList.add("E");

		return preferenceList;
	}
	
	//This method should query the database to get the preference lists of each tutor
	public ArrayList<String> buildRow2()
	{
		//Create the preference list 
		ArrayList <String> preferenceList = new ArrayList <String>();	
		
		/*preferenceList.add("Penny");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("Oliver");
		preferenceList.add("Ginny");
		preferenceList.add("Ralph");
		preferenceList.add("Boris");
		preferenceList.add("Tammy");*/
		
		preferenceList.add("B");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("D");
		preferenceList.add("E");
		preferenceList.add("F");
		preferenceList.add("A");
		preferenceList.add("C");

		return preferenceList;
	}
	
	//This method should query the database to get the preference lists of each tutor
	public ArrayList<String> buildRow3()
	{
		//Create the preference list 
		ArrayList <String> preferenceList = new ArrayList <String>();				
		
		/*preferenceList.add("Boris");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("Oliver");
		preferenceList.add("Tammy");
		preferenceList.add("Penny");
		preferenceList.add("Ralph");
		preferenceList.add("Ginny");*/
		
		preferenceList.add("C");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("D");
		preferenceList.add("E");
		preferenceList.add("F");
		preferenceList.add("A");
		preferenceList.add("B");

		return preferenceList;
	}
	
	//This method should query the database to get the preference lists of each tutor
	public ArrayList<String> buildRow4()
	{
		//Create the preference list 
		ArrayList <String> preferenceList = new ArrayList <String>();				
		
		/*preferenceList.add("Ginny");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("Ralph");
		preferenceList.add("Boris");
		preferenceList.add("Tammy");
		preferenceList.add("Penny");
		preferenceList.add("Oliver");*/
		
		preferenceList.add("D");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("F");
		preferenceList.add("C");
		preferenceList.add("A");
		preferenceList.add("E");
		preferenceList.add("B");

		return preferenceList;
	}
	
	//This method should query the database to get the preference lists of each tutor
	public ArrayList<String> buildRow5()
	{
		//Create the preference list 
		ArrayList <String> preferenceList = new ArrayList <String>();
		
		/*preferenceList.add("Oliver");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("Ralph");
		preferenceList.add("Penny");
		preferenceList.add("Ginny");
		preferenceList.add("Tammy");
		preferenceList.add("Boris");*/
		
		preferenceList.add("E");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("F");
		preferenceList.add("C");
		preferenceList.add("D");
		preferenceList.add("B");
		preferenceList.add("A");

		return preferenceList;
	}
	
	//This method should query the database to get the preference lists of each tutor
	public ArrayList<String> buildRow6()
	{
		//Create the preference list 
		ArrayList <String> preferenceList = new ArrayList <String>();
		
		/*preferenceList.add("Tammy");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("Penny");
		preferenceList.add("Ralph");
		preferenceList.add("Ginny");
		preferenceList.add("Boris");
		preferenceList.add("Oliver");*/
		
		preferenceList.add("F");
		
		//Elements 1-n are the possible evaluators
		preferenceList.add("A");
		preferenceList.add("B");
		preferenceList.add("D");
		preferenceList.add("C");
		preferenceList.add("E");

		return preferenceList;
	}
}
