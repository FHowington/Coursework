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
CS401
Lab 10
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Lab10 {
    public static void main(String[] args) throws IOException {
        finder2(args[0], args[1]);
    }

    private static void finder2(String dictName, String jumblerName) throws IOException {
        HashMap<String, String> answerKey = new HashMap<>();
        TreeSet<String> jumbles = new TreeSet<>();
        TreeSet<String> dictionary = new TreeSet<>();

        File file = new File(jumblerName);
        FileReader reader = new FileReader(file);
        BufferedReader buffReader = new BufferedReader(reader);
        try {
            String s;
            while ((s = buffReader.readLine()) != null) {
                jumbles.add(s);
            }

            file = new File(dictName);
            reader = new FileReader(file);
            buffReader = new BufferedReader(reader);
            while ((s = buffReader.readLine()) != null) {
                dictionary.add(s);
            }
        } catch (IOException e) {
        }

        while (!dictionary.isEmpty()) {
            String x = dictionary.first();
            dictionary.remove(x);
            char y[] = x.toCharArray();
            Arrays.sort(y);
            String xSearch = new String(y);
            if (answerKey.containsKey(xSearch))
                answerKey.replace(xSearch, answerKey.get(xSearch) + " " + x);
            else
                answerKey.put(xSearch, x);
        }

        while(!jumbles.isEmpty())
        {
            String x = jumbles.first();
            jumbles.remove(x);
            char[] y = x.toCharArray();
            Arrays.sort(y);
            String z = new String(y);
            System.out.print(x + " ");
            if (answerKey.containsKey(z))
                System.out.print(answerKey.get(z));
            System.out.println();
        }

    }
}
