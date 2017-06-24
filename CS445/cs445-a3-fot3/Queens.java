import java.util.Arrays;

public class Queens {
    /**
     * Checks if a partial solution is a complete and valid solution.
     * @param partial The partial solution
     * @return true if the partial solution is complete and valid, false otherwise.
     */
    public static boolean isFullSolution(int[] partial) {
        // Check that all queens were placed
        for (int i = 7; i >= 0; i--) {
            if (partial[i] == 0) {
                return false;
            }
        }
        // The solution is known to be complete, check if it is valid
        if (reject(partial)) {
            return false;
        }
        // The solution is complete and valid
        return true;
    }

    /**
     * Checks if a partial solution should be rejected because it can never be extended to a
     * complete solution.
     * @param partial The partial solution
     * @return true if the partial solution should be rejected, false otherwise.
     */
    public static boolean reject(int[] partial) {
        // Check every pair of queens to see if they are in conflict
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i; j++) {
                if (partial[i] == 0 || partial[j] == 0) {
                    // One of these queens is not placed, so they cannot be in conflict
                } else if (i != j && partial[i] == partial[j]) {
                    // These queens are in the same row
                    return true;
                } else if (partial[j] - partial[i] == j - i) {
                    // These queens are in the same positive diagonal
                    return true;
                } else if (partial[j] - partial[i] == i - j) {
                    // These queens are in the same negative diagonal
                    return true;
                }
            }
        }
        // No pair of queens was in conflict, so we should not reject
        return false;
    }

    /**
     * Extends a partial solution by adding one additional queen.
     * @param partial The partial solution
     * @return a partial solution with one more queen added, or null if no queen can be added.
     */
    public static int[] extend(int[] partial) {
        // Initialize the new partial solution
        int[] temp = new int[8];
        for (int i = 0; i < 8; i++) {
            if (partial[i] != 0) {
                // Copy each queen that has been placed
                temp[i] = partial[i];
            } else {
                // Add a queen in row 1 of the first empty column
                temp[i] = 1;
                return temp;
            }
        }
        // If we reached this point, all queens were already placed, so we cannot extend. Return
        // null.
        return null;
    }

    /**
     * Moves the most recently-placed queen to its next possible position.
     * @param partial The partial solution
     * @return a partial solution with the most recently-placed queen moved to its next possible
     * position, or null if we are out of options for the most recent queen.
     */
    public static int[] next(int[] partial) {
        int[] temp = new int[8];
        int i = 0;
        while (i < 8) {
            if (i == 7 || partial[i+1] == 0) {
                // The most recent queen is the one in the last column, or the last one in a
                // non-empty column
                if (partial[i] >= 8) {
                    // The queen is at the top, so we cannot try anything else
                    return null;
                } else {
                    // Move the queen up one row
                    temp[i] = partial[i] + 1;
                    break;
                }
            } else {
                // This isn't the last queen, so just copy it
                temp[i] = partial[i];
            }
            i++;
        }
        return temp;
    }


    /**
     * Tests the isFullSolution method using several partial solutions.
     */
    public static void testIsFullSolution() {
        int[][] fullSolutions = new int[][] {
            {2, 4, 6, 8, 3, 1, 7, 5},
            {1, 7, 4, 6, 8, 2, 5, 3},
        };

        int[][] notFullSolutions = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 4, 6, 8, 3, 5, 7, 0},
            {0, 2, 0, 0, 2, 0, 0, 0},
            {0, 2, 0, 0, 1, 0, 0, 2},
            {2, 4, 6, 8, 3, 1, 5, 0},
            {0, 5, 0, 0, 2, 0, 0, 0},
            {1, 7, 4, 6, 8, 2, 5, 2},
            {1, 3, 5, 7, 2, 4, 6, 8},
        };

        System.err.println("These should be full:");
        for (int[] test : fullSolutions) {
            if (isFullSolution(test)) {
                System.err.println("\tFull sol'n:\t" + Arrays.toString(test));
            } else {
                System.err.println("\tNot full sol'n:\t" + Arrays.toString(test));
            }
        }

        System.err.println("These should NOT be full:");
        for (int[] test : notFullSolutions) {
            if (isFullSolution(test)) {
                System.err.println("\tFull sol'n:\t" + Arrays.toString(test));
            } else {
                System.err.println("\tNot full sol'n:\t" + Arrays.toString(test));
            }
        }
    }

    /**
     * Tests the reject method using several partial solutions.
     */
    public static void testReject() {
        int[][] notRejected = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 4, 6, 8, 3, 5, 7, 0},
            {2, 4, 6, 8, 3, 1, 7, 5},
        };

        int[][] rejected = new int[][] {
            {0, 2, 0, 0, 2, 0, 0, 0},
            {0, 2, 0, 0, 1, 0, 0, 2},
            {2, 4, 6, 8, 3, 1, 5, 0},
            {0, 5, 0, 0, 2, 0, 0, 0},
        };

        System.err.println("These should NOT be rejected:");
        for (int[] test : notRejected) {
            if (reject(test)) {
                System.err.println("\tRejected:\t" + Arrays.toString(test));
            } else {
                System.err.println("\tNot rejected:\t" + Arrays.toString(test));
            }
        }

        System.err.println("These should be rejected:");
        for (int[] test : rejected) {
            if (reject(test)) {
                System.err.println("\tRejected:\t" + Arrays.toString(test));
            } else {
                System.err.println("\tNot rejected:\t" + Arrays.toString(test));
            }
        }
    }

    /**
     * Tests the extend method using several partial solutions.
     */
    public static void testExtend() {
        int[][] noExtend = new int[][] {
            {2, 4, 6, 8, 3, 1, 7, 5},
            {1, 7, 4, 6, 8, 2, 5, 3},
            {1, 7, 4, 6, 8, 2, 5, 2},
            {1, 3, 5, 7, 2, 4, 6, 8},
        };

        int[][] extend = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {2, 4, 6, 8, 3, 5, 7, 0},
            {2, 4, 6, 8, 0, 0, 0, 0},
            {2, 4, 6, 8, 3, 1, 5, 0},
        };

        System.err.println("These can NOT be extended:");
        for (int[] test : noExtend) {
            System.err.println("\tExtended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
        }

        System.err.println("These can be extended:");
        for (int[] test : extend) {
            System.err.println("\tExtended " + Arrays.toString(test) + " to " + Arrays.toString(extend(test)));
        }
    }

    /**
     * Tests next using a partial solution.
     * @param test The partial solution to test
     */
    static void testNextUnit(int[] test) {
        System.err.println("Nexted " + Arrays.toString(test) + " to " + Arrays.toString(next(test)));
    }

    /**
     * Tests the next method using several partial solutions.
     */
    public static void testNext() {
        int[][] noNext = new int[][] {
            {1, 3, 5, 7, 2, 4, 6, 8},
            {2, 4, 6, 8, 0, 0, 0, 0},
        };

        int[][] next = new int[][] {
            {2, 4, 6, 8, 3, 1, 7, 5},
            {1, 7, 4, 6, 8, 2, 5, 3},
            {1, 7, 4, 6, 8, 2, 5, 2},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {2, 4, 6, 8, 3, 5, 7, 0},
            {2, 4, 6, 8, 3, 1, 5, 0},
        };

        System.err.println("These can NOT be next'd:");
        for (int[] test : noNext) {
            System.err.println("\tNexted " + Arrays.toString(test) + " to " + Arrays.toString(next(test)));
        }

        System.err.println("These can be next'd:");
        for (int[] test : next) {
            System.err.println("\tNexted " + Arrays.toString(test) + " to " + Arrays.toString(next(test)));
        }
    }


    /**
     * Solves the 8-queens problem and outputs all solutions
     * @param partial The partial solution
     */
    public static void solve(int[] partial) {
        if (reject(partial)) return;
        if (isFullSolution(partial)) System.out.println(Arrays.toString(partial));
        int[] attempt = extend(partial);
        while (attempt != null) {
            solve(attempt);
            attempt = next(attempt);
        }
    }

    /**
     * Solves the 8-queens problem and returns one solution
     * @param partial The partial solution
     * @return A full, correct solution
     */
    public static int[] solveOnce(int[] partial) {
        if (reject(partial)) return null;
        if (isFullSolution(partial)) return partial;
        int[] attempt = extend(partial);
        while (attempt != null) {
            int[] solution = solveOnce(attempt);
            if (solution != null) return solution;
            attempt = next(attempt);
        }
        return null;
    }



    public static void main(String[] args) {
        if (args.length >= 1 && args[0].equals("-t")) {
            testReject();
            testIsFullSolution();
            testExtend();
            testNext();
        } else if (args.length >= 1 && args[0].equals("-a")) {
            // Find all solutions starting from the empty solution
            solve(new int[] {0, 0, 0, 0, 0, 0, 0, 0});
        } else {
            // Find one solution starting from the empty solution
            int[] solution = solveOnce(new int[] {0, 0, 0, 0, 0, 0, 0, 0});
            System.out.println(Arrays.toString(solution));
        }
    }
}

