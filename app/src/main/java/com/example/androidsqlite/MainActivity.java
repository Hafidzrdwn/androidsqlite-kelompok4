package com.example.androidsqlite;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.*;
import java.util.ArrayList;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;


public class MainActivity extends AppCompatActivity {

    DatabaseManager dm;
    private EditText eNm, eTelp, ekode;
    private Button bBaru, bSimpan, bUbah, bHapus;
    TableLayout tabelData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DatabaseManager(this);
        tabelData = (TableLayout) findViewById(R.id.table_data);
        ekode = (EditText) findViewById(R.id.ed_kode);
        eNm = (EditText) findViewById(R.id.ed_name);
        eTelp = (EditText) findViewById(R.id.ed_notelp);
        bSimpan = (Button) findViewById(R.id.btn_simpan);
        bUbah = (Button) findViewById(R.id.btn_ubah);
        bHapus = (Button) findViewById(R.id.btn_hapus);
        bBaru = (Button) findViewById(R.id.btn_baru);

        bSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanTable();
            }
        });

        bUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubahTable();
            }
        });

        bHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusTable();
            }
        });

        bBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kosongkanField();
            }
        });

        updateTable();
    }

    protected void simpanTable() {
        try {
            if (ekode.getText().toString().isEmpty() ||
                    eNm.getText().toString().isEmpty() ||
                    eTelp.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            dm.addRow(Integer.parseInt(ekode.getText().toString()), eNm.getText().toString(), eTelp.getText().toString());
            Toast.makeText(getBaseContext(),
                    eNm.getText().toString() + ", Berhasil disimpan",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Gagal simpan, " +
                    e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void ubahTable() {
        try {
            if (ekode.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "Kode pelanggan harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            dm.UpdateRecord(Integer.parseInt(ekode.getText().toString()), eNm.getText().toString(), eTelp.getText().toString());
            Toast.makeText(getBaseContext(),
                    eNm.getText().toString() + ", berhasil diubah",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "gagal ubah, " +
                    e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void hapusTable() {
        try {
            if (ekode.getText().toString().isEmpty()) {
                Toast.makeText(getBaseContext(), "Kode pelanggan harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            dm.DeleteRecord(Integer.parseInt(ekode.getText().toString()));
            Toast.makeText(getBaseContext(),
                    "Kode " + ekode.getText().toString() + ", Data Berhasil Dihapus",
                    Toast.LENGTH_SHORT).show();
            updateTable();
            kosongkanField();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "gagal hapus, " +
                    e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void kosongkanField() {
        eNm.setText("");
        eTelp.setText("");
        ekode.setText("");
    }

    protected void updateTable() {
        // Keep the header row (index 0) and remove all other rows
        while (tabelData.getChildCount() > 1) {
            tabelData.removeViewAt(1);
        }

        ArrayList<ArrayList<Object>> data = dm.ambilSemuaBaris();

        // If no data, show a message
        if (data.size() == 0) {
            TableRow emptyRow = new TableRow(this);
            TextView emptyText = new TextView(this);
            emptyText.setText("Tidak ada data");
            emptyText.setTextSize(16);
            emptyText.setTextColor(Color.BLACK);
            emptyText.setPadding(10, 20, 10, 20);
            emptyText.setGravity(Gravity.CENTER);

            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.span = 3; // Span across all columns
            params.width = TableRow.LayoutParams.MATCH_PARENT;
            emptyText.setLayoutParams(params);

            emptyRow.addView(emptyText);
            tabelData.addView(emptyRow);
            return;
        }

        // Add data rows
        for (int posisi = 0; posisi < data.size(); posisi++) {
            TableRow tabelBaris = new TableRow(this);
            tabelBaris.setPadding(5, 10, 5, 10);

            // Set alternating row background
            if (posisi % 2 == 0) {
                tabelBaris.setBackgroundColor(Color.parseColor("#E0F7FA"));
            } else {
                tabelBaris.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

            ArrayList<Object> baris = data.get(posisi);

            // ID column
            TextView idTxt = new TextView(this);
            idTxt.setTextSize(14);
            idTxt.setPadding(10, 5, 10, 5);
            idTxt.setGravity(Gravity.CENTER);
            idTxt.setText(baris.get(0).toString());
            idTxt.setTextColor(Color.BLACK);

            // Name column
            TextView namaTxt = new TextView(this);
            namaTxt.setTextSize(14);
            namaTxt.setPadding(10, 5, 10, 5);
            namaTxt.setGravity(Gravity.CENTER);
            namaTxt.setText(baris.get(1).toString());
            namaTxt.setTextColor(Color.BLACK);

            // Phone column
            TextView TelpTxt = new TextView(this);
            TelpTxt.setTextSize(14);
            TelpTxt.setPadding(10, 5, 10, 5);
            TelpTxt.setGravity(Gravity.CENTER);
            TelpTxt.setText(baris.get(2).toString());
            TelpTxt.setTextColor(Color.BLACK);

            // Set layout weights for each column
            TableRow.LayoutParams idParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            TableRow.LayoutParams namaParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f);
            TableRow.LayoutParams telpParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f);

            idTxt.setLayoutParams(idParams);
            namaTxt.setLayoutParams(namaParams);
            TelpTxt.setLayoutParams(telpParams);

            tabelBaris.addView(idTxt);
            tabelBaris.addView(namaTxt);
            tabelBaris.addView(TelpTxt);

            // Make rows clickable to edit
            final int rowPosition = posisi;
            tabelBaris.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Object> selectedRow = data.get(rowPosition);
                    ekode.setText(selectedRow.get(0).toString());
                    eNm.setText(selectedRow.get(1).toString());
                    eTelp.setText(selectedRow.get(2).toString());
                }
            });

            tabelData.addView(tabelBaris);
        }
    }
}

