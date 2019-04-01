import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * Ian Anderson
 * 3/21/2019
 */
public class OptionButtons extends JToolBar{
    private Color colorChosen = Color.WHITE;
    private JLabel memoryUsage;
    public OptionButtons()
    {
        super("Selection Buttons");
        memoryUsage = new JLabel("");
        getMemory();
        setFloatable(false);
        setLocation(0, 500);
        setRollover(true);
        JButton place = new JButton("Add Card");
        JButton remove = new JButton("Remove Card");
        JButton clear = new JButton("Clear Cards");
        JButton impText = new JButton("Import Cards");
        JButton random1000 = new JButton("Generate 1000");
        JButton select = new JButton("Color Picker");
        JButton randomize = new JButton("Randomize");
        JButton sort1 = new JButton("Sort by Name");
        JButton sort2 = new JButton("Sort by Category");
        JButton sort3 = new JButton("Sort by Color");
        JButton sort4 = new JButton("Sort by Sound");
        JButton style = new JButton("Change Style");
        JButton asciiTable = new JButton("ASCII Table");
        JButton about = new JButton("About...");

        RadixSort radSort = new RadixSort();
        CardStorage sortBox = CardStorage.getInstance();
        String[] cardCategories = {"Fun", "Wrk", "Edu", "Car", "Vid"};
        JList usedCategories = new JList<>(cardCategories);
        usedCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usedCategories.setLayoutOrientation(JList.VERTICAL);
        usedCategories.setVisibleRowCount(-1);

        random1000.addActionListener(e ->
        {

            Random rand = new Random();
            for(int i = 0; i < 1000; i++)
            {
                String name = "";
                String type = "";
                String desc = "";
                char a = (char) (rand.nextInt(57) + 65);
                char b = (char) (rand.nextInt(57) + 65);
                char c = (char) (rand.nextInt(57) + 65);
                char d = (char) (rand.nextInt(57) + 65);
                char m = (char) (rand.nextInt(57) + 65);
                char f = (char) (rand.nextInt(57) + 65);
                char g = (char) (rand.nextInt(57) + 65);
                char h = (char) (rand.nextInt(57) + 65);
                char j = (char) (rand.nextInt(57) + 65);
                //System.out.println(a);
                name += Character.toString(a) + b + c;
                type += Character.toString(d) + m + f;
                desc += Character.toString(g) + h + j;
                sortBox.addCardToUnsorted(new Card(name, type, desc, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)), 1 + rand.nextInt(1999)));
            }
        });
        about.addActionListener(e ->
        {
            {
                JOptionPane.showMessageDialog(null, "Radix Sort Gui 1.1" +
                                "\nBy Ian Anderson and Cole Brooks" +
                                "\nCurrent Memory Usage: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " KB" +
                                "\nBuilt on " + LocalDate.now(),
                        "About Radix Sort Gui",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
            }
        });
        asciiTable.addActionListener(e ->
        {
            JFrame colorBackdrop = new JFrame("Ascii Table");
            colorBackdrop.setSize(1280, 851);
            ImageIcon image = new ImageIcon(getClass().getResource("e.png"));
            colorBackdrop.add(new JLabel(image));
            colorBackdrop.setVisible(true);
        });
        select.addActionListener(e ->
        {
            JFrame colorBackdrop = new JFrame("Card Color Picker");
            colorBackdrop.setSize(700, 400);
            colorChosen = JColorChooser.showDialog(colorBackdrop, "Card Color Picker", colorChosen);
        });
       clear.addActionListener(e ->
        {
            sortBox.clearAllSlots();
        });

        place.addActionListener(e ->
        {
            JFrame addCard = new JFrame("New Card Creator");
            addCard.setLayout(new FlowLayout(FlowLayout.TRAILING));
            addCard.setSize(325, 400);
            JLabel enterName = new JLabel("Name:     ");
            JLabel enterType = new JLabel("Category: ");
            JTextField nameBar = new JTextField(20);
            JScrollPane categoryScroll = new JScrollPane(usedCategories);
            categoryScroll.setPreferredSize(new Dimension(250, 80));
            JLabel enterDesc = new JLabel("Description: ");
            JTextArea descBox = new JTextArea(20, 20);
            addCard.add(enterName);
            addCard.add(nameBar);
            addCard.add(enterType);
            addCard.add(categoryScroll);
            addCard.add(enterDesc);
            addCard.add(descBox);
            addCard.setResizable(false);
            addCard.setVisible(true);
            addCard.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e) {
                    String name = nameBar.getText();
                    String desc = descBox.getText();
                    String type = (String) usedCategories.getSelectedValue();
                    // placeholder sound
                    sortBox.addCardToUnsorted(new Card(name, type, desc, colorChosen, 500));
                }
            });
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });

        impText.addActionListener(e ->
        {
            JFrame getCard = new JFrame("Card File Importer");
            getCard.setLayout(new FlowLayout(FlowLayout.TRAILING));
            getCard.setSize(550, 400);
            final JFileChooser cardSelector = new JFileChooser();
            getCard.add(cardSelector);
            getCard.setVisible(true);
            cardSelector.addActionListener(f ->
            {
                int fileSelected = cardSelector.showOpenDialog(cardSelector);
                if (fileSelected == JFileChooser.APPROVE_OPTION) {
                    File importCardFile = cardSelector.getSelectedFile();
                    try {
                        Scanner scan = new Scanner(importCardFile);
                        String name;
                        String desc;
                        String type;
                        int r;
                        int g;
                        int b;
                        int sound;
                        while (scan.hasNextLine()) {
                            name = scan.nextLine();
                            type = scan.nextLine();
                            desc = scan.nextLine();
                            r = scan.nextInt();
                            g = scan.nextInt();
                            b = scan.nextInt();
                            sound = scan.nextInt();
                            sortBox.addCardToUnsorted(new Card(name, type, desc, new Color(r, g, b), sound));
                            scan.nextLine();
                        }
                    } catch (Exception Ignored) {
                    }
                }
                getCard.setVisible(false);
            });
        });
        style.addActionListener(e ->
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
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });

        sort1.addActionListener(e ->
        {
            getMemory();
            int maxLength = 0;
            ArrayList<String> allTitles = new ArrayList<>();
            ArrayList<Card> allCards = new ArrayList<>();
            for(int i = 0; i < sortBox.getCardSlot(0).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(0).get(i).getName());
                if(sortBox.getCardSlot(0).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(0).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(0).get(i));
            }
            sortBox.clearSlot(0);
            for(int i = 0; i < sortBox.getCardSlot(1).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(1).get(i).getName());
                if(sortBox.getCardSlot(1).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(1).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(1).get(i));
            }
            sortBox.clearSlot(1);
            for(int i = 0; i < sortBox.getCardSlot(2).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(2).get(i).getName());
                if(sortBox.getCardSlot(2).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(2).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(2).get(i));
            }
            sortBox.clearSlot(2);
            for(int i = 0; i < sortBox.getCardSlot(3).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(3).get(i).getName());
                if(sortBox.getCardSlot(3).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(3).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(3).get(i));
            }
            sortBox.clearSlot(3);
            for(int i = 0; i < sortBox.getCardSlot(4).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(4).get(i).getName());
                if(sortBox.getCardSlot(4).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(4).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(4).get(i));
            }
            sortBox.clearSlot(4);
            for(int i = 0; i < sortBox.getCardSlot(5).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(5).get(i).getName());
                if(sortBox.getCardSlot(5).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(5).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(5).get(i));
            }
            sortBox.clearSlot(5);
            for(int i = 0; i < sortBox.getCardSlot(6).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(6).get(i).getName());
                if(sortBox.getCardSlot(6).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(6).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(6).get(i));
            }
            sortBox.clearSlot(6);
            for(int i = 0; i < sortBox.getCardSlot(7).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(7).get(i).getName());
                if(sortBox.getCardSlot(7).get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(7).get(i).getName().length();
                }
                allCards.add(sortBox.getCardSlot(7).get(i));
            }
            sortBox.clearSlot(7);
            for(int i = 0; i < sortBox.getUnsortedCards().size(); i++)
            {
                allTitles.add(sortBox.getUnsortedCards().get(i).getName());
                if(sortBox.getUnsortedCards().get(i).getName().length() > maxLength)
                {
                    maxLength = sortBox.getUnsortedCards().get(i).getName().length();
                }
                allCards.add(sortBox.getUnsortedCards().get(i));
            }
            sortBox.clearUnsorted();
            String[] arrayTitles = new String[allTitles.size()];
            String[] sortedTitles = radSort.sortStringAlt(allTitles.toArray(arrayTitles));
            ArrayList<String> sortedArrayList = new ArrayList<>(Arrays.asList(sortedTitles));
            //System.out.println("Sorted titles length = " + sortedTitles.length);
            int amountInEachBin = sortedTitles.length / 8;
            //System.out.println("Amount in each bin = " + amountInEachBin);
            int currentTitlePlace = 0;
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < amountInEachBin; j++)
                {
                    String currentTitle = sortedArrayList.get(currentTitlePlace);
                    //System.out.println("Current Title = " + currentTitle);
                    currentTitlePlace++;
                    int k = 0;
                    while (!currentTitle.equals(allCards.get(k).getName()))
                    {
                        //System.out.println("Observing Title = " + allCards.get(k).getName());
                        k++;
                    }
                    sortBox.addCardToSlot(allCards.get(k), i);
                    allCards.remove(k);
                }
            }
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });
        sort2.addActionListener(e ->
        {
            int maxLength = 0;
            ArrayList<String> allTitles = new ArrayList<>();
            ArrayList<Card> allCards = new ArrayList<>();
            for(int i = 0; i < sortBox.getCardSlot(0).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(0).get(i).getType());
                if(sortBox.getCardSlot(0).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(0).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(0).get(i));
            }
            sortBox.clearSlot(0);
            for(int i = 0; i < sortBox.getCardSlot(1).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(1).get(i).getType());
                if(sortBox.getCardSlot(1).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(1).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(1).get(i));
            }
            sortBox.clearSlot(1);
            for(int i = 0; i < sortBox.getCardSlot(2).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(2).get(i).getType());
                if(sortBox.getCardSlot(2).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(2).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(2).get(i));
            }
            sortBox.clearSlot(2);
            for(int i = 0; i < sortBox.getCardSlot(3).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(3).get(i).getType());
                if(sortBox.getCardSlot(3).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(3).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(3).get(i));
            }
            sortBox.clearSlot(3);
            for(int i = 0; i < sortBox.getCardSlot(4).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(4).get(i).getType());
                if(sortBox.getCardSlot(4).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(4).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(4).get(i));
            }
            sortBox.clearSlot(4);
            for(int i = 0; i < sortBox.getCardSlot(5).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(5).get(i).getType());
                if(sortBox.getCardSlot(5).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(5).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(5).get(i));
            }
            sortBox.clearSlot(5);
            for(int i = 0; i < sortBox.getCardSlot(6).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(6).get(i).getType());
                if(sortBox.getCardSlot(6).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(6).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(6).get(i));
            }
            sortBox.clearSlot(6);
            for(int i = 0; i < sortBox.getCardSlot(7).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(7).get(i).getType());
                if(sortBox.getCardSlot(7).get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getCardSlot(7).get(i).getType().length();
                }
                allCards.add(sortBox.getCardSlot(7).get(i));
            }
            sortBox.clearSlot(7);
            for(int i = 0; i < sortBox.getUnsortedCards().size(); i++)
            {
                allTitles.add(sortBox.getUnsortedCards().get(i).getType());
                if(sortBox.getUnsortedCards().get(i).getType().length() > maxLength)
                {
                    maxLength = sortBox.getUnsortedCards().get(i).getType().length();
                }
                allCards.add(sortBox.getUnsortedCards().get(i));
            }
            sortBox.clearUnsorted();
            String[] arrayTitles = new String[allTitles.size()];
            String[] sortedTitles = radSort.sortStringAlt(allTitles.toArray(arrayTitles));
            ArrayList<String> sortedArrayList = new ArrayList<>(Arrays.asList(sortedTitles));
            //System.out.println("Sorted titles length = " + sortedTitles.length);
            int amountInEachBin = sortedTitles.length / 8;
            //System.out.println("Amount in each bin = " + amountInEachBin);
            int currentTitlePlace = 0;
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < amountInEachBin; j++)
                {
                    String currentTitle = sortedArrayList.get(currentTitlePlace);
                    //System.out.println("Current Title = " + currentTitle);
                    currentTitlePlace++;
                    int k = 0;
                    while (!currentTitle.equals(allCards.get(k).getType()))
                    {
                        //System.out.println("Observing Title = " + allCards.get(k).getType());
                        k++;
                    }
                    sortBox.addCardToSlot(allCards.get(k), i);
                    allCards.remove(k);
                }
            }
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });
        sort3.addActionListener(e ->
        {
            ArrayList<Integer> allTitles = new ArrayList<>();
            ArrayList<Card> allCards = new ArrayList<>();
            for(int i = 0; i < sortBox.getCardSlot(0).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(0).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(0).get(i));
            }
            sortBox.clearSlot(0);
            for(int i = 0; i < sortBox.getCardSlot(1).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(1).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(1).get(i));
            }
            sortBox.clearSlot(1);
            for(int i = 0; i < sortBox.getCardSlot(2).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(2).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(2).get(i));
            }
            sortBox.clearSlot(2);
            for(int i = 0; i < sortBox.getCardSlot(3).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(3).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(3).get(i));
            }
            sortBox.clearSlot(3);
            for(int i = 0; i < sortBox.getCardSlot(4).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(4).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(4).get(i));
            }
            sortBox.clearSlot(4);
            for(int i = 0; i < sortBox.getCardSlot(5).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(5).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(5).get(i));
            }
            sortBox.clearSlot(5);
            for(int i = 0; i < sortBox.getCardSlot(6).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(6).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(6).get(i));
            }
            sortBox.clearSlot(6);
            for(int i = 0; i < sortBox.getCardSlot(7).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(7).get(i).getHSBColorSingle());
                allCards.add(sortBox.getCardSlot(7).get(i));
            }
            sortBox.clearSlot(7);
            for(int i = 0; i < sortBox.getUnsortedCards().size(); i++)
            {
                allTitles.add(sortBox.getUnsortedCards().get(i).getHSBColorSingle());
                allCards.add(sortBox.getUnsortedCards().get(i));
            }
            sortBox.clearUnsorted();
            int[] intermediate = new int[allTitles.size()];
            for(int i = 0; i < intermediate.length; i++)
            {
                intermediate[i] = allTitles.get(i);
            }
            int[] sortedTitles = radSort.sortInt(intermediate);
            ArrayList<ArrayList<Integer>> colorSets = new ArrayList<>();
            for(int i = 0; i < sortedTitles.length; i++)
            {
                colorSets.add(new ArrayList<>());
            }
            String[] numStrings = new String[sortedTitles.length];
            for(int i = 0; i < numStrings.length; i++)
            {
                numStrings[i] = Integer.toString(sortedTitles[i]);
                while (numStrings[i].length() < 9)
                {
                    numStrings[i] = "0" + numStrings[i];
                }
            }
            for(int i = 0; i < allTitles.size(); i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    colorSets.get(i).add(Integer.parseInt(numStrings[i].substring(0, 3)));
                    numStrings[i] = numStrings[i].substring(3);
                }
            }
            int amountInEachBin = sortedTitles.length / 8;
            int currentTitlePlace = 0;
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < amountInEachBin; j++)
                {
                    ArrayList<Integer> currentTitle = colorSets.get(currentTitlePlace);
                    currentTitlePlace++;
                    int k = 0;
                    while (!currentTitle.equals(allCards.get(k).getHSBColorArray()))
                    {
                        k++;
                    }
                    sortBox.addCardToSlot(allCards.get(k), i);
                    allCards.remove(k);
                }
            }
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });
        sort4.addActionListener(e ->
        {
            ArrayList<Integer> allTitles = new ArrayList<>();
            ArrayList<Card> allCards = new ArrayList<>();
            for(int i = 0; i < sortBox.getCardSlot(0).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(0).get(i).getSound());
                allCards.add(sortBox.getCardSlot(0).get(i));
            }
            sortBox.clearSlot(0);
            for(int i = 0; i < sortBox.getCardSlot(1).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(1).get(i).getSound());
                allCards.add(sortBox.getCardSlot(1).get(i));
            }
            sortBox.clearSlot(1);
            for(int i = 0; i < sortBox.getCardSlot(2).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(2).get(i).getSound());
                allCards.add(sortBox.getCardSlot(2).get(i));
            }
            sortBox.clearSlot(2);
            for(int i = 0; i < sortBox.getCardSlot(3).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(3).get(i).getSound());
                allCards.add(sortBox.getCardSlot(3).get(i));
            }
            sortBox.clearSlot(3);
            for(int i = 0; i < sortBox.getCardSlot(4).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(4).get(i).getSound());
                allCards.add(sortBox.getCardSlot(4).get(i));
            }
            sortBox.clearSlot(4);
            for(int i = 0; i < sortBox.getCardSlot(5).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(5).get(i).getSound());
                allCards.add(sortBox.getCardSlot(5).get(i));
            }
            sortBox.clearSlot(5);
            for(int i = 0; i < sortBox.getCardSlot(6).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(6).get(i).getSound());
                allCards.add(sortBox.getCardSlot(6).get(i));
            }
            sortBox.clearSlot(6);
            for(int i = 0; i < sortBox.getCardSlot(7).size(); i++)
            {
                allTitles.add(sortBox.getCardSlot(7).get(i).getSound());
                allCards.add(sortBox.getCardSlot(7).get(i));
            }
            sortBox.clearSlot(7);
            for(int i = 0; i < sortBox.getUnsortedCards().size(); i++)
            {
                allTitles.add(sortBox.getUnsortedCards().get(i).getSound());
                allCards.add(sortBox.getUnsortedCards().get(i));
            }
            sortBox.clearUnsorted();
            int[] intermediate = new int[allTitles.size()];
            for(int i = 0; i < intermediate.length; i++)
            {
                intermediate[i] = allTitles.get(i);
            }
            int[] sortedTitles = radSort.sortInt(intermediate);
            ArrayList<Integer> soundArray = new ArrayList<>();
            for(int i = 0; i < sortedTitles.length; i++)
            {
                soundArray.add(sortedTitles[i]);
            }
            int amountInEachBin = sortedTitles.length / 8;
            int currentTitlePlace = 0;
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < amountInEachBin; j++)
                {
                    int currentTitle = soundArray.get(currentTitlePlace);
                    currentTitlePlace++;
                    int k = 0;
                    while (currentTitle != allCards.get(k).getSound())
                    {
                        k++;
                    }
                    sortBox.addCardToSlot(allCards.get(k), i);
                    allCards.remove(k);
                }
            }
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });
        remove.addActionListener(e ->
        {
            Frame showCards;
            showCards = new JFrame("Remove Cards");
            showCards.setLayout(new FlowLayout(FlowLayout.TRAILING));
            showCards.setSize(350, 300);
            ArrayList<String> allCards = new ArrayList<>();
            for(int i = 0; i < 8; i++)
            {
                if(!sortBox.getCardSlot(i).isEmpty())
                {
                    for(int j = 0; i < sortBox.getCardSlot(i).size(); j++)
                    {
                        allCards.add(sortBox.getCardSlot(i).get(j).getName());
                    }
                }
            }
            if(!sortBox.getUnsortedCards().isEmpty())
            {
                for(int i = 0; i < sortBox.getUnsortedCards().size(); i++)
                {
                    allCards.add(sortBox.getUnsortedCards().get(i).getName());
                }
            }
            String[] currentCards = new String[allCards.size()];
            currentCards = allCards.toArray(currentCards);
            JList cardList = new JList<>(currentCards);
            cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cardList.setLayoutOrientation(JList.VERTICAL);
            cardList.setVisibleRowCount(-1);
            JScrollPane cardScroll = new JScrollPane(cardList);
            cardScroll.setPreferredSize(new Dimension(250, 250));
            showCards.add(cardScroll);
            showCards.setVisible(true);
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getRoot((Component) e.getSource()));
        });
        add(place);
        addSeparator();
        add(remove);
        addSeparator();
        add(clear);
        addSeparator();
        add(impText);
        addSeparator();
        add(random1000);
        addSeparator();
        add(select);
        addSeparator();
        add(randomize);
        addSeparator();
        add(sort1);
        addSeparator();
        add(sort2);
        addSeparator();
        add(sort3);
        addSeparator();
        add(sort4);
        addSeparator();
        add(style);
        addSeparator();
        add(asciiTable);
        addSeparator();
        add(about);
        addSeparator();
        add(memoryUsage);
        addSeparator();
    }

    public void getMemory()
    {
        ActionListener getMem = e -> memoryUsage.setText(((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " MB");
        new Timer(10, getMem).start();
    }
}
