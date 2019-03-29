import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by 21brooksc on 3/20/2019.
 */
public class RadixSort {


    public RadixSort () {

    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int val: array){
            if (val > max){
                max = val;
            }
        }
        return max;
    }

    private long getLongMax(long[] array) {
        long max = array[0];
        for (long val: array){
            if (val > max){
                max = val;
            }
        }
        return max;
    }

    public int[] sortInt(int[] array){
        int n = array.length;
        int max = getMax(array);
        int[] result = new int[n];

        for(int exp = 1; max/exp > 0; exp *=10){
            int[] count = new int[10];

            for (int i = 0; i < 10; i++){
                count[i] = 0;
            }

            for (int i = 0; i < n; i++){
                count[(array[i]/exp) % 10]++;
            }

            for (int i = 1; i < 10; i++){
                count[i] += count[i-1];
            }

            for (int i = n-1; i >= 0; i--){
                result[count[(array[i]/exp) % 10] - 1] = array[i];
                count[(array[i]/exp)%10]--;
            }

            for (int i = 0; i < n; i++){
                array[i] = result[i];
            }
        }
        return result;
    }

    public long[] sortLong(long[] array){
        long n = array.length;
        long max = getLongMax(array);
        long[] result = new long[array.length];

        for(long exp = 1; max/exp > 0; exp *=10){
            long[] count = new long[10];

            for (int i = 0; i < 10; i++){
                count[i] = 0;
            }

            for (int i = 0; i < n; i++){
                System.out.println("int i " + array[(int) i]);
                System.out.println("exp " + exp);
                System.out.println(count);
                System.out.println("countdex " + (array[i]/exp) % 10);
                long temp = (array[i]/exp) % 10;
                int conv = (int) temp;
                System.out.println("temp " + temp + " conv " + conv);
                System.out.println(count[conv]);
                long temp2;
                if(conv < array.length)
                {
                    temp2 = array[conv];
                }
                else
                {
                    temp2 = 0;
                }
                System.out.println("temp2 " + temp2);
                count[conv]++;
            }

            for (long i = 1; i < 10; i++){
                count[(int) i] += count[(int) i - 1];
            }

            for (long i = n-1; i >= 0; i--){
                result[(int) count[(int) (array[(int) i]/exp) % 10] - 1] = array[(int) i];
                count[(int) (array[(int) i]/exp)%10]--;
            }

            for (long i = 0; i < n; i++){
                array[(int) i] = result[(int) i];
            }
        }
        return result;
    }

    public String[] sortStringAlt(String[] array)
    {
        String[] theStorage = new String[array.length];

        for(int i = 0; i < array.length; i++)
        {
            String asciiStorage = "";
            for(int j = 0; j < 3; j++)
            {
                if(j > array[i].length() - 1)
                {
                    asciiStorage += "000";
                }
                else
                {
                    int charVal = array[i].charAt(j);
                    if(charVal < 10)
                    {
                        asciiStorage += "00" + charVal;
                    }
                    else if(charVal < 100)
                    {
                        asciiStorage += "0" + charVal;
                    }
                    else
                    {
                        asciiStorage += charVal;
                    }
                }
            }
            theStorage[i] = asciiStorage;
        }


        int[] stringNums = new int[array.length];
        for(int i = 0; i < theStorage.length; i++)
        {
            stringNums[i] = Integer.parseInt(theStorage[i]);
        }
        int[] sortedNums = sortInt(stringNums);
        String[] numStrings = new String[sortedNums.length];
        for(int i = 0; i < numStrings.length; i++)
        {
            numStrings[i] = Integer.toString(sortedNums[i]);
            while (numStrings[i].length() < 9)
            {
                numStrings[i] = "0" + numStrings[i];
            }
        }
        String[] rebuiltWords = new String[array.length];
        for(int i = 0; i < array.length; i++)
        {
            String currentS = "";
            for(int j = 0; j < 3; j++)
            {
                currentS = currentS + (char) Integer.parseInt(numStrings[i].substring(0, 3));
                numStrings[i] = numStrings[i].substring(3);
            }
            rebuiltWords[i] = currentS;
        }
        return rebuiltWords;
    }
/*    public String[] sortString(String[] array, int maxStringLength)
    {
        ArrayList<ArrayList<Integer>> characterValues = new ArrayList<>();
        ArrayList<ArrayList<Integer>> sortedValues = new ArrayList<>();
        ArrayList<String> preSortedStrings = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            characterValues.add(new ArrayList<>());
            preSortedStrings.add(array[i]);
        }
        for (int i = 0; i < maxStringLength; i++)
        {
            sortedValues.add(new ArrayList<>());
        }

*//*        System.out.println("FIRST SET");
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < maxStringLength; j++) {
                System.out.print(characterValues.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }*//*
        for(int i = maxStringLength - 1; i > -1; i--)
        {
            for (int j = 0; j < array.length; j++)
            {
                for (int k = 0; k < maxStringLength; k++)
                {
                    System.out.println("KVAL = " + k);
                    System.out.println("CURRENT WORKING STRING = " + preSortedStrings.get(j));
                    System.out.println("K LEN = " + preSortedStrings.get(j).length());
                    if (k < preSortedStrings.get(j).length())
                    {
                        characterValues.get(j).add(new Integer(preSortedStrings.get(j).charAt(k)));
                    }
                    else
                    {
                        characterValues.get(j).add(new Integer(0));
                    }
                    System.out.println(characterValues.get(j));
                }
            }
            int[] currentDigit = new int[array.length];
            for(int j = 0; j < array.length; j++)
            {
                currentDigit[j] = characterValues.get(j).get(i);
                System.out.println("CURRENT DIGIT VALUE WHEN J IS " + j + " : " + currentDigit[j]);
            }
            int[] currentBackup = currentDigit.clone();
            int[] sortedDigit = sortInt(currentDigit);
            ArrayList<Integer> intermediateSort = new ArrayList<>();
            for(int j = sortedDigit.length - 1; j > -1; j--)
            {
                intermediateSort.add(sortedDigit[j]);
            }
            int[] reversedDigit = new int[sortedDigit.length];
            for(int j = 0; j < array.length; j++)
            {
                reversedDigit[j] = intermediateSort.get(j);
            }
            for(int j = 0; j < array.length; j++)
            {
                System.out.println("REVERSED DIGIT VALUE WHEN J IS " + j + " : " + reversedDigit[j]);
            }
            for(int j = 0; j < array.length; j++)
            {
                currentDigit[j] = characterValues.get(j).get(i);
                System.out.println("CURRENT BACKUP VALUE WHEN J IS " + j + " : " + currentBackup[j]);
            }
            System.out.println("2 VALUE BEFORE = " + currentBackup[2]);
            int currentSpace = 0;
            for(int k = 0; k < array.length; k++)
            {
                System.out.println("J VALUE = " + k);
                System.out.println("CURRENT DIGIT VALUE: " + currentBackup[k]);
                System.out.println("REVERSE DIGIT VALUE: " + reversedDigit[k]);
                System.out.println("IS " + currentBackup[k] + " > " + reversedDigit[k] + "?");
                if(currentBackup[k] > reversedDigit[k]) {
                    System.out.println("YES");
                    String holdTitle = preSortedStrings.get(k);
                    System.out.println("Hold Title = " + holdTitle);
                    preSortedStrings.remove(holdTitle);
                    preSortedStrings.add(0, holdTitle);
                    *//*if(k > 0 && currentBackup[k] > preSortedStrings.get(i).charAt(k))
                    {
                        preSortedStrings.add(1, holdTitle);
                    }
                    else
                    {
                        preSortedStrings.add(0, holdTitle);
                    }*//*
                    currentSpace++;
                    System.out.println("=======");
                    for(int l = 0; l < preSortedStrings.size(); l++)
                    {
                        System.out.println(preSortedStrings.get(l));
                    }
                    System.out.println("=======");
                }
            }
            characterValues.clear();
            for (int j = 0; j < array.length; j++) {
                characterValues.add(new ArrayList<>());
            }

        }
        System.out.println("SECOND SET");
        for (int i = 0; i < array.length; i++) {
            System.out.println(preSortedStrings.get(i));
        }
        *//*System.out.println("CHAR VALUES SIZE = " + characterValues.size());
        int timesRun = 0;
        System.out.println("SORTED VALUES SIZE = " + sortedValues.size());
        for (int i = maxStringLength - 1; i > -1; i--) {
            System.out.println("BEGIN LOOP I VALUE = " + i);
            ArrayList<Integer> currentRadixNumbers = new ArrayList<>();
            for (int j = 0; j < characterValues.size(); j++) {
                System.out.println("J VALUE = " + j);
                System.out.println("I VALUE = " + i);
                System.out.println(characterValues.get(j).get(i));
                currentRadixNumbers.add(characterValues.get(j).get(i));
            }
            int[] currentRadixNumsPrim = new int[currentRadixNumbers.size()];
            for (int j = 0; j < currentRadixNumbers.size(); j++) {
                currentRadixNumsPrim[j] = currentRadixNumbers.get(j);
            }
            int[] sortedRadixNumsPrim = sortInt(currentRadixNumsPrim);
            for (int j = 0; j < sortedRadixNumsPrim.length; j++) {
                System.out.println("TIMES RUN = " + timesRun);
                System.out.println("NEW SORTED PRIM = " + sortedRadixNumsPrim[j]);

                sortedValues.get(timesRun).add(new Integer(sortedRadixNumsPrim[j]));
            }
            timesRun++;
        }
        System.out.println("COLLECTION BELOW.");
        for (int i = 0; i < sortedValues.size(); i++) {
            for (int j = 0; j < sortedValues.get(i).size(); j++) {
                System.out.print(sortedValues.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < sortedValues.size(); i++) {
            Collections.reverse(sortedValues.get(i));
        }
        System.out.println("REVERSED COLLECTION BELOW.");
        for (int i = 0; i < sortedValues.size(); i++) {
            for (int j = 0; j < sortedValues.get(i).size(); j++) {
                System.out.print(sortedValues.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
        int count = 0;
        boolean alreadyHappened = false;
        System.out.println("SVAL 2 0 SIZE = " + sortedValues2.get(0).size());
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < maxStringLength; j++) {
                System.out.println("I VALUE = " + i + " J VALUE = " + j);
                sortedValues2.get(i).add(sortedValues.get(j).get(i));
            }
        }
        System.out.println("COLLECTION 2 BELOW.");
        for (int i = 0; i < sortedValues2.size(); i++) {
            for (int j = 0; j < sortedValues2.get(i).size(); j++) {
                System.out.print(sortedValues2.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
        for (int i = 0; i < sortedValues2.size(); i++) {
            Collections.reverse(sortedValues2.get(i));
        }
        System.out.println("REVERSED COLLECTION 2 BELOW.");
        for (int i = 0; i < sortedValues2.size(); i++) {
            for (int j = 0; j < sortedValues2.get(i).size(); j++) {
                System.out.print(sortedValues2.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
        int k = maxStringLength - 1;
        int l = 0;
        for(int i = 0; i < array.length; i++)
        {
            for(int j = maxStringLength - 1; j > -1; j--)
            {
                System.out.println("CHAR VAL GET = " + characterValues.get(l).get(k));
                System.out.println("SORT VAL GET = " + sortedValues2.get(i).get(j));
                while(!sortedValues2.get(i).get(j).equals(characterValues.get(l).get(k)))
                {
                    l++;
                }
                sortedValues2.get(i).add(0, sortedValues2.get(i).get(k));
                sortedValues2.get(i).remove(k);
                k = maxStringLength - 1;
                l = 0;
            }
        }*//*
        *//*System.out.println("SORTED VALUES 2 SIZE = " + sortedValues2.size());
        for(int i = maxStringLength - 1; i > -1; i--)
        {
            System.out.println("I INDEX = " + i);
            for(int j = 0; j < sortedValues2.size(); j++)
            {
                System.out.println("I VALUE = " + i + " J VALUE = " + j);
                System.out.println("CURRED ADD VALUE = " + sortedValues.get(i).get(j) + " AT I VALUE = " + i);
                sortedValues2.get(j).add(sortedValues.get(i).get(j));
            }
        }
        System.out.println("COLLECTION 2 BELOW.");
        for(int i = 0; i < sortedValues2.size(); i++)
        {
            for(int j = 0; j < sortedValues2.get(i).size(); j++)
            {
                System.out.print(sortedValues2.get(i).get(j));
                System.out.print(" ");
            }
            System.out.println();
        }
        for(int i = 0; i < array.length; i++)
        {
            String currentBuild = "";
            for(int j = 0; j < maxStringLength; j++)
            {
                int passThru = sortedValues2.get(i).get(j);
                currentBuild += (char) passThru;
            }
            sortedStrings.add(currentBuild);
        }*//*
*//*        for (int i = 0; i < array.length; i++)
        {
            String currentBuild = "";
            for (int j = 0; j < maxStringLength; j++) {
                int passThru = sortedValues2.get(i).get(j);
                currentBuild += (char) passThru;
            }
            postSortedStrings.add(currentBuild);
            //return array;
        }*//*
        String[] finalizedSort = new String[preSortedStrings.size()];
        Collections.reverse(preSortedStrings);
        return preSortedStrings.toArray(finalizedSort);
    }*/
}
