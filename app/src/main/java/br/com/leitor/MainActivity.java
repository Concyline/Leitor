package br.com.leitor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        if (PermissionUtils.hasCameraPermission(this)) {
           takePicture();
        } else {
            // Pede a permissão
            PermissionUtils.requestCameraPermission(this);
        }


    }

    private void takePicture() {
        Intent intent = new Intent(getBaseContext(), LeitorActivity.class);
        String codigo = "C=7898958119652;L=50962;V=30/09/2019";
        intent.putExtra(LeitorActivity.CODE_TEST,codigo);
        startActivityForResult(intent, 123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
        }
    }




}