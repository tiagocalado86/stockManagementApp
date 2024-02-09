package com.example.stockmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.stockmanagementapp.DB.StockDataSource;

public class productEditActivity extends AppCompatActivity {

    //variavel para o nome do produto
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        //declaracoes iniciais
        Button save = findViewById(R.id.save);
        Button delete = findViewById(R.id.delete);
        EditText nomeEdit = findViewById(R.id.nomeEdit);
        EditText categoriaEdit = findViewById(R.id.categoriaEdit);
        EditText precoEdit = findViewById(R.id.precoEdit);
        EditText quantEdit = findViewById(R.id.quantEdit);

        //armazena os detalhes do produto vindos da MainActivity
        Intent intent = getIntent();
        if (intent.hasExtra("productDetails")) {
            String productDetails = intent.getStringExtra("productDetails");

            //os detalhes são separados por "\n"
            String[] detailsArray = productDetails.split("\n");

            //verifica se o array tem pelo menos 4 elementos (para evitar ArrayIndexOutOfBoundsException)
            if (detailsArray.length >= 4) {
                //armazenamos o nome do produto
                productName = detailsArray[0].substring(6);

                //remove o texto pre escrito devido a ser guardado (l65 - StockDataSource) com precedentes na lista
                nomeEdit.setText(productName);
                categoriaEdit.setText(detailsArray[1].substring(11));
                quantEdit.setText(detailsArray[2].substring(12));
                precoEdit.setText(detailsArray[3].substring(7));
            }
        }

        //botao de salvar
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //armazena os novos valores dados nos EditTexts
                String newName = nomeEdit.getText().toString();
                String newType = categoriaEdit.getText().toString();
                int newQuantity = Integer.parseInt(quantEdit.getText().toString());
                double newPrice = Double.parseDouble(precoEdit.getText().toString());

                //da update ao produto na DB com o metodo do StockDataSource
                StockDataSource dataSource = new StockDataSource(productEditActivity.this);
                dataSource.open();
                dataSource.updateProduct(productName, newName, newType, newQuantity, newPrice);
                dataSource.close();

                //volta para a MainActivity
                Intent intent = new Intent(productEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //botao de delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //apaga o produto da DB com o metodo do StockDataSource
                StockDataSource dataSource = new StockDataSource(productEditActivity.this);
                dataSource.open();
                dataSource.deleteProduct(productName);
                dataSource.close();

                //volta para a MainActivity
                Intent intent = new Intent(productEditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
