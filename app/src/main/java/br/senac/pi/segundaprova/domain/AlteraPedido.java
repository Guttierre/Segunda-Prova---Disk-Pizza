package br.senac.pi.segundaprova.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.senac.pi.segundaprova.R;

public class AlteraPedido extends AppCompatActivity {

    private PizzaDB pizzaDB;
    private SQLiteDatabase db;
    private EditText edtAlteraSabor, edtAlteraTamanho, edtAlteraQuantidade;
    private TextView txtIdSabor;
    private String id;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_pedido);

        id = getIntent().getStringExtra("id");
        pizzaDB = new PizzaDB(this);
        txtIdSabor = (TextView) findViewById(R.id.txtIdSabor);
        edtAlteraSabor = (EditText) findViewById(R.id.edtAlteraSabor);
        edtAlteraTamanho = (EditText) findViewById(R.id.edtAlteraTamanho);
        edtAlteraQuantidade = (EditText) findViewById(R.id.edtAlteraQuantidade);
        cursor = carregaPedido(Integer.parseInt(id));
        txtIdSabor.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        edtAlteraSabor.setText(cursor.getString(cursor.getColumnIndexOrThrow("sabor")));
        edtAlteraTamanho.setText(cursor.getString(cursor.getColumnIndexOrThrow("tamanho")));
        edtAlteraQuantidade.setText(cursor.getString(cursor.getColumnIndexOrThrow("quantidade")));
        findViewById(R.id.btnAlteraPedido).setOnClickListener(alterarPedido());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Cursor carregaPedido(int id){
        db = pizzaDB.getWritableDatabase();
        String[] campos = {"_id","sabor","tamanho","quantidade"};
        String whereArgs = String.valueOf(id);
        cursor = db.query("pizza", campos, whereArgs, null, null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    private View.OnClickListener alterarPedido(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                db = pizzaDB.getWritableDatabase();
                ContentValues values = new ContentValues();
                String whereArgs = id;
                Log.i("curso", "ID capturado:" + id);
                values.put("sabor", edtAlteraSabor.getText().toString());
                values.put("tamanho", edtAlteraTamanho.getText().toString());
                values.put("quantidade", edtAlteraQuantidade.getText().toString());
                db.update("pizza", values, "_id = "+ whereArgs, null);
                db.close();
            }
        };
    }

}
