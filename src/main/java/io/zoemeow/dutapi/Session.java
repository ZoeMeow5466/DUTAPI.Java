package io.zoemeow.dutapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.zoemeow.dutapi.customhttprequests.HttpRequestParameters;
import io.zoemeow.dutapi.customhttprequests.HttpRequestString;
import io.zoemeow.dutapi.objects.SubjectFee;
import io.zoemeow.dutapi.objects.SubjectInfo;

public class Session {
    private final String __VIEWSTATE = "/wEPDwUKMTY2NjQ1OTEyNA8WAh4TVmFsaWRhdGVSZXF1ZXN0TW9kZQIBFgJmD2QWAgIFDxYCHglpbm5lcmh0bWwF/iw8dWwgaWQ" +
            "9J21lbnUnIHN0eWxlPSd3aWR0aDogMTAyNHB4OyBtYXJnaW46IDAgYXV0bzsgJz48bGk+PGEgSUQ9ICdsUGFIT01FJyBzdHlsZS" +
            "A9J3dpZHRoOjY1cHgnIGhyZWY9J0RlZmF1bHQuYXNweCc+VHJhbmcgY2jhu6c8L2E+PGxpPjxhIElEPSAnbFBhQ1REVCcgc3R5b" +
            "GUgPSd3aWR0aDo4NXB4JyBocmVmPScnPkNoxrDGoW5nIHRyw6xuaDwvYT48dWwgY2xhc3M9J3N1Ym1lbnUnPjxsaT48YSBJRCA9" +
            "J2xDb0NURFRDMicgICBzdHlsZSA9J3dpZHRoOjE0MHB4JyBocmVmPSdHX0xpc3RDVERULmFzcHgnPkNoxrDGoW5nIHRyw6xuaCD" +
            "EkcOgbyB04bqhbzwvYT48L2xpPjxsaT48YSBJRCA9J2xDb0NURFRDMScgICBzdHlsZSA9J3dpZHRoOjE0MHB4JyBocmVmPSdHX0" +
            "xpc3RIb2NQaGFuLmFzcHgnPkjhu41jIHBo4bqnbjwvYT48L2xpPjwvdWw+PC9saT48bGk+PGEgSUQ9ICdsUGFLSERUJyBzdHlsZ" +
            "SA9J3dpZHRoOjYwcHgnIGhyZWY9Jyc+S+G6vyBob+G6oWNoPC9hPjx1bCBjbGFzcz0nc3VibWVudSc+PGxpPjxhIElEID0nbENv" +
            "S0hEVEMxJyAgIHN0eWxlID0nd2lkdGg6MjAwcHgnIGhyZWY9J2h0dHBzOi8vZHJpdmUuZ29vZ2xlLmNvbS9maWxlL2QvMUUwNl9" +
            "mMmVDd3hiaHBEaUQ3Sm9QajJ0UEJ4SnlrdmZYL3ZpZXcnPkvhur8gaG/huqFjaCDEkcOgbyB04bqhbyBuxINtIGjhu41jPC9hPj" +
            "wvbGk+PGxpPjxhIElEID0nbENvS0hEVEMyJyAgIHN0eWxlID0nd2lkdGg6MjAwcHgnIGhyZWY9J2h0dHA6Ly9kazQuZHV0LnVkb" +
            "i52bic+xJDEg25nIGvDvSBo4buNYzwvYT48L2xpPjxsaT48YSBJRCA9J2xDb0tIRFRDMycgICBzdHlsZSA9J3dpZHRoOjIwMHB4" +
            "JyBocmVmPSdodHRwOi8vZGs0LmR1dC51ZG4udm4vR19Mb3BIb2NQaGFuLmFzcHgnPkzhu5twIGjhu41jIHBo4bqnbiAtIMSRYW5" +
            "nIMSRxINuZyBrw708L2E+PC9saT48bGk+PGEgSUQgPSdsQ29LSERUQzQnICAgc3R5bGUgPSd3aWR0aDoyMDBweCcgaHJlZj0nR1" +
            "9Mb3BIb2NQaGFuLmFzcHgnPkzhu5twIGjhu41jIHBo4bqnbiAtIGNow61uaCB0aOG7qWM8L2E+PC9saT48bGk+PGEgSUQgPSdsQ" +
            "29LSERUQzUnICAgc3R5bGUgPSd3aWR0aDoyMDBweCcgaHJlZj0naHR0cDovL2RrNC5kdXQudWRuLnZuL0dfREt5Tmh1Q2F1LmFz" +
            "cHgnPkto4bqjbyBzw6F0IG5odSBj4bqndSBt4bufIHRow6ptIGzhu5twPC9hPjwvbGk+PGxpPjxhIElEID0nbENvS0hEVEM2JyA" +
            "gIHN0eWxlID0nd2lkdGg6MjAwcHgnIGhyZWY9J2h0dHA6Ly9jYi5kdXQudWRuLnZuL1BhZ2VMaWNoVGhpS0guYXNweCc+VGhpIG" +
            "N14buRaSBr4buzIGzhu5twIGjhu41jIHBo4bqnbjwvYT48L2xpPjxsaT48YSBJRCA9J2xDb0tIRFRDNycgICBzdHlsZSA9J3dpZ" +
            "HRoOjIwMHB4JyBocmVmPSdHX0RLVGhpTk4uYXNweCc+VGhpIFRp4bq/bmcgQW5oIMSR4buLbmgga+G7sywgxJHhuqd1IHJhPC9h" +
            "PjwvbGk+PGxpPjxhIElEID0nbENvS0hEVEM4JyAgIHN0eWxlID0nd2lkdGg6MjAwcHgnIGhyZWY9J0dfTGlzdExpY2hTSC5hc3B" +
            "4Jz5TaW5oIGhv4bqhdCBs4bubcCDEkeG7i25oIGvhu7M8L2E+PC9saT48bGk+PGEgSUQgPSdsQ29LSERUQzknICAgc3R5bGUgPS" +
            "d3aWR0aDoyMDBweCcgaHJlZj0naHR0cDovL2ZiLmR1dC51ZG4udm4nPkto4bqjbyBzw6F0IMO9IGtp4bq/biBzaW5oIHZpw6puP" +
            "C9hPjwvbGk+PGxpPjxhIElEID0nbENvS0hEVEM5JyAgIHN0eWxlID0nd2lkdGg6MjAwcHgnIGhyZWY9J0dfREtQVkNELmFzcHgn" +
            "Pkhv4bqhdCDEkeG7mW5nIHBo4bulYyB24bulIGPhu5luZyDEkeG7k25nPC9hPjwvbGk+PC91bD48L2xpPjxsaT48YSBJRD0gJ2x" +
            "QYVRSQUNVVScgc3R5bGUgPSd3aWR0aDo3MHB4JyBocmVmPScnPkRhbmggc8OhY2g8L2E+PHVsIGNsYXNzPSdzdWJtZW51Jz48bG" +
            "k+PGEgSUQgPSdsQ29UUkFDVVUwMScgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdHX0xpc3ROZ3VuZ0hvYy5hc3B4Jz5Ta" +
            "W5oIHZpw6puIG5n4burbmcgaOG7jWM8L2E+PC9saT48bGk+PGEgSUQgPSdsQ29UUkFDVVUwMycgICBzdHlsZSA9J3dpZHRoOjI0" +
            "MHB4JyBocmVmPSdHX0xpc3RMb3AuYXNweCc+U2luaCB2acOqbiDEkWFuZyBo4buNYyAtIGzhu5twPC9hPjwvbGk+PGxpPjxhIEl" +
            "EID0nbENvVFJBQ1VVMDQnICAgc3R5bGUgPSd3aWR0aDoyNDBweCcgaHJlZj0nR19MaXN0Q0NDTlRULmFzcHgnPlNpbmggdmnDqm" +
            "4gY8OzIGNo4bupbmcgY2jhu4kgQ05UVDwvYT48L2xpPjxsaT48YSBJRCA9J2xDb1RSQUNVVTA1JyAgIHN0eWxlID0nd2lkdGg6M" +
            "jQwcHgnIGhyZWY9J0dfTGlzdENDTk4uYXNweCc+U2luaCB2acOqbiBjw7MgY2jhu6luZyBjaOG7iSBuZ2/huqFpIG5n4buvPC9h" +
            "PjwvbGk+PGxpPjxhIElEID0nbENvVFJBQ1VVMDYnICAgc3R5bGUgPSd3aWR0aDoyNDBweCcgaHJlZj0naHR0cDovL2Rhb3Rhby5" +
            "kdXQudWRuLnZuL1NWL0dfS1F1YUFuaFZhbi5hc3B4Jz5TaW5oIHZpw6puIHRoaSBUaeG6v25nIEFuaCDEkeG7i25oIGvhu7M8L2" +
            "E+PC9saT48bGk+PGEgSUQgPSdsQ29UUkFDVVUwNycgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdHX0xpc3REb0FuVE4uY" +
            "XNweCc+U2luaCB2acOqbiBsw6BtIMSQ4buTIMOhbiB04buRdCBuZ2hp4buHcDwvYT48L2xpPjxsaT48YSBJRCA9J2xDb1RSQUNV" +
            "VTA4JyAgIHN0eWxlID0nd2lkdGg6MjQwcHgnIGhyZWY9J0dfTGlzdEhvYW5Ib2NQaGkuYXNweCc+U2luaCB2acOqbiDEkcaw4bu" +
            "jYyBob8OjbiDEkcOzbmcgaOG7jWMgcGjDrTwvYT48L2xpPjxsaT48YSBJRCA9J2xDb1RSQUNVVTE2JyAgIHN0eWxlID0nd2lkdG" +
            "g6MjQwcHgnIGhyZWY9J0dfTGlzdEhvYW5fVGhpQlMuYXNweCc+U2luaCB2acOqbiDEkcaw4bujYyBob8OjbiB0aGksIHRoaSBi4" +
            "buVIHN1bmc8L2E+PC9saT48bGk+PGEgSUQgPSdsQ29UUkFDVVUwOScgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdHX0xp" +
            "c3RIb2NMYWkuYXNweCc+U2luaCB2acOqbiBk4buxIHR1eeG7g24gdsOgbyBo4buNYyBs4bqhaTwvYT48L2xpPjxsaT48YSBJRCA" +
            "9J2xDb1RSQUNVVTEwJyAgIHN0eWxlID0nd2lkdGg6MjQwcHgnIGhyZWY9J0dfTGlzdEt5THVhdC5hc3B4Jz5TaW5oIHZpw6puIG" +
            "Lhu4sga+G7tyBsdeG6rXQ8L2E+PC9saT48bGk+PGEgSUQgPSdsQ29UUkFDVVUxMScgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBoc" +
            "mVmPSdHX0xpc3RCaUh1eUhQLmFzcHgnPlNpbmggdmnDqm4gYuG7iyBo4buneSBo4buNYyBwaOG6p248L2E+PC9saT48bGk+PGEg" +
            "SUQgPSdsQ29UUkFDVVUxMicgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdHX0xpc3RMb2NrV2ViLmFzcHgnPlNpbmggdmn" +
            "Dqm4gYuG7iyBraMOzYSB3ZWJzaXRlPC9hPjwvbGk+PGxpPjxhIElEID0nbENvVFJBQ1VVMTMnICAgc3R5bGUgPSd3aWR0aDoyND" +
            "BweCcgaHJlZj0nR19MaXN0TG9ja1dlYlRhbS5hc3B4Jz5TaW5oIHZpw6puIGLhu4sgdOG6oW0ga2jDs2Egd2Vic2l0ZTwvYT48L" +
            "2xpPjxsaT48YSBJRCA9J2xDb1RSQUNVVTE0JyAgIHN0eWxlID0nd2lkdGg6MjQwcHgnIGhyZWY9J0dfTGlzdEhhbkNoZVRDLmFz" +
            "cHgnPlNpbmggdmnDqm4gYuG7iyBo4bqhbiBjaOG6vyB0w61uIGNo4buJIMSRxINuZyBrw708L2E+PC9saT48bGk+PGEgSUQgPSd" +
            "sQ29UUkFDVVUxNScgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdHX0xpc3RDYW5oQmFvS1FIVC5hc3B4Jz5TaW5oIHZpw6" +
            "puIGLhu4sgY+G6o25oIGLDoW8ga+G6v3QgcXXhuqMgaOG7jWMgdOG6rXA8L2E+PC9saT48L3VsPjwvbGk+PGxpPjxhIElEPSAnb" +
            "FBhQ1VVU1YnIHN0eWxlID0nd2lkdGg6ODhweCcgaHJlZj0nJz5D4buxdSBzaW5oIHZpw6puPC9hPjx1bCBjbGFzcz0nc3VibWVu" +
            "dSc+PGxpPjxhIElEID0nbENvQ1VVU1YxJyAgIHN0eWxlID0nd2lkdGg6MTEwcHgnIGhyZWY9J0dfTGlzdFROZ2hpZXAuYXNweCc" +
            "+xJDDoyB04buRdCBuZ2hp4buHcDwvYT48L2xpPjxsaT48YSBJRCA9J2xDb0NVVVNWMicgICBzdHlsZSA9J3dpZHRoOjExMHB4Jy" +
            "BocmVmPSdHX0xpc3RLaG9uZ1ROLmFzcHgnPktow7RuZyB04buRdCBuZ2hp4buHcDwvYT48L2xpPjwvdWw+PC9saT48bGk+PGEgS" +
            "UQ9ICdsUGFDU1ZDJyBzdHlsZSA9J3dpZHRoOjE0NXB4JyBocmVmPScnPlBow7JuZyBo4buNYyAmIEjhu4cgdGjhu5FuZzwvYT48" +
            "dWwgY2xhc3M9J3N1Ym1lbnUnPjxsaT48YSBJRCA9J2xDb0NTVkMwMScgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdodHR" +
            "wOi8vY2IuZHV0LnVkbi52bi9QYWdlQ05QaG9uZ0hvYy5hc3B4Jz5Uw6xuaCBow6xuaCBz4butIGThu6VuZyBwaMOybmcgaOG7jW" +
            "M8L2E+PC9saT48bGk+PGEgSUQgPSdsQ29DU1ZDMDInICAgc3R5bGUgPSd3aWR0aDoyNDBweCcgaHJlZj0nR19MaXN0VGhCaUhvb" +
            "mcuYXNweCc+VGjhu5FuZyBrw6ogYsOhbyB0aGnhur90IGLhu4sgcGjDsm5nIGjhu41jIGjhu49uZzwvYT48L2xpPjxsaT48YSBJ" +
            "RCA9J2xDb0NTVkMwOScgICBzdHlsZSA9J3dpZHRoOjI0MHB4JyBocmVmPSdHX1N5c1N0YXR1cy5hc3B4Jz5UcuG6oW5nIHRow6F" +
            "pIGjhu4cgdGjhu5FuZyB0aMO0bmcgdGluIHNpbmggdmnDqm48L2E+PC9saT48L3VsPjwvbGk+PGxpPjxhIElEPSAnbFBhTElFTk" +
            "tFVCcgc3R5bGUgPSd3aWR0aDo1MHB4JyBocmVmPScnPkxpw6puIGvhur90PC9hPjx1bCBjbGFzcz0nc3VibWVudSc+PGxpPjxhI" +
            "ElEID0nbENvTElFTktFVDEnICAgc3R5bGUgPSd3aWR0aDo3MHB4JyBocmVmPSdodHRwOi8vbGliLmR1dC51ZG4udm4nPlRoxrAg" +
            "dmnhu4duPC9hPjwvbGk+PGxpPjxhIElEID0nbENvTElFTktFVDInICAgc3R5bGUgPSd3aWR0aDo3MHB4JyBocmVmPSdodHRwOi8" +
            "vbG1zMS5kdXQudWRuLnZuJz5EVVQtTE1TPC9hPjwvbGk+PC91bD48L2xpPjxsaT48YSBJRD0gJ2xQYUhFTFAnIHN0eWxlID0nd2" +
            "lkdGg6NDVweCcgaHJlZj0nJz5I4buXIHRy4bujPC9hPjx1bCBjbGFzcz0nc3VibWVudSc+PGxpPjxhIElEID0nbENvSEVMUDEnI" +
            "CAgc3R5bGUgPSd3aWR0aDoyMTBweCcgaHJlZj0naHR0cDovL2ZyLmR1dC51ZG4udm4nPkPhu5VuZyBo4buXIHRy4bujIHRow7Ru" +
            "ZyB0aW4gdHLhu7FjIHR1eeG6v248L2E+PC9saT48bGk+PGEgSUQgPSdsQ29IRUxQMicgICBzdHlsZSA9J3dpZHRoOjIxMHB4JyB" +
            "ocmVmPSdodHRwczovL2RyaXZlLmdvb2dsZS5jb20vZmlsZS9kLzFaMHFsYmhLYVNHbXpFWkpEMnVCNGVVV2VlSGFROUhIbC92aW" +
            "V3Jz5IxrDhu5tuZyBk4bqrbiDEkMSDbmcga8O9IGjhu41jPC9hPjwvbGk+PGxpPjxhIElEID0nbENvSEVMUDMnICAgc3R5bGUgP" +
            "Sd3aWR0aDoyMTBweCcgaHJlZj0naHR0cDovL2Rhb3Rhby5kdXQudWRuLnZuL2Rvd25sb2FkMi9FbWFpbF9HdWlkZS5wZGYnPkjG" +
            "sOG7m25nIGThuqtuIFPhu60gZOG7pW5nIEVtYWlsIERVVDwvYT48L2xpPjxsaT48YSBJRCA9J2xDb0hFTFA3JyAgIHN0eWxlID0" +
            "nd2lkdGg6MjEwcHgnIGhyZWY9J2h0dHA6Ly9kdXQudWRuLnZuL1RyYW5nc2luaHZpZW4nPlbEg24gYuG6o24gUXV5IMSR4buLbm" +
            "ggY+G7p2EgVHLGsOG7nW5nPC9hPjwvbGk+PGxpPjxhIElEID0nbENvSEVMUDgnICAgc3R5bGUgPSd3aWR0aDoyMTBweCcgaHJlZ" +
            "j0naHR0cHM6Ly90aW55dXJsLmNvbS95NGtkajNzcCc+Qmnhu4N1IG3huqt1IHRoxrDhu51uZyBkw7luZzwvYT48L2xpPjwvdWw+" +
            "PC9saT48bGk+PGEgaWQgPSdsaW5rRGFuZ05oYXAnIGhyZWY9J1BhZ2VEYW5nTmhhcC5hc3B4JyBzdHlsZSA9J3dpZHRoOjgwcHg" +
            "7Jz4gxJDEg25nIG5o4bqtcCA8L2E+PC9saT48bGk+PGRpdiBjbGFzcz0nTG9naW5GcmFtZSc+PGRpdiBzdHlsZSA9J21pbi13aW" +
            "R0aDogMTAwcHg7Jz48L2Rpdj48L2Rpdj48L2xpPjwvdWw+ZGSG2IsLearNCsLzIS4IUfWLr31R7FXOMNnIboe38N+JJg==";
    private final String __VIEWSTATEGENERATOR = "20CC0D2F";
    private final String QLTH_btnLogin = "Đăng+nhập";

    private CookieManager cookieManager = null;

    public Session() {
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    // ------------------------------------------------------------------------
    // Login information (Username, password,...)
    // ------------------------------------------------------------------------

    private String user = null;
    private String pass = null;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    // ------------------------------------------------------------------------
    // Cookies
    // ------------------------------------------------------------------------

    private List<String> cookies;
    private void setCookies(List<String> cookies) {
        if (cookies != null) {
            this.cookies = cookies;
        }
    }
    private List<String> getCookies() {
        return cookies;
    }

    // ------------------------------------------------------------------------
    // Query
    // ------------------------------------------------------------------------

    // https://stackoverflow.com/questions/26701116/cookies-not-set-with-httpurlconnection

    public Boolean login() {
        HttpURLConnection client = null;
        Boolean result = false;

        try {
            URL url = new URL("http://sv.dut.udn.vn/PageDangNhap.aspx");

            HttpRequestString httpRequestString = new HttpRequestString();
            httpRequestString.addRequest(new HttpRequestParameters("__VIEWSTATE", __VIEWSTATE));
            httpRequestString.addRequest(new HttpRequestParameters("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR));
            httpRequestString.addRequest(new HttpRequestParameters("_ctl0:MainContent:DN_txtAcc", user));
            httpRequestString.addRequest(new HttpRequestParameters("_ctl0:MainContent:DN_txtPass", pass));
            httpRequestString.addRequest(new HttpRequestParameters("_ctl0:MainContent:QLTH_btnLogin", QLTH_btnLogin));

            byte[] requestBytes = httpRequestString.toURLEncodeByteArray("UTF-8");

            client = (HttpURLConnection) url.openConnection();
            client.setInstanceFollowRedirects(false);
            client.setDoOutput(true);
            client.setUseCaches(false);
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            client.setRequestProperty("Charset", "UTF-8");
            client.setRequestProperty("Content-Length", Integer.toString(requestBytes.length));

            // Set cookie
            List<String> cookieTemp = getCookies();
            if (cookieTemp != null) {
                for (String cookie : cookieTemp) {
                    client.addRequestProperty("Set-Cookie", cookie.split(";", 1)[0]);
                }
            }

            // Send data to server
            try (DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream())) {
                dataOutputStream.write(requestBytes);
            }

            // Set cookie and disconnect
            setCookies(client.getHeaderFields().get("Set-Cookie"));
            client.disconnect();

            result = isLoggedIn();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

    public Boolean logout() {
        HttpURLConnection client = null;
        Boolean result = false;

        try {
            URL url = new URL("http://sv.dut.udn.vn/PageLogout.aspx");
            client = (HttpURLConnection) url.openConnection();

            // Set cookie
            List<String> cookieTemp = getCookies();
            if (cookieTemp != null) {
                for (String cookie : cookieTemp) {
                    client.addRequestProperty("Set-Cookie", cookie.split(";", 1)[0]);
                }
            }

            client.connect();

            // Set cookie and disconnect
            setCookies(client.getHeaderFields().get("Set-Cookie"));
            client.disconnect();

            result = !isLoggedIn();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

    public Boolean isLoggedIn() {
        HttpURLConnection client = null;
        Boolean result = false;

        try {
            URL url = new URL("http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=TTKBLoad&Code=2110");
            client = (HttpURLConnection) url.openConnection();

            // Set cookie
            List<String> cookieTemp = getCookies();
            if (cookieTemp != null) {
                for (String cookie : cookieTemp) {
                    client.addRequestProperty("Set-Cookie", cookie.split(";", 1)[0]);
                }
            }

            client.connect();
            if (client.getResponseCode() == 200 || client.getResponseCode() == 204)
                result = true;

            // Set cookie and disconnect
            setCookies(client.getHeaderFields().get("Set-Cookie"));
            client.disconnect();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }

    // semester: 1: hk1, 2: hk2, 3: hk he
    public ArrayList<SubjectInfo> getSubjectsSchedule(Integer year, Integer semester) {
        HttpURLConnection client = null;
        ArrayList<SubjectInfo> result = null;

        try {
            result = new ArrayList<SubjectInfo>();
            String url = "http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=TTKBLoad&Code=%d%d%d";

            switch (semester) {
                case 1:
                case 2:
                    url = String.format(url, year, semester, 0);
                    break;
                case 3:
                    url = String.format(url, year, 2, 1);
                    break;
                default:
                    // TODO: Set exception name here!
                    throw new Exception();
            }

            URL url2 = new URL(url);
            client = (HttpURLConnection) url2.openConnection();

            // Set cookie
            List<String> cookieTemp = getCookies();
            if (cookieTemp != null) {
                for (String cookie : cookieTemp) {
                    client.addRequestProperty("Set-Cookie", cookie.split(";", 1)[0]);
                }
            }
            client.connect();

            // TODO: Set exception name here!
            if (client.getResponseCode() != 200 && client.getResponseCode() != 204)
                throw new Exception("hello =))");

            // https://stackoverflow.com/questions/8616781/how-to-get-a-web-pages-source-code-from-java
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8")))
            {
                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(inputLine);
                }
            }

            // Set cookie and disconnect
            setCookies(client.getHeaderFields().get("Set-Cookie"));
            client.disconnect();

            // Processing here
            Document webData = Jsoup.parse(stringBuilder.toString());

            // Schedule study
            Element schStudy = webData.getElementById("TTKB_GridInfo");
            Elements schStudyList = schStudy.getElementsByClass("GridRow");
            if (schStudyList != null && schStudyList.size() > 0) {
                for (Element row: schStudyList) {
                    Elements cellList = row.getElementsByClass("GridCell");

                    if (cellList.size() < 10)
                        continue;

                    SubjectInfo si = new SubjectInfo();
                    si.setId(cellList.get(1).text());
                    si.setName(cellList.get(2).text());
                    try {
                        si.setCredit(Float.parseFloat(cellList.get(3).text()));
                    } catch (Exception ex) {
                        si.setCredit(0F);
                    }
                    si.setIsHighQuality(cellList.get(5).attr("class").contains("GridCheck"));
                    si.setLecturer(cellList.get(6).text());
                    si.setScheduleStudy(cellList.get(7).text());
                    si.setWeeks(cellList.get(8).text());
                    si.setPointFomula(cellList.get(10).text());

                    result.add(si);
                }
            }

            // Schedule examination
            Element schExam = webData.getElementById("TTKB_GridLT");
            Elements schExamList = schExam.getElementsByClass("GridRow");
            if (schExamList != null && schExamList.size() > 0) {
                for (Element row: schExamList) {
                    Elements cellList = row.getElementsByClass("GridCell");

                    if (cellList.size() < 5)
                        continue;

                    SubjectInfo si = result.stream()
                        .filter(s -> s.getId() == cellList.get(1).text())
                        .findFirst()
                        .orElse(null);
                    
                    if (si != null) {
                        si.setGroupExam(cellList.get(3).text());
                        si.setIsGlobalExam(Boolean.parseBoolean(cellList.get(4).text()));
                        si.setDateExamInString(cellList.get(5).text());

                        if (si.getDateExamInString() != null) {
                            String[] splitted = si.getDateExamInString().split(", " );
                            String time = null;
                            for (Integer i = 0; i < splitted.length; i++) {
                                switch (splitted[i].split(", ")[1]) {
                                    case "Phòng":
                                        si.setRoomExam(splitted[i].split(": ")[1]);
                                        break;
                                    case "Ngày":
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        si.setDateExam(LocalDateTime.parse(splitted[i].split(": ")[1], formatter));
                                        break;
                                    case "Giờ":
                                        time = splitted[i].split(": ")[1];
                                        break;
                                    default:
                                        break;
                                }
                            }

                            if (si.getDateExam() != null && time != null) {
                                si.setDateExam(si.getDateExam().plusHours(Integer.parseInt(time.split("h")[0])));
                                if (time.split("h").length == 2) {
                                    Integer mInteger = 0;
                                    try {
                                        mInteger = Integer.parseInt(time.split("h")[1]);
                                    } catch (Exception ex) {
                                        mInteger = 0;
                                    }
                                    si.setDateExam(si.getDateExam().plusMinutes(mInteger));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            if (result != null) {
                result.clear();
                result = null;
            }
        }

        return result;
    }

    public ArrayList<SubjectFee> getSubjectsFee(Integer year, Integer semester) {
        ArrayList<SubjectFee> result = null;
        HttpURLConnection client = null;

        try {
            result = new ArrayList<SubjectFee>();

            String url = "http://sv.dut.udn.vn/WebAjax/evLopHP_Load.aspx?E=THPhiLoad&Code=%d%d%d";

            switch (semester) {
                case 1:
                case 2:
                    url = String.format(url, year, semester, 0);
                    break;
                case 3:
                    url = String.format(url, year, 2, 1);
                    break;
                default:
                    // TODO: Set exception name here!
                    throw new Exception();
            }

            URL url2 = new URL(url);
            client = (HttpURLConnection) url2.openConnection();

            // Set cookie
            List<String> cookieTemp = getCookies();
            if (cookieTemp != null) {
                for (String cookie : cookieTemp) {
                    client.addRequestProperty("Set-Cookie", cookie.split(";", 1)[0]);
                }
            }
            client.connect();

            // TODO: Set exception name here!
            if (client.getResponseCode() != 200 && client.getResponseCode() != 204)
                throw new Exception("hello =))");

            // https://stackoverflow.com/questions/8616781/how-to-get-a-web-pages-source-code-from-java
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8")))
            {
                while ((inputLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(inputLine);
                }
            }

            // Set cookie and disconnect
            setCookies(client.getHeaderFields().get("Set-Cookie"));
            client.disconnect();

            // Processing here
            Document webData = Jsoup.parse(stringBuilder.toString());

            Element schFee = webData.getElementById("THocPhi_GridInfo");
            Elements schFeeList = schFee.getElementsByClass("GridRow");
            if (schFeeList != null && schFeeList.size() > 0) {
                for (Element row: schFeeList) {
                    Elements cellList = row.getElementsByClass("GridCell");

                    if (cellList.size() < 10)
                        continue;

                    SubjectFee sf = new SubjectFee();
                    sf.setId(cellList.get(1).text());
                    sf.setName(cellList.get(2).text());
                    try {
                        sf.setCredit(Float.parseFloat(cellList.get(3).text()));
                    } catch (Exception ex) {
                        sf.setCredit(0F);
                    }
                    sf.setIsQuality(cellList.get(4).attr("class").contains("GridCheck"));
                    try {
                        sf.setPrice(Double.parseDouble(cellList.get(5).text().replace(",", "")));
                    } catch (Exception ex) {
                        sf.setPrice(0.0);
                    }
                    sf.setDebt(cellList.get(6).attr("class").contains("GridCheck"));
                    sf.setIsReStudy(cellList.get(7).attr("class").contains("GridCheck"));
                    sf.setVerifiedPaymentAt(cellList.get(8).text());
                    result.add(sf);
                }
            }
        }
        catch (Exception ex) {
            if (result != null) {
                result.clear();
                result = null;
            }
        }

        return result;
    }

}
