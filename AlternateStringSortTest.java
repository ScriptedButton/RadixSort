/**
 * Ian Anderson
 * 3/26/19
 */

public class AlternateStringSortTest {
    public static void main(String[] args)
    {
        RadixSort rad = new RadixSort();
        String[] test = {"Zak", "Ada", "mao", "ejo", "frf", "tbh"};
        String[] sort = rad.sortStringAlt(test);
        for(int i = 0; i < sort.length; i++)
        {
            System.out.println(sort[i]);
        }
    }
}
