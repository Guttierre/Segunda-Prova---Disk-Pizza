package br.senac.pi.segundaprova;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.segundaprova.domain.AlteraPedido;
import br.senac.pi.segundaprova.domain.Pizza;
import br.senac.pi.segundaprova.domain.PizzaDB;

public class ListarPedidos extends AppCompatActivity {

    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private static final String campos[] = {"sabor","tamanho","quantidade","_id"};
    ListView listView;
    PizzaDB pizzaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos);

        listView = (ListView) findViewById(R.id.listView);
        pizzaDB = new PizzaDB(this);
        database = pizzaDB.getWritableDatabase();

        //PEGA ID PARA SETAR O LISTENER DE LISTAGEM DOS PEDIDOS
        findViewById(R.id.btnListar).setOnClickListener(listar());

        //CHAMA LISTENER DE DELETE
        listView.setOnItemClickListener(deletarItem());

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

    private View.OnClickListener listar(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Cursor pizzas = database.query("pizza", campos, null, null, null,null,null);
                if (pizzas.getCount() > 0){
                    dataSource = new SimpleCursorAdapter(ListarPedidos.this,R.layout.row, pizzas, campos,new int[] {R.id.txtSabor, R.id.txtQuantidade, R.id.txtTamanho});
                    listView.setAdapter(dataSource);
                }else{
                    Toast.makeText(ListarPedidos.this, getString(R.string.zero_registros), Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    //RECUPERAR ITEM DO BANCO E DELETAR
    private AdapterView.OnItemClickListener deletarItem(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long itemSelecinado = id;
                final int posicao = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListarPedidos.this);
                builder.setTitle("Pergunta");
                builder.setMessage("Escolha uma das opções?");
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String codigo;
                        MainActivity c = new MainActivity();
                        Cursor pizza = database.query("pizza",campos,null,null,null,null,null);
                        pizza.moveToPosition(posicao);
                        codigo = pizza.getString(pizza.getColumnIndexOrThrow("_id"));
                        Intent intent = new Intent(getApplicationContext(),AlteraPedido.class);
                        intent.putExtra("id",codigo);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("pizza", "ID do item Selecionado: " + itemSelecinado);
                        Pizza pizza = new Pizza();
                        pizza.setId(itemSelecinado);
                        pizzaDB.delete(pizza);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

}
