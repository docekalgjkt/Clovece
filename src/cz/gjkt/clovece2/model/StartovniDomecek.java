package cz.gjkt.clovece2.model;

public class StartovniDomecek {

    private BarvaFigurky barvaFigurky;
    private int maximalniPocetFigurek;
    private int aktualniPocetFigurek;

    //konstruktory - jak ten objekt vytvořit
    public StartovniDomecek(BarvaFigurky bf, int pocetFigurek){
        barvaFigurky = bf;
        maximalniPocetFigurek = pocetFigurek;
        aktualniPocetFigurek = pocetFigurek;
    }

    //přístupové metody
    //určují vlastnosti objektu, zpřístupňují atributy (data) nebo vypočítávají hodnotu z atributů
    //určují, zda je vlastnost read only = existují jen gettery
    public BarvaFigurky getBarvaFigurky(){return barvaFigurky;}
    public boolean jePrazdny(){return aktualniPocetFigurek==0;}

    /**
     * Metoda, která vytvoří figurku dané barvy, používá se ve chvíli, kdy potřebujeme fifurku nasadit
     * @return Figurka - vrací figurku, pokud je možnost
     * @throws IllegalStateException - v případě prázdného seznamu vyhodí chybu
     */
    public Figurka getFigurka() throws IllegalStateException {
        if(jePrazdny()){
            throw new IllegalStateException("Nelze nasadit figurku, domeček je prázdný");
        }
        else {
            aktualniPocetFigurek--;
            return new Figurka(barvaFigurky);
        }
    }

    /**
     * metoda, která přijímá vyhozenou figurku zpět do domečku
     * @throws MocFigurekException - pokud je domeček plný, vyhodí výjimku
     */
    public void vratFigurku() throws MocFigurekException {
        try {
            if (aktualniPocetFigurek==maximalniPocetFigurek) throw new MocFigurekException();
            aktualniPocetFigurek++;
        }catch (MocFigurekException e){
            e.printStackTrace();
        }
    }

}
