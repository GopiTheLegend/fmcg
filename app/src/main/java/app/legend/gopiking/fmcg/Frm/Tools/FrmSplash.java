package app.legend.gopiking.fmcg.Frm.Tools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.legend.gopiking.fmcg.LoginActivity;
import app.legend.gopiking.fmcg.R;

public class FrmSplash extends AppCompatActivity {

    protected boolean _active = true;
    protected int _splashTime = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_splash);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(FrmSplash.this,
                            LoginActivity.class));
                    finish();
                }
            };
        };
        splashTread.start();

    }

}
