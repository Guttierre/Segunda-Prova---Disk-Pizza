package br.senac.pi.segundaprova.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guttierre on 20/11/2015.
 */
public class PizzaDB extends SQLiteOpenHelper {

    private static final String TAG = "sql";
    //NOME DO BANCO DE DADOS
    private static final String NOME_BANCO = "cursoandroid.sqlite";
    private static final int VERSAO_BANCO = 1;

    public PizzaDB(Context context){
        //context, nome do banco, factory, versão
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.d(TAG, "Criando a tabela pizza...");
        db.execSQL("CREATE TABLE IF NOT EXISTS pizza(_id integer primary key autoincrement, sabor text, tamanho text, quantidade int);");
        Log.d(TAG, "Tabela pizza criada com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Caso mude a versão do banco de dados sql de atualização deve ser executado aqui.

    }


    //FUNÇÃO SALVAR PEDIDO NO BANCO
    public long save(Pizza pizza){
        long id = pizza.getId();
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("nome",pizza.getSabor());
            values.put("marca",pizza.getTamanho());
            values.put("marca",pizza.getQuantidade());
            if (id != 0){
                String _id = String.valueOf(pizza.getId());
                String[] whereargs = new String[]{_id};

                int count = db.update("pizza",values, "_id=?", whereargs);
                return count;
            }else{
                //INSERT INTO PIZZA VALUES
                id = db.insert("pizza","",values);
                return id;
            }
        }finally {
            db.close();
        }
    }

    //FUNÇÃO DELETAR PEDIDO
    public int delete(Pizza pizza){
        SQLiteDatabase db = getWritableDatabase();
        try{
            //DELETE FROM PIZZAR WHERE ID = (número do id)
            int count = db.delete("pizza","_id=?",new String[]{String.valueOf(pizza.getId())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        }finally {
            db.close();
        }
    }

    //CONSULTAR LISTA COM TODOS OS PEDIDOS
    public List<Pizza> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            //SELECT * FROM pizza
            Cursor cursor = db.query("pizza", null, null, null, null, null, null, null);
            return toList(cursor);
        }finally {
            db.close();
        }
    }

    //CRIAR LISTA
    public List<Pizza> toList(Cursor cursor){
        List<Pizza> pizza = new ArrayList<Pizza>();
        if (cursor.moveToFirst()){
            do {
                Pizza pizzas = new Pizza();
                pizza.add(pizzas);
                //recupera os atributos do pedido
                pizzas.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                pizzas.setSabor(cursor.getString(cursor.getColumnIndex("sabor")));
                pizzas.setTamanho(cursor.getString(cursor.getColumnIndex("tamanho")));
                pizzas.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));

            }while (cursor.moveToNext());
        }
        return pizza;
    }

}
