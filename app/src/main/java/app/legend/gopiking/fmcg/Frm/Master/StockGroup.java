package app.legend.gopiking.fmcg.Frm.Master;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import app.legend.gopiking.fmcg.Frm.Master.Adapters.StockSearchAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.CameraUtil.CameraPermissionAlert;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.base64ToBitMap;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.decodeSampledBitmapFromFile;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.options;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.saveToPreferences;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.ExitAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.InfoAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class StockGroup extends AppCompatActivity {

    TextInputEditText stockName;
    AppCompatAutoCompleteTextView stockDesc;
    AutoCompleteTextView underStcok;
    static String undercat;
    FloatingActionButton stock_reset,stock_save,stock_delete,stock_search,button;
    private ImageView image;
    AppCompatCheckBox purchase,sales,breakfast,lunch,dinner;

    String Pid,FormState;
    static BizDatabase db;
    static String cus;

    int p=0,s=0,b=0,l=0,d=0;

    public static boolean emptyform = false;
    private static int LOAD_IMAGE_RESULTS = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private static final int CAMERA_PIC_REQUEST = 1111;

    tbStockGroup searchText;

    AlertDialog dialog;
    AlertDialog.Builder alert;

    private ListView lststockSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = (ImageView)findViewById(R.id.image);
        image.getLayoutParams().height=300;
        image.getLayoutParams().width=300;

        button = (FloatingActionButton)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{options();}catch (Exception e){msg(e.toString());}
            }
        });

        db = new BizDatabase(getApplication());
        Pid="";

        stockName = (TextInputEditText)findViewById(R.id.txtStockName);
        underStcok = (AppCompatAutoCompleteTextView)findViewById(R.id.txtUnderStock);

        stock_save =(FloatingActionButton)findViewById(R.id.fab_StockSave);
        stock_reset =(FloatingActionButton)findViewById(R.id.fab_StockReset);
        stock_delete =(FloatingActionButton)findViewById(R.id.fab_StockDelete);
        stock_search =(FloatingActionButton)findViewById(R.id.fab_StockSearch);

        load();
        Adapters();


    }

    //--------------------------------->> Permission Event Menu
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_PIC_REQUEST) {

        }
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        try {
            image.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 300, 300));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            msg(e.toString());
        }

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            image.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            CameraPermissionAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(StockGroup.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        emptyform = false;
        switch (item.getItemId()) {
            case android.R.id.home:
                ValidateForm();
                if(emptyform){
                    finish();
                }else{
                    ExitAlert(getString(R.string.somethinkwaitingtosave));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            emptyform = false;
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    ValidateForm();
                    if (emptyform) {
//                        FrmSaleCategoryList f = new FrmSaleCategoryList();
//                        f.recreate();
                        finish();
                    } else {
                        ExitAlert(getString(R.string.somethinkwaitingtosave));
                    }
                case KeyEvent.KEYCODE_HOME:
                    ValidateForm();
                    if(emptyform){
                        finish();
                    }else{ExitAlert(getString(R.string.somethinkwaitingtosave));}
                    return true;
            }

        } catch (Exception ex) {
        }

        return super.onKeyDown(keyCode, event);
    }

    //--------------------------------->> OnStart Items
    void Adapters(){

        try{
            //For searchtextbox AutoComplete
            tbStockGroup UnderCatItem = new tbStockGroup();
            List<String> UnderstockItems = db.GetColumn(UnderCatItem.GetTableName(),UnderCatItem.StockName.FName,"");
            ArrayAdapter UnderdataAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,UnderstockItems);
            underStcok.setAdapter(UnderdataAdapter);
            underStcok.setThreshold(1);

        }catch (Exception ex){}

    }

    void ValidateForm(){

        try{

            if(stockName.getText().toString().equals("")){
                emptyform = true;
            }

        }catch (Exception ex){}
    }

    void NewForm() {

        try {

            FormState = "new";

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(underStcok.getApplicationWindowToken(), 0);

            stockName.setText("");
            underStcok.setText("");

          
            image.setImageBitmap(base64ToBitMap(AppLib.noImage));


            underStcok.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(underStcok.getApplicationWindowToken(), 0);
                        StockSave_Click(v);
                        stockName.requestFocus();
                    }
                    return false;
                }
            });

            underStcok.requestFocus();
            Adapters();
            new Def_Lib(this);
            new CameraUtil(this);

        }catch(Exception ex){}
    }

    String getit(){

        tbStockGroup ca = new tbStockGroup();

        String FNames = "Id";
        String WQry = String.format("%s = '%s'", ca.StockName.FName, underStcok.getText().toString());
        Object obj;
        obj = db.GetGrid(ca.GetTableName(), FNames, WQry, "", "");

        Cursor q = (Cursor) obj;

        if(q != null)q.moveToFirst();
        for(int i=0;i<q.getCount();i++){
            ca.Id.FValue = q.getString(i);
        }
        cus = ca.Id.FValue;

        return cus;
    }

    void load(){
        try{

            stock_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StockSave_Click(v);
                }
            });
            stock_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StockReset_Click(v);
                }
            });


            stock_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StockDelete_Click(v);
                }
            });
            stock_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StockSearch_Click(v);
                }
            });

            NewForm();

        }catch (Exception e){}
    };

    void searchList(String SearchText){

        try{

            StockSearchAdapter adapter = new StockSearchAdapter(this,SearchText);
            lststockSearch.setAdapter(adapter);

//            searchText = CategorySearchAdapter.lstCategorySearchItem.get(lstCategorySearch.getSelectedItemPosition());



            lststockSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    searchText = StockSearchAdapter.lstStockSearchItem.get(position);

                    //search function

                    try{

                        if(lststockSearch.getCount()>0){
                            tbStockGroup Udata = new tbStockGroup();
                            db.GetRecordByCode(Udata.GetTable(),searchText.StockName.FValue);
                            Pid=Udata.Id.FValue;
                            stockName.setText(Udata.StockName.FValue);
                            underStcok.setText(Udata.UnderStock.FValue);

                            try{
                                cus = Udata.UnderStock.FValue;

                                if(cus != ""){
                                    tbStockGroup category = new tbStockGroup();
                                    db.GetRecord(category.GetTable(),cus);
                                    underStcok.setText(category.StockName.FValue);
                                }else{underStcok.setText("");}
                            }catch (Exception e){}


                            String img = Udata.ImageCat.FValue;
                            if(img == ""){
                                img=AppLib.noImage;
                            }
                            image.setImageBitmap(base64ToBitMap(img));

                            FormState="edit";

                        }else{
                            InfoAlert(getString(R.string.no_record_found));
                        }

                    }catch (Exception ex){msg(ex.toString());}

                    dialog.dismiss();
                }
            });

        }catch (Exception e){msg(e.toString());}
    }

    //--------------------------------->> Functions
    void StockSave_Click(View v){
        try{
            if(stockName.getText().toString().equals("")|| image.getDrawable().equals(null)){
                InfoAlert(getString(R.string.fill_all_details));
            }else{
                tbStockGroup cattable = new tbStockGroup();
                cattable.Id.FValue = Pid;
                cattable.StockName.FValue = stockName.getText().toString();
                cattable.UnderStock.FValue = getit();

                String img = CameraUtil.bitmapToBase64(((BitmapDrawable) image.getDrawable()).getBitmap());
                if(img == AppLib.noImage) img ="";
                cattable.ImageCat.FValue = img ;

                String pname;
                String pcat;
                String icat;

                pname = stockName.getText().toString();
                pcat = underStcok.getText().toString();
                icat = image.getDrawable().toString();

                String Wqry = String.format("%s = '%s' and %s = '%s' and %s = '%s'",cattable.StockName.FName,pname,cattable.UnderStock.FName,pcat,cattable.ImageCat.FName,icat);
                List<String> catL = db.GetColumn(cattable.GetTableName(),cattable.StockName.FName,Wqry);

                if(FormState.equals("new") && catL.size() > 0){
                    InfoAlert(getString(R.string.category_already_exist));
                }else{
                    Long Rv;
                    if(Pid.equals("")){
                        Rv = db.addEntry(cattable.GetTable());
                    }else{
                        Rv = db.UpdateRecord(cattable.GetTable());
                    }
                    if(Rv!=0){
                        InfoAlert(getString(R.string.saved_success));
                    }else{
                        InfoAlert(getString(R.string.cantsavesomthingwrong));
                    }
                    NewForm();
                }
            }
        }catch (Exception ex){msg(ex.toString());}
    }

    void StockDelete_Click(View v) {

        try {

            if(stockName.getText().toString().equals("")){

                InfoAlert(getString(R.string.select_category_to_delete));

            }else{

                android.app.AlertDialog.Builder ADB = new android.app.AlertDialog.Builder(StockGroup.this);
                ADB.setTitle(this.getTitle());
                ADB.setMessage(getString(R.string.are_you_sure));
                ADB.setPositiveButton(getString(R.string.alert_ButtonYes), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try{

                            tbStockGroup Udata = new tbStockGroup();
                            long RV = db.DeleteRecord(Udata.GetTable(),Pid);

                            if(RV!=0){
                                android.app.AlertDialog.Builder ADB = new android.app.AlertDialog.Builder(StockGroup.this);
                                ADB.setTitle("FrmCategory");
                                ADB.setMessage(getString(R.string.delete_successful));
                                ADB.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                android.app.AlertDialog ad = ADB.create();
                                ad.setIcon(R.drawable.denariusoft125);
                                ad.show();
                                NewForm();
                            }

                            else
                            {
                                InfoAlert(getString(R.string.cantdeletesomthingwrong));

                            }

                        }catch (Exception e){msg(e.toString());}

                    }
                });

                ADB.setNegativeButton(getString(R.string.alert_ButtonNo), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                android.app.AlertDialog ad = ADB.create();
                ad.setIcon(R.drawable.denariusoft125);
                ad.show();
            }

        } catch (Exception ex) {}

    }

    void StockReset_Click(View v){

        try {
            NewForm();

        } catch (Exception ex) {}

    }

    void StockSearch_Click(View v){

        try {

            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.activity_search_list,null,true);
            lststockSearch = (ListView)alertLayout.findViewById(R.id.lstSearch);
            final EditText text = (EditText)alertLayout.findViewById(R.id.txtsearchHint);
            text.setHint("Filter category using name");

            text.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchList(text.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            searchList("");

            alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.alert_title_search));
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);
            alert.setNegativeButton(getString(R.string.alert_ButtonCancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog = alert.create();
            dialog.show();

        } catch (Exception ex) { Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();}

    }

}
