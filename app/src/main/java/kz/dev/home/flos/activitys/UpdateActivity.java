package kz.dev.home.flos.activitys;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kz.dev.home.flos.MainActivity;
import kz.dev.home.flos.R;
import kz.dev.home.flos.helper.PInfo;
import kz.dev.home.flos.services.URLs;


public class UpdateActivity extends AppCompatActivity {
    public int VersionCode;
    public String VersionName="";
    public String ApkName ;
    public String AppName ;
    public String BuildVersionPath="";
    public String urlpath ;
    public String PackageName;
    public String Text="";
    TextView tvApkStatus;
    Button btnCheckUpdates;
    TextView tvInstallVersion;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_fast_rewind_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });





        //Text= "Old".toString();
        Text= "New";

        ApkName = "flost.apk";//"Test1.apk";// //"DownLoadOnSDcard_01.apk"; //
        AppName = (String) this.getApplicationInfo().loadLabel(this.getPackageManager());//"Test1"; //

        BuildVersionPath = URLs.URL_GET_VERSION;
        PackageName = getApplicationContext().getPackageName(); //"package:com.Test1".toString();
        urlpath = URLs.URL_GET_UPDATE + ApkName;

        tvApkStatus = findViewById(R.id.tvApkStatus);
        tvApkStatus.setText(Text+ " Apk Download.");


        tvInstallVersion = findViewById(R.id.tvInstallVersion);
        String temp = getInstallPackageVersionInfo(AppName);
        tvInstallVersion.setText("" + temp);

        btnCheckUpdates = findViewById(R.id.btnCheckUpdates);
        btnCheckUpdates.setOnClickListener(arg0 -> {
            GetVersionFromServer(BuildVersionPath);

            if(checkInstalledApp(AppName))
            {
                Toast.makeText(getApplicationContext(), "Application Found " + AppName, Toast.LENGTH_SHORT).show();


            }else{
                Toast.makeText(getApplicationContext(), "Application Not Found. "+ AppName, Toast.LENGTH_SHORT).show();
            }
        });

    }// On Create END.

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {// app icon in action bar clicked; go home
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private Boolean checkInstalledApp(String appName){
        return getPackages(appName);
    }

    // Get Information about Only Specific application which is Install on Device.
    public String getInstallPackageVersionInfo(String appName)
    {
        String InstallVersion = "";
        ArrayList<PInfo> apps = getInstalledApps(); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++)
        {
            //apps.get(i).prettyPrint();
            if(apps.get(i).appname.equals(appName))
            {
                InstallVersion = "Install Version Code: "+ apps.get(i).versionCode+
                        " Version Name: "+ apps.get(i).versionName;
                break;
            }
        }

        return InstallVersion;
    }
    private Boolean getPackages(String appName)
    {
        boolean isInstalled = false;
        ArrayList<PInfo> apps = getInstalledApps(); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++)
        {
            //apps.get(i).prettyPrint();

            if(apps.get(i).appname.equals(appName))
            {
                /*if(apps.get(i).versionName.toString().contains(VersionName.toString()) == true &&
                        VersionCode == apps.get(i).versionCode)
                {
                    isInstalled = true;
                    Toast.makeText(getApplicationContext(),
                            "Code Match", Toast.LENGTH_SHORT).show();
                    openMyDialog();
                }*/
                if(VersionCode <= apps.get(i).versionCode)
                {
                    isInstalled = true;

                    /*Toast.makeText(getApplicationContext(),
                            "Install Code is Less.!", Toast.LENGTH_SHORT).show();*/

                    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                //SelfInstall01Activity.this.finish(); Close The App.

                                DownloadOnSDcard();
                                InstallApplication();
                                UnInstallApplication(PackageName);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                break;
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("New Apk Available..").setPositiveButton("Yes Proceed", dialogClickListener)
                            .setNegativeButton("No.", dialogClickListener).show();

                }
                if(VersionCode > apps.get(i).versionCode)
                {
                    isInstalled = true;
                    /*Toast.makeText(getApplicationContext(),
                            "Install Code is better.!", Toast.LENGTH_SHORT).show();*/

                    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                        switch (which)
                        {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                //SelfInstall01Activity.this.finish(); Close The App.

                                DownloadOnSDcard();
                                InstallApplication();
                                UnInstallApplication(PackageName);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked

                                break;
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("NO need to Install.").setPositiveButton("Install Forcely", dialogClickListener)
                            .setNegativeButton("Cancel.", dialogClickListener).show();
                }
            }
        }

        return isInstalled;
    }
    private ArrayList<PInfo> getInstalledApps()
    {
        ArrayList<PInfo> res = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);

        for(int i=0;i<packs.size();i++)
        {
            PackageInfo p = packs.get(i);
            if ((p.versionName == null)) {
                continue ;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            //newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
            res.add(newInfo);
        }
        return res;
    }


    public void UnInstallApplication(String packageName)// Specific package Name Uninstall.
    {
        //Uri packageURI = Uri.parse("package:com.CheckInstallApp");
        Uri packageURI = Uri.parse(packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
    }
    public void InstallApplication()
    {
        Uri packageURI = Uri.parse(PackageName);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, packageURI);

//      Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(Intent.ACTION_PACKAGE_REPLACED);

        //intent.setAction(Settings. ACTION_APPLICATION_SETTINGS);

        intent.setDataAndType
                (Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/"  + ApkName)),
                        "application/vnd.android.package-archive");

        // Not open this Below Line Because...
        ////intent.setClass(this, Project02Activity.class); // This Line Call Activity Recursively its dangerous.

        startActivity(intent);
    }
    public void GetVersionFromServer(String BuildVersionPath)
    {
        //this is the file you want to download from the remote server
        //path ="http://10.0.2.2:82/version";
        //this is the name of the local file you will create
        // version.txt contain Version Code = 2; \n Version name = 2.1;
        URL u;
        try {
            u = new URL(BuildVersionPath);

            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            //Toast.makeText(getApplicationContext(), "HttpURLConnection Complete.!", Toast.LENGTH_SHORT).show();

            InputStream in = c.getInputStream();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024]; //that stops the reading after 1024 chars..
            //in.read(buffer); //  Read from Buffer.
            //baos.write(buffer); // Write Into Buffer.

            int len1;
            while ( (len1 = in.read(buffer)) != -1 )
            {
                baos.write(buffer,0, len1); // Write Into ByteArrayOutputStream Buffer.
            }

            StringBuilder temp = new StringBuilder();
            String s = baos.toString();// baos.toString(); contain Version Code = 2; \n Version name = 2.1;

            for (int i = 0; i < s.length(); i++)
            {
                i = s.indexOf("=") + 1;
                while (s.charAt(i) == ' ') // Skip Spaces
                {
                    i++; // Move to Next.
                }
                while (s.charAt(i) != ';'&& (s.charAt(i) >= '0' && s.charAt(i) <= '9' || s.charAt(i) == '.'))
                {
                    temp = new StringBuilder(temp.toString().concat(Character.toString(s.charAt(i))));
                    i++;
                }
                //
                s = s.substring(i); // Move to Next to Process.!
                temp.append(" "); // Separate w.r.t Space Version Code and Version Name.
            }
            String[] fields = temp.toString().split(" ");// Make Array for Version Code and Version Name.

            VersionCode = Integer.parseInt(fields[0]);// .ToString() Return String Value.
            VersionName = fields[1];

            baos.close();
        }
        catch (MalformedURLException e) {
            Toast.makeText(getApplicationContext(), "Error." + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error." + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //return true;
    }// Method End.

    // Download On My Mobile SDCard or Emulator.
    public void DownloadOnSDcard()
    {
        try{
            URL url = new URL(urlpath); // Your given URL.

            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect(); // Connection Complete here.!

            //Toast.makeText(getApplicationContext(), "HttpURLConnection complete.", Toast.LENGTH_SHORT).show();

            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            File file = new File(PATH); // PATH = /mnt/sdcard/download/
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
            }
            File outputFile = new File(file, ApkName);
            FileOutputStream fos = new FileOutputStream(outputFile);

            //      Toast.makeText(getApplicationContext(), "SD Card Path: " + outputFile.toString(), Toast.LENGTH_SHORT).show();

            InputStream is = c.getInputStream(); // Get from Server and Catch In Input Stream Object.

            byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1); // Write In FileOutputStream.
            }
            fos.close();
            is.close();//till here, it works fine - .apk is download to my sdcard in download file.
            // So please Check in DDMS tab and Select your Emulator.

            //Toast.makeText(getApplicationContext(), "Download Complete on SD Card.!", Toast.LENGTH_SHORT).show();
            //download the APK to sdcard then fire the Intent.
        }
        catch (IOException e)
        {
            Toast.makeText(getApplicationContext(), "Error! " +
                    e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
