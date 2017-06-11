package app.legend.gopiking.fmcg.Frm.Transaction.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbCustomer;

/**
 * Created by GopiKing on 10-06-2017.
 */

public class CustomerAdapter extends ArrayAdapter<tbCustomer> {

    private Activity context;
    public static List<tbCustomer> lstCustomer = new ArrayList<>();

    public CustomerAdapter(Context context) {
        super(context, R.layout.activity_customer_list_adapter,getCustomerList(context));

        this.context = (Activity) context;
        this.lstCustomer=getCustomerList(context);

        new Def_Lib((Activity) context);
        new CameraUtil((Activity)context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_customer_list_adapter, null,true);

        tbCustomer data = lstCustomer.get(position);

        TextView name = (TextView) rowView.findViewById(R.id.txtNames);
        TextView phone= (TextView) rowView.findViewById(R.id.txtPhoneNum);
        TextView mail = (TextView) rowView.findViewById(R.id.txtMail);
        TextView bill = (TextView) rowView.findViewById(R.id.txtBillAmt);

        name.setText(data.CustomerName.FValue);
        phone.setText(data.Phone.FValue);
        mail.setText(data.MailId.FValue);

        float amt = AppLib.FindItemAmount(data.Id.FValue);
        if(amt==0){
            bill.setVisibility(view.GONE);
        }else{
            bill.setVisibility(view.VISIBLE);
            bill.setText(String.format("Bill Amount : RM %.2f", AppLib.FindItemAmount(data.Id.FValue)));
        }
        return rowView;
    }

    public static List<tbCustomer> getCustomerList(Context context){
        BizDatabase db = new BizDatabase(context);
        List<tbCustomer> RV = new ArrayList<tbCustomer>();
        try{
            Cursor cursor = (Cursor) db.ExequteQuery("Select * from tbCustomer");
            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        tbCustomer data = new tbCustomer();
                        data.Id.FValue = cursor.getString(0);
                        data.CustomerName.FValue = cursor.getString(1);
                        data.Phone.FValue = cursor.getString(5);
                        data.MailId.FValue = cursor.getString(6);

                        RV.add(i,data);
                        cursor.moveToNext();
                    }
                }
            }
        }catch (Exception ex){
            System.out.print(ex.getMessage());
        }
        return  RV;
    }

}
