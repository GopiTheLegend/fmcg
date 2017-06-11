package app.legend.gopiking.fmcg.Frm.Master;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.legend.gopiking.fmcg.Frm.Master.Adapters.UomSearchAdapter;
import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.appLib.Def_Lib;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbUom;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.Alert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.BackAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.ExitAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.InfoAlert;
import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;

public class FrmUom extends AppCompatActivity {

    TextInputEditText symbol;
    android.support.v7.widget.AppCompatAutoCompleteTextView formalName;
    FloatingActionButton Uom_reset,Uom_save,Uom_delete,Uom_search,Uom_Report;
    CollapsingToolbarLayout collapsingToolbar;
    String Pid,FormState;
    BizDatabase db;
    ArrayList dataAdapter;
    AlertDialog dialog;
    AlertDialog.Builder alert;

    private ListView lstUomSearch;

    tbUom searchText;

    public static boolean emptyform = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_uom);

        db = new BizDatabase(getApplication());
        Pid="";

        symbol = (TextInputEditText)findViewById(R.id.txtUomSymbol);
        formalName = (android.support.v7.widget.AppCompatAutoCompleteTextView)findViewById(R.id.txtUomFormalName);

        Uom_save =(FloatingActionButton)findViewById(R.id.fab_UomSave);
        Uom_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UomSave_Click(v);
            }
        });
        Uom_reset =(FloatingActionButton)findViewById(R.id.fab_UomReset);
        Uom_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UomReset_Click(v);
            }
        });
        Uom_delete =(FloatingActionButton)findViewById(R.id.fab_UomDelete);
        Uom_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UomDelete_Click(v);
            }
        });
        Uom_search =(FloatingActionButton)findViewById(R.id.fab_UomSearch);
        Uom_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UomSearch_Click(v);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NewForm();
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        new Def_Lib(this);
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
                    //AlertMessage = "Somthing Waiting To Save Are You Sure Go Back Without Save....?";
                    BackAlert();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void Adapters(){

        try{
            //For Item AutoComplete
            tbUom TableUomCategory = new tbUom();
            List<String> UomCategory = db.GetColumn(TableUomCategory.GetTableName(),TableUomCategory.FormalName.FName,"");
            ArrayAdapter UomCategorydataAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,UomCategory);
            formalName.setAdapter(UomCategorydataAdapter);
            formalName.setThreshold(1);

        }catch (Exception ex){}

    }

    void ValidateForm(){

        try{

            if(symbol.getText().toString().equals("")&& formalName.getText().toString().equals("")){
                emptyform = true;
            }

        }catch (Exception ex){}
    }

    void NewForm(){

        try{

            FormState = "new";
            Adapters();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromInputMethod(formalName.getApplicationWindowToken(),0);

            symbol.setText("");
            formalName.setText("");
            symbol.requestFocus();

            formalName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(formalName.getApplicationWindowToken(), 0);
                        UomSave_Click(v);
                    }
                    return false;
                }
            });

        }catch (Exception ex){}

    }

    void UomSave_Click(View v){
        try{
            if(symbol.getText().toString().equals("") || formalName.getText().toString().equals("")){

                InfoAlert("Fill All Details...!");
            }else{
                tbUom UomTable = new tbUom();
                UomTable.Id.FValue = Pid;
                UomTable.Symbol.FValue = symbol.getText().toString();
                UomTable.FormalName.FValue = formalName.getText().toString();

                String sym;
                String fname;

                sym = symbol.getText().toString();
                fname = formalName.getText().toString();

                String Wqry = String.format("%s = '%s' and %s = '%s'",UomTable.Symbol.FName,sym,UomTable.FormalName.FName,fname);
                List<String> UomList = db.GetColumn(UomTable.GetTableName(),UomTable.Symbol.FName,Wqry);

                if(FormState.equals("new") && UomList.size() > 0){
                    InfoAlert("Symbol Already Exist");
                }else{
                    Long Rv;
                    if(Pid.equals("")){
                        Rv = db.AddRecord(UomTable.GetTable());
                    }else{
                        Rv = db.UpdateRecord(UomTable.GetTable());
                    }
                    if(Rv!=0){
                        InfoAlert("Saved SuccessFully...!");
                    }else{
                        InfoAlert("Can't Save Something Went Wrong...!");
                    }
                    NewForm();
                }
            }
        }catch (Exception ex){msg(ex.toString());}
    }

    void UomDelete_Click(View v) {

        try {

            if(symbol.getText().toString().equals("")){

                InfoAlert("Select Uom To Delete");

            }else{

                android.app.AlertDialog.Builder ADB = new android.app.AlertDialog.Builder(FrmUom.this);
                ADB.setTitle(this.getTitle()+"Alert");
                ADB.setMessage("Are You Sure ...?");
                ADB.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        try{

                            tbUom Udata = new tbUom();
                            long RV = db.DeleteRecord(Udata.GetTable(),Pid);

                            if(RV!=0){
                                android.app.AlertDialog.Builder ADB = new android.app.AlertDialog.Builder(FrmUom.this);
                                ADB.setTitle("Uom"+"Alert");
                                ADB.setMessage("Deleted Successfully");
                                ADB.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });

                                android.app.AlertDialog ad = ADB.create();
                                ad.setIcon(R.drawable.denariusoft64);
                                ad.show();
                                NewForm();                        }
                            else
                            {
                                InfoAlert("Delete Not Done Something Went Wrong");

                            }

                        }catch (Exception e){msg(e.toString());}

                    }
                });

                ADB.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                android.app.AlertDialog ad = ADB.create();
                ad.setIcon(R.drawable.denariusoft64);
                ad.show();
            }

        } catch (Exception ex) {}

    }

    void UomReset_Click(View v){

        try {
            NewForm();

        } catch (Exception ex) {}

    }

    private void searchList(final String SearchText) {

        try{

            UomSearchAdapter adapter = new UomSearchAdapter(this,SearchText);
            lstUomSearch.setAdapter(adapter);

            lstUomSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    searchText = UomSearchAdapter.lstUomSearchItem.get(position);

                    if (lstUomSearch.getCount() <= 0) {
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        };
                        Alert("No Symbol Found,Need Action.","Add UOM",r);
                    } else {
                        try {

                            if (lstUomSearch.getCount() > 0) {
                                InfoAlert(String.valueOf(lstUomSearch.getCount()));
                                tbUom Udata = new tbUom();
                                db.GetRecordByCode(Udata.GetTable(), searchText.Symbol.FValue);

                                Pid = Udata.Id.FValue;
                                symbol.setText(Udata.Symbol.FValue);
                                formalName.setText(Udata.FormalName.FValue);

                                FormState = "edit";

                            } else {
                                InfoAlert("No Record Found...!");
                            }

                        } catch (Exception e) { msg(e.toString());}
                    }
                    dialog.dismiss();
                }
            });

        }catch (Exception e){
            msg(e.toString());
        }
    }

    void UomSearch_Click(View v){

        try {

            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.activity_search_list, null,true);
            lstUomSearch = (ListView) alertLayout.findViewById(R.id.lstSearch);
            final EditText editText = (EditText)alertLayout.findViewById(R.id.txtsearchHint);
            editText.setHint("Symbol Name");

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
            alert.setTitle("Search");
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            dialog = alert.create();
            dialog.show();


        } catch (Exception ex) {msg(ex.toString());}

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try{
            emptyform =false;
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    ValidateForm();
                    if(emptyform){
                        finish();
                    }else{
                        ExitAlert("Somthing Waiting To Save Are You Sure Go Back Without Save....?");
                    }
                    return true;
            }

        }catch (Exception ex){}

        return super.onKeyDown(keyCode, event);
    }

}
