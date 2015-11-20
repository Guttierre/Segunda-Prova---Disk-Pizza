package br.senac.pi.segundaprova;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.senac.pi.segundaprova.domain.PizzaDB;

public class MainActivity extends AppCompatActivity {


    PizzaDB pizzaDB;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        pizzaDB = new PizzaDB(this);

        findViewById(R.id.btnInserirPedido).setOnClickListener(inserirPedido());
        findViewById(R.id.btnListarPedido).setOnClickListener(listarPedidos());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    private View.OnClickListener inserirPedido(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = pizzaDB.getWritableDatabase();
                EditText edtSabor = (EditText) findViewById(R.id.edtSabor);
                EditText edtTamanho = (EditText) findViewById(R.id.edtTamanho);
                EditText edtQuantidade = (EditText) findViewById(R.id.edtQuantidade);
                String Sabor = edtSabor.getText().toString();
                String Tamanho = edtTamanho.getText().toString();
                String Quantidade = edtQuantidade.getText().toString();
                ContentValues values = new ContentValues();
                values.put("sabor", Sabor);
                values.put("tamanho", Tamanho);
                values.put("quantidade", Quantidade);
                long id = database.insert("pizza" +
                        "", null, values);
                if(id != 0){
                    Toast.makeText(getApplicationContext(), getString(R.string.sucesso), Toast.LENGTH_LONG).show();
                    edtSabor.setText("");
                    edtTamanho.setText("");
                    edtQuantidade.setText("");
                    edtSabor.requestFocus();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.error),Toast.LENGTH_LONG).show();
                }
            }
        };
    }


    private View.OnClickListener listarPedidos(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarPedidos.class);
                startActivity(intent);
            }
        };
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pizza, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
