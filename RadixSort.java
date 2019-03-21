/**
 * Created by 21brooksc on 3/20/2019.
 */
public class RadixSort {
    private int[] array;

    public RadixSort (int[] array) {
        this.array = array;
    }

    private int getMax() {
        int max = this.array[0];
        for (int val: this.array){
            if (val > max){
                max = val;
            }
        }
        return max;
    }

    public int[] sort(){
        int n = this.array.length;
        int max = getMax();
        int[] result = new int[n];

        for(int exp = 1; max/exp > 0; exp *=10){
            int[] count = new int[10];

            for (int i = 0; i < 10; i++){
                count[i] = 0;
            }

            for (int i = 0; i < n; i++){
                count[(this.array[i]/exp) % 10]++;
            }

            for (int i = 1; i < 10; i++){
                count[i] += count[i-1];
            }

            for (int i = n-1; i >= 0; i--){
                result[count[(this.array[i]/exp) % 10] - 1] = this.array[i];
                count[(this.array[i]/exp)%10]--;
            }

            for (int i = 0; i < n; i++){
                this.array[i] = result[i];
            }
        }
        return result;
    }

    public int[] getArray () {
        return this.array;
    }
}
