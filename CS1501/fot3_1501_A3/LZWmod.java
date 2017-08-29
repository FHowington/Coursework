/*
    Forbes Turley
    CS 1502 Assignment 3
    University of Pittsburgh

    This is an implementation of the Lempel-Ziv-Welch algorithm for compression of arbitrary file types

    To use, specify compression with - or decompression with +, and input and output files as streams.
    Example: java LZWmod - < all.tar > all.tar.lzw

    A modification to the compression scheme may be specified by adding r as a second command line argument
    Example: java LZWmod - r < all.tar > all.lzw
    Note that this must be done for compression and decompression
    This causes the dictionary for the file to be discarded once it reaches maximum size, which may be useful for certain
    large files that vary in format. In particular, tarball files will likely benefit from this.
 */

import java.io.BufferedInputStream;
import java.io.IOException;

public class LZWmod{
    private static final int R = 256;        // number of input chars

    // Performs a compress and output the file as a stream
    private static void compress(boolean rebuild) throws IOException {
        int width=9;
        int max_value= 512;

        BufferedInputStream in = null;
        DLB<Integer> st = new DLB<>((char)0,0);

        for (int i = 1; i < R; i++) {
            st.add(new StringBuilder("" + (char) i), i);
        }

        int code = R + 1;  // R is codeword for EOF

        try {
            in = new BufferedInputStream(System.in);
            int c=0;
            int value;
            boolean eof = false;
            boolean lostChar = false;
            Integer tempVal;

            while (!eof) {
                value = 0;
                StringBuilder toAdd = new StringBuilder();

                if (lostChar) {
                    toAdd.append((char) c);
                    value = st.searchPrefix(toAdd);
                    lostChar = false;
                }

                while (true) {
                    if((c = in.read()) != -1) {
                        toAdd.append((char)c);
                        if ((tempVal = st.searchPrefix(toAdd)) != null ) {
                            value = tempVal;
                        } else {
                            BinaryStdOut.write(value, width);
                            lostChar=true;
                            break;
                        }
                    }
                    else {
                        BinaryStdOut.write(value, width);
                        eof = true;
                        break;
                    }
                }

                // This magic number corresponds to the maximum size of 16 bits
                if (code < 65536-1) {

                    st.add(toAdd, code++);
                    if(((code+1) & (code)) == 0){
                        max_value*=2;
                        if(width<16) {
                            width++;
                        }

                        // If the rebuild option is used, this will reset the dictionary
                        else if (rebuild) {
                            width=9;
                            max_value=512;
                            code=R+1;
                            st = new DLB<>((char)0,0);
                            for (int i = 1; i < R; i++) {
                                st.add(new StringBuilder("" + (char) i), i);
                            }
                        }
                    }
                }
            }// Find max prefix match s.
            BinaryStdOut.write(R, width);
            BinaryStdOut.close();
        }
        finally {
            if (in != null) {
                in.close();
            }
        }
    }

    // Performs an expansion on a file compressed with LZW, outputs as a stream
    private static void expand(boolean rebuild) {
        int width=9;
        int max_value = 512;

        String[] st = new String[65536];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(width);
        String val = st[codeword];
        BinaryStdOut.write(val);

        while (true) {
            codeword = BinaryStdIn.readInt(width);
            if (codeword == R) break;
            String s=st[codeword];

            if (i == codeword) {
                s = val + val.charAt(0);
            }

            if (i < max_value)
                st[i++] = val + s.charAt(0);
            val = s;

            BinaryStdOut.write(val);

            if(((i+2) & (i+1)) == 0 && i>257 && i<60000){
                max_value*=2;
                width++;
            }
            else if(((i+2) & (i+1)) == 0 && i>257 && i>60000 && rebuild){
                width=9;
                max_value=512;
                st = new String[65536];

                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";

                codeword = BinaryStdIn.readInt(width);
                val = st[codeword];
                BinaryStdOut.write(val);
            }
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) throws IOException{
        boolean rebuild = false;

        // Checks for rebuild flag
        if (args.length > 1)
            if(args[1].equals("r"))
                rebuild=true;

        if (args[0].equals("-")) compress(rebuild);
        else if (args[0].equals("+")) expand(rebuild);
        else throw new RuntimeException("Illegal command line argument");
    }
}
