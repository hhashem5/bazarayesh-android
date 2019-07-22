package com.idpz.bazarayesh;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class DownloadApk extends AsyncTask<String, Integer, Void> {

    File filep;
    public static OnDownload downloadListener;
    public static OnDownload downloadCompleteListener;

    Context mContext;
File file;

    public DownloadApk(Context mContext, File file) {
        this.mContext = mContext;

        this.file=file;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... url) {


        // -> maven.pdf
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();


//        File folder = new File(extStorageDirectory, "HambaziSho");
//        folder.mkdir();
//        filep = new File(folder, "VIDEO_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".pdf");
        try {

           // filep.createNewFile();

            URL url2 = new URL(url[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[50000];
            long downloaded = 0;
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                downloaded += bufferLength;

                fileOutputStream.write(buffer, 0, bufferLength);
//                publishProgress(getIntProgress(downloaded, totalSize));

                downloadListener.downloadPr(String.valueOf(getIntProgress(downloaded, totalSize)));
            }

            if (getIntProgress(downloaded, totalSize) == 100)
                downloadCompleteListener.OnDownloadComplete(file.getName());

            fileOutputStream.close();
        } catch (IOException e) {
            Log.d("mehrdad", "doInBackground: " + e.getMessage());
        }


        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    protected int getIntProgress(long downloaded, long length) {
        float percent = (float) downloaded / (float) length;
        return (int) (percent * 100);
    }

    public static void downloadListener(OnDownload listener) {
        downloadCompleteListener = listener;
        downloadListener = listener;
    }

}
