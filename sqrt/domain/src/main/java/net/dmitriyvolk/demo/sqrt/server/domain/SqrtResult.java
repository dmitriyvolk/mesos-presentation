package net.dmitriyvolk.demo.sqrt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SqrtResult {

    @Id
    @GeneratedValue
    private long id;
    private long square;
    private float root;
    private long iterations;

    public SqrtResult(WorkResult wr) {
        square = wr.getSquare();
        root = wr.getApproximation();
        iterations = wr.getIterationNumer();
    }

    public SqrtResult() {
    }

    public long getId() {
        return id;
    }

    public long getSquare() {
        return square;
    }

    public float getRoot() {
        return root;
    }

    public long getIterations() {
        return iterations;
    }
}
