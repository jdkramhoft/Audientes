package a3.audientes.dto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import a3.audientes.typeconverters.AudiogramConverter;
import a3.audientes.typeconverters.AudiogramTimeConverter;

@Entity(tableName = "audiogram_table")
public class Audiogram implements Comparable<Audiogram> {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(AudiogramConverter.class)
    private ArrayList<Integer> x = new ArrayList<>();
    @TypeConverters(AudiogramConverter.class)
    private ArrayList<Integer> y = new ArrayList<>();
    @TypeConverters(AudiogramTimeConverter.class)
    private Date date;


    public Audiogram() {}

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Audiogram audiogram = (Audiogram) other;
        return id == audiogram.id &&
                x == audiogram.x &&
                y == audiogram.y &&
                date == audiogram.date;
    }


    @NonNull
    @Override
    public String toString() {
        return "Audiogram{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Audiogram other) {
        return this.date.compareTo(other.date) > 0 ? -1 : 1 ;
    }

    // Getters and setters

    public ArrayList<int[]> getGraph(){
        ArrayList<int[]> temp = new ArrayList<>();
        for(int i = 0; i < x.size(); i++){
            int[] xy = {x.get(i), y.get(i)};
            temp.add(xy);
        }
        return temp;
    }

    public void addIndex(int[] xy){
        x.add(xy[0]);
        y.add(xy[1]);
    }

    public String getDateString() {
        DateFormat x = SimpleDateFormat.getDateInstance();
        return x.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getX() {
        return x;
    }

    public void setX(ArrayList<Integer> x) {
        this.x = x;
    }

    public ArrayList<Integer> getY() {
        return y;
    }

    public void setY(ArrayList<Integer> y) {
        this.y = y;
    }

    public Date getDate() {
        return date;
    }

}
