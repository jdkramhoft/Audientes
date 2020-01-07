package a3.audientes.model;

import java.util.ArrayList;
import java.util.List;

public class Audiogram {

    private List<int[]> graf = new ArrayList<>();

    public Audiogram() {}

    public List<int[]> getGraf() {
        return graf;
    }

    public void addIndex(int[] xy){
        graf.add(xy);
    }
}
