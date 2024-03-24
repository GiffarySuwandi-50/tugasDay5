package com.example.quizpb1;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class OutputActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_output);

        //Material Activity
        TextView tvPembeli = findViewById(R.id.tvPembeli);
        TextView tvTransaksi = findViewById(R.id.tvTransaksi);
        TextView tvMembership = findViewById(R.id.tvMembershipTerpilih);
        Button Bagikan = findViewById(R.id.btnBagikan);

        //Intent
        Intent intent = getIntent();
        String namaPembeli = intent.getStringExtra("namaPembeli");
        String kodeBarang = intent.getStringExtra("kodeBarang");
        int jumlahBarang = intent.getIntExtra("jumlahBarang", 0);
        String membershipTerpilih = intent.getStringExtra("membershipTerpilih");

        //Perhitungan/kalkulasi harga total
        double hargaSatuan = 0;
        String namaBarang = null;
        int totalHarga;
        double totalHargaSetelahDiskon;
        switch (Objects.requireNonNull(kodeBarang)) {
            case "AV4":
                hargaSatuan = 9150999;
                namaBarang = "Asus Vivobook 14";
                break;
            case "LV3":
                hargaSatuan = 6666666;
                namaBarang = "Lenovo V14 Gen 3";
                break;
            case "MP3":
                hargaSatuan = 28999999;
                namaBarang = "Macbook Pro M3";
                break;
        }

        totalHarga = (int) (hargaSatuan * jumlahBarang);

        int diskonDiatas10Juta = 0;
        if (totalHarga > 10000000){
            diskonDiatas10Juta = 100000;
        } else if (totalHarga < 10000000) {
            diskonDiatas10Juta = 0;
        }

        int diskonMembership = 0;
        if (membershipTerpilih != null) {
            switch (membershipTerpilih) {
                case "Gold":
                    diskonMembership = (int) (0.1 * totalHarga);
                    break;
                case "Silver":
                    diskonMembership = (int) (0.05 * totalHarga);
                    break;
                case "Biasa":
                    diskonMembership = (int) (0.02 * totalHarga);
                    break;
                default:
                    break;
            }
        }
        totalHargaSetelahDiskon = totalHarga - diskonMembership - diskonDiatas10Juta;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        String hargaSatuanFormatted = formatter.format(hargaSatuan);
        String totalHargaFormatted = formatter.format(totalHarga);
        String diskonDiatas10JutaFormatted = formatter.format(diskonDiatas10Juta);
        String diskonMembershipFormatted = formatter.format(diskonMembership);
        String totalHargaSetelahDiskonFormatted = formatter.format(totalHargaSetelahDiskon);

        tvPembeli.setText(getString(R.string.greetings_customer) + namaPembeli);
        tvMembership.setText(getString(R.string.tipe_member_result) + membershipTerpilih);
        tvTransaksi.setText(
                getString(R.string.today_transaction) + "\n" +
                        getString(R.string.item_code_transaction) + kodeBarang  + "\n" +
                        getString(R.string.item_name_transaction) + namaBarang  + "\n" +
                        getString(R.string.price_transaction) + hargaSatuanFormatted + "\n" +
                        getString(R.string.price_total_transaction) + totalHargaFormatted + "\n" +
                        getString(R.string.price_discount_transaction) + diskonDiatas10JutaFormatted + "\n" +
                        getString(R.string.member_discount_transaction) + diskonMembershipFormatted + "\n" +
                        getString(R.string.total_payment_transaction) + totalHargaSetelahDiskonFormatted);

        //Bagikan! (Intent Implisit)
        String finalNamaBarang = namaBarang;
        Bagikan.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Nama Barang : "+ finalNamaBarang +
                    "\nMelakukan transaksi sebesar: " + totalHargaSetelahDiskonFormatted +
                    " pada aplikasi Giffary Arfalino Suwandi Quiz 1");
            startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
        });
    }
}
