package com.example.stockmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stockmanagementapp.DB.StockDataSource;

public class productAddActivity extends AppCompatActivity {

    private StockDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);

        //inicia a DB
        dataSource = new StockDataSource(this);
        dataSource.open();

        //declaracoes iniciais
        EditText nome = findViewById(R.id.nome);
        EditText categoria = findViewById(R.id.categoria);
        EditText preco = findViewById(R.id.preco);
        EditText quant = findViewById(R.id.quant);
        Button add = findViewById(R.id.add);
        Button voltar = findViewById(R.id.voltar);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(productAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //guarda os valores dos EditTexts
                String productName = nome.getText().toString();
                String productCategory = categoria.getText().toString();
                double productPrice = Double.parseDouble(preco.getText().toString());
                int productQuantity = Integer.parseInt(quant.getText().toString());

                //armazena os valores na DB
                long result = dataSource.insertProduct(productName, productCategory, productQuantity, productPrice);

                if (result != -1) {
                    //se tiver sucesso
                    Toast.makeText(productAddActivity.this, "Produto adicionado com sucesso", Toast.LENGTH_SHORT).show();

                    //volta para a MainActivity
                    Intent intent = new Intent(productAddActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    //caso nao tenha sucesso
                    Toast.makeText(productAddActivity.this, "Falha ao adicionar produto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        //fecha a DB ao destruir a activity
        dataSource.close();
        super.onDestroy();
    }
}
