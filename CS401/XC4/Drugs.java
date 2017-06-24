/**
Copyright (c) 2017, Forbes Turley
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the <organization> nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
University of Pittsburgh
CS401 Extra Credit 4
*/
import java.io.*;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;


public class Drugs
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader foodDrug2CategoryFile = new BufferedReader( new FileReader( "foodDrug2Category.txt" ) );
		BufferedReader patient2FoodDrugFile = new BufferedReader( new FileReader( "patient2FoodDrug.txt" ) );
		BufferedReader dontMixFile = new BufferedReader( new FileReader( "dontMix.txt" ) );

		TreeMap<String,TreeSet<String>> drug2Food = new TreeMap<>();
		TreeMap<String,String> foodMap = new TreeMap<>();

		while(foodDrug2CategoryFile.ready()) {
			ArrayList<String> foods = new ArrayList<>(Arrays.asList(foodDrug2CategoryFile.readLine().split(",")));
			drug2Food.put(foods.remove(0), new TreeSet<>(foods));
		}

		for(String k: drug2Food.keySet()) {
			System.out.print(k + " ");
			for(String p:drug2Food.get(k)) {
				System.out.print(p + " ");
				foodMap.put(p,k);
			}
			System.out.println();
		}


		TreeMap<String,TreeSet<String>> pat2food = new TreeMap<>();

		while(patient2FoodDrugFile.ready()) {
			ArrayList<String> pats = new ArrayList<>(Arrays.asList(patient2FoodDrugFile.readLine().split(",")));
			pat2food.put(pats.remove(0), new TreeSet<>(pats));
		}

		for(String k: pat2food.keySet()) {
			System.out.print(k + " ");
			for(String p:pat2food.get(k)) {
				System.out.print(p + " ");
			}
			System.out.println();
		}


		TreeMap<String,TreeSet<String>> pat2Drugtypes = new TreeMap<>();


		for(String k: pat2food.keySet()) {
			ArrayList<String> fooddrugs = new ArrayList<>();
			for(String p:pat2food.get(k)) {
				fooddrugs.add(foodMap.get(p));
			}
			pat2Drugtypes.put(k,new TreeSet<>(fooddrugs));
		}

		System.out.println();


		TreeMap<String,String> dontMix = new TreeMap<>();

		while(dontMixFile.ready()) {
			String x = dontMixFile.readLine();
			dontMix.put(x.split(",")[0],x.split(",")[1]);
		}

		for(String k: pat2food.keySet()){
			for(String d : dontMix.keySet()) {
				if(pat2Drugtypes.get(k).contains(d) && pat2Drugtypes.get(k).contains(dontMix.get(d)))
					System.out.print(k + " ");
			}
		}
		System.out.println();

		} // END MAIN

}
