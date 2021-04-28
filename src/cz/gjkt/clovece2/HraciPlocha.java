package cz.gjkt.clovece2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HraciPlocha {

    private Map<BarvaFigurky, StartovniDomecek> startovniDomecekMap = new HashMap<>();
    private Map<BarvaFigurky, CilovyDomecek> cilovyDomecekMap = new HashMap<>();
    private Map<BarvaFigurky, List<Figurka>> nasazeneFigurky = new HashMap<>();
    private List<Figurka> hraciPole = new ArrayList<>();
    private List<BarvaFigurky> barvyFigurek = new ArrayList<>();

    public HraciPlocha(int pocetHracu, int pocetFigurek, int pocetMeziPoli){
        for(int i=0;i<pocetHracu;i++){
            //Vytvoření startovních domečků
            BarvaFigurky barvaFigurky = new BarvaFigurky(i+1,i*pocetMeziPoli,pocetHracu*pocetMeziPoli);
            barvyFigurek.add(i,barvaFigurky);
            StartovniDomecek startovniDomecek = new StartovniDomecek(barvaFigurky, pocetFigurek);
            startovniDomecekMap.put(barvaFigurky, startovniDomecek);
            //Vytvoření cílových domečků
            CilovyDomecek cilovyDomecek = new CilovyDomecek(pocetFigurek, barvaFigurky);
            cilovyDomecekMap.put(barvaFigurky, cilovyDomecek);
        }
        //Vytvoření hracího pole
        //Naplní hrací pole hodnotou null
        for(int i=0;i<pocetMeziPoli*pocetHracu;i++){
            hraciPole.add(i,null);
        }
    }

    @Override
    public String toString(){
        String pole = "";
        int poradi = 1;
        for (BarvaFigurky bf : barvyFigurek){
            pole += poradi++ + ": ";
            pole += startovniDomecekMap.get(bf)+"||";
            for (Figurka f : hraciPole){
                if (f == null) pole += "|0";
                else pole += "|"+f.getBarvaFigurky().getPoradi();
            }
            pole += cilovyDomecekMap.get(bf);
            pole+="|\n\r";
        }
        return pole;
    }
    public List<BarvaFigurky> getBarvyFigurek(){
        return barvyFigurek;
    }

    //Zjištění, se kterými figurkami může hráč hrát
    public List<Figurka> vratMozneFigurky(BarvaFigurky barvaFigurky, int hodnotaHodu){
        List<Figurka> figurky = new ArrayList<>();
        if ((nasazeneFigurky!=null)&&(nasazeneFigurky.get(barvaFigurky)!=null)){
            for(Figurka f : nasazeneFigurky.get(barvaFigurky)){
                if (muzuPohyb(f,hodnotaHodu)) figurky.add(f);
            }
        }
        figurky.addAll(cilovyDomecekMap.get(barvaFigurky).getFigurkyKPohybu());
        return figurky;
    }

    //Nasazení figurky na správné místo
    public void nasad(BarvaFigurky barvaFigurky){
        StartovniDomecek startovniDomecek = startovniDomecekMap.get(barvaFigurky);
        Figurka figurka = startovniDomecek.getFigurka();
        if (figurka!=null) nasad(figurka);
    }
    private void nasad(Figurka figurka){
        int start = figurka.getBarvaFigurky().getStart();
        if (muzuPohybNa(figurka, start)){
            Figurka ciziFigurka = hraciPole.get(start);
            if (ciziFigurka != null){
                try {
                    startovniDomecekMap.get(ciziFigurka.getBarvaFigurky()).vratFigurku();
                } catch (MocFigurekException e) {
                    e.printStackTrace();
                }
            }
            hraciPole.set(start, figurka);
        }
    }

    /** Posunutí figurky o daný počet míst
     *
     * @param figurka
     * @param pocetPoli
     *
     * Každá figurka má své startovací a cílové místo, pokud by hodnota pocetPoli překročila cíl, musí se pokusit posunout do cílového domečku.
     * Při pohybu figurky nesmí vstoupit na místo, kde už nějaká vlastní figurka stojí
     * Pokud při pohybu dojde k vyhození cizí figurky, musí se odložit do startovního domečku.
     *
     */
    public void posunFigurku(Figurka figurka, int pocetPoli){
        CilovyDomecek cilovyDomecek = cilovyDomecekMap.get(figurka.getBarvaFigurky());
        if (cilovyDomecek.jeZDomecku(figurka)){
            if (cilovyDomecek.jeVolno(cilovyDomecek.getPozice(figurka)+pocetPoli)){
                cilovyDomecek.posunO(figurka,pocetPoli);
            }
        } else {
            int staraPozice = hraciPole.indexOf(figurka);
            int novaPozice = getNovePole(figurka, pocetPoli);
            if (muzuPohyb(figurka, pocetPoli)) {
                if (prekroceniCile(figurka, pocetPoli)) {
                    cilovyDomecekMap.get(figurka.getBarvaFigurky()).vlozFigurku(figurka, kolikVDomecku(figurka, pocetPoli));
                    hraciPole.set(staraPozice, null);
                    //nasazeneFigurky.get(figurka.getBarvaFigurky()).remove(figurka);
                } else {
                    hraciPole.set(novaPozice, figurka);
                    hraciPole.set(staraPozice, null);
                }
            }
        }
    }
    public void posunFigurku(int odkud, int pocetPoli){
        Figurka f = hraciPole.get(odkud);
        if (f != null) posunFigurku(f,pocetPoli);
    }


    /**
     *  Ověření možnosti pohybu figurky
     * @param f
     * @param pocetPoli
     * @return
     *
     * Při pohybu figurky se musí ověřit, zda na cílovém místě nestojí vlastní figurka, nebo při překročení cílového pole, můžeme figurku umístit do cílového domečku.
     */
    private boolean muzuPohyb(Figurka f, int pocetPoli) {
        boolean vysledek = false;
        int novePole = getNovePole(f,pocetPoli);
        //překročení cíle
        if (prekroceniCile(f,pocetPoli)) {
            int kolikDomu = novePole - f.getBarvaFigurky().getCil()-1;
            vysledek = cilovyDomecekMap.get(f.getBarvaFigurky()).jeVolno(kolikDomu);
        } else {
            //nepřekročení cíle, ošetření volnosti pole na vlastní figurku
            vysledek = muzuPohybNa(f,novePole);
        }
        return vysledek;
    }
    private boolean muzuPohybNa(Figurka f, int pole){
        boolean vysledek = false;
        Figurka figurkaPredeMnou = hraciPole.get(pole);
        if (figurkaPredeMnou == null) vysledek = true;
        else if (figurkaPredeMnou.getBarvaFigurky() != f.getBarvaFigurky()) vysledek = true;
        return vysledek;
    }
    private boolean prekroceniCile(Figurka f, int pocetPoli){
        return ((getNovePole(f,pocetPoli)>f.getBarvaFigurky().getCil())&&(hraciPole.indexOf(f)<f.getBarvaFigurky().getCil()));
    }
    private int kolikVDomecku(Figurka figurka, int pocetPoli) {
        return getNovePole(figurka,pocetPoli) - figurka.getBarvaFigurky().getCil() - 1;
    }
    /**
     * výpočet indexu nového pole, pokud hráč překročí konec hracího pole
     * @param f
     * @param pocetPoli
     * @return
     */
    private int getNovePole(Figurka f, int pocetPoli){
        int novePole = hraciPole.indexOf(f)+pocetPoli;
        if (novePole>hraciPole.size()-1) novePole -= hraciPole.size();
        return novePole;
    }

}
