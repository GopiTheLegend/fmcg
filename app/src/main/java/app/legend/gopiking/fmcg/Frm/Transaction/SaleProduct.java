package app.legend.gopiking.fmcg.Frm.Transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.io.OutputStream;

import app.legend.gopiking.fmcg.BTLib.BTDeviceList;
import app.legend.gopiking.fmcg.BTLib.BTPrint;
import app.legend.gopiking.fmcg.Frm.Master.Product;
import app.legend.gopiking.fmcg.Frm.Transaction.Adapter.ProductAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.AppLib.isViewSelectedItem;
import static app.legend.gopiking.fmcg.appLib.AppLib.selectedCat;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Alert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Formshow;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.FormshowResult;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class SaleProduct extends AppCompatActivity {

    ListView lstProduct;
    FloatingActionButton print_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(String.format("Product List - %s - %s", selectedCat.StockName.FValue, AppLib.selectedCustomer.CustomerName.FValue));


        lstProduct = (ListView) findViewById(R.id.lstSalesProduct);
        print_product = (FloatingActionButton) findViewById(R.id.product_print);

        load();

        new Def_Lib(this);

    }

    void load() {
        try {

            print_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        if (AppLib.FindItemAmount("") > 0) {
                            if (AppLib.SaveBill(getApplicationContext(), SaleProduct.this)) {
                                msg(getString(R.string.saved));
                                Formshow(SaleCustomer.class);
                            }
                        } else {
                            msg(getString(R.string.please_choose_item_to_print));
                        }

                    } catch (Exception e) {
                        e.toString();
                    }
                }
            });

            ProductAdapter adp1 = new ProductAdapter(this);
            lstProduct.setAdapter(adp1);


            lstProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });

            if (isViewSelectedItem == true) {
                setTitle(String.format("Selected Product List - %s", AppLib.selectedCustomer.CustomerName.FValue));
                print_product.setVisibility(View.VISIBLE);
            }

            if (isViewSelectedItem == true && BTPrint.btsocket == null) {
                try {
                    FormshowResult(BTDeviceList.class,BTDeviceList.REQUEST_CONNECT_BT);
                } catch (Exception e) {}
            }

            if(isViewSelectedItem != true && lstProduct.getCount() == 0){
                Runnable R = new Runnable() {
                    @Override
                    public void run() {
                        Formshow(Product.class);
                    }
                };
                Alert("No Product Found.? Need Action.","Add Product",R);

            }

        } catch (Exception e) {
            msg(e.toString());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            BTPrint.btsocket = BTDeviceList.getSocket();
            if (BTPrint.btsocket != null) {
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


    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        if (KeyCode == KeyEvent.KEYCODE_BACK) {
            try {
                selectedCat = new tbStockGroup();
                Formshow(SaleStockGroup.class);
            } catch (Exception e) {
            }
            return true;
        }
        return super.onKeyDown(KeyCode, event);
    }

}
