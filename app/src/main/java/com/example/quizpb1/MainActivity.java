package com.example.quizpb1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;

//Widget
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//TIDAK ADA PLAGIARISME KARYA MAHASISWA-MAHASISWI LAINNYA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Input
        EditText namaPembeliEditText = findViewById(R.id.etNama);
        EditText kodeBarangEditText = findViewById(R.id.etKodeBarang);
        EditText jumlahBarangEditText = findViewById(R.id.etJumlahBarang);
        RadioGroup membershipRadioGroup = findViewById(R.id.rgMembership);
        Button prosesButton = findViewById(R.id.btnProses);

        prosesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Masukkan Nama Pembeli
                String namaPembeli = namaPembeliEditText.getText().toString();
                //Masukkan Kode Barang
                String kodeBarang = kodeBarangEditText.getText().toString();
                //Masukkan Jumlah Barang
                String jumlahBarangStr = jumlahBarangEditText.getText().toString();
                int jumlahBarang = 0;
                if (!jumlahBarangStr.isEmpty()) { jumlahBarang = Integer.parseInt(jumlahBarangStr); }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Jumlah barang kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Radio Group diskon Membership
                int membershipTerpilihId = membershipRadioGroup.getCheckedRadioButtonId();
                RadioButton membershipTerpilihRadioButton = findViewById(membershipTerpilihId);
                String membershipTerpilih = membershipTerpilihRadioButton.getText().toString();

                //Intent ke OutputActivity
                Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                intent.putExtra("namaPembeli", namaPembeli);
                intent.putExtra("kodeBarang", kodeBarang);
                intent.putExtra("jumlahBarang", jumlahBarang);
                intent.putExtra("membershipTerpilih", membershipTerpilih);
                startActivity(intent);
            }
        });
    }
}