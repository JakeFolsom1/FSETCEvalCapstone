package io.swagger.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StableMatch
{
	private Map<String, List<String>> preferenceTable;

	public StableMatch(Map<String, List<String>> preferenceTable)
	{
		this.preferenceTable = preferenceTable;
	}

	//This method does irvings matching algorithm. It should return an ArrayList of ArrayList of Strings
	//containing the matches ex return type: [(1, 2), (3, 4), (5, 6), (2, 1), (4, 3), (6, 5)].
	//This means 1 and 2 are matched, 3 and 4 are matched, and 5 and 6 are matched
	public Map<String, String> getMatches()
	{
		Map<String, String> matches = new HashMap<>();

		//Create an arrayList of the unmatched evaluatees and a length variable
		List<String> unmatchedEvaluatees = new ArrayList<>();
		int unmatchedEvaluateesLength = 0;

		//populate unmatched arraylist and also make all tutors not matched to anyone
		for(String evaluatee: preferenceTable.keySet())
		{
			unmatchedEvaluatees.add(evaluatee);
			unmatchedEvaluateesLength++;
			matches.put(evaluatee, null);
		}

		//create a copy of the preferenceTable to work with as to not modify the table
		//The preference table should only be modified in updateTable() method
		Map<String, List<String>> phase1Table = new HashMap<>(preferenceTable);

		List <String> possibleEvaluators = new ArrayList<>();

		//phase 1 of the algorithm///////////////////////////////////////////////////
		//while there are unmatched evaluatees
		while(unmatchedEvaluateesLength > 0)
		{
			//get an evaluatee
			String evaluatee = unmatchedEvaluatees.get(0);

			//populate the possible evaluators for the evaluatee
			possibleEvaluators.addAll(phase1Table.get(evaluatee));

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


						int newEvaluateeIndex = phase1Table.get(evaluator).indexOf(evaluatee);
						int currentEvaluateeIndex = phase1Table.get(evaluator).indexOf(matches.get(evaluator));

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
							phase1Table.get(evaluator).remove(old_evaluatee);
							phase1Table.get(old_evaluatee).remove(evaluator);
							break;
						}
						else //they do not prefer the new match
						{
							//remove the current evaluatee from the evaluators pref list since
							//the prefer somebody else. Also remove the evaluator from the evaluatee
							//pref list since they are less preferred than another evaluatee
							//iterate to next possible evaluator on evaluatee pref list
							phase1Table.get(evaluator).remove(evaluatee);
							phase1Table.get(evaluatee).remove(evaluator);
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
		List <String> evaluators = new ArrayList<>();

		//populate evaluators list from the table
		evaluators.addAll(preferenceTable.keySet());

		//phase 2 of the algorithm/////////////////////////////////////////////////////////
		for(int i = 0; i < numMatches; i++)
		{
			//get the evaluator from the evaluators list
			String evaluator = evaluators.get(i);

			//get the evaluatee from the matches hashmap
			String evaluatee = matches.get(evaluator);

			//find the index of evaluatee in evaluator's pref list then add one to it
			int startIndex = phase1Table.get(evaluator).indexOf(evaluatee) + 1;
			int counter = startIndex;
			int endIndex = phase1Table.get(evaluator).size();

			//while there are worse evaluatees on the evaluator's pre list, remove them
			//from evaluators pref list and symmetrictly remove the evaluator from the evaluatee's
			//pref list
			while(counter < endIndex)
			{
				String e2 = phase1Table.get(evaluator).get(startIndex);
				phase1Table.get(evaluator).remove(startIndex);
				phase1Table.get(e2).remove(evaluator);
				counter++;
			}

			//check for any empty lists. If true there is no stable match
			if(phase1Table.get(evaluatee).isEmpty())
			{
				return null;
			}
		}
		////end of phase 2////////////////////////////////////////////////////////

		// did not convert phase 3
		//begin phase 3//////////////////////////////////////////////////////////
		//create a boolean variable to loop until only one match left on everybody's list
		boolean flag = true;

		while(flag)
		{
			//Create a table consisting of all rows in phase 1 table that are at least size 2
			//It is 3 instead of 2 because index 0 is used to store the evaluator
			List<List<String>> phase3Table = new ArrayList<>();

			for(String evaluator: evaluators)
			{
				if(phase1Table.get(evaluator).size() > 1) // down one from Steven's original impl. check here if output is wrong
				{
					List<String> row = new ArrayList<>();
					row.add(evaluator);
					row.addAll(phase1Table.get(evaluator));
					phase3Table.add(row);
				}
			}

			//if the phase 3 table is not empty, two rows must have 2 or more elements in them
			if(phase3Table.isEmpty() == false)
			{
				//Create the p and q arrays
				List <String> p = new ArrayList<>();
				List <String> q = new ArrayList<>();

				//add the initial values
				String pElement = phase3Table.get(0).get(0);
				p.add(pElement);

				String qElement = phase3Table.get(0).get(2);
				q.add(qElement);


				//while the p array does not contain any duplicates build the cycles
				while(!containsDuplicate(p, pElement))
				{
					//get the row with Q and add the last element of the preference list to the p array
					List<String> rowWithQ = phase1Table.get(qElement);
					pElement = rowWithQ.get(rowWithQ.size()-1);
					p.add(pElement);

					//if the element just added cause a duplicate break
					if(containsDuplicate(p,pElement))
					{
						break;
					}

					//add the second preferred from p to the q array
					qElement = phase1Table.get(pElement).get(1); // switched from 2 to 1, check here
					q.add(qElement);
				}


				//loop to delete the cycles generated in the p and q array lists
				for(int i =0; i <p.size()-1; i++)
				{
					if(phase1Table.get(p.get(i)).size() > 1) // switched from 2 to 1, check here
					{
						for(int j= 0; j < p.size()-1; j++)
						{
							String x = q.get(j);
							String y = p.get(j + 1);
							if(phase1Table.get(q.get(j)).size() > 1) // switched from 2 to 1, check here
							{
								phase1Table.get(q.get(j)).remove(p.get(j+1));
							}
							if(phase1Table.get(p.get(j+1)).size() > 1) // switched from 2 to 1, check here
							{
								phase1Table.get(p.get(j+1)).remove(q.get(j));
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

		// add the top match back to the match map
		matches.clear();
		for(String evaluator: phase1Table.keySet())
		{
			matches.put(evaluator, phase1Table.get(evaluator).get(0));
		}

		// remove the matches from the preferenceTable
		updateTable(matches); // added by Jedde: repeatedly call match() to get new results
		//end of phase 3//////////////////////////////////////////////

		return matches;
	}

	private void updateTable(Map<String, String> matches)
	{
		for(String evaluator: matches.keySet())
		{
			preferenceTable.get(evaluator).remove(matches.get(evaluator));
		}
	}

	private boolean containsDuplicate(List<String> arr, String key)
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
}
