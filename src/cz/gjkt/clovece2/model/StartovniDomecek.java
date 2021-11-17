package cz.gjkt.clovece2.model;


import java.util.ArrayList;

public class StartovniDomecek {

    private BarvaFigurky barvaFigurky;
    private int pocetFigurek;
    private ArrayList<Figurka> figurky = new ArrayList<>();

    //konstruktory - jak ten objekt vytvořit
    public StartovniDomecek(BarvaFigurky bf, int pocetFigurek){
        barvaFigurky = bf;
        this.pocetFigurek = pocetFigurek;
        for(int i = 0; i<pocetFigurek;i++){
            figurky.add(new Figurka(bf));
        }
    }

    //přístupové metody
    //určují vlastnosti objektu, zpřístupňují atributy (data) nebo vypočítávají hodnotu z atributů
    //určují, zda je vlastnost read only = existují jen gettery
    public BarvaFigurky getBarvaFigurky(){return barvaFigurky;}
    public boolean jePrazdny(){return figurky.isEmpty();}

    /**
     * Metoda, která vytvoří figurku dané barvy, používá se ve chvíli, kdy potřebujeme fifurku nasadit
     * @return Figurka - vrací figurku, pokud je možnost
     * @throws IllegalStateException - v případě prázdného seznamu vyhodí chybu
     */
    public Figurka removeFigurka() throws IllegalStateException {
        if(jePrazdny()){
            throw new IllegalStateException("Nelze nasadit figurku, domeček je prázdný");
        }
        else {
            return figurky.remove(figurky.size() - 1);
        }
    }

    public Figurka removeFigurka(Figurka figurka){
        figurky.remove(figurka);
        return figurka;
    }

    public Figurka getFigurka(int index) throws IllegalStateException {
        if (jePrazdny()){
            throw new IllegalStateException("Domeček je prázdný");
        }
        else {
            return figurky.get(index);
        }
    }

    /**
     * metoda, která přijímá vyhozenou figurku zpět do domečku
     * @throws MocFigurekException - pokud je domeček plný, vyhodí výjimku
     */
    public void vratFigurku(Figurka figurka) throws MocFigurekException {
        try {
            if (figurky.size()==pocetFigurek) throw new MocFigurekException();
            figurky.add(figurka);
        }catch (MocFigurekException e){
            e.printStackTrace();
        }
    }

}
