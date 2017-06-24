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
CS401 Project 8
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Project8 {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();

        Project8 speed = new Project8();
        speed.finder2(args[0], args[1]);

        System.out.println(System.nanoTime()-startTime);

    }

    private void finder2(String dictName, String jumblerName) throws IOException {

        ArrayList<Node> dictionary = new ArrayList<>();
        ArrayList<Node> dictionary2 = new ArrayList<>();
        ArrayList<String> jumbles = new ArrayList<>();

        File file = new File(dictName);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(reader);
            String s;
            while((s = buffReader.readLine()) != null)
                dictionary.add(new Node(s));
        }
        catch(IOException e){
        }

        Collections.sort(dictionary);

        Node y;
        Node x = dictionary.remove(dictionary.size()-1);

        dictionary2.add(new Node(x.canonWord,x.word));
        while(dictionary.size()>0) {
            x = dictionary.remove(dictionary.size()-1);
            y = dictionary2.get(dictionary2.size()-1);
            if(y.canonWord.equals(x.canonWord)){
                if(x.word.compareTo(y.word)<0)
                    dictionary2.set(dictionary2.size()-1,new Node(x.canonWord,x.word + " " + y.word));
                else
                    dictionary2.set(dictionary2.size()-1,new Node(x.canonWord,y.word + " " + x.word));
            }
            else
                dictionary2.add(new Node(x.canonWord,x.word));
        }

        file = new File(jumblerName);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(reader);
            String s;
            while((s = buffReader.readLine()) != null)
                jumbles.add(s);
        }
        catch(IOException e){
        }

        Collections.sort(jumbles);

        Node answerSplit;
        char yy[];
        String xCanon;
        int start,end,mid,index;

        for (int i = 0; i < jumbles.size(); i++) {
            String xx = jumbles.get(i);
            answerSplit = null;
            yy = xx.toCharArray();
            Arrays.sort(yy);
            xCanon = new String(yy);
            start = 0;
            end = dictionary2.size() - 1;
            index = -1;

            while (start <= end) {
                mid = (start + end) / 2;
                answerSplit = dictionary2.get(mid);
                if (answerSplit.canonWord.equals(xCanon)) {
                    index = mid;
                    break;
                } else if (answerSplit.canonWord.compareTo(xCanon) < 0) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }

            if (index != -1) {
                System.out.println(xx + " " + answerSplit.word);
            } else
                System.out.println(xx);
        }
    }

    public class Node implements Comparable<Node> {
        private String word; // Word
        private String canonWord; // Word after canonicalization

        private Node(String data) {
            word = data;
            char y[] = word.toCharArray();
            Arrays.sort(y);
            canonWord = new String(y);
        }
        //Remember, will be added in reverse alphabetic order.
        private Node(String cWord, String wordx) {
            canonWord=cWord;
            word=wordx;
        }

        public int compareTo(Node x) {
            return canonWord.compareTo(x.canonWord);
        }
    }
}
