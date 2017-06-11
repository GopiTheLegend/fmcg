package app.legend.gopiking.fmcg.Frm.Master.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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
import app.legend.gopiking.fmcg.dbTables.tbProduct;

/**
 * Created by GopiKing on 10-06-2017.
 */

public class ProductSearchAdapter extends ArrayAdapter<tbProduct> {



    private Activity context;
    public static List<tbProduct> lstProductSearchItems = new ArrayList<>();

    public ProductSearchAdapter(Context context,String SearchText) {
        super(context, R.layout.activity_search_list_item,getProductSearchList(context,SearchText));
        this.context = (Activity) context;
        this.lstProductSearchItems = getProductSearchList(context,SearchText);

        new Def_Lib((Activity) context);
        new CameraUtil((Activity)context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =   context.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_search_list_item,null,true);

        TextView txtProductName = (TextView)rootView.findViewById(R.id.lstSearchItems);
        tbProduct searchData = lstProductSearchItems.get(position);
        txtProductName.setText(searchData.ProductName.FValue);


        return rootView;
    }

    public static List<tbProduct> getProductSearchList(Context context,String SearchText) {

        BizDatabase db = new BizDatabase(context);
        List<tbProduct> RV = new ArrayList<tbProduct>();

        try{
            String Qry = "Select ProductName from tbProduct ";
            if( !SearchText.equals("")) Qry += " where ProductName like '%" + SearchText + "%' ";

            Qry = Qry + " order by ProductName";

            Cursor cursor = (Cursor) db.ExequteQuery(Qry);
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        tbProduct data = new tbProduct();
                        data.ProductName.FValue = cursor.getString(0);
                        RV.add(i,data);
                        cursor.moveToNext();
                    }
                }
            }
        }catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        return RV;
    }
}
