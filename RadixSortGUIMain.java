import java.awt.*;
import javax.swing.*;

/**
 * Ian Anderson
 * 3/20/2019
 */
public class RadixSortGUIMain {
    public static void main(String[] args)
    {
        selectStyle();
        JFrame mainFrame = new JFrame("Radix Sort GUI 1.2");
        JFrame.setDefaultLookAndFeelDecorated(true);
        OptionButtons userOptionButtons = new OptionButtons();
        CardButtons machineCardButtons = new CardButtons();
        SortingMachineDrawing theMachine = new SortingMachineDrawing();
        mainFrame.setLayout(null);
        mainFrame.setBackground(Color.GRAY);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(1600, 720));
        Container layer = mainFrame.getContentPane();
        layer.setLayout(new BorderLayout());
        layer.add(userOptionButtons, BorderLayout.NORTH);
        layer.add(machineCardButtons, BorderLayout.SOUTH);
        layer.add(theMachine, BorderLayout.CENTER);
        Timer refreshScreen = new Timer(10, (e -> theMachine.repaint()));
        refreshScreen.start();
        machineCardButtons.updateUI();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void selectStyle()
    {
        UIManager.LookAndFeelInfo []theLooks = UIManager.getInstalledLookAndFeels();
        Object[] styles = new Object[theLooks.length];
        for(int i = 0; i < theLooks.length; i++) {
            styles[i] = theLooks[i].getName();
        }
        int styleSelection = JOptionPane.showOptionDialog(null, "Select a window style.", "UI Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, styles, styles[0]);
        try
        {
            UIManager.setLookAndFeel(theLooks[styleSelection].getClassName());
        }
        catch (Exception ignored){}
    }

}
