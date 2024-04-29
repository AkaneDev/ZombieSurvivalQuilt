package net.akane.akanemaths;

public class SecToTick {
    private final int number;
    public SecToTick(int secs){
        this.number = secs;
    }

    public int GetTicks(){
        return 20 * number;
    }

    public int Ticked() {
        return number - 1;
    }
}
