package net.dmitriyvolk.demo.sqrt.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WorkItem {

    @Id
    @GeneratedValue
    private long id;

    private long square;
    private float currentApproximation;
    private long iterationNumber;

    public long getId() {
        return id;
    }

    public long getSquare() {
        return square;
    }

    public float getCurrentApproximation() {
        return currentApproximation;
    }

    public long getIterationNumber() {
        return iterationNumber;
    }

    public WorkItem(WorkResult wr) {
        square = wr.getSquare();
        currentApproximation = wr.getApproximation();
        iterationNumber = wr.getIterationNumer() + 1;
    }

    public WorkItem(Input input) {
        square = input.getSquare();
        currentApproximation = square / 2;
        iterationNumber = 0;
    }

    public WorkItem() {
    }

    @Override
    public String toString() {
        return "WorkItem{" +
                "id=" + id +
                ", square=" + square +
                ", currentApproximation=" + currentApproximation +
                ", iterationNumber=" + iterationNumber +
                '}';
    }
}
