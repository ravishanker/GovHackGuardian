package com.fovea.govhackguardian;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fovea.govhackguardian.model.AlcoholConsumption;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    List<AlcoholConsumption> mAlcoholConsumptions = new ArrayList<>();

    private LineChart mLineChart;
    private BarChart mBarChart;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_main, container, false);
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mLineChart = (LineChart) view.findViewById(R.id.line_chart);
//        mBarChart = (BarChart) view.findViewById(R.id.bar_chart);

        mLineChart.setDescription("");
        mLineChart.setNoDataTextDescription("No data for the moment");
        mLineChart.setBackgroundColor(Color.LTGRAY);

        mLineChart.setTouchEnabled(true);
        mLineChart.setPinchZoom(true);

        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);

//        LineData lineData = new LineData();
//        LineData lineData = new LineData(getXAxisValues(), getLineDataSet());
//        lineData.setValueTextColor(Color.WHITE);
//        mLineChart.setData(lineData);

//        BarData barData = new BarData(getXAxisValues(), getBarDataSet());
//        mBarChart.setData(barData);

        Legend legend = mLineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis axisLeft = mLineChart.getAxisLeft();
        axisLeft.setTextColor(Color.WHITE);
//        axisLeft.setAxisMaxValue(120f);
        axisLeft.setAxisMaxValue(100f);
        axisLeft.setDrawGridLines(true);

        YAxis axisRight = mLineChart.getAxisRight();
        axisRight.setEnabled(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadCsvData();

        LineData lineData = new LineData(getXAxisValues(), getLineDataSet());
        lineData.setValueTextColor(Color.WHITE);

        mLineChart.setData(lineData);
        mLineChart.notifyDataSetChanged();

//        //simulate real-time data
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //add 100 entries
//                for (int i = 0; i < 100; i++) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            addEntry();
//                        }
//                    });
//
//                    //pause between adds
//                    try {
//                        Thread.sleep(600);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }).start();
    }

    private void loadCsvData() {

        String alcoholConsumptionCsvFile = "beh_alc_comparison.csv";
        AssetManager assetManager = getActivity().getAssets();

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(AlcoholConsumption.class);
//        CsvSchema csvSchema = csvMapper.schemaFor(AlcoholConsumption.class).withHeader();


        try {
//            mapper.reader(AlcoholConsumption.class).with(bootstrapSchema).readValue(assetManager.open(alcoholConsumptionCsvFile));

            MappingIterator<AlcoholConsumption> mi = csvMapper
                    .reader(AlcoholConsumption.class)
//                    .with(bootstrapSchema)
                    .with(csvSchema)
                    .readValues(assetManager.open(alcoholConsumptionCsvFile));

            while (mi.hasNextValue()) {
                AlcoholConsumption alcoholConsumption = mi.nextValue();
                mAlcoholConsumptions.add(alcoholConsumption);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("CSV", mAlcoholConsumptions.get(0).toString());
        Log.d("CSV", mAlcoholConsumptions.get(1).toString());

//        LineData lineData = new LineData(getXAxisValues(), getLineDataSet());
//        lineData.setValueTextColor(Color.WHITE);
//
//        mLineChart.setData(lineData);
//        mLineChart.notifyDataSetChanged();

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        String year = " 2000";

        HashMap<String, Integer> ageRange = new HashMap<>();
        ageRange.put(" 16-24 years", 16);
        ageRange.put(" 25-34 years", 25);
        ageRange.put(" 35-44 years", 35);
        ageRange.put(" 45-54 years", 45);
        ageRange.put(" 55-64 years", 55);
        ageRange.put(" 65-74 years", 65);
        ageRange.put(" 75+ years", 75);

        for (AlcoholConsumption ac : mAlcoholConsumptions) {
            if (year != null && !year.equals(ac.getYear()) && ageRange.containsKey(ac.getCategory())) {
                xAxis.add(ac.getYear());
                year = ac.getYear();
            }
        }
        return xAxis;
    }

    private ArrayList<LineDataSet> getLineDataSet() {
        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();

        String year = " 2000";
        ArrayList<Entry> entryList = new ArrayList<>();
        LineDataSet lineDataSet = null;

        HashMap<String, Integer> ageRange = new HashMap<>();
        ageRange.put(" 16-24 years", 16);
        ageRange.put(" 25-34 years", 25);
        ageRange.put(" 35-44 years", 35);
        ageRange.put(" 45-54 years", 45);
        ageRange.put(" 55-64 years", 55);
        ageRange.put(" 65-74 years", 65);
        ageRange.put(" 75+ years", 75);

        for (AlcoholConsumption ac : mAlcoholConsumptions) {

            if (year != null && year.equals(ac.getYear()) && ageRange.containsKey(ac.getCategory())) {
                // existing age range, just add the data point.
                Entry entry = new Entry(getActualEstimatePercent(ac.getActualEstimatePerCent()), getAgeLowerBound(ac.getCategory()));
                entryList.add(entry);
            }

            if (year != null && !year.equals(ac.getYear()) && ageRange.containsKey(ac.getCategory())) {
                year = ac.getYear();


                lineDataSet = new LineDataSet(entryList, ac.getCategory());
                lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                entryList = new ArrayList<>();

                lineDataSets.add(lineDataSet);
            }
        }

        sortLineDataSetsByYear(lineDataSets);
        return lineDataSets;
    }

    private void sortLineDataSetsByYear(ArrayList<LineDataSet> lineDataSets) {
        Collections.sort(lineDataSets, CATEGORY_ORDER);
    }

    static final Comparator<LineDataSet> CATEGORY_ORDER = new Comparator<LineDataSet>() {
        @Override
        public int compare(LineDataSet lhs, LineDataSet rhs) {
            int categoryCompare = lhs.getLabel().compareTo(rhs.getLabel());

            if (categoryCompare != 0) {
                return categoryCompare;
            }

            return getAgeLowerBound(lhs.getLabel()) < getAgeLowerBound(rhs.getLabel()) ? -1 :
                    getAgeLowerBound(lhs.getLabel()) == getAgeLowerBound(rhs.getLabel()) ? 0 : 1;
        }
    };

    private float getActualEstimatePercent(String actualEstimatePercent) {
        return Float.parseFloat(actualEstimatePercent.trim());
    }

    private static int getAgeLowerBound(String category) {
        HashMap<String, Integer> ageRange = new HashMap<>();
        ageRange.put(" 16-24 years", 16);
        ageRange.put(" 25-34 years", 25);
        ageRange.put(" 35-44 years", 35);
        ageRange.put(" 45-54 years", 45);
        ageRange.put(" 55-64 years", 55);
        ageRange.put(" 65-74 years", 65);
        ageRange.put(" 75+ years", 75);

        if (ageRange.containsKey(category)) {
            return ageRange.get(category);
        }
//
        return 100;

//        if (category != null && isInteger(category.trim().substring(0, 2))) {
//            return Integer.parseInt(category.trim().substring(0, 2));
//        } else {
//            return 90;
//        }
//        return (category != null ? Integer.valueOf(category.trim().substring(0, 2)) : 90);
//        age = Integer.parseInt(category.trim().substring(0, 2));
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ne) {
            return false;
        }
        return true;
    }

    private void addEntry() {
        LineData lineData = mLineChart.getData();

        if (lineData != null) {
            LineDataSet set = lineData.getDataSetByIndex(0);

            if (set == null) {
                set = createDataSet();
                lineData.addDataSet(set);
            }

            // add a new random value
            lineData.addXValue("");
//            lineData.addEntry(new Entry((float)(Math.random() * 75) + 60f, set.getEntryCount()), 0);
            lineData.addEntry(new Entry((float)(Math.random() * 120) + 5f, set.getEntryCount()), 0);

            mLineChart.notifyDataSetChanged();

            //limit number of visible entries
            mLineChart.setVisibleXRange(6);

            //scroll to the last entry
            mLineChart.moveViewToX(lineData.getXValCount() - 7);
        }
    }

    private LineDataSet createDataSet() {
        LineDataSet set = new LineDataSet(null, "SPL Db");
        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244,177,177));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set;
    }


}
