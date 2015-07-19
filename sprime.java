/*
TASK: sprime
LANG: JAVA
ID: maheshm2
 */

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.lang.Integer;
import java.lang.System;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class sprime {
    static int n;
    static int[] parr = {2,3,5,7};
    static int[] parr1 = {1,3,7,9};
    static PrintWriter output;
    public static void main(String[] args) throws java.io.IOException {
        String prob = "sprime";
        StreamTokenizer input = new StreamTokenizer(new BufferedReader(new FileReader(prob + ".in")));
        output = new PrintWriter(new FileWriter(prob + ".out"));

        input.nextToken();
        n = (int) input.nval;
//        String str = "";
//        for (int i = 0; i < n; i++) str += "0";
//        str = "1" + str;
//        int stri = Integer.parseInt(str);
//        int[] arr = new int[stri];

        boolean a = true;
        int k;
        char x;

        /*
        for (int i = (int) Math.pow(10, (n - 1)) + 1; i < Math.pow(10, n); i += 2) {
//            x = (i+"").charAt(0);
//            if(x!=2&&x!=3&&x!=7){
//                i+=stri/10;
//                continue;
//            }

            k = i;
            a = true;
            while (("" + k).length() != 1) {
                if (arr[k] != 0) {
                    if (arr[k] == 1) {
                        k /= 10;
                    } else {
                        a = false;
                        break;
                    }
                } else if (isPrime(k)) {
                    arr[k] = 1;
                    k /= 10;
                } else {
                    arr[k] = -1;
                    a = false;
                    break;
                }
            }
            if ((k == 2 || k == 3 || k == 5 || k == 7) && a) {
                output.println(i);
            }
        }
//        System.out.println(java.util.Arrays.toString(arr).replace(" ",""));
*/
        rec(0,1);
        output.close();

    }

    public static void rec(int val, int layer){
        if(layer==n+1){
            if(isPrime(val))
                output.println(val);
            return;
        }
        if(layer == 1){
            for(int i = 0 ; i < parr.length;i++){
                rec(parr[i], layer+1);
            }
        }
        else{
            for(int i = 0 ; i < parr1.length;i++){
                if(isPrime(val)) {
//                    System.out.println(val);
                    rec(val * 10 + parr1[i], layer + 1);
                }
            }
        }
    }

    public static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        long sqrtN = (long) Math.sqrt(n) + 1;
        for (long i = 6L; i <= sqrtN; i += 6) {
            if (n % (i - 1) == 0 || n % (i + 1) == 0) return false;
        }
        return true;
    }

}
