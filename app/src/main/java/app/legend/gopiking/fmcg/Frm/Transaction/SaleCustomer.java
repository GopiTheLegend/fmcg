package app.legend.gopiking.fmcg.Frm.Transaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import app.legend.gopiking.fmcg.Frm.Master.Customer;
import app.legend.gopiking.fmcg.Frm.Transaction.Adapter.CustomerAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.AppLib.selectedCustomer;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Alert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Formshow;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class SaleCustomer extends AppCompatActivity {

    ListView lstTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lstTable = (ListView) findViewById(R.id.lstTable);
        CustomerAdapter adp1 = new CustomerAdapter(this);
        lstTable.setAdapter(adp1);

        if(lstTable.getCount() == 0){
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Formshow(Customer.class);
                }
            };
            Alert("No Customer Found,Need Action.","Add Customers",r);
        }

        lstTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCustomer = CustomerAdapter.lstCustomer.get(position);
                AppLib.selectedCat = new tbStockGroup();
              Formshow(SaleStockGroup.class);
            }
        });

        new Def_Lib(this);
    }

    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        try {

            switch (KeyCode) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    return true;
            }

        } catch (Exception ex) {
        }
        return super.onKeyDown(KeyCode, event);
    }
}
