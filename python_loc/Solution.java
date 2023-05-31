import java.util.Arrays;
import java.util.Stack;

/*
 * Credits to the @author of this masterpiece.
 * @Author Zane Wang
 */

class SolveShortestSuperString {
    // Function to find the shortest superstring among all strings in array A
    public String shortestSuperstring(String[] A) {
        // get the length of the array
        int n = A.length;

        // initialize the graph as a 2D array
        int[][] graph = new int[n][n];

        // Build the graph, where graph[i][j] represents the cost of appending string j to string i
        // calc() function is used to calculate the cost
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = calc(A[i], A[j]);
                graph[j][i] = calc(A[j], A[i]);
            }
        }

        // Initialize dp and path arrays for dynamic programming and storing paths
        int[][] dp = new int[1 << n][n];
        int[][] path = new int[1 << n][n];

        // Variables to keep track of the last node and the minimum cost
        int last = -1, min = Integer.MAX_VALUE;

        // Start of the Travelling Salesman Problem (TSP) Dynamic Programming
        for (int i = 1; i < (1 << n); i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            for (int j = 0; j < n; j++) {
                // If j is in the subset i
                if ((i & (1 << j)) > 0) {
                    int prev = i - (1 << j);
                    if (prev == 0) {
                        dp[i][j] = A[j].length();
                    } else {
                        // Choose the minimum cost for dp[i][j]
                        for (int k = 0; k < n; k++) {
                            if (dp[prev][k] < Integer.MAX_VALUE && dp[prev][k] + graph[k][j] < dp[i][j]) {
                                dp[i][j] = dp[prev][k] + graph[k][j];
                                path[i][j] = k;
                            }
                        }
                    }
                }
                // Update the minimum cost and last node
                if (i == (1 << n) - 1 && dp[i][j] < min) {
                    min = dp[i][j];
                    last = j;
                }
            }
        }

        // Building the path using Stack
        StringBuilder sb = new StringBuilder();
        int cur = (1 << n) - 1;
        Stack<Integer> stack = new Stack<>();
        while (cur > 0) {
            stack.push(last);
            int temp = cur;
            cur -= (1 << last);
            last = path[temp][last];
        }

        // Build the result by popping from the stack and appending to the string
        int i = stack.pop();
        sb.append(A[i]);
        while (!stack.isEmpty()) {
            int j = stack.pop();
            sb.append(A[j].substring(A[j].length() - graph[i][j]));
            i = j;
        }
        // Return the shortest superstring
        return sb.toString();
    }

    // Function to calculate the cost of appending string b to string a
    private int calc(String a, String b) {
        for (int i = 1; i < a.length(); i++) {
            // If b starts with a substring of a starting at index i
            if (b.startsWith(a.substring(i))) {
                return b.length() - a.length() + i;
            }
        }
        return b.length();
    }
}
