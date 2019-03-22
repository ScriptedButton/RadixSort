import java.awt.*;
/**
 * Ian Anderson
 * 3/21/2019
 */
public class Card {

    private String name;
    private String category;
    private String description;
    private Color theColor;

    public Card(String cardName, String cardType, String cardDesc, Color c)
    {
        name = cardName;
        category = cardType;
        description = cardDesc;
        theColor = c;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return category;
    }

    public String getDesc()
    {
        return description;
    }

    public int[] getHSBColor()
    {
        float[] colors = Color.RGBtoHSB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), null);
        int[] convertedColors = new int[3];
        for(int i = 0; i < colors.length; i++)
        {
            if(i == 0)
            {
                convertedColors[i] = Math.round(colors[i] * 360f);
            }
            else
            {
                convertedColors[i] = Math.round(colors[i] * 100f);
            }
        }
        return convertedColors;
    }
}
