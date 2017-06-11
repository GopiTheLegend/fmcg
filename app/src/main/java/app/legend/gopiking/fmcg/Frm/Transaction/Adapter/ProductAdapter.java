package app.legend.gopiking.fmcg.Frm.Transaction.Adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.BillItem;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbProduct;

import static app.legend.gopiking.fmcg.appLib.AppLib.isViewSelectedItem;
import static app.legend.gopiking.fmcg.appLib.AppLib.lstBillItem;
import static app.legend.gopiking.fmcg.appLib.AppLib.selectedBillNo;
import static app.legend.gopiking.fmcg.appLib.AppLib.selectedCustomer;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.base64ToBitMap;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

/**
 * Created by GopiKing on 10-06-2017.
 */

public class ProductAdapter extends ArrayAdapter<tbProduct> {


    private Activity context;
    public static List<tbProduct> lstProduct = new ArrayList<tbProduct>();

    public ProductAdapter(Context context) {
        super(context, R.layout.activity_category_list,getProductList(context));
        this.context = (Activity) context;
        this.lstProduct = getProductList(context);

        new Def_Lib(((Activity) context));
        new CameraUtil(((Activity) context));
    }

    public static List<tbProduct> getProductList(Context context){
        BizDatabase db = new BizDatabase(context);
        List<tbProduct> RV = new ArrayList<tbProduct>();
        try{

            if(isViewSelectedItem == false){

                String Qry = String.format("Select * from tbProduct where Category='%s'", AppLib.selectedCat.Id.FValue);

                Cursor cursor = (Cursor) db.ExequteQuery(Qry);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {

                        for (int i = 0; i < cursor.getCount(); i++) {
                            tbProduct data = new tbProduct();
                            data.Id.FValue = cursor.getString(0);
                            data.ProductName.FValue = cursor.getString(1);
                            data.Category.FValue = cursor.getString(2);
                            data.Rate.FValue = cursor.getString(3);
                            data.ImagProd.FValue = cursor.getString(4);
                            RV.add(i,data);
                            cursor.moveToNext();
                        }
                    }
                }
            }else{
                for(int j = 0;j < lstBillItem.size(); j++) {
                    BillItem datas = lstBillItem.get(j);
                    if(selectedCustomer.Id.FValue.equals(datas.CustomerId) && selectedBillNo.equals(datas.BillNo)){
                        tbProduct data = new tbProduct();
                        data.Id.FValue = datas.ProductId;
                        data.ProductName.FValue = datas.ProductName;
                        data.Category.FValue = datas.CatId;
                        data.Rate.FValue = Float.toString(datas.Rate);
                        data.ImagProd.FValue = datas.imgItem;
                        RV.add(j,data);
                    }
                }
            }

        }catch (Exception ex){msg(ex.getMessage());}
        return  RV;
    }

    public View getView(int position, final View view, ViewGroup parent) {

        final tbProduct item = lstProduct.get(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.myitemlist, null,true);

        final float rate = Float.parseFloat(item.Rate.FValue);
        final int qty = GetQtyItem(item.Id.FValue);

        TextView txtName = (TextView) rowView.findViewById(R.id.txtItemName);
        ImageView imgIcon = (ImageView) rowView.findViewById(R.id.icon);
        TextView txtDescription= (TextView) rowView.findViewById(R.id.txtDescription);
        final TextView txtTotal = (TextView) rowView.findViewById(R.id.txtTotal);

        Button btnDec = (Button) rowView.findViewById(R.id.btnDec);
        Button btnInc = (Button) rowView.findViewById(R.id.btnInc);

        final EditText txtQty = (EditText)rowView.findViewById(R.id.txtQty);

        txtQty.setText(Integer.toString(qty));

        if(qty>0){
            txtTotal.setVisibility(View.VISIBLE);
            txtTotal.setText(String.format("Total : RM %.2f", rate * qty));
        }else{
            txtTotal.setVisibility(View.GONE);
        }

        txtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{

                    if (txtQty.getText().toString().equals("")) {
                        txtTotal.setVisibility(view.GONE);
                        txtQty.setText(String.format("%.2f",0));

                    } else {

                        int qty = Integer.parseInt(txtQty.getText().toString());
                        txtTotal.setVisibility(view.VISIBLE);
                        String tot = String.format("Total : RM %.2f", rate * qty);
                        txtTotal.setText(tot);
                        SaveItem(item.Id.FValue, item.ProductName.FValue, rate, qty, rate * qty,item.ImagProd.FValue);
                    }

                }catch (Exception e){}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(txtQty.getText().toString());
                if(qty>0) qty-=1;
                txtQty.setText(Integer.toString(qty));
                if(qty==0){
                    txtTotal.setVisibility(View.INVISIBLE);
                    RemoveItem(item.Id.FValue);
                }
                else{
                    txtTotal.setVisibility(View.VISIBLE);
                    txtTotal.setText(String.format("Total : RM %.2f",rate*qty));
                    SaveItem(item.Id.FValue, item.ProductName.FValue, rate,qty,rate*qty,item.ImagProd.FValue );
                }
            }
        });

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(txtQty.getText().toString());
                qty+=1;
                txtQty.setText(Integer.toString(qty));
                if(qty==0){
                    txtTotal.setVisibility(View.INVISIBLE);
                    RemoveItem(item.Id.FValue);
                }
                else{
                    txtTotal.setVisibility(View.VISIBLE);
                    txtTotal.setText(String.format("Total : RM %.2f",rate*qty));
                    SaveItem(item.Id.FValue, item.ProductName.FValue, rate,qty,rate*qty,item.ImagProd.FValue );
                }
            }
        });

        txtName.setText(lstProduct.get(position).ProductName.FValue);
        String img = lstProduct.get(position).ImagProd.FValue;
        if(img == ""){
            img=AppLib.noImage;
        }
        imgIcon.setImageBitmap(base64ToBitMap(img));
        txtDescription.setText(String.format("Rate : RM %s", lstProduct.get(position).Rate.FValue));


        return rowView;

    };

    void SaveItem(String ProductId, String ProductName,float Rate, int Qty,float Amount,String imgItem ){
        try{

            int i=0;
            for(i=0; i< lstBillItem.size(); i++){
                BillItem data = lstBillItem.get(i);

                if(data.CustomerId == selectedCustomer.Id.FValue && data.BillNo== selectedBillNo && data.ProductId==ProductId){
                    data.Qty=Qty;
                    data.Rate=Rate;
                    data.Amount=Amount;
                    break;
                }
            }
            if(i== lstBillItem.size()){
                BillItem data = new BillItem();

                data.BillNo = "";
                data.CustomerId = selectedCustomer.Id.FValue;
                data.CustomerName = selectedCustomer.CustomerName.FValue;

                data.CatId = AppLib.selectedCat.Id.FValue;
                data.CatName = AppLib.selectedCat.StockName.FValue;

                data.ProductId = ProductId;
                data.ProductName = ProductName;
                data.imgItem = imgItem;
                data.Qty=Qty;
                data.Rate=Rate;
                data.Amount=Amount;

                lstBillItem.add(data);

            }

        }catch (Exception e){msg(e.toString());}
    }

    void RemoveItem(String ProductId){
        try{

            int i=0;
            for(i=0; i< lstBillItem.size(); i++){
                BillItem data = lstBillItem.get(i);

                if(data.CustomerId== selectedCustomer.Id.FValue && data.BillNo== selectedBillNo && data.ProductId==ProductId){
                    lstBillItem.remove(i);
                    break;
                }
            }

        }catch (Exception e){msg(e.toString());}
    }

    int GetQtyItem(String ProductId){
        int i=0;
        int qty=0;
        for(i=0; i< lstBillItem.size(); i++){
            BillItem data = lstBillItem.get(i);

            if(data.CustomerId.equals(selectedCustomer.Id.FValue) && data.BillNo.equals(selectedBillNo) && data.ProductId.equals(ProductId)){
                qty=data.Qty;
                break;
            }
        }
        return  qty;
    }
}
