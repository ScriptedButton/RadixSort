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

    public String[] sortString(String[] array, int maxStringLength)
    {
        int length = array.length;
        int asciiSize = 256;
        String[] sorted = new String[length];

        System.out.println(sorted);
        return sorted;
    }
}
