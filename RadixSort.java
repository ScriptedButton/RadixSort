import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by 21brooksc on 3/20/2019.
 */
public final class RadixSort {
    private static int getMax(int[] array) {
        int max = array[0];
        for (int val: array){
            if (val > max){
                max = val;
            }
        }
        return max;
    }

    public static int[] sortInt(int[] array){
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
    public static String[] sortStringAlt(String[] array)
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
}
