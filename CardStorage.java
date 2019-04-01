import java.util.ArrayList;
import java.util.Collections;

/**
 * Ian Anderson
 * 3/22/19
 */

public class CardStorage {
    private static CardStorage instance = null;
    private ArrayList<ArrayList<Card>> machineSlots;
    private ArrayList<Card> unsortedCards;
    public CardStorage()
    {
        machineSlots = new ArrayList<>(8);
        for (int i = 0; i < 8; i++)
        {
            machineSlots.add(new ArrayList<>());
        }
        unsortedCards = new ArrayList<>();
    }
    public void addCardToUnsorted(Card newCard)
    {
        unsortedCards.add(newCard);
    }
    public void removeCardFromUnsorted(Card oldCard)
    {
        unsortedCards.remove(oldCard);
    }
    public void removeCardFromUnsorted(int oldCardIndex)
    {
        unsortedCards.remove(oldCardIndex);
    }

    public void clearSlot(int slot)
    {
        machineSlots.get(slot).clear();
    }
    public void clearAllSlots(){
        for(ArrayList<Card> cards: machineSlots){
            cards.clear();
        }
        clearUnsorted();
    }
    public void clearUnsorted()
    {
        unsortedCards.clear();
    }
    public ArrayList<Card> getUnsortedCards() {
        return unsortedCards;
    }

    public void addCardToSlot(Card newCard, int slot)
    {
        machineSlots.get(slot).add(newCard);
    }
    public ArrayList<Card> getCardSlot(int slot)
    {
        return machineSlots.get(slot);
    }
    public static CardStorage getInstance()
    {
        if(instance == null)
        {
            instance = new CardStorage();
        }
        return instance;
    }
}
