package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import app.legend.gopiking.fmcg.R;
import app.legend.gopiking.fmcg.dbLib.BizDatabase;
import app.legend.gopiking.fmcg.dbTables.tbPurchase;
import app.legend.gopiking.fmcg.dbTables.tbSales;

import static app.legend.gopiking.fmcg.appLib.AppLib.companyName;
import static app.legend.gopiking.fmcg.appLib.AppLib.excel_link;
import static app.legend.gopiking.fmcg.appLib.Network.Check_Internet;

//import static king.legend.gtk.denrestaurant.Frm.Report.FrmPurchaseReport.PFdate;
//import static king.legend.gtk.denrestaurant.Frm.Report.FrmPurchaseReport.PTdate;
//import static king.legend.gtk.denrestaurant.Frm.Report.FrmSaleReport.SFdate;
//import static king.legend.gtk.denrestaurant.Frm.Report.FrmSaleReport.STdate;

/**
 * Created by siva on 3/29/2017.
 */

public class Pdf {

    public static String pfdate;
    public static String ptodate;

    static Activity activity;

    static Cursor sdGrid;
    static Object obj;
    static String query;
    static tbPurchase Purchase;
    static tbSales sales;

    public Pdf(Activity _activity){
        activity = _activity;
    }

   public static void pdf_Reader(Context context, String file_name) {
        try {

            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/DenPOS/"+ "/HELP/");
            if (!file.exists())file.mkdirs();

            File fileBrochure = new File(file,file_name);
            if (!fileBrochure.exists()) {
                CopyAssetsbrochure(context,file_name);
            }

            /** PDF reader code */

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(fileBrochure), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                excel_link = "n";
                context.getApplicationContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                excel_link = "n";
                Permission_Alert("NO Pdf Viewer",context,"ACTION NEED");
            }

        } catch (Exception e) {
            e.toString();
        }
    }

    //method to write the PDFs file to sd card
    private static void CopyAssetsbrochure(Context context, String file_name) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase(file_name)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/DenPOS/"+ "/HELP/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void Permission_Alert(String AlertMessage, final Context context, String title) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        adb.setTitle(title);
        adb.setIcon(R.drawable.denariusoft64);
        adb.setMessage(AlertMessage);
        adb.setPositiveButton(context.getString(R.string.alert_openlink),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
        String yes = "y";

        if(Network.getInstance(context).isOnline()){
            if(yes == excel_link){
                permission_Action("https://play.google.com/store/apps/details?id=com.infraware.office.link&hl=en",context);
            }else {
                permission_Action("https://play.google.com/store/apps/details?id=com.adobe.reader&hl=en",context);
            }
        }else{
            Check_Internet(context,"No Data Alert","Network Missing Alert");
        }

            }
        });
        adb.setNeutralButton(context.getString(R.string.install_later), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
//                pur.finish();
//                context.startActivity(new Intent(context,FrmPurchaseReport.class));
            }
        });
        AlertDialog ad = adb.create();
        ad.setIcon(R.drawable.denariusoft64);
        ad.show();
    }

    static void permission_Action(String URL, Context context) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Copy App URL from Google Play Store.
            intent.setData(Uri.parse(URL));
            context.startActivity(intent);
        } catch (Exception e) {
            e.toString();
        }
    }


    public static void Read_Pdf(Context context, File file_path){
        try{

            /** PDF reader code */
    //            File file = new File(Environment.getExternalStorageDirectory() + "/" + file_name);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file_path), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                excel_link = "n";
                context.getApplicationContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                excel_link = "n";
                Permission_Alert("NO Pdf Viewer",context,"ACTION NEED");
            }

        }catch (Exception e){e.toString();}
    }

    static void pdf_read_alert(String title, String message, final File FilePath, final Context context){
        try{

            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setCancelable(false);
            alert.setNegativeButton("Attach and Mail", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                  try{

                      String names = null;
                      Object obj;
                      BizDatabase db;
                      db= new BizDatabase(context.getApplicationContext());

                      String query = "select * from tbSettings";
                      obj = db.ExequteQuery(query);

                      Cursor sdGrid=(Cursor)obj;

                      if(sdGrid!= null)
                      {

                          if(sdGrid.moveToFirst())
                          {
                              do
                              {
                                  names = sdGrid.getString(0);

                              }while(sdGrid.moveToNext());
                          }
                      }
                      if(Network.getInstance(context).isOnline()){

                          attach_and_email(context,new Date().toString()+"REPORT",names+" : REPORT",FilePath);
                      }else{

                          Check_Internet(context,"No Data Alert","Network Missing Alert");
                      }


                  }catch (Exception e){
                      Log.d("MESS",e.toString());}
                }
            });

            alert.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Read_Pdf(context,FilePath);
                }
            });

            alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = alert.create();
            dialog.show();

        }catch (Exception e){e.toString();}
    }

    public static void attach_and_email(Context context, String subject, String body, File file_path){
        try{

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_SUBJECT,subject);
            email.putExtra(Intent.EXTRA_TEXT, body);
            Uri uri = Uri.parse("file://" +file_path.getAbsolutePath());
            email.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(email,"Select E-Mail Client"));

        }catch (Exception e){
            Def_Lib.msg(e.toString());}
    }

    public static PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    //views
    public static void Purchase_PDF(Context context){

        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

        Document document;
        File file;
        Paragraph paragraph;
        PdfPTable table;
        String filename = "Purchase.pdf";
        String url = Environment.getExternalStorageDirectory().getPath();
        BizDatabase db;
        db = new BizDatabase(context.getApplicationContext());


        try{

            {
                document = new Document();

                file = new File(url + "/DenRestaurant/"+ "/PDF/", filename);

                if (!file.exists())
                {
                    file.mkdirs();
                }

                file = new File(file, filename);
                try
                {
                    PdfWriter.getInstance(document,new FileOutputStream(file));
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

                document.open();

                paragraph = new Paragraph(companyName, catFont);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                addEmptyLine(paragraph, 1);

                paragraph =new Paragraph("Report generated on " + DateDialog.newDate(new Date())+" at "+ DateDialog.GetTimeNow(),smallBold);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                addEmptyLine(paragraph, 2);
                document.add(paragraph);

                paragraph = new Paragraph();
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add("PURCHASE REPORT");
                paragraph.add("");
                try
                {
                    document.add(paragraph);
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                Purchase = new tbPurchase();
                String FNames = "*";
                String WQry = String.format("%s >= '%s'and %s<='%s'", Purchase.PurchaseDate.FName,pfdate, Purchase.PurchaseDate.FName, ptodate);
                obj = db.GetGrid(Purchase.GetTableName(), FNames, WQry, "", "");

                sdGrid=(Cursor)obj;

                table = new PdfPTable(3);

                table.addCell("Purchase Date");
                table.addCell("Invoice Number");
                table.addCell("Total Amount");

                if(sdGrid!= null)
                {

                    if(sdGrid.moveToFirst())
                    {
                        do
                        {
                            table.addCell(sdGrid.getString(1));
                            table.addCell(sdGrid.getString(2));
                            table.addCell(sdGrid.getString(5));

                        }while(sdGrid.moveToNext());
                    }
                }

                try
                {
                    document.add(table);
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                document.addCreationDate();

                try{
                    pdf_read_alert("Select Option","PDF Created",file,context);
                }catch (Exception e){e.toString();}

                document.close();
            }

        }catch (Exception e){e.toString();}
    }

    public static void Sales_PDF(Context context){

        Document document;
        File file;
        Paragraph paragraph;
        PdfPTable table;
        String filename = "Sales.pdf";
        String url = Environment.getExternalStorageDirectory().getPath();
        BizDatabase db;
        db = new BizDatabase(context.getApplicationContext());

        try{

            {
                document = new Document();

                file = new File(url + "/DenRestaurant/"+ "/PDF/", filename);

                if (!file.exists())
                {
                    file.mkdirs();
                }

                file = new File(file, filename);
                try
                {
                    PdfWriter.getInstance(document,new FileOutputStream(file));
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

                document.open();

                paragraph = new Paragraph();
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add("Sales REPORT");
                paragraph.add("");
                try
                {
                    document.add(paragraph);
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }

                sales = new tbSales();
                String FNames = "*";
                String WQry = String.format("%s >= '%s'and %s<='%s'", sales.SaleDate.FName,pfdate, sales.SaleDate.FName, ptodate);
                obj = db.GetGrid(sales.GetTableName(), FNames, WQry, "", "");

                sdGrid=(Cursor)obj;

                table = new PdfPTable(3);

                table.addCell("Sales Date");
                table.addCell("Bill Number");
                table.addCell("Total Amount");

                if(sdGrid!= null)
                {

                    if(sdGrid.moveToFirst())
                    {
                        do
                        {
                            table.addCell(sdGrid.getString(1));
                            table.addCell(sdGrid.getString(0));
                            table.addCell(sdGrid.getString(5));

                        }while(sdGrid.moveToNext());
                    }
                }


                try
                {
                    document.add(table);
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                document.addCreationDate();

                try{
                    pdf_read_alert("Select Option","PDF Created",file,context);
                }catch (Exception e){e.toString();}

                document.close();
            }

        }catch (Exception e){e.toString();}
    }

    public static void Stock_PDF(Context context){

        Document document;
        File file;
        Paragraph paragraph;
        PdfPTable table = null;
        String filename = "Stock.pdf";
        String url = Environment.getExternalStorageDirectory().getPath();
        BizDatabase db;
        db = new BizDatabase(context.getApplicationContext());

        try{

            {
                document = new Document();

                file = new File(url + "/DenRestaurant/"+ "/PDF/", filename);

                if (!file.exists())
                {
                    file.mkdirs();
                }

                file = new File(file, filename);
                try
                {
                    PdfWriter.getInstance(document,new FileOutputStream(file));
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

                document.open();

                paragraph = new Paragraph();
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add("Stock REPORT");
                paragraph.add("");
                try
                {
                    document.add(paragraph);
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }

                Object obj;

                String query = "select im.ProductName,im.quantity,im.rquantity, ifnull(( select sum(qty) from tbPurchaseDetails as pd where pd.ProductId = im.id ),0) as PQty, ifnull(( select sum(qty) from tbSalesDetails as sd where sd.ProductId = im.id),0) as sQty from tbProduct as im";
                obj = db.ExequteQuery(query);

                Cursor sdGrid=(Cursor)obj;

                table = new PdfPTable(3);

                table.addCell("Product Name");
                table.addCell("Available");
                table.addCell("Re-Order");

                if(sdGrid!= null)
                {

                    if(sdGrid.moveToFirst())
                    {
                        do
                        {
                            float OQty = Float.parseFloat(sdGrid.getString(1));
                            float PQty = Float.parseFloat(sdGrid.getString(3));
                            float SQty = Float.parseFloat(sdGrid.getString(4));
                            int CQty = (int) ((OQty + PQty) - SQty);

                            table.addCell(sdGrid.getString(0));
                            table.addCell(String.valueOf(CQty));
                            table.addCell(sdGrid.getString(2));

                        }while(sdGrid.moveToNext());
                    }
                }

                try
                {
                    document.add(table);
                }
                catch (DocumentException e)
                {
                    e.printStackTrace();
                }
                document.addCreationDate();

                try{
                    pdf_read_alert("Select Option","PDF Created",file,context);
                }catch (Exception e){e.toString();}

                document.close();
            }

        }catch (Exception e){e.toString();}
    }

}
