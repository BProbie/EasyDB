package com.probie.Database.Local;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import com.probie.Database.Local.Interface.ILocalDBRemote;

public class LocalDBRemote implements ILocalDBRemote, X509TrustManager {

    @Override
    public Boolean downloadRemoteFile(Object remoteFile, Object localFile) {
        try {
            return ILocalDBRemote.super.downloadRemoteFile(remoteFile, localFile);
        } catch (Exception exception) {
            connectProxy();
            return ILocalDBRemote.super.downloadRemoteFile(remoteFile, localFile);
        }
    }

    @Override
    public void connectProxy() {
        TrustManager[] trustManagers = new TrustManager[] {new LocalDBRemote()};
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
        try {
            sslContext.init(null,trustManagers,null);
        } catch (KeyManagementException keyManagementException) {
            throw new RuntimeException(keyManagementException);
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier((urlHostName,session) -> true);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}