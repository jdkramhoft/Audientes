package a3.audientes.models;

import androidx.room.Entity;

@Entity(tableName = "program_table")
public class Program {

    private int low;
    private int low_plus;
    private int middle;
    private int high;
    private int high_plus;

    public Program(int low, int low_plus, int middle, int high, int high_plus) {
        this.low = low;
        this.low_plus = low_plus;
        this.middle = middle;
        this.high = high;
        this.high_plus = high_plus;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getLow_plus() {
        return low_plus;
    }

    public void setLow_plus(int low_plus) {
        this.low_plus = low_plus;
    }

    public int getMiddle() {
        return middle;
    }

    public void setMiddle(int middle) {
        this.middle = middle;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getHigh_plus() {
        return high_plus;
    }

    public void setHigh_plus(int high_plus) {
        this.high_plus = high_plus;
    }
}
