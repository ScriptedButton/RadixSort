import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Ian Anderson
 * 3/21/2019
 */
public class OptionButtons extends JToolBar{
    private Color colorChosen = Color.WHITE;
    public OptionButtons()
    {
        super("Selection Buttons");
        setFloatable(false);
        setLocation(0, 500);
        setRollover(true);

        JButton place = new JButton("Add Card");
        JButton change = new JButton("Edit Card");
        JButton remove = new JButton("Remove Card");
        JButton impText = new JButton("Import Cards");
        JButton cList = new JButton("Card List");
        JButton select = new JButton("Color Picker");
        JButton randomize = new JButton("Randomize");
        JButton sort1 = new JButton("Sort by Name");
        JButton sort2 = new JButton("Sort by Category");
        JButton sort3 = new JButton("Sort by Color");
        JButton style = new JButton("Change Style");
        JButton about = new JButton("About...");

        RadixSort radSort = new RadixSort();
        CardStorage sortBox = CardStorage.getInstance();
        String[] cardCategories = {"Cheese", "Work", "School", "Houses", "Trucks"};
        JList usedCategories = new JList<>(cardCategories);
        usedCategories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usedCategories.setLayoutOrientation(JList.VERTICAL);
        usedCategories.setVisibleRowCount(-1);

        about.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, "Radix Sort Gui Alpha" +
                                "\nBy Ian Anderson and Cole Brooks" +
                                "\nCurrent Memory Usage: " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " KB" +
                                "\nBuilt on " + LocalDate.now(),
                        "About Radix Sort Gui",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        select.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFrame colorBackdrop = new JFrame("Card Color Picker");
                colorBackdrop.setSize(700, 400);
                colorChosen = JColorChooser.showDialog(colorBackdrop, "Card Color Picker", colorChosen);
            }
        });

        place.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFrame addCard = new JFrame("New Card Creator");
                addCard.setLayout(new FlowLayout(FlowLayout.TRAILING));
                addCard.setSize(350, 300);
                JLabel enterName = new JLabel("Name: ");
                JTextField nameBar = new JTextField(20);
                JScrollPane catergoryScroll = new JScrollPane(usedCategories);
                catergoryScroll.setPreferredSize(new Dimension(250, 80));
                JLabel enterDesc = new JLabel("Description: ");
                JTextArea descBox = new JTextArea(20, 20);
                addCard.add(enterName);
                addCard.add(nameBar);
                addCard.add(catergoryScroll);
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
                        sortBox.addCardToUnsorted(new Card(name, type, desc, colorChosen));
                    }
                });
            }
        });

        impText.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFrame getCard = new JFrame("Card File Importer");
                getCard.setLayout(new FlowLayout(FlowLayout.TRAILING));
                getCard.setSize(550, 400);
                final JFileChooser cardSelector = new JFileChooser();
                getCard.add(cardSelector);
                getCard.setVisible(true);
                cardSelector.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        int fileSelected = cardSelector.showOpenDialog(cardSelector);
                        if(fileSelected == JFileChooser.APPROVE_OPTION)
                        {
                            File importCardFile = cardSelector.getSelectedFile();
                            try
                            {
                                Scanner scan = new Scanner(importCardFile);
                                String name;
                                String desc;
                                String type;
                                int r;
                                int g;
                                int b;
                                while (scan.hasNextLine())
                                {
                                    name = scan.nextLine();
                                    type = scan.nextLine();
                                    desc = scan.nextLine();
                                    r = scan.nextInt();
                                    g = scan.nextInt();
                                    b = scan.nextInt();
                                    sortBox.addCardToUnsorted(new Card(name, type, desc, new Color(r, g, b)));
                                    scan.nextLine();
                                }
                            }
                            catch (Exception Ignored){}
                        }
                        getCard.setVisible(false);
                    }
                });
            }
        });
        style.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
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
            }
        });

        sort1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
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
                String[] sortedTitles = radSort.sortString(allTitles.toArray(arrayTitles), maxLength);
                ArrayList<String> sortedArrayList = new ArrayList<>(Arrays.asList(sortedTitles));
                int amountInEachBin = sortedTitles.length / 8;
                int currentTitlePlace = 0;
                for(int i = 0; i < 7; i++)
                {
                    for(int j = 0; j < amountInEachBin; j++)
                    {
                        String currentTitle = sortedArrayList.get(currentTitlePlace);
                        currentTitlePlace++;
                        int k = 0;
                        while (!currentTitle.equals(allCards.get(k).getName()))
                        {
                            k++;
                        }
                        sortBox.addCardToSlot(allCards.get(k), i);
                        allCards.remove(k);
                    }
                }
            }
        });
        remove.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
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
            }
        });
        add(place);
        addSeparator();
        add(change);
        addSeparator();
        add(remove);
        addSeparator();
        add(impText);
        addSeparator();
        add(cList);
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
        add(style);
        addSeparator();
        add(about);
    }

    public Color getColorChosen() {
        return colorChosen;
    }

}
