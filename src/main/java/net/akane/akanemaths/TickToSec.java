package net.akane.akanemaths;

public class TickToSec {
    private final int number;
    public TickToSec(int ticks){
        this.number = ticks;
    }

    public double GetSeconds(){
        return (double) 20 / number;
    }
}
