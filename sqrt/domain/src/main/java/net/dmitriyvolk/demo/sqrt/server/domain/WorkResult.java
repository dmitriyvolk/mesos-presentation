package net.dmitriyvolk.demo.sqrt.server.domain;

public class WorkResult {

    private long square;
    private float approximation;
    private long iterationNumer;

    public WorkResult(long square, float approximation, long iterationNumer) {
        this.square = square;
        this.approximation = approximation;
        this.iterationNumer = iterationNumer;
    }

    public WorkResult() {
    }

    public long getSquare() {
        return square;
    }

    public float getApproximation() {
        return approximation;
    }

    public long getIterationNumer() {
        return iterationNumer;
    }

    @Override
    public String toString() {
        return "WorkResult{" +
                "square=" + square +
                ", approximation=" + approximation +
                ", iterationNumer=" + iterationNumer +
                '}';
    }
}
