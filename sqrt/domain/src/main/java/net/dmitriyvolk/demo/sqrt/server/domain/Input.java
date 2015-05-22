package net.dmitriyvolk.demo.sqrt.server.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners({Input.InputListener.class})
public class Input implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    private long square;

    private Date created;

    public Input(long square) {
        this.square = square;
    }

    private Input() {}

    public long getId() {
        return id;
    }

    public long getSquare() {
        return square;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "Input{" +
                "id=" + id +
                ", square=" + square +
                ", created=" + created +
                '}';
    }

    public static class InputListener {

        @PrePersist
        public void setCreated(Input i) {
            i.created = new Date();
        }
    }
}
