package com.fovea.govhackguardian;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fovea.govhackguardian.model.AlcoholConsumption;
import com.fovea.govhackguardian.model.DomesticAssault;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ValueFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DomesticAssaultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DomesticAssaultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DomesticAssaultsFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    List<DomesticAssault> mDomesticAssaults = new ArrayList<>();

    private BarChart mBarChart;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DomesticAssaultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DomesticAssaultsFragment newInstance(String param1, String param2) {
        DomesticAssaultsFragment fragment = new DomesticAssaultsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DomesticAssaultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_domestic_assaults, container, false);

        mBarChart = (BarChart) view.findViewById(R.id.bar_chart);

        mBarChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) getActivity());

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);

        mBarChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        mBarChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(8);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8);
        rightAxis.setSpaceTop(15f);

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();

        loadCsvData();

        BarData barData = new BarData(getXAxisValues(), getBarDataSet());
        mBarChart.setData(barData);
        mBarChart.notifyDataSetChanged();
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        for (DomesticAssault da : mDomesticAssaults) {
            xAxis.add(da.getLGA());
        }

        return xAxis;
    }

    private ArrayList<BarDataSet> getBarDataSet() {
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();


        ArrayList<BarEntry> barEntryList = new ArrayList<>();
        int idx = 0;
        for (DomesticAssault da : mDomesticAssaults) {

            if (da.getLGA().equals("TOTAL")) {
                BarEntry barEntry = new BarEntry(Float.parseFloat(da.getLGA()), idx);
                barEntryList.add(barEntry);
                idx += 1;

            }

            BarDataSet barDatSet = new BarDataSet(barEntryList, da.getLGA());
            barDatSet.setColors(ColorTemplate.COLORFUL_COLORS);

            barEntryList.clear();

            barDataSets.add(barDatSet);
        }

        return barDataSets;
    }

    private void loadCsvData() {

        String domesticAssaultsCsvFile = "assaults_domestic_syd.csv";
        AssetManager assetManager = getActivity().getAssets();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(DomesticAssault.class);
//        CsvSchema csvSchema = csvMapper.schemaFor(AlcoholConsumption.class).withHeader();


        try {
//            mapper.reader(AlcoholConsumption.class).with(bootstrapSchema).readValue(assetManager.open(alcoholConsumptionCsvFile));

            MappingIterator<DomesticAssault> mi = csvMapper
                    .reader(DomesticAssault.class)
//                    .with(bootstrapSchema)
                    .with(csvSchema)
                    .readValues(assetManager.open(domesticAssaultsCsvFile));

            while (mi.hasNextValue()) {
                DomesticAssault domesticAssault = mi.nextValue();
                mDomesticAssaults.add(domesticAssault);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Log.d("CSV", mDomesticAssaults.get(0).toString());
//        Log.d("CSV", mDomesticAssaults.get(1).toString());
    }


//    private ArrayList<String> getXAxisValues() {
//        ArrayList<String> xAxis = new ArrayList<>();
//        xAxis.add("JAN");
//        xAxis.add("FEB");
//
//        return xAxis;
//    }

//    private ArrayList<BarDataSet> getBarDataSet() {
//        ArrayList<BarDataSet> barDataSets = new ArrayList<>();
//
//        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
//
//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
//        valueSet1.add(v1e4);
//        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
//        valueSet1.add(v1e5);
//        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
//        valueSet1.add(v1e6);
//
//        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
//
//        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
//        valueSet2.add(v2e1);
//        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
//        valueSet2.add(v2e2);
//        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
//        valueSet2.add(v2e3);
//        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
//        valueSet2.add(v2e4);
//        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
//        valueSet2.add(v2e5);
//        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
//        valueSet2.add(v2e6);
//
//        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
//        barDataSet1.setColor(Color.rgb(0, 155, 0));
//
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        barDataSets.add(barDataSet1);
//        barDataSets.add(barDataSet2);
//
//        return barDataSets;
//    }
}
