package app.legend.gopiking.fmcg.Frm.Master.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

/**
 * Created by GopiKing on 10-06-2017.
 */

public class StockSearchAdapter extends ArrayAdapter<tbStockGroup> {


    private Activity context;
    public static List<tbStockGroup> lstStockSearchItem = new ArrayList<>();

    public StockSearchAdapter(Context context,String SearchText) {
        super(context, R.layout.activity_search_list_item,getStockSearchList(context,SearchText));

        this.context = (Activity) context;
        this.lstStockSearchItem = getStockSearchList(context,SearchText);

        new Def_Lib((Activity) context);
        new CameraUtil((Activity)context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_search_list_item,null,true);

        TextView txtProductName = (TextView)view.findViewById(R.id.lstSearchItems);
        tbStockGroup searchData = lstStockSearchItem.get(position);
        txtProductName.setText(searchData.StockName.FValue);

        return view;
    }

    private static List<tbStockGroup> getStockSearchList(Context context,String SearchText) {
        BizDatabase db = new BizDatabase(context.getApplicationContext());
        List<tbStockGroup> RV = new ArrayList<tbStockGroup>();


        try{
            String Qry = "Select StockName from tbStockGroup ";
            if( !SearchText.equals("")) Qry += " where StockName like '%" + SearchText + "%' ";

            Qry = Qry + " order by StockName";

            Cursor cursor = (Cursor) db.ExequteQuery(Qry);
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        tbStockGroup data = new tbStockGroup();
                        data.StockName.FValue = cursor.getString(0);
                        RV.add(i,data);
                        cursor.moveToNext();
                    }
                }
            }
        }catch (Exception ex){}

        return RV;
    }
}
