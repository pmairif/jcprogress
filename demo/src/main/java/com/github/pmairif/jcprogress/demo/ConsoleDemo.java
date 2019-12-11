/*
 * Copyright 2009-2019 Patrick Mairif.
 * The program is distributed under the terms of the Apache License (ALv2).
 *
 */
package com.github.pmairif.jcprogress.demo;

import com.github.pmairif.jcprogress.BubbleCharIndicator;
import com.github.pmairif.jcprogress.ConsoleProgressBarThread;
import com.github.pmairif.jcprogress.ConsoleProgressIndicatorThread;
import com.github.pmairif.jcprogress.ConsoleProgressThreadBase;
import com.github.pmairif.jcprogress.CounterClockwiseRotCharIndicator;
import com.github.pmairif.jcprogress.ProgressStatusProvider;
import com.github.pmairif.jcprogress.ProgressThread;

import java.util.Locale;

/**
 * @author pmairif
 */
public class ConsoleDemo implements ProgressStatusProvider {
    int count = 0;

    int countAll = 0;

    int sleep;

    public ConsoleDemo(int countAll) {
        this(countAll, 1);
    }

    public ConsoleDemo(int countAll, int sleep) {
        this.countAll = countAll;
        this.sleep = sleep;
    }

    public static void main(String[] args) throws InterruptedException {
        ConsoleDemo demo = new ConsoleDemo(50000);

        ConsoleProgressThreadBase progress = null;

        System.err.println("\ndefault config:");
        progress = new ConsoleProgressBarThread(System.err, demo);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nwithout percentage in english:");
        progress = new ConsoleProgressBarThread(System.err, demo, Locale.ENGLISH);
        progress.setShowPercentage(false);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nwith counter in german:");
        progress = new ConsoleProgressBarThread(System.err, demo, Locale.GERMAN);
        progress.setShowPercentage(false);
        progress.setShowCounter(true);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nwith counter and percentage:");
        progress = new ConsoleProgressBarThread(System.err, demo);
        progress.setShowCounter(true);
        progress.setShowPercentage(true);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nwith bubbles:");
        progress = new ConsoleProgressBarThread(System.err, demo);
        progress.setShowCounter(false);
        progress.setCharIndicator(new BubbleCharIndicator());
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nwithout end date:");
        progress = new ConsoleProgressBarThread(System.err, demo);
        progress.setShowCounter(false);
        progress.setShowEndDate(false);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nwith remaining time:");
        progress = new ConsoleProgressBarThread(System.err, demo);
        progress.setShowCounter(false);
        progress.setShowEndDate(false);
        progress.setShowRemainingTime(true);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar


        System.err.println("\nno progress bar, just an indicator:");
        progress = new ConsoleProgressIndicatorThread(System.err, demo);
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\ncounter clockwise rotating chars:");
        progress = new ConsoleProgressIndicatorThread(System.err, demo);
        progress.setCharIndicator(new CounterClockwiseRotCharIndicator());
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nindicator with bubbles:");
        progress = new ConsoleProgressIndicatorThread(System.err, demo);
        progress.setCharIndicator(new BubbleCharIndicator());
        progress.start();
        demo.run();
        progress.waitToStop();    //stop progress bar

        System.err.println("\nprogress bar without thread:");
        progress = new ConsoleProgressBarThread(System.err, demo);
        demo.run(progress);
    }

    public void run() throws InterruptedException {
        for (count = 0; count < countAll; count += 100) {
            Thread.sleep(sleep);
        }
    }

    public void run(ProgressThread progress) throws InterruptedException {
        for (count = 0; count < countAll; count += 100) {
            progress.update(false);
            Thread.sleep(sleep);
        }
        progress.afterLastInvoking();
    }

    public String getProgressStatusText() {
        return "[I'm a dummy]";
    }

    public int getCurrentProgressCount() {
        return count;
    }

    public int getWholeProcessCount() {
        return countAll;
    }
}
