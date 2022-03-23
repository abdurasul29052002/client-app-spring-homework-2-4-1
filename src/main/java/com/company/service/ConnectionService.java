package com.company.service;

import com.company.entity.User;
import com.sun.jndi.toolkit.url.UrlUtil;
import lombok.SneakyThrows;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionService {

    @SneakyThrows
    public String postConnectionWithoutToken(URL url, String json) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-type","application/json");
        OutputStream outputStream = httpURLConnection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(json);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        outputStream.close();
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode()==200){
            return httpURLConnection.getHeaderField("Authorization");
        }
        if (httpURLConnection.getResponseCode()==201){
            JOptionPane.showMessageDialog(new JFrame(),"Registration successfully");
        }
        return null;
    }

    @SneakyThrows
    public InputStream postConnectionWithToken(String stringUrl,String json,String token) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(stringUrl).openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization",token);
        httpURLConnection.setRequestProperty("Content-type","application/json");
        return getInputStream(json, httpURLConnection);
    }

    @SneakyThrows
    public InputStream getConnectionWithToken(String stringUrl,String token){
        URL url = new URL(stringUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization",token);
        httpURLConnection.setRequestProperty("Content-type","application/json");
        httpURLConnection.connect();
        return httpURLConnection.getInputStream();
    }

    @SneakyThrows
    public InputStream putConnectionWithToken(URL url,String json, String token) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization",token);
        httpURLConnection.setRequestProperty("Content-type","application/json");
        return getInputStream(json, httpURLConnection);
    }

    @SneakyThrows
    public InputStream deleteConnectionWithToken(URL url, String token) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("DELETE");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization",token);
        httpURLConnection.setRequestProperty("Content-type","application/json");
        return getInputStream("",httpURLConnection);
    }

    private InputStream getInputStream(String json, HttpURLConnection httpURLConnection) throws IOException {
        OutputStream outputStream = httpURLConnection.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        outputStreamWriter.write(json);
        outputStreamWriter.flush();
        outputStreamWriter.close();
        outputStream.close();
        httpURLConnection.connect();
        return httpURLConnection.getInputStream();
    }
}
