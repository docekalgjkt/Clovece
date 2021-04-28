package cz.gjkt.clovece2;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class CilovyDomecek {

    private List<Figurka> figurky;
    private BarvaFigurky barvaFigurky;
    private int pocet;

    //Konstruktory
    public CilovyDomecek(int pocet, BarvaFigurky barvaFigurky){
        figurky = new ArrayList<>(pocet);
        this.pocet = pocet;
        this.barvaFigurky = barvaFigurky;
        for(int i = 0; i < pocet; i++){figurky.add(i,null);}
    }

    @Override
    public String toString(){
        String pole = "|";
        for (Figurka f : figurky){
            if (f==null) pole += "|0";
            else pole += "|1";
        }
        return pole;
    }

    //Zjišťuje, zda je na určené pozici volno, v případě, že je zadáno větší číslo vyhodí chybu

    /**
     *
     * @param pozice - pozice, na kterou chceme vložit figurku
     * @return - boolean - vrací informaci, zda je pole volné
     * @throws IllegalArgumentException - v případě chybné pozice vyhodí chybu
     */
    public boolean jeVolno(int pozice) throws IllegalArgumentException {
        if (pozice<figurky.size()) return (figurky.get(pozice)==null);
        else throw new IllegalArgumentException("Tolik míst v domečku není.");
    }

    public boolean jeZDomecku(Figurka figurka){
        return (figurky.contains(figurka));
    }

    public int getPozice(Figurka figurka){
        return figurky.indexOf(figurka);
    }

    /**
     * Metoda vloží do seznamu domečku figurku na odpovídající pozici
     * @param figurka - konkrétní figurka
     * @param pozice - konkrétní pozice
     * @throws InvalidParameterException - v případě, že se snažíme vložit figurku jiné barvy, oznámí chybu
     * @throws IllegalStateException - v případě, že je místo obsazené, oznámí chybu, je potřeba před vložením možnost pohybu ověřit
     */
    public void vlozFigurku(Figurka figurka, int pozice) throws InvalidParameterException, IllegalStateException {
        if (jeVolno(pozice)) {
            if (figurka.getBarvaFigurky()==barvaFigurky) {
                figurky.set(pozice, figurka);
            } else throw new InvalidParameterException("Barva figurky neodpovídá.");
        } else throw new IllegalStateException("Místo v domečku je obsazené");
    }

    /**
     * Metoda zjišťuje, zda je domeček plnně obsazený
     * @return boolean
     */
    public boolean jePlno(){
        for (Figurka f : figurky){
            if (f==null){return false;}
        }
        return true;
    }

    /**
     *
     * @param figurka
     * @param kolik
     */
    public void posunO(Figurka figurka, int kolik){
        try {
            int pozice = figurky.indexOf(figurka);
            vlozFigurku(figurka, pozice + kolik);
            figurky.set(pozice, null);
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (InvalidParameterException e){
            e.printStackTrace();
        }
    }

    /**
     * Zjišťuje, zda je možnost pohybu v domečku
     * @return boolean
     */
    public boolean jePohyb(){
        List<Figurka> figurkaList = getFigurkyKPohybu();
        return (!figurkaList.isEmpty());
    }

    /**
     * Metoda vrací seznam figurek, se kterými můžeme udělat ještě nějaký pohyb v domečku
     * @return List<Figurka> seznam figurek, se kterými můžeme pohnout v domečku
     * @return null - v případě neexistence figurky vrací null
     */
    public List<Figurka> getFigurkyKPohybu(){
        List<Figurka> figurkaList = new ArrayList<>();
        for (Figurka f: figurky){
            for (int i = figurky.indexOf(f); i < pocet; i++) {
                if (figurky.get(i)==null) figurkaList.add(f);
            }
        }
        if (figurkaList.size() > 0) {return figurkaList;}
        else return null;
    }
}
