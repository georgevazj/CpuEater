package com.bbva;

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by jorge on 14/07/17.
 */
public class CalculationThread implements Runnable {

    private final Random rng;
    private final LongAdder calculationsPerformed;
    private boolean stopped;
    private double store;

    public CalculationThread(LongAdder calculationsPerformed) {
        this.calculationsPerformed = calculationsPerformed;
        this.stopped = false;
        this.rng = new Random();
        this.store = 1;
    }

    public void stop(){
        this.stopped = true;
    }

    @Override
    public void run() {

        while (! this.stopped){
            double r = this.rng.nextFloat();
            double v = Math.sin(Math.cos(Math.sin(Math.cos(r))));
            this.store *= v;
            this.calculationsPerformed.add(1);
        }

    }
}
