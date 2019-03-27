import javax.swing.*;
import java.awt.*;

/**
 * Ian Anderson
 * 3/21/2019
 */
public class SortingMachineDrawing extends JPanel{


    private CardStorage sortBox;
    public SortingMachineDrawing()
    {
        setPreferredSize(new Dimension(800, 800));
        setOpaque(true);
        setVisible(true);
        sortBox = CardStorage.getInstance();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.GRAY);
        g.fillRect(100, 200, 150, 400);
        g.fillRect(100, 200, 950, 25);
        g.fillRect(1000, 200, 120, 400);
        for(int i = 100; i <= 1000; i += 110)
        {
            g.fillRect(i, 100, 25, 100);
        }
        int boxCounter = 0;
        if(!sortBox.getUnsortedCards().isEmpty() || !sortBox.getCardSlot(0).isEmpty()
         ||!sortBox.getCardSlot(1).isEmpty() || !sortBox.getCardSlot(2).isEmpty()
         ||!sortBox.getCardSlot(3).isEmpty() || !sortBox.getCardSlot(4).isEmpty()
         ||!sortBox.getCardSlot(5).isEmpty() || !sortBox.getCardSlot(6).isEmpty()
         ||!sortBox.getCardSlot(7).isEmpty())
        {
            for(int i = 125; i <= 1100; i += 110)
            {
                int boxSize;
                if(boxCounter == 8)
                {
                    boxSize = sortBox.getUnsortedCards().size();
                }
                else
                {
                    boxSize = sortBox.getCardSlot(boxCounter).size();
                }

                //System.out.println("Box Size = " + boxSize);
                for(int j = 0; j < boxSize; j++)
                {
                    //System.out.println("J value = " + j);
                    Color currentCardColor;
                    if(boxCounter == 8)
                    {
                        currentCardColor = sortBox.getUnsortedCards().get(j).getColor();
                    }
                    else
                    {
                        currentCardColor = sortBox.getCardSlot(boxCounter).get(j).getColor();
                    }
                    //System.out.println("Current Card Color = " + currentCardColor);
                    g.setColor(currentCardColor);
                    g.fillRect(i, 200 - (j * 10), 85, 10);
                }
                boxCounter++;
            }
        }

    }
}
