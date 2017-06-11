package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbSales;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.InfoAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

//import apps.legend.gopiking.denres.Frm.Report.FrmPurchaseReport;
//import apps.legend.gopiking.denres.Frm.Report.FrmSaleReport;

/**
 * Created by GopiKingMaker on 4/16/2017.
 */

public class ChartUtil {

    static Activity activity;
    static BizDatabase db;

    static PieChart pieChart;

    public ChartUtil(Activity _activity){

        activity = _activity;
        db = new BizDatabase(activity.getApplicationContext());
        new Def_Lib(activity);

    }
    
    public static void fun_PieChart(){
        
        pieChart = (PieChart) activity.findViewById(R.id.emp_chart);
        pieChart.setUsePercentValues(true);

//        pieChart.getDescription().setEnabled(false);
//        pieChart.setExtraOffsets(5, 10, 5, 5);
//        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);//background color
        pieChart.setTransparentCircleAlpha(5);
        pieChart.setHoleRadius(2f);//center point size
        pieChart.setTransparentCircleRadius(200f);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                pieChart.spin(1000, pieChart.getRotationAngle(),
                        pieChart.getRotationAngle() + 360, Easing.EasingOption.EaseInCubic);//spine effect
//                pieChart.animateXY(1400, 1400);//refresh effect

//                if (pieChart.isDrawHoleEnabled())
//
//                    pieChart.setDrawHoleEnabled(false);
//
//                else
//
//                    pieChart.setDrawHoleEnabled(true);
//
//                pieChart.invalidate();

//                InfoAlert("Value: " + e.getY() + ", index: " + h.getX()
//
//                        + ", DataSet index: " + h.getDataSetIndex());
            }

            @Override
            public void onNothingSelected() {

            }
        });


        try{
            // set value and label
            Object obj;

            String Query = "select Date,EmployeeName,sum(Amount) from tbSales group by Date order by Date";

            obj = db.ExequteQuery(Query);

            Cursor sdGrid = (Cursor) obj;

            List<PieEntry> lstchart  = new ArrayList<PieEntry>();

            try{

                if (sdGrid != null) {

                    if (sdGrid.moveToFirst()) {
                        int i = 0;
                        do {
                            String name = sdGrid.getString(1);
                            lstchart.add(new PieEntry(Float.parseFloat(sdGrid.getString(2)),name));
                            i++;
                            InfoAlert(sdGrid.getString(1));
                        } while (sdGrid.moveToNext());
                    }
                }

            }catch (Exception e){msg(e.toString());}

            PieDataSet dataSet = new PieDataSet(lstchart,"Employee Details");
            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(3f);
            dataSet.setIconsOffset(new MPPointF(0,40));
            dataSet.setSelectionShift(5f);

            ArrayList<Integer> colors = new ArrayList<Integer>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS)

                colors.add(c);
            for (int c : ColorTemplate.JOYFUL_COLORS)

                colors.add(c);
            for (int c : ColorTemplate.COLORFUL_COLORS)

                colors.add(c);
            for (int c : ColorTemplate.LIBERTY_COLORS)

                colors.add(c);
            for (int c : ColorTemplate.PASTEL_COLORS)

                colors.add(c);
            for (int c : ColorTemplate.MATERIAL_COLORS)

                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());
            dataSet.setColors(colors);

            dataSet.setSelectionShift(0f);

            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.WHITE);
//          data.setValueTypeface(mTfLight);

            pieChart.setData(data);

            // undo all highlights
            pieChart.highlightValues(null);
            pieChart.invalidate();

            pieChart.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    msg("hi");
                    return false;
                }
            });

        }catch (Exception e){}


        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        pieChart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                msg("Hi");
                return true ;
            }
        });
    }

    public static void fun_BarChart(){

        final BarChart barChart = (BarChart) activity.findViewById(R.id.BarChart);


        tbSales sale = new tbSales();
        List<tbSales> sales = new ArrayList<tbSales>();

        Object obj;
        String Query = "select Date,sum(Amount) from tbSales group by Date order by Date";

        obj = db.ExequteQuery(Query);

        Cursor sdGrid = (Cursor) obj;
        //bar chart value set
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        try{

            if (sdGrid != null) {

                if (sdGrid.moveToFirst()) {
                    int i = 0;
                    do {
                        String head = sdGrid.getString(0);
                        entries.add(new BarEntry(i, Float.parseFloat(sdGrid.getString(1)),head));
                        i++;
                    } while (sdGrid.moveToNext());
                }
            }

        }catch (Exception e){msg(e.toString());}


        final BarDataSet dataset = new BarDataSet(entries, activity.getString(R.string.chat_sale_report_name));//set arrayvalues to dataset

        ArrayList<IBarDataSet> iBarDataSets = new ArrayList<IBarDataSet>();
        iBarDataSets.add(dataset);

        BarData data = new BarData(dataset);//load barchart content and head
        dataset.setColors(ColorTemplate.MATERIAL_COLORS); //bar colors
        barChart.setData(data);//set content and head to barchart
        barChart.animateY(5000);//animation time delay.
        barChart.setMaxVisibleValueCount(7);//Limit
        barChart.setDescription(barChart.getDescription());
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try{

                    msg(dataset.getLabel());

                }catch (Exception ex){
                    msg(ex.toString());}
            }

            @Override
            public void onNothingSelected() {

            }
        });


        barChart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try{
//                    Formshow(FrmSaleReport.class);
                }catch (Exception e){
                    msg(e.toString());}
                return false;
            }
        });
    }

//    public static void fun_LineChart(){
//
//        LineChart lineChart = (LineChart) activity.findViewById(R.id.LineChart);
//
//        tbPurchase purchase = new tbPurchase();
//        List<tbPurchase> Purchases = new ArrayList<tbPurchase>();
//
//        Object object;
//
//        String Qry = "select PurchaseDate,sum(Amount) from tbPurchase group by PurchaseDate order by PurchaseDate";
//
//        object = db.ExequteQuery(Qry);
//
//        final Cursor cursor = (Cursor) object;
//
//
//        //Lable for chart heading
//        ArrayList<String> lineLabels = new ArrayList<String>();
//
//        try{
//
//
//            if (cursor != null) {
//
//                if (cursor.moveToFirst()) {
//                    //int i = 0;
//                    do {
//
//                        lineLabels.add(cursor.getString(0));
//
//                        // i++;
//                    } while (cursor.moveToNext());
//                }
//            }
//
//        }catch (Exception e){msg(e.toString());}
//
//
//        Object obj;
////      String Query = "select * from tbSales";
//
//        String Query = "select PurchaseDate,sum(Amount) from tbPurchase group by PurchaseDate order by PurchaseDate";
//
//        obj = db.ExequteQuery(Query);
//
//        Cursor sdGrid = (Cursor) obj;
//        //line chart value set
//        final List<Entry> entry = new ArrayList<>();
//
//        try{
//
//
//            if (sdGrid != null) {
//
//                if (sdGrid.moveToFirst()) {
//                    int i = 0;
//                    do {
//
//                        entry.add(new Entry(Float.parseFloat(sdGrid.getString(1)),i));
//
//                        i++;
//                    } while (sdGrid.moveToNext());
//                }
//            }
//
//        }catch (Exception e){msg(e.toString());}
//
//        final LineDataSet lineDataSet = new LineDataSet(entry,activity.getString(R.string.chat_purchase_head));
//
//        LineData linedata = new LineData(lineLabels,lineDataSet);
//        lineChart.setData(linedata);
//        lineChart.animateY(5000);
//        lineChart.animate();
//        lineChart.setMaxVisibleValueCount(7);
//
//        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                try{
//
//                    msg(String.valueOf(e.getVal()));
////                    msg(String.valueOf(entry.get(dataSetIndex)));
//
//                }catch (Exception ex){
//                    msg(ex.toString());
//                }
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
//
//        lineChart.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                try{
////                    Formshow(FrmPurchaseReport.class);
//                    }catch (Exception e){msg(e.toString());}
//                return false;
//            }
//        });
//    }

}
