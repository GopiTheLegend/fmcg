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
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.Frm.Master.Adapters.CustomerSearchAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.AppLib;
import app.legend.gopiking.fmcg.appLib.CameraUtil;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbCustomer;
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

public class Customer extends AppCompatActivity {

    TextInputEditText name,address1,address2,city,phone,email,gst,opbanace,creditAmt,creditLimit;
    AppCompatSpinner  actype,crlimitType;

    private ListView lstSearchitems;
    FloatingActionButton Customer_reset, Customer_save, Customer_delete, Customer_search,button;

    public static boolean emptyform = false;
    String Pid, FormState;
    BizDatabase db;

    tbCustomer searchText;

    AlertDialog dialog;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new BizDatabase(getApplication());
        Pid="";

        name = (TextInputEditText)findViewById(R.id.txtCustomerName);
        address1 = (TextInputEditText)findViewById(R.id.txtAddress1);
        address2 = (TextInputEditText)findViewById(R.id.txtAddress2);
        city = (TextInputEditText)findViewById(R.id.txtCity);
        phone = (TextInputEditText)findViewById(R.id.txtPhone);
        email = (TextInputEditText)findViewById(R.id.txtMailId);
        gst = (TextInputEditText)findViewById(R.id.txtGst);
        opbanace = (TextInputEditText)findViewById(R.id.txtOpenningBalance);
        creditAmt = (TextInputEditText)findViewById(R.id.txtCustomerCreditAmount);
        creditLimit = (TextInputEditText)findViewById(R.id.txtCustomerCreditLimit);

        actype = (AppCompatSpinner)findViewById(R.id.cmbAccountType);
        crlimitType = (AppCompatSpinner)findViewById(R.id.cmbCustomerCreditLimitType);

        NewForm();

    }

    //--------------------------------->> Permission Event Menu

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

    //--------------------------------->> OnStart Items
    void Adapters() {

        try {

            //For category AutoComplete
            List<String> CrLType = new ArrayList<>();
            CrLType.add("select");CrLType.add("Day"); CrLType.add("Week"); CrLType.add("Month"); CrLType.add("Year");
            ArrayAdapter catAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,CrLType);
            crlimitType.setAdapter(catAdapter);

            //For category AutoComplete
            List<String> ACType = new ArrayList<>();
            ACType.add("select");ACType.add("Credit"); ACType.add("Debit");
            ArrayAdapter ACAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,CrLType);
            actype.setAdapter(ACAdapter);


        } catch (Exception ex) {}

    }

    void ValidateForm() {

        try {

            if (name.getText().toString().equals("")
                    && address1.getText().toString().equals("")
                    && address2.getText().toString().equals("")
                    && address2.getText().toString().equals("")
                    && city.getText().toString().equals("")
                    && phone.getText().toString().equals("")
                    && email.getText().toString().equals("")
                    && gst.getText().toString().equals("")
                    && opbanace.getText().toString().equals("")
                    && creditAmt.getText().toString().equals("")
                    && creditLimit.getText().toString().equals("")
                    && actype.getSelectedItem().toString().equals("")
                    && crlimitType.getSelectedItem().toString().equals("")) {
                emptyform = true;
            }

        } catch (Exception ex) {
        }
    }

    void NewForm() {

        try {

            FormState = "new";
            Adapters();
//            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromInputMethod(crlimitType.getApplicationWindowToken(), 0);

            name.setText("");
            address1.setText("");
            address2.setText("");
            city.setText("");
            phone.setText("");
            email.setText("");
            gst.setText("");
            opbanace.setText("");
            creditAmt.setText("");
            creditLimit.setText("");
            actype.setSelection(0);
            crlimitType.setSelection(0);

            name.requestFocus();

            creditAmt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        String inputvalue = creditAmt.getText().toString();
                        if (TextUtils.isEmpty(inputvalue)) {

                        } else {
                            float f = Float.parseFloat(inputvalue);
                            inputvalue = String.format("%.2f", f);
                        }
                        creditAmt.setText(inputvalue);
                    }
                }
            });

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

            Customer_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomerSave_Click(v);
                }
            });

            Customer_reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomerReset_Click(v);
                }
            });

            Customer_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {CustomerDelete_Click(v);
                }
            });

            Customer_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {CustomerSearch_Click(v);
                }
            });

        }catch (Exception e){

        }
    }

    void BackAlert() {


        AlertDialog.Builder adb = new AlertDialog.Builder(Customer.this);
        adb.setTitle(getTitle());
        adb.setIcon(R.drawable.denariusoft125);
        adb.setMessage(getString(R.string.somethinkwaitingtosave));
        adb.setPositiveButton(R.string.alert_ButtonYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NavUtils.navigateUpFromSameTask(Customer.this);
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

            CustomerSearchAdapter adapter = new CustomerSearchAdapter(this,SearchText);
            lstSearchitems.setAdapter(adapter);

            lstSearchitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    searchText = CustomerSearchAdapter.lstCustomerSearchItems.get(position);

                    //search function

                    if (lstSearchitems.getCount() <= 0) {
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        };
                        Alert("No Customer Found,Need Action.","Add Customers",r);
                    } else {

                        try{

                            if (lstSearchitems.getCount() > 0) {

                                tbCustomer Udata = new tbCustomer();
                                db.GetRecordByCode(Udata.GetTable(), searchText.CustomerName.FValue);
                                Pid = Udata.Id.FValue;
                                name.setText(Udata.CustomerName.FValue);

                                address1.setText(Udata.Address1.FValue);
                                address2.setText(Udata.Address2.FValue);
                                city.setText(Udata.City.FValue);
                                phone.setText(Udata.Phone.FValue);
                                email.setText(Udata.MailId.FValue);
                                gst.setText(Udata.GST.FValue);
                                opbanace.setText(Udata.OpenningBalance.FValue);
                                actype.setSelection(0);

                                float str2 = Float.parseFloat(Udata.CustomerCreditAmount.FValue);
                                String str3 = String.format("%.2f", str2);
                                creditAmt.setText(str3);

                                creditLimit.setText(Udata.CustomerCreditLimit.FValue);
                                crlimitType.setSelection(0);

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
    void CustomerSave_Click(View v) {


        try {


            if (name.getText().toString().equals("")
                    || address1.getText().toString().equals("")
                    || address2.getText().toString().equals("")
                    || city.getText().toString().equals("")
                    || phone.getText().toString().equals("")
                    || email.getText().toString().equals("")
                    || gst.getText().toString().equals("")
                    || opbanace.getText().toString().equals("")
                    || creditAmt.getText().toString().equals("")
                    || creditLimit.getText().toString().equals("")
                    || actype.getSelectedItem().toString().equals("")
                    || crlimitType.getSelectedItem().toString().equals("")) {

                InfoAlert(getString(R.string.fill_all_details));
            } else {
                tbCustomer CustomerTable = new tbCustomer();
                CustomerTable.Id.FValue = Pid;
                CustomerTable.CustomerName.FValue = name.getText().toString();
                CustomerTable.Address1.FValue = address1.getText().toString();
                CustomerTable.Address2.FValue = address2.getText().toString();
                CustomerTable.City.FValue = city.getText().toString();
                CustomerTable.Phone.FValue = phone.getText().toString();
                CustomerTable.MailId.FValue = email.getText().toString();
                CustomerTable.GST.FValue = gst.getText().toString();
                CustomerTable.OpenningBalance.FValue = opbanace.getText().toString();
                CustomerTable.CustomerCreditAmount.FValue = creditAmt.getText().toString();
                CustomerTable.CustomerCreditLimit.FValue = creditLimit.getText().toString();
                CustomerTable.AcType.FValue = actype.getSelectedItem().toString();
                CustomerTable.CustomerCreditLimitType.FValue = crlimitType.getSelectedItem().toString();
                CustomerTable.RemoteId.FValue = "0";

                String cname;
                String ph;

                cname = name.getText().toString();
                ph = phone.getText().toString();

                String Wqry = String.format("%s = '%s' and %s = '%s'", CustomerTable.CustomerName.FName, cname, CustomerTable.Phone.FName, ph);
                List<String> ProductList = db.GetColumn(CustomerTable.GetTableName(), CustomerTable.CustomerName.FName, Wqry);

                if (FormState.equals("new") && ProductList.size() > 0) {
                    InfoAlert(getString(R.string.product_already_exist));
                } else {
                    Long Rv;
                    if (Pid.equals("")) {
                        Rv = db.AddRecord(CustomerTable.GetTable());
                    } else {
                        Rv = db.UpdateRecord(CustomerTable.GetTable());
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

    void CustomerDelete_Click(View v) {

        try {

            if (name.getText().toString().equals("")) {

                InfoAlert(getString(R.string.select_product_to_delete));

            } else {

                android.app.AlertDialog.Builder ADB = new android.app.AlertDialog.Builder(Customer.this);
                ADB.setTitle(this.getTitle());
                ADB.setMessage(getString(R.string.are_you_sure));
                ADB.setPositiveButton(getString(R.string.alert_ButtonYes), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try {

                            tbCustomer Udata = new tbCustomer();
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

    void CustomerReset_Click(View v) {

        try {
            NewForm();

        } catch (Exception ex) {
        }

    }

    void CustomerSearch_Click(View v) {

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
