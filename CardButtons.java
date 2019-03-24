import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Ian Anderson
 * 3/23/19
 */

public class CardButtons extends JToolBar {

    private CardStorage sortBox;

    public CardButtons()
    {
        super("Card Buttons");
        setFloatable(false);
        setLocation(0, 700);
        setRollover(true);

        sortBox = CardStorage.getInstance();
        JButton slot1 = new JButton("Slot 1");
        JButton slot2 = new JButton("Slot 2");
        JButton slot3 = new JButton("Slot 3");
        JButton slot4 = new JButton("Slot 4");
        JButton slot5 = new JButton("Slot 5");
        JButton slot6 = new JButton("Slot 6");
        JButton slot7 = new JButton("Slot 7");
        JButton slot8 = new JButton("Slot 8");
        JButton unSort = new JButton("Unsorted Cards");

        slot1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(0);
            }
        });
        slot2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(1);
            }
        });
        slot3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(2);
            }
        });
        slot4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(3);
            }
        });
        slot5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(4);
            }
        });
        slot6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(5);
            }
        });
        slot7.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(6);
            }
        });
        slot8.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(7);
            }
        });
        unSort.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                slotAction(8);
            }
        });

        add(Box.createHorizontalStrut(130));
        add(slot1);
        add(Box.createHorizontalStrut(60));
        add(slot2);
        add(Box.createHorizontalStrut(60));
        add(slot3);
        add(Box.createHorizontalStrut(60));
        add(slot4);
        add(Box.createHorizontalStrut(60));
        add(slot5);
        add(Box.createHorizontalStrut(60));
        add(slot6);
        add(Box.createHorizontalStrut(60));
        add(slot7);
        add(Box.createHorizontalStrut(60));
        add(slot8);
        add(Box.createHorizontalStrut(35));
        add(unSort);
    }

    public void slotAction(int slot)
    {
        JFrame showCards;
        if(slot < 8)
        {
            showCards = new JFrame("Slot " + (slot + 1) + " Cards");
        }
        else
        {
            showCards = new JFrame("Unsorted Cards");
        }
        showCards.setLayout(new FlowLayout(FlowLayout.TRAILING));
        showCards.setSize(350, 300);
        ArrayList<Card> theCurrentCardSlot;
        if(slot == 8)
        {
            theCurrentCardSlot = sortBox.getUnsortedCards();
        }
        else
        {
            theCurrentCardSlot = sortBox.getCardSlot(slot);
        }
        if(theCurrentCardSlot.isEmpty())
        {
            String[] currentCards = new String[theCurrentCardSlot.size()];
            for(int i = 0; i < currentCards.length; i++)
            {
                currentCards[i] = theCurrentCardSlot.get(i).getName();
            }
            JList cardList = new JList<>(currentCards);
            cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            cardList.setLayoutOrientation(JList.VERTICAL);
            cardList.setVisibleRowCount(-1);
            JScrollPane cardScroll = new JScrollPane(cardList);
            cardScroll.setPreferredSize(new Dimension(250, 250));
            showCards.add(cardScroll);
            showCards.setVisible(true);

        }
    }

}
