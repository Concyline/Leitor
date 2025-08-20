package br.com.leitor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import br.com.leitor.codescanner.CodeScanner;
import br.com.leitor.codescanner.CodeScannerView;
import br.com.leitor.codescanner.DecodeCallback;

public class LeitorActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    public static String CODE_TEST = "CODE_TEST";
    private String teste = "";

    private boolean hasPermision = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitor_ui);

        try {
            setBar("Leitor");

            teste = getIntent().getStringExtra(CODE_TEST) == null ? "" : getIntent().getStringExtra(CODE_TEST);

            hasPermision = testaPermisao();

            if (hasPermision) {
                initScan();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(hasPermision) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {

        if(hasPermision) {
            mCodeScanner.releaseResources();
        }

        super.onPause();
    }

    private void initScan() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.putExtra("CODIGO", result.getText());
                        setResult(0, intent);
                        finish();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_leitor_ui, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return itemSelected(item, 1, 0);
    }

    public boolean itemSelected(MenuItem item, int tipoMenu, int position) {

        switch (item.getItemId()) {
            case 16908332: { // HOME
                if (!teste.equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra("CODIGO", teste);
                    setResult(0, intent);
                }
                finish();
                break;
            }

        }
        return true;
    }

    public void setBar(String title) {
        setBar(title, "");
    }

    public void setBar(String title, String subtitle) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setTitle(Html.fromHtml("<small>" + title + "</small>"));
        if (subtitle != null) {
            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    public boolean testaPermisao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.e("ERRO", "A aplicação precisa da permissão da câmera no AndroidManifest.xml\n\n <uses-permission android:name=\"android.permission.CAMERA\" />");
            return false;
        } else {
            return true;
        }
    }
}
