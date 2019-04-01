import java.io.*;
import java.util.*;

import java.util.Random;


class Tester {
    static Random rand = new Random();


    // A utility function to print an array
    static void print(int arr[], int n)
    {
        for (int i=0; i<n; i++)
            System.out.print(arr[i]+" ");
    }

    static int[] randomArray(int length){

        int[] randomArray = new int[length];
        for (int i = 0; i < length; i++){
            randomArray[i] = rand.nextInt(length);
        }
        return randomArray;
    }


    public static void main (String[] args)
    {
        int[] arr = randomArray(1000);
        RadixSort radix = new RadixSort();
        System.out.println("Sorting now...");
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        int n = 1000;
        print(radix.sortInt(arr),n);
        System.out.println();
        System.out.println("Total time: " + (endTime - startTime));

    }
}