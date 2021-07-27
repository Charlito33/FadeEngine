package fr.charlito33.fadeengine.sdk.utils;

public class RepeatedTask {
    private static final long STARTED_TIME = System.currentTimeMillis();

    private Thread thread;
    private Runnable runnable;
    private boolean running;
    private long start;
    private int fps;

    public RepeatedTask(Runnable runnable, int fps, boolean run) {
        this.runnable = runnable;
        running = true;
        this.fps = fps;
        thread = new Thread(() -> {
            while (running) {
                this.runnable.run();
                capFrameRate(fps);
            }
        });

        start = 0;

        if (run) {
            thread.start();
        }
    }

    public RepeatedTask(Runnable runnable, int fps) {
        this(runnable, 60, true);
    }

    public RepeatedTask(Runnable runnable) {
        this(runnable, 60);
    }

    public void run() {
        thread.start();
    }

    private void capFrameRate(int fps) {
        long diff;
        double wait = (1.0 / fps) * 2000;
        diff = getMillisSinceStarted() - start;
        start = getMillisSinceStarted();

        if (diff <= wait) {
            try {
                Thread.sleep((long)wait - diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private long getSecondsSinceStarted() {
        return getMillisSinceStarted() / 1000;
    }

    private long getMillisSinceStarted() {
        return System.currentTimeMillis() - STARTED_TIME;
    }
}