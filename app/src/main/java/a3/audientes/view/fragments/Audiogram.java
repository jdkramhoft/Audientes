package a3.audientes.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import a3.audientes.R;
import a3.audientes.dao.AudiogramDAO;
import a3.audientes.utils.SharedPrefUtil;

public class Audiogram extends Fragment {
    private AudiogramDAO audiogramDAO = AudiogramDAO.getInstance();

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;
    private  List<int[]> left;
    private  List<int[]> right;


    private int[] frequencies = {};
    private View v;

    private String LEFT_EAR_LABEL = "", RIGHT_EAR_LABEL = "";

    private final int[] colors = new int[] {
            Color.rgb(30, 176, 97),     // green
            Color.rgb(202, 0, 86),      // red
            Color.rgb(190, 195, 233)    // purple

    };
    static Context context1;
    public Audiogram() {

    }

    public static Audiogram newInstance(Context context) {
        context1 = context;
        return new Audiogram();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        audiogramDAO.setCurrent(Integer.parseInt(SharedPrefUtil.readSharedSetting(getContext(), "currentAudiogram", "0")));
        left = audiogramDAO.getCurrentAudiogram().getGraph();
        right = audiogramDAO.getCurrentAudiogram().getGraph();
        return inflater.inflate(R.layout.audiogram, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        audiogramDAO.setCurrent(Integer.parseInt(SharedPrefUtil.readSharedSetting(getContext(), "currentAudiogram", "0")));
        left = audiogramDAO.getCurrentAudiogram().getGraph();
        right = audiogramDAO.getCurrentAudiogram().getGraph();
        drawAudiogram(left, right, v);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        v = view.findViewById(R.id.chart);
        drawAudiogram(left, right, view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTab2ChildInteraction(Uri uri);
    }

    public void drawAudiogram(List<int[]> left, List<int[]> right, View v){
        RIGHT_EAR_LABEL = context1.getString(R.string.randlear);
        LineChart chart = v.findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(false);
        LineDataSet left_ear = new LineDataSet(makeEntries(left), LEFT_EAR_LABEL);
        left_ear.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // styling left ear
        left_ear.setColor(0);
        left_ear.setCircleColor(colors[0]);
        left_ear.setLineWidth(3f);
        left_ear.setCircleRadius(4f);
        left_ear.setDrawCircleHole(false);
        left_ear.setDrawValues(false);

        // right ear
        LineDataSet right_ear = new LineDataSet(makeEntries(right), RIGHT_EAR_LABEL);
        right_ear.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // styling right ear
        right_ear.setColor(colors[1]);
        right_ear.setCircleColor(colors[1]);
        right_ear.setLineWidth(3f);
        right_ear.setCircleRadius(4f);
        right_ear.setDrawCircleHole(false);
        right_ear.setDrawValues(false);

        // y axis
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setSpaceBottom(25f);
        yAxis.setSpaceTop(25f);
        yAxis.setDrawZeroLine(false);
        yAxis.setTextColor(colors[2]);
        yAxis.setDrawGridLines(false);
        yAxis.setLabelCount(3, true);

        chart.getAxisRight().setEnabled(false);

        // x axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setSpaceMin(100f);
        xAxis.setSpaceMax(100f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(colors[2]);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(30);
        xAxis.setLabelCount(5, true);

        // modify legend
        Legend legend = chart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextColor(colors[2]);
        legend.setTextSize(15f);


        // data
        List<ILineDataSet> data_lines = new ArrayList<>();
        data_lines.add(left_ear);
        data_lines.add(right_ear);
        LineData data = new LineData(data_lines);
        chart.setData(data);
    }

    private List<Entry> makeEntries(List<int[]> graph){
        int x = 0, y = 1;
        List<Entry> coordinates = new ArrayList<>();

        for(int i = 0; i < graph.size(); i++){
            coordinates.add(new Entry(graph.get(i)[x], graph.get(i)[y]));
        }
        Collections.sort(coordinates, new EntryXComparator());

        return coordinates;
    }
}
