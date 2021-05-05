package cz.gjkt.clovece2.model;

import java.util.Random;

public class HraciKostka {
    private int pocetSten;
    private Random generator;

    public HraciKostka(int pocetSten){
        this.pocetSten = pocetSten;
        generator = new Random();
    }

    public HraciKostka(){
        this(6);
    }

    public int getPocetSten(){
        return pocetSten;
    }

    public int hod(){
        int hod;
        hod = generator.nextInt(pocetSten) + 1;
        return hod;
    }
}
