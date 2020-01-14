package a3.audientes.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "program_table")
public class Program {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int low;
    private int low_plus;
    private int middle;
    private int high;
    private int high_plus;
    private int type;
    private boolean deletable;

    public Program(String name, int low, int low_plus, int middle, int high, int high_plus, int type, boolean deletable) {
        this.name = name;
        this.low = low;
        this.low_plus = low_plus;
        this.middle = middle;
        this.high = high;
        this.high_plus = high_plus;
        this.type = type;
        this.deletable = deletable;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
