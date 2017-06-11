package app.legend.gopiking.fmcg.appLib;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;

import static app.legend.gopiking.fmcg.appLib.Def_Lib.msg;


/**
 * Created by GopiKingMaker on 4/12/2017.
 */

public class DisplayUtil {

    public static int dh1,dh2,dh3,dw1,dw2,dw3;
    public static int ph1,ph2,ph3,ph4,ph5,pw1,pw2,pw3,pw4,pw5;
    public static int ah1,ah2,ah3,ah4,aw1,aw2,aw3,aw4;

    static Activity activity;

    public DisplayUtil(Activity _activity){
        activity = _activity;

        new Def_Lib(_activity);
    }


    public static double Displaysize;
    public static double x;
    public static double y;

    public static double screenInches;
    public static int purchaseHight1, purchaseHight2, purchaseHight3, purchaseWidth1, purchaseWidth2, purchaseWidth3;
    public static int SaleHight1, SaleHight2, SaleHight3, SaleWidth1, SaleWidth2, SaleWidth3;
    public static int StockHight1, StockHight2,StockHight3, StockWidth1, StockWidth2,StockWidth3;

    //declare a bill item values

    //1st Remove button
    //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
    public static int rbl, rbt, rbr, rbb;

    //layout params width height and weight remove button
    public static int rbw, rbh;

    //2nd Product Name width and height weight and color TextSize
    public static int nw, nh, nts;
    public static String nc;
    //3rd Quantity width height and weight color TextSize
    public static int qw, qh, qts;
    public static String qc;
    //4th Rate width height weight and color TextSize
    public static int rw, rh, rts;
    public static String rc;
    //5th Amount width height weight and color TextSize
    public static int aw, ah, ats;
    public static String ac;
    //6th parent params weight
    public static int pw;

    //===================================


     //find your display size
     public static double finddisplaysize() {

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double X = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double Y = Math.pow(dm.heightPixels / dm.ydpi, 2);

        screenInches = Math.sqrt(X + Y);

        Displaysize = screenInches;
        x = dm.widthPixels;
        y = dm.heightPixels;

         //InfoAlert(x+"----"+y+"----"+Displaysize);
         return screenInches;
    }

    //make screen for ur screen size by using this validation
    public static void ReportDisplayValidate(){

        try{

            if((screenInches > 4)&&(screenInches < 4.5)){

                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                FourInchScreen();

            }else if((screenInches >= 4.5)&&(screenInches < 5)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 4.5 - 5 inch Display" + "x value is : "+ String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) , Toast.LENGTH_LONG).show();
                Five_Two_InchScreen();
            }else if((screenInches >= 5)&&(screenInches < 5.1)){

                if((AppLib.x > 1500)&&(AppLib.x <1850)){
//                    Toast.makeText(getApplicationContext(), "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) + " 5 - 5.1 1920px inch Display", Toast.LENGTH_LONG).show();

                }else if((AppLib.x > 2400)&&(AppLib.x <=2560)){
//                    Toast.makeText(getApplicationContext(), "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) + " 5 - 5.1 2560px inch Display", Toast.LENGTH_LONG).show();

                }else{
//                    Toast.makeText(getApplicationContext(),"x value is :  "+String.valueOf(AppLib.x) +" ==== "+ String.valueOf(AppLib.Displaysize)+"UnknownTablet", Toast.LENGTH_LONG).show();

                }

            }else if((screenInches > 5.1)&&(screenInches < 5.2)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 5.1 - 5.2 inch Display", Toast.LENGTH_LONG).show();
                Five_One_to_Five_Two_InchScreen();
            }else if((screenInches >= 5.2)&&(screenInches < 5.3)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 5.2 - 5.3 inch Display", Toast.LENGTH_LONG).show();

            }else if((screenInches > 5.3)&&(screenInches < 5.4)){
                if((AppLib.x > 2500)&&(AppLib.x <2600)){
//                    Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 5.3 - 5.4 inch Display"+" X : "+AppLib.x + " Y  :"+AppLib.y, Toast.LENGTH_LONG).show();

                }else{
//                    Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 5.3 - 5.4 inch Display"+" X : "+AppLib.x + " Y  :"+AppLib.y, Toast.LENGTH_LONG).show();

                }

            }else if((screenInches > 5.4)&&(screenInches < 5.7)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 5.4 - 5.7 inch Display" + " Five_One_480_800_Emulator", Toast.LENGTH_LONG).show();
                Five_Four_to_Five_Seven_InchScreen();

            }else if((screenInches > 5.7)&&(screenInches < 6)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 5.7 - 6 inch Display", Toast.LENGTH_LONG).show();
                Five_Seven_to_Six_InchScreen();

            }else if((screenInches > 6)&&(screenInches < 6.2)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 6 - 6.2 inch Display", Toast.LENGTH_LONG).show();

            }else if((screenInches > 6.2)&&(screenInches < 6.5)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 6.2 - 6.5 inch Display", Toast.LENGTH_LONG).show();

            }else if((screenInches > 6.92) && (screenInches < 6.93)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 6.92 - 6.93 inch Display", Toast.LENGTH_LONG).show();
                Six_Nine_Two_to_Six_Nine_Three_InchScreen();
            }else if((screenInches > 6.93) && (screenInches < 7)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 6.93 - 7 inch Display" +  "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) , Toast.LENGTH_LONG).show();
                Six_Nine_Three_to_SevenInchScreen();
            }else if((screenInches > 7)&&(screenInches < 7.2)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 7 - 7.8 inch Display", Toast.LENGTH_LONG).show();
                //Account book  Report height width you can set here
                defaultheightwidthReport();
            }else if((screenInches >= 7.8)&&(screenInches < 7.9)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 7.8 - 7.9 inch Display", Toast.LENGTH_LONG).show();

            }else if((screenInches > 7.9)&&(screenInches < 9)){
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize) + " 7.9 - 9 inch Display", Toast.LENGTH_LONG).show();
                Seven_Nine_to_Nine_InchScreen();
            }else if((screenInches > 9)&&(screenInches < 10)){

                if((AppLib.x > 700)&&(AppLib.x <900)){
//                    Toast.makeText(getApplicationContext(), "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) + " 9 - 10 800px inch Display", Toast.LENGTH_LONG).show();

                }else if((AppLib.x > 1200)&&(AppLib.x <2400)){
//                    Toast.makeText(getApplicationContext(), "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) + " 9 - 10 1280px inch Display", Toast.LENGTH_LONG).show();

                }else if((AppLib.x > 2400)&&(AppLib.x <3000)){
//                    Toast.makeText(getApplicationContext(), "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) + " 9 - 10 2560px inch Display", Toast.LENGTH_LONG).show();

                }else{
//                    Toast.makeText(getApplicationContext(),"x value is :  "+String.valueOf(AppLib.x) +" ==== "+ String.valueOf(AppLib.Displaysize)+"UnknownTablet", Toast.LENGTH_LONG).show();

                }

            }else{
//                Toast.makeText(getApplicationContext(), String.valueOf(AppLib.Displaysize)+"UnknownName" +  "x value is : "+String.valueOf(AppLib.x) + "y value is : "+String.valueOf(AppLib.y) , Toast.LENGTH_LONG).show();
                Six_Nine_Three_to_SevenInchScreen();
            }
        }catch(Exception ex){}

    }

    public static void defaultheightwidthReport() {
//
        //Account book  Report height width you can set here
        ah1 = 50;   ah2 = 50;   ah3 = 50;   ah4 = 50;   aw1 = 100;  aw2 = 100;  aw3 = 100;  aw4 = 100;

        //DaySale book Report  height width you can set here
        dh1 = 55;   dh2 = 55;   dh3 = 55;   dw1 = 150;  dw2 = 150;  dw3 = 150;

        //purchase book Report height width you can set here
        ph1 = 50;   ph2 = 50;   ph3 = 50;   ph4 = 50;   ph5 = 50;   pw1 = 100;  pw2 = 100;  pw3 = 100;  pw4 = 100;  pw5 = 100;

    }

    //4.7 (720 x 1280) , (768 x 1280) and 4.95(1080 x 1920) Emulator
    public static  void FourInchScreen () {

        if ((AppLib.x > 1100) && (AppLib.x < 1950)) {

            msg(String.valueOf(AppLib.Displaysize) + " 4 - 4.5 inch Display and x is : " + AppLib.x + "  and Y : " + AppLib.y + " Four_Nine_Six_Emulator");

            //Account book  Report height width you can set here
            ah1 = 50;
            ah2 = 50;
            ah3 = 50;
            ah4 = 50;
            aw1 = 250;
            aw2 = 200;
            aw3 = 200;
            aw4 = 200;

            //DaySale book Report  height width you can set here
            dh1 = 55;
            dh2 = 55;
            dh3 = 55;
            dw1 = 300;
            dw2 = 300;
            dw3 = 300;

            //purchase book Report height width you can set here
            ph1 = 55;
            ph2 = 55;
            ph3 = 55;
            ph4 = 55;
            ph5 = 55;
            pw1 = 230;
            pw2 = 230;
            pw3 = 230;
            pw4 = 230;
            pw5 = 230;
        } else if ((AppLib.x > 730) && (AppLib.x < 770)) {
            msg(String.valueOf(AppLib.Displaysize) + " 4 - 4.5 inch Display and x is : " + AppLib.x + "  and Y : " + AppLib.y + " Four_Seven_Emulator");
            //Account book  Report height width you can set here
            ah1 = 50;
            ah2 = 50;
            ah3 = 50;
            ah4 = 50;
            aw1 = 250;
            aw2 = 200;
            aw3 = 200;
            aw4 = 200;

            //DaySale book Report  height width you can set here
            dh1 = 55;
            dh2 = 55;
            dh3 = 55;
            dw1 = 300;
            dw2 = 300;
            dw3 = 300;

            //purchase book Report height width you can set here
            ph1 = 55;
            ph2 = 55;
            ph3 = 55;
            ph4 = 55;
            ph5 = 55;
            pw1 = 230;
            pw2 = 230;
            pw3 = 230;
            pw4 = 230;
            pw5 = 230;

        } else if ((AppLib.x > 770) && (AppLib.x < 1300)) {
            msg(String.valueOf(AppLib.Displaysize) + " 4 - 4.5 inch Display and x is : " + AppLib.x + "  and Y : " + AppLib.y);

            //Account book  Report height width you can set here
            ah1 = 50;
            ah2 = 50;
            ah3 = 50;
            ah4 = 50;
            aw1 = 250;
            aw2 = 200;
            aw3 = 200;
            aw4 = 200;

            //DaySale book Report  height width you can set here
            dh1 = 55;
            dh2 = 55;
            dh3 = 55;
            dw1 = 300;
            dw2 = 300;
            dw3 = 300;

            //purchase book Report height width you can set here
            ph1 = 55;
            ph2 = 55;
            ph3 = 55;
            ph4 = 55;
            ph5 = 55;
            pw1 = 350;
            pw2 = 350;
            pw3 = 350;
            pw4 = 350;
            pw5 = 350;
        } else {

            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            msg(String.valueOf(AppLib.Displaysize) + " 4 - 4.5 inch Display and x is : " + AppLib.x + "  and Y : " + AppLib.y);
            //Account book  Report height width you can set here
            ah1 = 75;
            ah2 = 75;
            ah3 = 75;
            ah4 = 75;
            aw1 = 200;
            aw2 = 190;
            aw3 = 180;
            aw4 = 180;

            //DaySale book Report  height width you can set here
            dh1 = 60;
            dh2 = 60;
            dh3 = 60;
            dw1 = 230;
            dw2 = 230;
            dw3 = 220;

            //purchase book Report height width you can set here
            ph1 = 60;
            ph2 = 60;
            ph3 = 60;
            ph4 = 60;
            ph5 = 60;
            pw1 = 150;
            pw2 = 190;
            pw3 = 190;
            pw4 = 150;
            pw5 = 150;
        }
    }


    //5.4(480 x 800) Five_One_480_800_Emulator
    public static void Five_Four_to_Five_Seven_InchScreen(){

        //Account book  Report height width you can set here
        ah1 = 50;   ah2 = 50;   ah3 = 50;   ah4 = 50;   aw1 = 150;  aw2 = 100;  aw3 = 100;  aw4 = 100;

        //DaySale book Report  height width you can set here
        dh1 = 55;   dh2 = 55;   dh3 = 55;   dw1 = 100;  dw2 = 100;  dw3 = 100;

        //purchase book Report height width you can set here
        ph1 = 55;   ph2 = 55;   ph3 = 55;   ph4 = 55;   ph5 = 55;   pw1 = 90;  pw2 = 90;  pw3 = 90;  pw4 = 90;  pw5 = 90;

    }

    //5.1 to 5.2 (2560 x 1532) Five_one_2560_1532_Emulator
    public static void Five_One_to_Five_Two_InchScreen(){

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Account book  Report height width you can set here
        ah1 = 65;   ah2 = 65;   ah3 = 65;   ah4 = 65;   aw1 = 250;  aw2 = 200;  aw3 = 200;  aw4 = 200;

        //DaySale book Report  height width you can set here
        dh1 = 65;   dh2 = 65;   dh3 = 65;   dw1 = 250;  dw2 = 250;  dw3 = 250;

        //purchase book Report height width you can set here
        ph1 = 65;   ph2 = 65;   ph3 = 65;   ph4 = 65;   ph5 = 65;   pw1 = 230;  pw2 = 330;  pw3 = 260;  pw4 = 250;  pw5 = 200;
    }

    //4.5 to 5 (1080 x 1920) Five_Two_1080_1920_Emulator and Five_Five_540_960_LG3_Stylus_Emulator and Five_Five_1440_2560_Emulator
    public static  void Five_Two_InchScreen(){

        if((AppLib.x > 530)&&(AppLib.x < 700)){

            //Account book  Report height width you can set here
            ah1 = 65;   ah2 = 65;   ah3 = 65;   ah4 = 65;   aw1 = 150;  aw2 = 100;  aw3 = 100;  aw4 = 100;

            //DaySale book Report  height width you can set here
            dh1 = 65;   dh2 = 65;   dh3 = 65;   dw1 = 150;  dw2 = 150;  dw3 = 150;

            //purchase book Report height width you can set here
            ph1 = 65;   ph2 = 65;   ph3 = 65;   ph4 = 65;   ph5 = 65;   pw1 = 120;  pw2 = 130;  pw3 = 100;  pw4 = 90;  pw5 = 100;

        }else if((AppLib.x > 700)&&(AppLib.x < 1300)){

            //Account book  Report height width you can set here
            ah1 = 65;   ah2 = 65;   ah3 = 65;   ah4 = 65;   aw1 = 150;  aw2 = 100;  aw3 = 100;  aw4 = 100;

            //DaySale book Report  height width you can set here
            dh1 = 65;   dh2 = 65;   dh3 = 65;   dw1 = 150;  dw2 = 150;  dw3 = 150;

            //purchase book Report height width you can set here
            ph1 = 65;   ph2 = 65;   ph3 = 65;   ph4 = 65;   ph5 = 65;   pw1 = 120;  pw2 = 130;  pw3 = 100;  pw4 = 90;  pw5 = 100;

        }else if ((AppLib.x >1400) && (AppLib.x < 2500)){

            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            //Account book  Report height width you can set here
            ah1 = 70;   ah2 = 70;   ah3 = 70;   ah4 = 70;   aw1 = 350;  aw2 = 250;  aw3 = 250;  aw4 = 250;

            //DaySale book Report  height width you can set here
            dh1 = 70;   dh2 = 70;   dh3 = 70;   dw1 = 350;  dw2 = 350;  dw3 = 350;

            //purchase book Report height width you can set here
            ph1 = 70;   ph2 = 70;   ph3 = 70;   ph4 = 70;   ph5 = 70;   pw1 = 270;  pw2 = 320;  pw3 = 250;  pw4 = 240;  pw5 = 230;

        }else{

            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //Account book  Report height width you can set here
            ah1 = 65;   ah2 = 65;   ah3 = 65;   ah4 = 65;   aw1 = 250;  aw2 = 200;  aw3 = 200;  aw4 = 200;

            //DaySale book Report  height width you can set here
            dh1 = 65;   dh2 = 65;   dh3 = 65;   dw1 = 250;  dw2 = 250;  dw3 = 250;

            //purchase book Report height width you can set here
            ph1 = 65;   ph2 = 65;   ph3 = 65;   ph4 = 65;   ph5 = 65;   pw1 = 220;  pw2 = 250;  pw3 = 200;  pw4 = 190;  pw5 = 150;

        }
    }

    //5.7 to 6 (480 x 854) Five_Four_480_854_Emulator and Five_Seven_1440_2560_Emulator
    public static void Five_Seven_to_Six_InchScreen(){

        if((AppLib.x >450)&&(AppLib.x < 860)){

            //Account book  Report height width you can set here
            ah1 = 50;   ah2 = 50;   ah3 = 50;   ah4 = 50;   aw1 = 150;  aw2 = 100;  aw3 = 100;  aw4 = 100;

            //DaySale book Report  height width you can set here
            dh1 = 50;   dh2 = 50;   dh3 = 50;   dw1 = 150;  dw2 = 150;  dw3 = 150;

            //purchase book Report height width you can set here
            ph1 = 55;   ph2 = 55;   ph3 = 55;   ph4 = 55;   ph5 = 55;   pw1 = 90;  pw2 = 90;  pw3 = 90;  pw4 = 90;  pw5 = 90;

        }else if ((AppLib.x > 1400)&&(AppLib.x < 2600)){

            //Account book  Report height width you can set here
            ah1 = 70;   ah2 = 70;   ah3 = 70;   ah4 = 70;   aw1 = 350;  aw2 = 300;  aw3 = 300;  aw4 = 300;

            //DaySale book Report  height width you can set here
            dh1 = 50;   dh2 = 50;   dh3 = 50;   dw1 = 350;  dw2 = 350;  dw3 = 350;

            //purchase book Report height width you can set here
            ph1 = 55;   ph2 = 55;   ph3 = 55;   ph4 = 55;   ph5 = 55;   pw1 = 290;  pw2 = 290;  pw3 = 290;  pw4 = 230;  pw5 = 270;

        }else{defaultheightwidth();}

    }

    //6.93 to 7 (800 x 1280)Seven_Two_Seven_800_1280_Emulator
    public static  void Six_Nine_Three_to_SevenInchScreen(){

        if((AppLib.x > 750)&&(AppLib.x < 850)){

            //Account book  Report height width you can set here
            ah1 = 65;   ah2 = 65;   ah3 = 65;   ah4 = 65;   aw1 = 150;  aw2 = 100;  aw3 = 100;  aw4 = 100;

            //DaySale book Report  height width you can set here
            dh1 = 65;   dh2 = 65;   dh3 = 65;   dw1 = 150;  dw2 = 150;  dw3 = 150;

            //purchase book Report height width you can set here
            ph1 = 65;   ph2 = 65;   ph3 = 65;   ph4 = 65;   ph5 = 65;   pw1 = 120;  pw2 = 130;  pw3 = 100;  pw4 = 90;  pw5 = 100;

        }else if ((AppLib.x > 1100)&&(AppLib.x < 1290)){

            //Account book  Report height width you can set here
            ah1 = 80;   ah2 = 80;   ah3 = 80;   ah4 = 80;   aw1 = 250;  aw2 = 300;  aw3 = 200;  aw4 = 200;

            //DaySale book Report  height width you can set here
            dh1 = 70;   dh2 = 70;   dh3 = 70;   dw1 = 350;  dw2 = 350;  dw3 = 350;

            //purchase book Report height width you can set here
            ph1 = 70;   ph2 = 70;   ph3 = 70;   ph4 = 70;   ph5 = 70;   pw1 = 200;  pw2 = 200;  pw3 = 200;  pw4 = 160;  pw5 = 160;

        }else if((AppLib.x > 1200)&&(AppLib.x < 1300)){

            //Account book  Report height width you can set here
            ah1 = 65;   ah2 = 65;   ah3 = 65;   ah4 = 65;   aw1 = 150;  aw2 = 100;  aw3 = 100;  aw4 = 100;

            //DaySale book Report  height width you can set here
            dh1 = 65;   dh2 = 65;   dh3 = 65;   dw1 = 150;  dw2 = 150;  dw3 = 150;

            //purchase book Report height width you can set here
            ph1 = 65;   ph2 = 65;   ph3 = 65;   ph4 = 65;   ph5 = 65;   pw1 = 120;  pw2 = 130;  pw3 = 100;  pw4 = 90;  pw5 = 100;
        }
    }

    //6.92 to 6.93 (1200 x 1920) Seven_Zero_Two_Emulator
    public static  void Six_Nine_Two_to_Six_Nine_Three_InchScreen(){

        //Account book  Report height width you can set here
        ah1 = 80;   ah2 = 80;   ah3 = 80;   ah4 = 80;   aw1 = 250;  aw2 = 300;  aw3 = 200;  aw4 = 200;

        //DaySale book Report  height width you can set here
        dh1 = 70;   dh2 = 70;   dh3 = 70;   dw1 = 350;  dw2 = 350;  dw3 = 350;

        //purchase book Report height width you can set here
        ph1 = 70;   ph2 = 70;   ph3 = 70;   ph4 = 70;   ph5 = 70;   pw1 = 200;  pw2 = 200;  pw3 = 200;  pw4 = 160;  pw5 = 160;

    }

    //7.9 to 9 (2048 x 1536)Eight_Eight_Six_Emulator
    public static void Seven_Nine_to_Nine_InchScreen(){

        //Account book  Report height width you can set here
        ah1 = 80;   ah2 = 80;   ah3 = 80;   ah4 = 80;   aw1 = 250;  aw2 = 300;  aw3 = 200;  aw4 = 200;

        //DaySale book Report  height width you can set here
        dh1 = 70;   dh2 = 70;   dh3 = 70;   dw1 = 350;  dw2 = 350;  dw3 = 350;

        //purchase book Report height width you can set here
        ph1 = 70;   ph2 = 70;   ph3 = 70;   ph4 = 70;   ph5 = 70;   pw1 = 200;  pw2 = 200;  pw3 = 200;  pw4 = 160;  pw5 = 160;
    }


    //make screen for ur screen size by using this validation
    public static void DisplayValidate() {

        try {

            if ((screenInches > 4) && (screenInches < 4.5)) {
//				msg(String.valueOf(Displaysize) + " 4 - 4.5 inch Display");
                FourFiveInchDisplay();
            } else if ((screenInches >= 4.5) && (screenInches < 5)) {
                if ((x > 900) && (x < 1000)) {
//                    msg("x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 4.5 - 5 inch Display"+"LG3 Stylus 960 x 540");
                } else {

//                    msg("x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 4.5 - 5 inch Display");
                    FourSevenInchDisplay();
                }
            } else if ((screenInches >= 5) && (screenInches < 5.1)) {

                if ((x > 1500) && (x < 1850)) {
//                msg("x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 5 - 5.1 1920px inch Display");
                    FiveOneInchDisplay();
                } else if ((x > 2400) && (x <= 2560)) {
//                    msg("x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 5 - 5.1 2560px inch Display");
                    FiveOneInchDisplay1();
                } else {
//                    msg("x value is :  "+String.valueOf(x) +" ==== "+ String.valueOf(Displaysize)+"UnknownTablet");
//                    UnknownInchDisplay(activity);
                }

            } else if ((screenInches > 5.1) && (screenInches < 5.2)) {
//				msg(String.valueOf(Displaysize) + " 5.1 - 5.2 inch Display");
                FiveOneSevenInchDisplay();
            } else if ((screenInches >= 5.2) && (screenInches < 5.3)) {
//                msg(String.valueOf(Displaysize) + " 5.2 - 5.3 inch Display");
                FiveTwoFiveInchDisplay();
            } else if ((screenInches > 5.3) && (screenInches < 5.4)) {
                if ((x > 2500) && (x < 2600)) {
//                    msg(String.valueOf(Displaysize) + " 5.3 - 5.4 inch Display"+" X : "+x + " Y  :"+y);
                    FiveThreeInch();
                } else {
//                    msg(String.valueOf(Displaysize) + " 5.3 - 5.4 inch Display"+" X : "+x + " Y  :"+y);
                    FiveInchDisplay();
                }

            } else if ((screenInches > 5.4) && (screenInches < 5.7)) {
//                msg(String.valueOf(Displaysize) + " 5.4 - 5.7 inch Display");
                FiveFourInchDisplay();
            } else if ((screenInches > 5.7) && (screenInches < 6)) {
//                msg(String.valueOf(Displaysize) + " 5.7 - 6 inch Display");
                FiveSevenInchDisplay();
            } else if ((screenInches > 6) && (screenInches < 6.2)) {
//                msg(String.valueOf(Displaysize) + " 6 - 6.2 inch Display");
                SixOneInchDisplay();
            } else if ((screenInches > 6.2) && (screenInches < 6.5)) {
//                msg(String.valueOf(Displaysize) + " 6.2 - 6.5 inch Display");
                SixInchDisplay();
            } else if ((screenInches > 6.5) && (screenInches < 6.8)) {
//                msg(String.valueOf(Displaysize) +"x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 6.5 to 6.8 inch Display");
                SixSevenInchDisplay();
            } else if ((screenInches > 6.92) && (screenInches < 6.93)) {
//                msg(String.valueOf(Displaysize) + " 6.92 - 6.93 inch Display");
                SixNineTwoInchDisplay();
            } else if ((screenInches > 6.93) && (screenInches < 7)) {
//                msg(String.valueOf(Displaysize) + " 6.93 - 7 inch Display");
                SixNineThreeInchDisplay();
            } else if ((screenInches > 7) && (screenInches < 7.8)) {
//                    msg(String.valueOf(Displaysize) + " 7 - 7.8 inch Display");
                SevenInchDisplay();
                defaultheightwidth();
            } else if ((screenInches >= 7.8) && (screenInches < 7.9)) {
//                msg(String.valueOf(Displaysize) + " 7.8 - 7.9 inch Display");
                SevenEightInchDisplay();
            } else if ((screenInches > 7.9) && (screenInches < 9)) {
//                msg(String.valueOf(Displaysize) + " 7.9 - 9 inch Display");
                SevenEightInchDisplay();
            } else if ((screenInches > 9) && (screenInches < 10)) {

                if ((x > 1200) && (x < 2400)) {
//                    msg("x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 9 - 10 1280px inch Display");
                    NineInchDisplay1();
                } else if ((x > 2400) && (x < 3000)) {
//                    msg("x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) + " 9 - 10 2560px inch Display");
                    NineInchDisplay();
                } else {
//                    msg("x value is :  "+String.valueOf(x) +" ==== "+ String.valueOf(Displaysize)+"UnknownTablet");
//                    UnknownInchDisplay(LinearLayoutId,GrandTotalId,purchaseDetails,activity);
                }

            } else {
//                msg(String.valueOf(Displaysize)+"x value is : "+String.valueOf(x) + "y value is : "+String.valueOf(y) +"UnknownName");
//                UnknownInchDisplay(LinearLayoutId,GrandTotalId,purchaseDetails,activity);
            }
        } catch (Exception ex) {
        }

    }


    //<<<<-------- Display size screens Defined by sized name----------->>>>>

    public static void loadDisplaySizes(){
        defaultheightwidth();
        FourFiveInchDisplay();
        FiveThreeInch();
        FourSevenInchDisplay();
        FiveInchDisplay();
        FiveTwoFiveInchDisplay();
        FiveOneInchDisplay();
        FiveOneInchDisplay1();
        FiveOneSevenInchDisplay();
        FiveFourInchDisplay();
        FiveSevenInchDisplay();
        SixInchDisplay();
        SixSevenInchDisplay();
        SixOneInchDisplay();
        SixNineTwoInchDisplay();
        SixNineThreeInchDisplay();
        SevenInchDisplay();
        SevenEightInchDisplay();
        NineInchDisplay();
        NineInchDisplay1();
    }

    //unknown screen size
    public static void defaultheightwidth() {

        //DayPurchase book Report  height width you can set here
        purchaseHight1 = 55;
        purchaseHight2 = 55;
        purchaseHight3 = 55;
        purchaseWidth1 = 100;
        purchaseWidth2 = 100;
        purchaseWidth3 = 100;

        //DaySale book Report  height width you can set here
        SaleHight1 = 55;
        SaleHight2 = 55;
        SaleHight3 = 55;
        SaleWidth1 = 100;
        SaleWidth2 = 100;
        SaleWidth3 = 100;

        //DayStock book Report  height width you can set here
        StockHight1 = 55;
        StockHight2 = 55;
        StockHight3 = 55;
        StockWidth1 = 100;
        StockWidth2 = 100;
        StockWidth3 = 100;
    }

    public static void FourFiveInchDisplay() {
        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 25;
            rbr = 20;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 35;
            rbh = 35;

            //2nd Product Name width and height weight and color TextSize
            nw = 250;
            nh = 40;
            nc = "#00513f ";
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 120;
            qh = 40;
            qc = "#00513f ";
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 130;
            rh = 40;
            rc = "#00513f ";
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 140;
            ah = 40;
            ac = "#00513f ";
            ats = 15;

            //6th parent params weight
            pw = 60;

        } catch (Exception ex) {
        }
    }

    public static void FiveThreeInch() {
        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 40;
            rbt = 40;
            rbr = 40;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 55;
            rbh = 55;

            //2nd Product Name width and height weight and color TextSize
            nw = 670;
            nh = 100;
            nc = "#00513f ";
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 200;
            qh = 100;
            qc = "#00513f ";
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 200;
            rh = 100;
            rc = "#00513f ";
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 300;
            ah = 100;
            ac = "#00513f ";
            ats = 15;

            //6th parent params weight
            pw = 110;

        } catch (Exception ex) {
        }
    }

    public static void FourSevenInchDisplay() {

        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 25;
            rbr = 20;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 35;
            rbh = 35;

            //2nd Product Name width and height weight and color TextSize
            nw = 300;
            nh = 40;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 120;
            qh = 40;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 130;
            rh = 40;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 140;
            ah = 40;
            ats = 15;

            //6th parent params weight
            pw = 60;

        } catch (Exception ex) {
        }
    }

    public static void FiveInchDisplay() {
        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 25;
            rbh = 25;

            //2nd Product Name width and height weight and color TextSize
            nw = 170;
            nh = 30;
            nts = 12;

            //3rd Quantity width height and weight color TextSize
            qw = 75;
            qh = 30;
            qts = 12;

            //4th Rate width height weight and color TextSize
            rw = 80;
            rh = 30;
            rts = 12;

            //5th Amount width height weight and color TextSize
            aw = 85;
            ah = 30;
            ats = 12;

            //6th parent params weight
            pw = 50;

        } catch (Exception ex) {
        }

    }

    public static void FiveTwoFiveInchDisplay() {
        try {

            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 40;
            rbt = 40;
            rbr = 40;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 60;
            rbh = 60;

            //2nd Product Name width and height weight and color TextSize
            nw = 550;
            nh = 100;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 250;
            qh = 100;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 300;
            rh = 100;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 280;
            ah = 100;
            ats = 15;

            //6th parent params weight
            pw = 130;

        } catch (Exception ex) {
        }
    }

    public static void FiveOneInchDisplay() {
        try {

            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 25;
            rbt = 35;
            rbr = 25;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 40;
            rbh = 40;

            //2nd Product Name width and height weight and color TextSize
            nw = 430;
            nh = 60;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 170;
            qh = 60;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 180;
            rh = 60;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 200;
            ah = 60;
            ats = 15;

            //6th parent params weight
            pw = 80;

        } catch (Exception ex) {
        }

    }

    public static void FiveOneInchDisplay1() {
        try {

            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 35;
            rbt = 40;
            rbr = 35;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 60;
            rbh = 60;

            //2nd Product Name width and height weight and color TextSize
            nw = 560;
            nh = 90;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 220;
            qh = 90;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 240;
            rh = 90;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 270;
            ah = 90;
            ats = 15;

            //6th parent params weight
            pw = 100;

        } catch (Exception ex) {
        }

    }

    public static void FiveOneSevenInchDisplay() {
        try {

            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 15;
            rbr = 20;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 25;
            rbh = 25;

            //2nd Product Name width and height weight and color TextSize
            nw = 230;
            nh = 40;
            nts = 12;

            //3rd Quantity width height and weight color TextSize
            qw = 80;
            qh = 40;
            qts = 12;

            //4th Rate width height weight and color TextSize
            rw = 80;
            rh = 40;
            rts = 12;

            //5th Amount width height weight and color TextSize
            aw = 120;
            ah = 40;
            ats = 12;

            //6th parent params weight
            pw = 50;

        } catch (Exception ex) {
        }
    }

    public static void FiveFourInchDisplay() {
        try {

            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 25;
            rbt = 40;
            rbr = 25;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 55;
            rbh = 55;

            //2nd Product Name width and height weight and color TextSize
            nw = 500;
            nh = 90;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 150;
            qh = 90;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 150;
            rh = 90;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 200;
            ah = 90;
            ats = 15;

            //6th parent params weight
            pw = 100;

        } catch (Exception ex) {
        }

    }

    public static void FiveSevenInchDisplay() {
        try {

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 10;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 20;
            rbh = 20;

            //2nd Product Name width and height weight and color TextSize
            nw = 190;
            nh = 30;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 75;
            qh = 30;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 70;
            rh = 30;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 90;
            ah = 30;
            ats = 15;

            //6th parent params weight
            pw = 40;

        } catch (Exception ex) {
        }


    }

    public static void SixInchDisplay() {
        try {

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 30;
            rbh = 30;

            //2nd Product Name width and height weight and color TextSize
            nw = 200;
            nh = 30;
            nts = 18;

            //3rd Quantity width height and weight color TextSize
            qw = 75;
            qh = 30;
            qts = 18;

            //4th Rate width height weight and color TextSize
            rw = 70;
            rh = 30;
            rts = 18;

            //5th Amount width height weight and color TextSize
            aw = 90;
            ah = 30;
            ats = 18;

            //6th parent params weight
            pw = 50;

        } catch (Exception ex) {
        }

    }

    public static void SixSevenInchDisplay() {
        try {

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 25;
            rbh = 25;

            //2nd Product Name width and height weight and color TextSize
            nw = 180;
            nh = 30;
            nts = 18;

            //3rd Quantity width height and weight color TextSize
            qw = 70;
            qh = 30;
            qts = 18;

            //4th Rate width height weight and color TextSize
            rw = 80;
            rh = 30;
            rts = 18;

            //5th Amount width height weight and color TextSize
            aw = 90;
            ah = 30;
            ats = 18;

            //6th parent params weight
            pw = 50;

        } catch (Exception ex) {
        }

    }

    public static void SixOneInchDisplay() {
        try {

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 20;
            rbh = 20;

            //2nd Product Name width and height weight and color TextSize
            nw = 200;
            nh = 30;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 70;
            qh = 30;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 90;
            rh = 30;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 100;
            ah = 30;
            ats = 15;

            //6th parent params weight
            pw = 40;

        } catch (Exception ex) {
        }
    }

    public static void SixNineTwoInchDisplay() {
        try {
            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 30;
            rbt = 30;
            rbr = 30;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 40;
            rbh = 40;

            //2nd Product Name width and height weight and color TextSize
            nw = 550;
            nh = 60;
            nts = 15;

            //3rd Quantity width height and weight color TextSize
            qw = 150;
            qh = 60;
            qts = 15;

            //4th Rate width height weight and color TextSize
            rw = 100;
            rh = 60;
            rts = 15;

            //5th Amount width height weight and color TextSize
            aw = 250;
            ah = 60;
            ats = 15;

            //6th parent params weight
            pw = 70;

        } catch (Exception ex) {
        }

    }

    public static void SixNineThreeInchDisplay() {
        try {

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 30;
            rbh = 30;

            //2nd Product Name width and height weight and color TextSize
            nw = 360;
            nh = 30;
            nts = 18;

            //3rd Quantity width height and weight color TextSize
            qw = 100;
            qh = 30;
            qts = 18;

            //4th Rate width height weight and color TextSize
            rw = 80;
            rh = 30;
            rts = 18;

            //5th Amount width height weight and color TextSize
            aw = 160;
            ah = 30;
            ats = 18;

            //6th parent params weight
            pw = 50;

        } catch (Exception ex) {
        }

    }

    public static void SevenInchDisplay() {
        try {

            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 15;
            rbt = 10;
            rbr = 15;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 25;
            rbh = 25;

            //2nd Product Name width and height weight and color TextSize
            nw = 260;
            nh = 30;
            nc = "#00513f ";
            nts = 18;

            //3rd Quantity width height and weight color TextSize
            qw = 90;
            qh = 30;
            qc = "#00513f ";
            qts = 18;

            //4th Rate width height weight and color TextSize
            rw = 80;
            rh = 30;
            rc = "#00513f ";
            rts = 18;

            //5th Amount width height weight and color TextSize
            aw = 125;
            ah = 30;
            ac = "#00513f ";
            ats = 18;

            //6th parent params weight
            pw = 40;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void SevenEightInchDisplay() {
        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 30;
            rbt = 25;
            rbr = 30;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 45;
            rbh = 45;

            //2nd Product Name width and height weight and color TextSize
            nw = 550;
            nh = 60;
            nc = "#00513f ";
            nts = 18;

            //3rd Quantity width height and weight color TextSize
            qw = 180;
            qh = 60;
            qc = "#00513f ";
            qts = 18;

            //4th Rate width height weight and color TextSize
            rw = 140;
            rh = 60;
            rc = "#00513f ";
            rts = 18;

            //5th Amount width height weight and color TextSize
            aw = 240;
            ah = 60;
            ac = "#00513f ";
            ats = 18;

            //6th parent params weight
            pw = 70;

        } catch (Exception ex) {
        }
    }

    public static void NineInchDisplay() {
        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 35;
            rbt = 25;
            rbr = 35;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 40;
            rbh = 40;

            //2nd Product Name width and height weight and color TextSize
            nw = 700;
            nh = 60;
            nc = "#00513f ";
            nts = 18;

            //3rd Quantity width height and weight color TextSize
            qw = 230;
            qh = 60;
            qc = "#00513f ";
            qts = 18;

            //4th Rate width height weight and color TextSize
            rw = 200;
            rh = 60;
            rc = "#00513f ";
            rts = 18;

            //5th Amount width height weight and color TextSize
            aw = 280;
            ah = 60;
            ac = "#00513f ";
            ats = 18;

            //6th parent params weight
            pw = 70;

        } catch (Exception ex) {
        }

    }

    public static void NineInchDisplay1() {
        try {
            //Asign values to a bill item values

            //1st Remove button
            //Margin rb as remove button l and t and r and b of suffix are left,top,right,bottom
            rbl = 20;
            rbt = 10;
            rbr = 20;
            rbb = 0;

            //layout params width height and weight remove button
            rbw = 25;
            rbh = 25;

            //2nd Product Name width and height weight and color TextSize
            nw = 360;
            nh = 30;
            nc = "#00513f ";
            nts = 20;

            //3rd Quantity width height and weight color TextSize
            qw = 100;
            qh = 30;
            qc = "#00513f ";
            qts = 20;

            //4th Rate width height weight and color TextSize
            rw = 90;
            rh = 30;
            rc = "#00513f ";
            rts = 20;

            //5th Amount width height weight and color TextSize
            aw = 150;
            ah = 30;
            ac = "#00513f ";
            ats = 20;

            //6th parent params weight
            pw = 50;

        } catch (Exception ex) {
        }

    }

//    public static void UnknownInchDisplay(int LinearLayoutId,int GrandTotalId, List purchaseDetails, Activity activity) {
//        try {
//            LinearLayout tl = (LinearLayout) activity.findViewById(LinearLayoutId);
//            tl.removeAllViews();
//
//            float tot = 0;
//            for (int i = 0; i < purchaseDetails.size(); i++) {
//
//                tbPurchaseDetails sd = (tbPurchaseDetails) purchaseDetails.get(i);
//
//                LinearLayout pnlrmbtn = new LinearLayout(activity);
//                pnlrmbtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0));
//
//                Button btnRemove = new Button(activity);
//                pnlrmbtn.addView(btnRemove);
//                btnRemove.setId(i);
//                btnRemove.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//                btnRemove.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//                btnRemove.setTag(sd.ProductName);
//                btnRemove.setGravity(Gravity.CENTER_VERTICAL);
//                btnRemove.setBackgroundResource(R.drawable.ic_remove_circle_black_24dp);
//
//                btnRemove.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RemoveItem(v);
//
//                    }
//                });
//
//
//                LinearLayout pnlitemname = new LinearLayout(activity);
//                pnlitemname.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0));
//
//                TextView itemName = new TextView(activity);
//                pnlitemname.addView(itemName);
//                itemName.setId(i);
//                itemName.setTextColor(Color.parseColor("#00513f"));
//                itemName.setText(sd.ProductName);
//                itemName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                itemName.setTypeface(null, Typeface.BOLD);
//                itemName.setGravity(Gravity.LEFT);
//
//                LinearLayout pnlrate = new LinearLayout(activity);
//                pnlrate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0));
//
//                TextView rate = new TextView(activity);
//                pnlrate.addView(rate);
//                rate.setId(i);
//                rate.setTextColor(Color.parseColor("#00513f"));
//                rate.setText(sd.Rate.FValue);
//                rate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                rate.setTypeface(null, Typeface.BOLD);
//                rate.setGravity(Gravity.RIGHT);
//
//                LinearLayout pnlqty = new LinearLayout(activity);
//                pnlqty.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0));
//
//                TextView qty = new TextView(activity);
//                pnlqty.addView(qty);
//                qty.setId(i);
//                qty.setTextColor(Color.parseColor("#00513f"));
//                qty.setText(sd.Qty.FValue);
//                qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                qty.setTypeface(null, Typeface.BOLD);
//                qty.setGravity(Gravity.RIGHT);
//
//                LinearLayout pnlamt = new LinearLayout(activity);
//                pnlamt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0));
//
//                TextView amt = new TextView(activity);
//                pnlamt.addView(amt);
//                amt.setId(i);
//                amt.setTextColor(Color.parseColor("#00513f"));
//                amt.setText(sd.Amount.FValue);
//                amt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
//                amt.setTypeface(null, Typeface.BOLD);
//                amt.setGravity(Gravity.RIGHT);
//
//                tot = tot + Float.parseFloat(sd.Amount.FValue);
//
//                LinearLayout tr = new LinearLayout(activity);
//
//                tr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));
//                //tr.setWeightSum(10f);
//                tr.addView(pnlrmbtn);
//                tr.addView(pnlitemname);
//                tr.addView(pnlqty);
//                tr.addView(pnlrate);
//                tr.addView(pnlamt);
//                tl.addView(tr);
//            }
//            TextView txt = (TextView) activity.findViewById(GrandTotalId);
//            txt.setText(Float.toString(tot));
//            txt.setGravity(Gravity.RIGHT);
//        } catch (Exception ex) {
//        }
//    }
}
