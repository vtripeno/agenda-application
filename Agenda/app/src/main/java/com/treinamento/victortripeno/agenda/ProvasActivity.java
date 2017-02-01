package com.treinamento.victortripeno.agenda;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.treinamento.victortripeno.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal, new ListaProvasFragment()); // trocou o frame pelo ListaProvasFragment,
                                                                    // pois a tag FrameLayout apenas guarda lugar para
                                                                    // alguém que precisa utilizar o espaço

        if (estaNoModoPaisagem()) { // verificar se estou em modo paisagem

            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionarProva(Prova prova) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        if(!estaNoModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle parametros = new Bundle(); // bundle serve para se popular e enviar como parametro de um fragment ou outras coisas
            parametros.putSerializable("prova", prova); // funciona de forma parecida com putExtra da intent

            detalhesProvaFragment.setArguments(parametros); // passar argumentos para o fragment, como se fosse um intent

            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            tx.addToBackStack(null); // faz rollback na pilha de transações (corrigir botão voltar dentro do fragment)
                                    // parametro serve para voltar para um ponto específico na pilha
            tx.commit();
        } else {
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);

            detalhesFragment.populaCamposCom(prova);
            
        }

    }
}

