package a3.audientes.fragments;

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
import android.widget.RelativeLayout;


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
import java.util.Random;

import a3.audientes.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Audiogram.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Audiogram#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Audiogram extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;

    private static int[][] left = {{500, 38},{1000, 40},{1500, 50},{2000, 40},{2500, 60},{3000,58},{3500,50}};
    private static int[][] right = {{500, 35},{1000, 45},{1500, 38},{2000, 55},{2500, 45},{3000,70},{3500,30}};
    private int[] freqs = {};
    private LineChart chart;

    private final String LEFT_EAR_LABEL = "Left Ear", RIGHT_EAR_LABEL = "Right Ear";

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
    public static Audiogram newInstance(byte[] left_ear, byte[] right_ear, byte[] freqs) {
        Audiogram fragment = new Audiogram();
        Bundle args = new Bundle();
        args.putByteArray(ARG_PARAM1, left_ear);
        args.putByteArray(ARG_PARAM2, right_ear);
        args.putByteArray(ARG_PARAM3, freqs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //left = getArguments().getByteArray(ARG_PARAM1);
            //right = getArguments().getByteArray(ARG_PARAM2);
            //freqs = getArguments().getByteArray(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.audiogram, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        chart = view.findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);
        chart.setViewPortOffsets(100f, 100f, 100f, 100f);

        // left ear
        LineDataSet left_ear = new LineDataSet(makeEntries(left), LEFT_EAR_LABEL);
        left_ear.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // styling left ear
        left_ear.setColor(colors[0]);
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

        // If animate(...) is called, no further calling of invalidate() is necessary to refresh the chart
        // TODO: Find a way to make chart.animateX() smooth instead of chart animateY();
        //chart.animateX(2500, Easing.EaseInCubic);
        //chart.animateY(2000, Easing.EaseInCubic);
        chart.animateXY(1500,1200, Easing.EaseInCubic);

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

    private List<Entry> makeEntries(int[][] data){
        int x = 0, y = 1;
        List<Entry> coordinates = new ArrayList<>();

        for (int[] coordinate : data) {
            coordinates.add(new Entry(coordinate[x], coordinate[y]));
        }

        Collections.sort(coordinates, new EntryXComparator());

        return coordinates;
    }
}
