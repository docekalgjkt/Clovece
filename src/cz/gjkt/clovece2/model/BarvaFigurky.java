package cz.gjkt.clovece2.model;

public class BarvaFigurky {

    private int poradi;
    private int start;
    private int cil;

    public BarvaFigurky(int poradi, int start, int pocetPoli){
        this.poradi = poradi;
        this.start = start;
        int cil = start - 1;
        if (start==0) cil = pocetPoli - 1;
    }

    public int getPoradi(){ return poradi;}

    public int getStart(){
        return start;
    }

    public int getCil(){
        return cil;
    }
}
