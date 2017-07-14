package com.bbva;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by jorge on 14/07/17.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        if (args.length < 1) {
            System.err.println("Por favor, indica el numero de hilos a emplear.");
            System.exit(1);
        }

        int numThreads = Integer.parseInt(args[0]);

        if (numThreads < 1){
            System.err.println("El numero de hilos deberia ser al menos 1.");
            System.exit(1);
        }

        LongAdder counter = new LongAdder();

        List<CalculationThread> runningCalcs = new ArrayList<>();
        List<Thread> runningThreads = new ArrayList<Thread>();

        System.out.printf("Starting %d threads.\n", numThreads);

        for (int i = 0; i < numThreads; i++){
            CalculationThread calculationThread = new CalculationThread(counter);
            Thread thread = new Thread(calculationThread);
            runningCalcs.add(calculationThread);
            runningThreads.add(thread);
            thread.start();
        }

        for (int i = 0; i < 15; i++)
        {
            counter.reset();
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                break;
            }
            System.out.printf("[%d] Calculations per second: %d (%.2f per thread)\n",
                    i,
                    counter.longValue(),
                    (double)(counter.longValue()) / numThreads
            );
        }

        for (int i = 0; i < runningCalcs.size(); i++)
        {
            runningCalcs.get(i).stop();
            runningThreads.get(i).join();
        }

    }

}
