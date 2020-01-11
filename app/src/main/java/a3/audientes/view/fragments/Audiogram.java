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


import com.github.mikephil.charting.animation.Easing;
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

import a3.audientes.R;
import a3.audientes.model.AudiogramManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Audiogram.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Audiogram#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Audiogram extends Fragment {
    private AudiogramManager audiogramManager = AudiogramManager.getInstance();

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;

    private  List<int[]> left = audiogramManager.getAudiograms().get(audiogramManager.getAudiograms().size()-1).getGraf();
    private  List<int[]> right = audiogramManager.getAudiograms().get(audiogramManager.getAudiograms().size()-1).getGraf();


    private int[] freqs = {};
    private View v;

    private final String LEFT_EAR_LABEL = "", RIGHT_EAR_LABEL = "Right and left Ear";

    private final int[] colors = new int[] {
            Color.rgb(30, 176, 97),     // green
            Color.rgb(202, 0, 86),      // red
            Color.rgb(190, 195, 233)    // purple

    };

    public Audiogram() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Audiogram.
     */
    // TODO: Rename and change types and number of parameters
    public static Audiogram newInstance() {
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
        return inflater.inflate(R.layout.audiogram, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        left = audiogramManager.getAudiograms().get(audiogramManager.getAudiograms().size()-1).getGraf();
        right = audiogramManager.getAudiograms().get(audiogramManager.getAudiograms().size()-1).getGraf();
        drawAudiogram(left, right, v);
        // left ear
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        v = view.findViewById(R.id.chart);
        drawAudiogram(left, right, view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onTab2ChildInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTab2ChildInteraction(Uri uri);
    }

    public void drawAudiogram(List<int[]> left, List<int[]> right, View v){

        LineChart chart = v.findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);
        chart.setViewPortOffsets(100f, 100f, 100f, 100f);
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
        chart.animateXY(150,120, Easing.EaseInCubic);
    }

    private List<Entry> makeEntries(List<int[]> graf){
        int x = 0, y = 1;
        List<Entry> coordinates = new ArrayList<>();

        for(int i = 0; i < graf.size(); i++){
            coordinates.add(new Entry(graf.get(i)[x], graf.get(i)[y]));
        }
        Collections.sort(coordinates, new EntryXComparator());

        return coordinates;
    }
}
