package com.enda.cash.remboursement.service;


import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import javax.security.cert.X509Certificate;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class smsSender {

    private JSONObject jsonInformation = new JSONObject();
    String httpLogin = "enda";
    String httpPwd = "45Ju4t6v";
    String wbLogin = "ENDA01";
    String wbPwd = "tygrm7Am";
    String wbAccount = "ENDA";
    String destNum = ""; //list of destination mobile numbers separated by ;
    String indicateur = "216";
    int type = 0;// 0 : latin ; 2 : arabic

    String hour;//sending hour ; format HH
    String minute;//sending minute ; format MM


    Date actuelle = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String date = dateFormat.format(actuelle);


    String label = "ENDATAMWEEL"; //source label
    String reference = "SI"; //sending reference
    String application = "T24";// sending application
    String body = "";

    public void SendingSMSDMD(String numTelephone, String msg) {
        // TODO Auto-generated method stub
        @SuppressWarnings("unused")
        Integer n = null;


        try {
            this.ssl();

            //	MobileNvlDemande nouvelleDemande = new MobileNvlDemande();
            //cin =nouvelleDemande.getCin();
//		  Random rand =new Random();
//		  n=rand.nextInt(999999) + 999999;

            destNum = indicateur.toString() + numTelephone;

            System.out.println("destNum " + destNum);
            Calendar now = Calendar.getInstance();

            System.out.println("Current Hour in 24 hour format is : " + now.get(Calendar.HOUR_OF_DAY));
            System.out.println("Current Hour in 24 hour format is : " + now.get(Calendar.MINUTE));
            System.out.println("testtttt");

            String urlParameters = "login=" + this.wbLogin +
                    "&pass=" + this.wbPwd +
                    "&compte=" + this.wbAccount +
                    "&op=1" +
                    "&dest_num=" + this.destNum +
                    "&msg=" + msg +
                    "&type=" + this.type +
                    "&dt=" + this.date +
                    "&hr=00" +
                    "&mn=00" +
                    "&label=" + this.label +
                    "&ref=" + this.reference;
            System.out.println("urlParameters " + urlParameters);
            String urlsms = "https://wbm.tn/wbmonitor/send/webapi/send_ack.php";
            URL obj = new URL(urlsms);
            HttpsURLConnection con5 = (HttpsURLConnection) obj.openConnection();
            this.setHeader(con5, urlParameters.length());

            // Send post request
            con5.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con5.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = con5.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + urlsms);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con5.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
//            try {
//                jsonInformation.put("Status",  "0");
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
        }
        //return  jsonInformation.toString();
    }

    private void ssl() throws Exception {

        /************************************************Avoid SSL***************************************/

        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @SuppressWarnings("unused")
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @SuppressWarnings("unused")
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                    throws CertificateException {
                // TODO Auto-generated method stub

            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        /***************************************************************************************/


    }

    private void setHeader(HttpsURLConnection con, int i) throws Exception {

        String stat = "ZW5kYTo0NUp1NHQ2dg==";


        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", this.application);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", "" + i);
        con.setRequestProperty("Authorization", "Basic " + stat);
    }


}
