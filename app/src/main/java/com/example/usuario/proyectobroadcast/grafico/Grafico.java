package com.example.usuario.proyectobroadcast.grafico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.proyectobroadcast.R;

public class Grafico extends AppCompatActivity {

    private WebView webView;
    private Intent i;
    private int[] a;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.webView = (WebView) findViewById(R.id.webView);
        tv=(TextView) findViewById(R.id.textView2);
        i=getIntent();
        a=i.getExtras().getIntArray("aux");
        int cont=0;

        for(int x:a) if(x==0) cont++;

        if(cont==7){
            tv.setText(getString(R.string.vacio));
            webView.setVisibility(View.GONE);
        }else tv.setVisibility(View.GONE);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("file:///android_asset/canvas/pruebagraficos.html");

        webView.addJavascriptInterface(this, "InterfazAndroid");
    }

    @JavascriptInterface
    public int enviarDia(int x){
        return a[x];
    }

}
