package app.legend.gopiking.fmcg.Frm.Transaction;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.io.IOException;
import java.io.OutputStream;

import app.legend.gopiking.fmcg.BTLib.BTDeviceList;
import app.legend.gopiking.fmcg.BTLib.BTPrint;
import app.legend.gopiking.fmcg.Frm.Master.Customer;
import app.legend.gopiking.fmcg.Frm.Master.StockGroup;
import app.legend.gopiking.fmcg.Frm.Transaction.Adapter.CustomerAdapter;
import app.legend.gopiking.fmcg.Frm.Transaction.Adapter.StockGroupAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbProduct;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.AppLib.isViewSelectedItem;
import static app.legend.gopiking.fmcg.appLib.AppLib.selectedCat;
import static app.legend.gopiking.fmcg.appLib.AppLib.selectedCustomer;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Alert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Formshow;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.activity;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class SaleStockGroup extends AppCompatActivity {

    static ListView lstStocks;
    BizDatabase db;
    static FloatingActionButton preview;
    static AppCompatTextView tot_amt;
    static float value = 0;

    static String temp;

    static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_stock_group);

        db = new BizDatabase(this);

        activity = this;

        setTitle(String.format("StockGroup List - %s ",AppLib.selectedCustomer.CustomerName.FValue));

        preview = (FloatingActionButton)findViewById(R.id.category_print_preview);
        tot_amt = (AppCompatTextView)findViewById(R.id.txtTotalAmount_cat);

        lstStocks = (ListView) findViewById(R.id.lstsalesStock);

        StockGroupAdapter adp1 = new StockGroupAdapter(this);
        lstStocks.setAdapter(adp1);

        if(lstStocks.getCount() == 0){
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Formshow(StockGroup.class);
                }
            };
            Alert("No StockGroup Found,Need Action.","Add StockGroup",r);
        }

        lstStocks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCat = StockGroupAdapter.getStockGroupList(activity.getApplicationContext()).get(position);
                AppLib.selectedItem = new tbProduct();
                Formshow(SaleStockGroup.class);
                msg(selectedCustomer.CustomerName.FValue +"  and "+ selectedCat.StockName.FValue);
            }
        });

        Catload();
    }

    public static void Catload(){
        isViewSelectedItem = false;
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isViewSelectedItem = true;
                Formshow(SaleProduct.class);
//                msg("product form will come soon");
            }
        });

        tot_amt.setText(String.format("%s - Bill Amount : RM %.2f",AppLib.selectedCustomer.CustomerName.FValue,AppLib.FindItemAmount("")));

        value = Float.parseFloat(String.format("%.2f", AppLib.FindItemAmount("")));

        if(value == 0){
            tot_amt.setVisibility(View.GONE);
            preview.setVisibility(View.GONE);
        }
        else{
            tot_amt.setVisibility(View.VISIBLE);
            preview.setVisibility(View.VISIBLE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            BTPrint.btsocket = BTDeviceList.getSocket();
            if(BTPrint.btsocket != null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OutputStream opstream = null;
                try {
                    opstream = BTPrint.btsocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BTPrint.btoutputstream = opstream;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            try{
                String qry = String.format("Select * from tbStockGroup where Id='%s'", selectedCat.UnderStock.FValue);
                Cursor cursor = (Cursor) db.ExequteQuery(qry);
                tbStockGroup data = new tbStockGroup();
                if (cursor != null) {
                    if (cursor.moveToFirst()) {

                        data.Id.FValue = cursor.getString(0);
                        data.StockName.FValue = cursor.getString(1);
                        data.UnderStock.FValue = cursor.getString(2);
                        data.ImageCat.FValue = cursor.getString(3);

                        selectedCat = data;
                        Formshow(SaleStockGroup.class);

                    }else{
                        if(selectedCat.StockName.FValue==""){
                            Formshow(SaleCustomer.class);
                        }
                        else{
                            selectedCat = new tbStockGroup();
                            Formshow(SaleStockGroup.class);
                        }

                    }
                }

            }catch (Exception e){msg(e.toString());}

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
