package cs445.a3;

import java.util.List;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Sudoku {
    static int originalBoard[][]=new int[9][9];


    static boolean isFullSolution(int[][] board) {
        int[] count = new int[9];
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board[i][j] == 0) {
                    return false;
                } else {
                    //Find which ints are still missing
                    count[board[i][j] - 1]++;
                }
            }
        }
        for(int i=0;i<9;i++)
            if(count[i]!=9) {
                return false;
            }
        return true;
    }

    static boolean reject(int[][] board) {
        int[] counts;
// Check every int to see if they are in conflict
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    if ((board[i][j] == board[i][k] && board[i][j] != 0 && k != j) || (board[i][j] == board[k][j] && board[i][j] != 0 && k != i)) {
                        return true;
                    }
                }
            }
        }
        //Check each 3*3 quadrant for each number
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                counts= new int[9];
                for (int k = 0; k < 3; k++)
                    for (int l = 0; l < 3; l++)
                        if(board[k+3*i][l+3*j]>0)
                            counts[board[k+3*i][l+3*j]-1]++;

                for(int m=0;m<9;m++)
                    if(counts[m] > 1)
                        return true;
            }
        return false;
    }

    static int[][] extend(int[][] board) {
        // Initialize the new partial solution
        boolean firstFound=false;
        int[][] temp = new int[9][9];
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board[i][j] != 0) {
                    temp[i][j] = board[i][j];
                } else {
                    if(!firstFound) {
                        firstFound = true;
                        temp[i][j] = 1;
                    }
                }
            }
        }
        if(!firstFound)
            return null;
        else
            return temp;
    }

    static int[][] next(int[][] board) {
        int[][] temp = new int[9][9];
        int foundX=-1, foundY=-1;
        //Find last initiialized spot
        for (int j = 0; j < 9; j++)
            for (int i = 0; i < 9; i++) {
                if (board[i][j] > 0 && originalBoard[i][j] == 0) {
                    foundX = i;
                    foundY = j;
                }
                temp[i][j]=board[i][j];
            }
            if(board[foundX][foundY]>=9)
                return null;
            else
                temp[foundX][foundY]++;
        return temp;
    }

    static void testIsFullSolution() {
        int test1[][]={{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
        System.out.println("testIsFullSolution only checks to ensure that each solution is complete, that is, there all numbers appear 9 times.");
        System.out.println("This should return TRUE (all numbers appear 9 times)");
        System.out.println(isFullSolution(test1));
        int test2[][]={{0,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
        System.out.println("This should return FALSE (there is a single 1 missing)");
        System.out.println(isFullSolution(test2));
        int test3[][]={{1,1,1,1,1,1,1,1,1},{2,2,2,2,2,2,2,2,2},{3,3,3,3,3,3,3,3,3},{4,4,4,4,4,4,4,4,4},{5,5,5,5,5,5,5,5,5},{6,6,6,6,6,6,6,6,6},{7,7,7,7,7,7,7,7,7},{8,8,8,8,8,8,8,8,8},{9,9,9,9,9,9,9,9,9}};
        System.out.println("This should return TRUE (all numbers appear 9 times)");
        System.out.println(isFullSolution(test3));
    }

    static void testReject() {
        int test1[][]={{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}};
        System.out.println("\n\ntestReject checks to see whether there are any disallowed number placements, NOT whether the board is complete");
        System.out.println("This should return TRUE(1s in same column");
        System.out.println(reject(test1));
        int test2[][]={{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        System.out.println("This should return FALSE (no numbers, therefore no problems)");
        System.out.println(reject(test2));
        int test3[][]={{1,1,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        System.out.println("This should return TRUE (same row)");
        System.out.println(reject(test3));
        int test4[][]={{1,0,0,0,0,0,0,0,0},{1,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        System.out.println("This should return TRUE (same column)");
        System.out.println(reject(test4));
        int test5[][]={{1,0,0,0,0,0,0,0,0},{0,0,1,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        System.out.println("This should return TRUE (same 3x3 square)");
        System.out.println(reject(test5));
    }

    static void testExtend() {
        System.out.println("\n\nExtend finds the first non-initialized spot in the board and sets it to 1");
        int test1[][]={{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        System.out.println("This should return 1 (0,0 extended)");
        System.out.println(extend(test1)[0][0]);

        int test2[][]={{2,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        System.out.println("This should return 1 (1,0 extended)");
        System.out.println(extend(test2)[1][0]);

        int test3[][]={{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0},{2,0,0,0,0,0,0,0,0}};
        System.out.println("This should return 1 (0,1 extended)");
        System.out.println(extend(test3)[0][1]);

        int test4[][]={{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,0}};
        System.out.println("This should return 1 (8,8 extended)");
        System.out.println(extend(test4)[8][8]);
    }

    static void testNext() {
        System.out.println("\nNext finds the last extended element, and increments it by one");
        int test1[][]={{4,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                originalBoard[i][j]=test1[i][j];
        System.out.println("This should print 2 (1,0 nexted)");
        System.out.println(next(extend(test1))[1][0]);

        int test2[][]={{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,0}};
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                originalBoard[i][j]=test2[i][j];
        System.out.println("This should print 2 (8,8 nexted)");
        System.out.println(next(extend(test2))[8][8]);

        int test3[][]={{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                originalBoard[i][j]=test3[i][j];
        System.out.println("This should print 2 (0,0 nexted)");
        System.out.println(next(extend(test3))[0][0]);
    }

    static void printBoard(int[][] board) {
        if (board == null) {
            System.out.println("No assignment");
            return;
        }
        for (int i = 0; i < 9; i++) {
            if (i == 3 || i == 6) {
                System.out.println("----+-----+----");
            }
            for (int j = 0; j < 9; j++) {
                if (j == 2 || j == 5) {
                    System.out.print(board[i][j] + " | ");
                } else {
                    System.out.print(board[i][j]);
                }
            }
            System.out.print("\n");
        }
    }

    static int[][] readBoard(String filename) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
        } catch (IOException e) {
            return null;
        }
        int[][] board = new int[9][9];
        int val = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
                } catch (Exception e) {
                    val = 0;
                }
                board[i][j] = val;
            }
        }
        return board;
    }

    static int[][] solve(int[][] board) {
        if (reject(board)) return null;
        if (isFullSolution(board)) {
            return board;
        }

        int[][] attempt = extend(board);

        while (attempt != null) {
            int[][] solution = solve(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }

    public static void main(String[] args) {
        if (args[0].equals("-t")) {
            testIsFullSolution();
            testReject();
            testExtend();
            testNext();
        } else {
            int[][] board = readBoard(args[0]);
            for(int i=0;i<9;i++)
                for(int j=0;j<9;j++)
                    originalBoard[i][j]=board[i][j];

            printBoard(board);
            System.out.println("Solution:");
            printBoard(solve(board));
        }
    }
}
