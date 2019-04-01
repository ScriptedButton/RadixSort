import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
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
        // 16000kHz

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
        JButton play1 = new JButton("Play Sounds (Normal)");
        JButton play2 = new JButton("Play Sounds (Retro)");

        slot1.addActionListener(e -> slotAction(0));
        slot2.addActionListener(e -> slotAction(1));
        slot3.addActionListener(e -> slotAction(2));
        slot4.addActionListener(e -> slotAction(3));
        slot5.addActionListener(e -> slotAction(4));
        slot6.addActionListener(e -> slotAction(5));
        slot7.addActionListener(e -> slotAction(6));
        slot8.addActionListener(e -> slotAction(7));
        unSort.addActionListener(e -> slotAction(8));

        play1.addActionListener(e -> playSounds(0));
        play2.addActionListener(e -> playSounds(1));

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
        add(Box.createHorizontalStrut(35));
        add(play1);
        add(Box.createHorizontalStrut(35));
        add(play2);
    }

    public void playSounds(int mode)
    {
        final AudioFormat cardMixer = new AudioFormat(16000, 16, 1, true, true);
        ArrayList<Integer> cardSounds = new ArrayList<>();
        for(int i = 0; i < 9; i++)
        {
            ArrayList<Card> currentSlotCards;
            if(i == 8)
            {
                currentSlotCards = sortBox.getUnsortedCards();
            }
            else
            {
                currentSlotCards = sortBox.getCardSlot(i);
            }
            for(int j = 0; j < currentSlotCards.size(); j++)
            {
                cardSounds.add(currentSlotCards.get(j).getSound());
            }
        }
        try {
            SourceDataLine line = AudioSystem.getSourceDataLine(cardMixer);
            line.open(cardMixer);
            line.start();
            //play Frequency = 200 Hz for 1 seconds
            for(int i = 0; i < cardSounds.size(); i++)
            {
                byte[] currentWave = generateSineWave(cardSounds.get(i),1, mode);
                line.write(currentWave, 0, currentWave.length);
            }
            line.drain();
            line.close();
        } catch (Exception f) { }
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
        if(!theCurrentCardSlot.isEmpty())
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



    private static byte[] generateSineWave(int frequency, int seconds, int mode) {
        // 16kHz
        byte[] sin = new byte[(seconds * 16000) / 16];
        double samplingInterval = (double) (16000 / frequency);
        for (int i = 0; i < sin.length; i++) {
            double angle = (2 * Math.PI * i) / samplingInterval;
            {
                if(mode == 0)
                {
                    sin[i] = (byte) (Math.sin(angle) * 127);
                }
                else if(mode == 1)
                {
                    sin[i] = (byte) (Math.signum((Math.sin(angle) * 127)) * 20);
                }
            }
        }
        return sin;
    }

}
