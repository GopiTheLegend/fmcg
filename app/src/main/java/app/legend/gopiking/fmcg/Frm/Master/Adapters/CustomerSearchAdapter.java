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
import app.legend.gopiking.fmcg.dbTables.tbCustomer;
import app.legend.gopiking.fmcg.dbTables.tbProduct;

/**
 * Created by GopiKing on 10-06-2017.
 */

public class CustomerSearchAdapter extends ArrayAdapter<tbCustomer> {

    private Activity context;
    public static List<tbCustomer> lstCustomerSearchItems = new ArrayList<>();

    public CustomerSearchAdapter(Context context,String SearchText) {
        super(context, R.layout.activity_search_list_item,getCustomerSearchList(context,SearchText));
        this.context = (Activity) context;
        this.lstCustomerSearchItems = getCustomerSearchList(context,SearchText);

        new Def_Lib((Activity) context);
        new CameraUtil((Activity)context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =   context.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.activity_search_list_item,null,true);

        TextView txtProductName = (TextView)rootView.findViewById(R.id.lstSearchItems);
        tbCustomer searchData = lstCustomerSearchItems.get(position);
        txtProductName.setText(searchData.CustomerName.FValue);

        return rootView;
    }

    public static List<tbCustomer> getCustomerSearchList(Context context,String SearchText) {

        BizDatabase db = new BizDatabase(context);
        List<tbCustomer> RV = new ArrayList<tbCustomer>();

        try{
            String Qry = "Select CustomerName from tbCustomer ";
            if( !SearchText.equals("")) Qry += " where CustomerName like '%" + SearchText + "%' ";

            Qry = Qry + " order by CustomerName";

            Cursor cursor = (Cursor) db.ExequteQuery(Qry);
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        tbCustomer data = new tbCustomer();
                        data.CustomerName.FValue = cursor.getString(0);
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
