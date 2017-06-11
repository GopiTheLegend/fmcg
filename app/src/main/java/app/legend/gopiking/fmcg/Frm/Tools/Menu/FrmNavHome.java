package app.legend.gopiking.fmcg.Frm.Tools.Menu;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import app.legend.gopiking.fmcg.Frm.Master.Customer;
import app.legend.gopiking.fmcg.Frm.Master.FrmUom;
import app.legend.gopiking.fmcg.Frm.Master.Product;
import app.legend.gopiking.fmcg.Frm.Master.StockGroup;
import app.legend.gopiking.fmcg.Frm.Transaction.SaleCustomer;
import app.legend.gopiking.fmcg.Frm.Transaction.SaleStockGroup;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.Def_Lib;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.ExitAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Formshow;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

/**
 * Created by GopiKing on 09-06-2017.
 */

public class FrmNavHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_menu);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        new Def_Lib(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.frm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
//                Logout_Alert(getString(R.string.alert_exit));
                return true;
            case R.id.action_help:
//                pdf_Reader(this,"Restaurant_UserManual.pdf");
                return true;
            case R.id.action_About:
//                Formshow(FrmAbout.class);
                return true;
            case R.id.action_Backup:
//                verify_user();
                return true;
            case R.id.action_Restore:
//                verify_user();
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_uom) {
            try{

                Formshow(FrmUom.class);

            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}


        } else if (id == R.id.nav_stock_group) {
            try{

                Formshow(StockGroup.class);

            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}
        } else if (id == R.id.nav_employee) {
            try{

                Formshow(Product.class);

            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}
        } else if (id == R.id.nav_accounts) {
            try{

                Formshow(Customer.class);

            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}
        } else if (id == R.id.nav_sales) {
            try{
                Formshow(SaleCustomer.class);

            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}
        } else if (id == R.id.nav_reports) {
            try{

//                Formshow(FrmReports.class);
//                Formshow(FrmPieChart.class);
            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}
        } else if (id == R.id.nav_companySettings) {
            try{

//                Formshow(FrmCompanySettings.class);

            }catch (android.content.ActivityNotFoundException e){msg(e.toString());}
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
