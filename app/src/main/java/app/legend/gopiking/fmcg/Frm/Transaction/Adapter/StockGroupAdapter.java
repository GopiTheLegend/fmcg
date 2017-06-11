package app.legend.gopiking.fmcg.Frm.Transaction.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.Frm.Transaction.SaleProduct;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbCustomer;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.AppLib.selectedCat;
import static app.legend.gopiking.fmcg.appLib.AppLib.tempCat;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.base64ToBitMap;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Formshow;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

/**
 * Created by GopiKing on 10-06-2017.
 */

public class StockGroupAdapter extends ArrayAdapter<tbStockGroup> {

    private Activity context;
    public static List<tbStockGroup> lstStock = new ArrayList<>();

    public StockGroupAdapter(Context context) {
        super(context, R.layout.activity_sale_stock_group,getStockGroupList(context));
        this.context = (Activity) context;
        this.lstStock=getStockGroupList(context);

        new Def_Lib((Activity) context);
        new CameraUtil((Activity)context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mycatlist, null,true);

        tbStockGroup data = lstStock.get(position);

        ImageView imgTable = (ImageView) rowView.findViewById(R.id.icon);
        TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
        TextView txtDescription= (TextView) rowView.findViewById(R.id.txtUnder);

        txtName.setText(data.StockName.FValue);
        txtDescription.setText(data.UnderStock.FValue);

        String img = data.ImageCat.FValue;
        if(img == ""){
            img= AppLib.noImage;
        }
        imgTable.setImageBitmap(base64ToBitMap(img));

        return rowView;
    }

    public static List<tbStockGroup> getStockGroupList(Context context){
        BizDatabase db = new BizDatabase(context);
        List<tbStockGroup> RV = new ArrayList<tbStockGroup>();

        try{
            String qry = String.format("Select * from tbStockGroup where UnderStock='%s'", selectedCat.Id.FValue);
            Cursor cursor = (Cursor) db.ExequteQuery(qry);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    for (int i = 0; i <cursor.getCount(); i++) {
                        tbStockGroup data = new tbStockGroup();
                        data.Id.FValue = cursor.getString(0);
                        data.StockName.FValue = cursor.getString(1);
                        data.UnderStock.FValue = cursor.getString(2);
                        data.ImageCat.FValue = cursor.getString(3);
                        RV.add(i,data);
                        cursor.moveToNext();
                    }
                }else{
                    tempCat = selectedCat.Id.FValue;
                    if(selectedCat.Id.FValue == ""){

                    }else{
                        Formshow(SaleProduct.class);
                        msg(tempCat);
                    }
                }
            }
        }catch (Exception ex){
            msg(ex.getMessage());
        }
        return  RV;
    }

}
