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
import app.legend.gopiking.fmcg.dbTables.tbUom;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.InfoAlert;

/**
 * Created by GopiKing on 09-06-2017.
 */

public class UomSearchAdapter extends ArrayAdapter<tbUom> {

    Activity context;
    public static List<tbUom> lstUomSearchItem = new ArrayList<>();

    public UomSearchAdapter(Context context,String SearchText) {
        super(context, R.layout.activity_search_list_item,getProductSearchItem(context,SearchText));

        this.context = (Activity) context;
        this.lstUomSearchItem = getProductSearchItem(context, SearchText);

        new Def_Lib((Activity) context);
        new CameraUtil((Activity) context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_search_list_item,null,true);

        TextView textView = (TextView)view.findViewById(R.id.lstSearchItems);
        tbUom SearchData = lstUomSearchItem.get(position);
        textView.setText(SearchData.Symbol.FValue);

        return view;
    }

    private static List<tbUom> getProductSearchItem(Context context, String SearchText) {
        BizDatabase db = new BizDatabase(context);
        List<tbUom> RV = new ArrayList<tbUom>();

        try {
            String Qry = "Select Symbol from tbUom";
            if (!SearchText.equals("")) Qry += " where Symbol like '%" + SearchText + "%' ";

            Qry = Qry + " order by Symbol";

            Cursor cursor = (Cursor) db.ExequteQuery(Qry);
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        tbUom data = new tbUom();
                        data.Symbol.FValue = cursor.getString(0);
                        RV.add(i, data);
                        cursor.moveToNext();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return RV;
    }
}
