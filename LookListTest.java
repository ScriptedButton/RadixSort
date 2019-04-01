import javax.swing.UIManager;
/**
 * Ian Anderson
 * 3/21/19
 */

public class LookListTest{
    public static void main(String[] args)
    {
        UIManager.LookAndFeelInfo []theLooks = UIManager.getInstalledLookAndFeels();
        for(int i = 0; i < theLooks.length; i++) {
            System.out.println("Name: " + theLooks[i].getName());
            System.out.println("Class: " + theLooks[i].getClassName());
        }
    }

}
