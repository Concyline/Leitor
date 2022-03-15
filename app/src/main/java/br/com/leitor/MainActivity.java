package br.com.leitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
        String codigo = "C=7898958119652;L=50962;V=30/09/2019";
        intent.putExtra(LeitorActivity.CODE_TEST,codigo);
        startActivityForResult(intent, 123);
    }
}