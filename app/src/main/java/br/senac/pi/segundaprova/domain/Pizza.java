package br.senac.pi.segundaprova.domain;

/**
 * Created by Guttierre on 20/11/2015.
 */

//ATRIBUTOS
public class Pizza {
    public long id;
    private String sabor,tamanho;
    int quantidade;


    //CONSTRUTOR DA CLASSE
    public Pizza(long id, String sabor, String tamanho, int quantidade){
        this.id = id;
        this.sabor = sabor;
        this.tamanho = tamanho;
        this.quantidade = quantidade;
    }

    public Pizza() {

    }

    //METÓDOS GETS E SETS
    public String getSabor() {
        return sabor;
    }

    public void setSabor (String sabor) {
        this.sabor = sabor;
    }

    public String getTamanho(){
        return tamanho;
    }

    public void setTamanho(String tamanho){
        this.tamanho = tamanho;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public void setQuantidade(int quantidade){
        this.quantidade = quantidade;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

}
