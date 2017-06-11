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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
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

import java.io.File;
import java.util.List;

import app.legend.gopiking.fmcg.Frm.Master.Adapters.ProductSearchAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbProduct;
import app.legend.gopiking.fmcg.dbTables.tbStockGroup;

import static app.legend.gopiking.fmcg.appLib.CameraUtil.CameraPermissionAlert;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.base64ToBitMap;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.decodeSampledBitmapFromFile;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.options;
import static app.legend.gopiking.fmcg.appLib.CameraUtil.saveToPreferences;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.Alert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.ExitAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.InfoAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class Product extends AppCompatActivity {

    private ListView lstSearchitems;
    TextInputEditText productName,  SellingRate;
    AutoCompleteTextView category;
    FloatingActionButton Product_reset, Product_save, Product_delete, Product_search,button;
    private ImageView image;

    public static boolean emptyform = false;
    String Pid, FormState;
    BizDatabase db;

    tbProduct searchText;

    AlertDialog dialog;
    AlertDialog.Builder alert;

    private static int LOAD_IMAGE_RESULTS = 1;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    private static final int CAMERA_PIC_REQUEST = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image=(ImageView)findViewById(R.id.image);
        image.getLayoutParams().height=300;
        image.getLayoutParams().width=300;
        button = (FloatingActionButton)findViewById(R.id.button);

        lstSearchitems = (ListView)findViewById(R.id.lstSearch);

        db = new BizDatabase(getApplication());
        Pid = "";


        productName = (TextInputEditText) findViewById(R.id.txtProductName);
        category = (AutoCompleteTextView) findViewById(R.id.txtCategory);
        SellingRate = (TextInputEditText) findViewById(R.id.txtProduct_Selling_Rate);

        Product_save = (FloatingActionButton) findViewById(R.id.fab_ProductSave);
        Product_reset = (FloatingActionButton) findViewById(R.id.fab_ProductReset);
        Product_delete = (FloatingActionButton) findViewById(R.id.fab_ProductDelete);
        Product_search = (FloatingActionButton) findViewById(R.id.fab_ProductSearch);

        NewForm();

    }


    //--------------------------------->> Permission Event Menu
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_PIC_REQUEST) {

            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
            try {

//                File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
                image.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 300, 300));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                msg(e.toString());
            }
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
                            saveToPreferences(Product.this, ALLOW_KEY, true);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            emptyform = false;
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    ValidateForm();
                    if (emptyform) {
                        finish();
                    } else {
                        ExitAlert(getString(R.string.somethinkwaitingtosave));
                    }
                    return true;
            }

        } catch (Exception ex) {
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        emptyform = false;
        switch (item.getItemId()) {
            case android.R.id.home:
                ValidateForm();
                if (emptyform) {
                    finish();
                } else {
                    //AlertMessage = "Somthing Waiting To Save Are You Sure Go Back Without Save....?";
                    BackAlert();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //--------------------------------->> OnStart Items
    void Adapters() {

        try {

            //For category AutoComplete
            tbStockGroup TableCategorySearchItem = new tbStockGroup();
            List<String> catItems = db.GetColumn(TableCategorySearchItem.GetTableName(),TableCategorySearchItem.StockName.FName,"");
            ArrayAdapter catAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,catItems);
            category.setAdapter(catAdapter);
            category.setThreshold(1);


        } catch (Exception ex) {}

    }

    void ValidateForm() {

        try {

            if (productName.getText().toString().equals("")
                    && SellingRate.getText().toString().equals("") && category.getText().toString().equals("")) {
                emptyform = true;
            }

        } catch (Exception ex) {
        }
    }

    void NewForm() {

        try {

            FormState = "new";
            Adapters();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(category.getApplicationWindowToken(), 0);

            productName.setText("");
            SellingRate.setText("");
            category.setText("");

            image.setImageResource(android.R.color.transparent);

            productName.requestFocus();

            SellingRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        String inputvalue = SellingRate.getText().toString();
                        if (TextUtils.isEmpty(inputvalue)) {

                        } else {
                            float f = Float.parseFloat(inputvalue);
                            inputvalue = String.format("%.2f", f);
                        }
                        SellingRate.setText(inputvalue);
                    }
                }
            });

            category.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(category.getApplicationWindowToken(), 0);
                        ProductSave_Click(v);
                        productName.requestFocus();
                    }
                    return false;
                }
            });

            productName.requestFocus();

            image.setImageBitmap(base64ToBitMap(AppLib.noImage));
            load();
            new Def_Lib(this);
            new CameraUtil(this);

        } catch (Exception ex) {
        }

    }

    void load(){
        try{

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {try{options();}catch (Exception e){msg(e.toString());}
                }
            });

            Product_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductSave_Click(v);
                }
            });

            Product_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductReset_Click(v);
                }
            });

            Product_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {ProductDelete_Click(v);
                }
            });

            Product_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {ProductSearch_Click(v);
                }
            });

        }catch (Exception e){

        }
    }

    void BackAlert() {


        AlertDialog.Builder adb = new AlertDialog.Builder(Product.this);
        adb.setTitle(getTitle());
        adb.setIcon(R.drawable.denariusoft125);
        adb.setMessage(getString(R.string.somethinkwaitingtosave));
        adb.setPositiveButton(R.string.alert_ButtonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NavUtils.navigateUpFromSameTask(Product.this);
            }
        });

        adb.setNegativeButton(R.string.alert_ButtonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });
        AlertDialog ad = adb.create();
        ad.setIcon(R.drawable.denariusoft125);
        ad.show();
    }

    void searchList(String SearchText){

        try{

            ProductSearchAdapter adapter = new ProductSearchAdapter(this,SearchText);
            lstSearchitems.setAdapter(adapter);

            lstSearchitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    searchText = ProductSearchAdapter.lstProductSearchItems.get(position);

                    //search function

                    if (lstSearchitems.getCount() <= 0) {
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        };
                        Alert("No Product Found,Need Action.","Add Products",r);
                    } else {

                        try{

                            if (lstSearchitems.getCount() > 0) {

                                tbProduct Udata = new tbProduct();
                                db.GetRecordByCode(Udata.GetTable(), searchText.ProductName.FValue);
                                Pid = Udata.Id.FValue;
                                productName.setText(Udata.ProductName.FValue);

                                float str2 = Float.parseFloat(Udata.Rate.FValue);
                                String str3 = String.format("%.2f", str2);
                                SellingRate.setText(str3);

                                tbStockGroup catData = new tbStockGroup();
                                db.GetRecord(catData.GetTable(),Udata.Category.FValue);
                                category.setText(catData.StockName.FValue);

                                String img = Udata.ImagProd.FValue;
                                if(img == ""){
                                    img=AppLib.noImage;
                                }
                                image.setImageBitmap(base64ToBitMap(img));

                                FormState = "edit";

                            } else {
                                InfoAlert(getString(R.string.no_record_found));
                            }

                        }catch (Exception ex){msg(ex.toString());}
                    }

                    dialog.dismiss();
                }
            });

        }catch (Exception e){}
    }

    //--------------------------------->> Functions
    void ProductSave_Click(View v) {


        try {

            tbStockGroup catData = new tbStockGroup();
            String CatId= db.GetIdByCode(catData.GetTable(), category.getText().toString());



            if (productName.getText().toString().equals("") || SellingRate.getText().toString().equals("")
                    || category.getText().toString().equals("")) {

                InfoAlert(getString(R.string.fill_all_details));
            } else {
                tbProduct ProductTable = new tbProduct();
                ProductTable.Id.FValue = Pid;
                ProductTable.ProductName.FValue = productName.getText().toString();
                ProductTable.Category.FValue = CatId;
                ProductTable.Rate.FValue = SellingRate.getText().toString();

                String img = CameraUtil.bitmapToBase64(((BitmapDrawable) image.getDrawable()).getBitmap());
                if(img == AppLib.noImage) img ="";
                ProductTable.ImagProd.FValue= img ;

                String pname;
                String pcat;

                pname = productName.getText().toString();
                pcat = category.getText().toString();

                String Wqry = String.format("%s = '%s' and %s = '%s'", ProductTable.ProductName.FName, pname, ProductTable.Category.FName, pcat);
                List<String> ProductList = db.GetColumn(ProductTable.GetTableName(), ProductTable.ProductName.FName, Wqry);

                if (FormState.equals("new") && ProductList.size() > 0) {
                    InfoAlert(getString(R.string.product_already_exist));
                } else {
                    Long Rv;
                    if (Pid.equals("")) {
                        Rv = db.AddRecord(ProductTable.GetTable());
                    } else {
                        Rv = db.UpdateRecord(ProductTable.GetTable());
                    }
                    if (Rv != 0) {
                        InfoAlert(getString(R.string.saved_success));
                    } else {
                        InfoAlert(getString(R.string.cantsavesomthingwrong));
                    }
                    NewForm();
                }
            }
        } catch (Exception ex) {
            msg(ex.toString());
        }
    }

    void ProductDelete_Click(View v) {

        try {

            if (productName.getText().toString().equals("")) {

                InfoAlert(getString(R.string.select_product_to_delete));

            } else {

                android.app.AlertDialog.Builder ADB = new android.app.AlertDialog.Builder(Product.this);
                ADB.setTitle(this.getTitle());
                ADB.setMessage(getString(R.string.are_you_sure));
                ADB.setPositiveButton(getString(R.string.alert_ButtonYes), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                            tbProduct Udata = new tbProduct();
                            long RV = db.DeleteRecord(Udata.GetTable(), Pid);

                            if (RV != 0) {
                                InfoAlert(getString(R.string.delete_successful));
                                NewForm();
                            } else {
                                InfoAlert(getString(R.string.cantdeletesomthingwrong));

                            }

                        } catch (Exception e) {
                            msg(e.toString());
                        }

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

    void ProductReset_Click(View v) {

        try {
            NewForm();

        } catch (Exception ex) {
        }

    }

    void ProductSearch_Click(View v) {

        try {



            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.activity_search_list, null,true);
            lstSearchitems = (ListView) alertLayout.findViewById(R.id.lstSearch);
            final EditText editText = (EditText)alertLayout.findViewById(R.id.txtsearchHint);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchList(editText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            searchList("");

            alert = new AlertDialog.Builder(this);
            alert.setTitle(getString(R.string.alert_title_search));
            alert.setView(alertLayout);
            alert.setCancelable(false);

            alert.setNegativeButton(getString(R.string.alert_ButtonCancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog = alert.create();
            dialog.show();

        } catch (Exception ex) {}

    }

}
