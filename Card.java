import java.awt.*;
import java.util.ArrayList;

/**
 * Ian Anderson
 * 3/21/2019
 */
public class Card {

    private String name;
    private String category;
    private String description;
    private Color theColor;
    private int theSound;

    public Card(String cardName, String cardType, String cardDesc, Color c, int s)
    {
        name = cardName;
        category = cardType;
        description = cardDesc;
        theColor = c;
        theSound = s;
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

    public Color getColor()
    {
        return theColor;
    }

    public int getSound()
    {
        return theSound;
    }

    public int getHSBColorSingle()
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
        return  (convertedColors[0] * 1000000) + (convertedColors[1] * 1000) + convertedColors[2];
    }
    public ArrayList<Integer> getHSBColorArray()
    {
        float[] colors = Color.RGBtoHSB(theColor.getRed(), theColor.getGreen(), theColor.getBlue(), null);
        ArrayList<Integer> convertedColors = new ArrayList<>();
        for(int i = 0; i < colors.length; i++)
        {
            if(i == 0)
            {
                convertedColors.add(Math.round(colors[i] * 360f));
            }
            else
            {
                convertedColors.add(Math.round(colors[i] * 100f));
            }
        }
        return convertedColors;
    }
    public String toString()
    {
        return "Name: " + name + "\nCategory: " + category + "\nDescription: " + description + "\nColor: " + theColor.toString() + "\nFrequency: " + theSound;
    }
}
