import javax.swing.*;
import java.awt.*;

/**
 * Ian Anderson
 * 3/21/2019
 */
public class CardRenderer extends JPanel{

    private CardStorage sortBox;
    public CardRenderer()
    {
        sortBox = CardStorage.getInstance();
        setPreferredSize(new Dimension(800, 800));
        setOpaque(true);
        setVisible(true);
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
    }
}
