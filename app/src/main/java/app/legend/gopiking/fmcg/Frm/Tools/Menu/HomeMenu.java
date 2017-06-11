package app.legend.gopiking.fmcg.Frm.Tools.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.Def_Lib;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class HomeMenu extends AppCompatActivity {

    private View v;

    FrameLayout layout1,layout2,layout3;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              try{

                  switch (item.getItemId()) {
                      case R.id.nav_menu_home:
                          startActivity(new Intent(HomeMenu.this,FrmNavHome.class));
                          return true;
                      case R.id.nav_report_dashboard:

                          return true;
                      case R.id.nav_about:
                           return true;

                      case R.id.nav_profile:
                          try{
//                              LayoutInflater inflater = getLayoutInflater();
//                              View v = inflater.inflate(R.layout.activity_profile_menu,null,true);
//                              setContentView(v);

                          }catch (Exception e){
                              msg(e.toString());
                          }
                          return true;
                  }

              }catch (Exception e){
                  msg(e.toString());
              }
                return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        layout1 = (FrameLayout)findViewById(R.id.content1);
        layout2 = (FrameLayout)findViewById(R.id.content2);
        layout3 = (FrameLayout)findViewById(R.id.content3);



        layout1.setVisibility(View.GONE);
        layout2.setVisibility(View.GONE);
        layout3.setVisibility(View.GONE);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new Def_Lib(this);
    }

}
