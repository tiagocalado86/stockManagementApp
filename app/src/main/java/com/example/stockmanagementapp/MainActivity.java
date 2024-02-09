package com.example.stockmanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockmanagementapp.DB.StockDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaracoes iniciais
        FloatingActionButton addProductRED = findViewById(R.id.addProductRED);
        ListView listProducts = findViewById(R.id.listProducts);

        //preenche a ListView com os produtos
        StockDataSource dataSource = new StockDataSource(this);
        dataSource.open();
        ArrayList<String> productDetails = dataSource.getAllProductsDetails();
        dataSource.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productDetails);
        listProducts.setAdapter(adapter);

        //click num item da ListView leva a productEditActivity
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //obtem o nome do produto selecionado
                String selectedProductDetails = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, productEditActivity.class);

                //adiciona detalhes do produto ao Intent para os seus dados poderem ser "importados"
                intent.putExtra("productDetails", selectedProductDetails);

                startActivity(intent);
            }
        });

        //click no botao de "+" leva a productAddActivity
        addProductRED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, productAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
