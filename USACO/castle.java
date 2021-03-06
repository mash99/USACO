/*
TASK: castle
LANG: JAVA
ID: maheshm2
 */
/*
Loops through each space, runs dfs on it if it hasn't been assigned. Loops through spaces for the second time to
determine the best walls to remove in order to make the biggest combined room.

dfs is recursively called on the 4 spaces around a space and if there's no wall between current space and next space,
it assigns the same room id to the next space.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class castle {
    static int M, N, maxcomp = 0, c = 0;
    static int[][] arr, comps, dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    static String[] dirstr = {"N", "E", "S", "W"};
    static int[] cmps;
    static ArrayList<Thing> optimals = new ArrayList<Thing>();

    public static void main(String[] args) throws java.io.IOException {
        String prob = "castle";
        StreamTokenizer input = new StreamTokenizer(new BufferedReader(new FileReader(prob + ".in")));
        PrintWriter output = new PrintWriter(new FileWriter(prob + ".out"));
        input.nextToken();
        M = (int) input.nval;
        input.nextToken();
        N = (int) input.nval;
        arr = new int[N][M];
        comps = new int[N][M];
        cmps = new int[2501];//stores size of rooms (index == room id, value = how many spaces are in that room)
        for (int i = 0; i < arr.length; i++) {//reads input
            for (int j = 0; j < arr[0].length; j++) {
                input.nextToken();
                arr[i][j] = (int) input.nval;
            }
        }
        for (int i = 0; i < arr.length; i++) {//loop through castle
            for (int j = 0; j < arr[0].length; j++) {
                if (comps[i][j] == 0) {//if a space hasnt been assigned a room, run dfs on it
                    c++;//increments after a room has been filled with spaces
                    comps[i][j] = c;
                    dfs(i, j, c);
                }
                cmps[comps[i][j]]++;
            }
        }
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[0].length; j++)
                dfs(i, j, comps[i][j]);//called for 2nd time to determine optimal walls to remove
        Arrays.sort(cmps);//sorts rooms by size
        Thing[] optimalsArr = new Thing[optimals.size()];
        optimalsArr = optimals.toArray(optimalsArr);
        Arrays.sort(optimalsArr);//sorts the optimal walls with the compareTo function below
        Thing best = optimalsArr[optimalsArr.length - 1];//gets best wall
        output.println(c);
        output.println(cmps[cmps.length - 1]);
        output.println(best.score);
        output.println(best.i + " " + best.j + " " + dirstr[best.dir]);
        output.close();
    }

    public static void dfs(int i, int j, int comp) {//recursive function
        int tmpx, tmpy;
        for (int x = 0; x < dirs.length; x++) {//loops through four directions in dirs
            tmpx = i + dirs[x][0];
            tmpy = j + dirs[x][1];
            if (withinBounds(tmpx, tmpy)) {//within arr bounds and >= 0
                if (!a(arr[i][j], x) && comps[tmpx][tmpy] == 0) {//if there's no wall & it hasnt been processed
                    comps[tmpx][tmpy] = comp;
                    dfs(tmpx, tmpy, comp);
                } else if (a(arr[i][j], x))//if there's a wall, then check if its optimal to remove it
                    if (comps[tmpx][tmpy] != comps[i][j] && (cmps[comps[tmpx][tmpy]] + cmps[comp] >= maxcomp)) {
                        optimals.add(new Thing(i + 1, j + 1, cmps[comps[tmpx][tmpy]] + cmps[comp], x));
                        maxcomp = cmps[comps[tmpx][tmpy]] + cmps[comp];
                    }
            }
        }
    }

    static class Thing implements Comparable<Thing> {//represents optimal wall to remove to get biggest room
        int i, j, score, dir;

        public Thing(int x, int y, int s, int d) {
            i = x;
            j = y;
            score = s;
            dir = d;
        }

        public int compareTo(Thing o2) {//sorts optimal walls by room size, south, west, and direction ('N'>'E')
            if (this.score == o2.score) {
                if (this.j == o2.j) {
                    if (this.i == o2.i)
                        return o2.dir - this.dir;
                    else return this.i - o2.i;
                } else return o2.j - this.j;
            } else return this.score - o2.score;
        }
    }

    public static boolean withinBounds(int x, int y) {//checks if x and y are within array bounds
        return ((x < N && x >= 0) && (y < M && y >= 0));
    }

    public static boolean a(int n, int d) {//checks if a wall exists in a certain direction (can be improved with '&')
        switch (d) {
            case 0:
                return (n == 2 || n == 3 || n == 6 || n == 7 || n == 10 || n == 11 || n == 14 || n == 15);
            case 1:
                return ((n >= 4 && n <= 7) || (n >= 12));
            case 2:
                return (n >= 8);
            case 3:
                return (n % 2 != 0);
        }
        return false;
    }
}
