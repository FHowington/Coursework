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
CS401 Extra Credit 3
*/
import java.io.*;
import java.util.*;

public class BloodBank
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( "type2pres.txt" ) );

		TreeMap<String,TreeSet<String>> presMap = new TreeMap<>();
		TreeMap<String,String> prestoBlood = new TreeMap<>();

		while(infile.ready()) {
			ArrayList<String> presidents = new ArrayList<>(Arrays.asList(infile.readLine().split(",")));
			for(int i=1;i<presidents.size();i++)
				prestoBlood.put(presidents.get(i),presidents.get(0));

			presMap.put( presidents.remove(0),new TreeSet<>(presidents));
		}

		for(String k: presMap.keySet()) {
			System.out.print(k + "\t");
			for(String p:presMap.get(k))
				System.out.print(p + " ");
			System.out.println();
		}

		for(String k:prestoBlood.keySet())
			System.out.println(k + "\t" + prestoBlood.get(k));

	} // MAIN
} // BLOODBANK
