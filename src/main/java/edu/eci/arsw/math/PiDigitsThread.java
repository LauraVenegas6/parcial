package edu.eci.arsw.math;

import java.util.List;
import java.util.ArrayList;

public class PiDigitsThread extends Thread {
    private int start;
    private int end; 
    private List<Integer> digits;

    public static final Object lock = new Object();

    public PiDigitsThread(int start, int end, List<Integer> digits){
        this.start = start;
        this.end = end;
        this.digits = new ArrayList<>();
    }
    
    @Override
    public void run() {
        formulaBBP(start, end);
    }

    private void formulaBBP(int start, int end) {
        long Pause = System.currentTimeMillis();
        int procesados = 0;
        for (int n = start; n < end; n++) { 
            double sum = 0.0;
            for (int k = 0; k <= n; k++) {
                sum += (1.0 / Math.pow(16, k)) * 
                       ((4.0 / (8 * k + 1)) - 
                        (2.0 / (8 * k + 4)) - 
                        (1.0 / (8 * k + 5)) - 
                        (1.0 / (8 * k + 6)));
            }
            int digit = (int) ((sum - Math.floor(sum)) * 16);
            digits.add(digit);
            procesados++;

            if (System.currentTimeMillis() - Pause >= 5000) {
                System.out.println("Hilo " + this.getName() + " ha procesado " + procesados + " dígitos.");
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Pause = System.currentTimeMillis();
            }
        }
        
        if (procesados > 0 && System.currentTimeMillis() - Pause < 5000) {
            System.out.println("Hilo " + this.getName() + " ha procesado " + procesados + " dígitos.");
        }
        }
    

    public List<Integer> getDigits() {
        return digits;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;

    }
}
    
