package com.treinamento.victortripeno.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.treinamento.victortripeno.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

/**
 * Created by victor.tripeno on 02/12/2016.
 */
public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicoPort = Arrays.asList("sujeito", "obj direto", "obj indireto");
        Prova provaPortugues = new Prova("Prova português", "01/01/2017", topicoPort);

        List<String> topicoMat = Arrays.asList("Equações de segundo grau", "Trigonometria");
        Prova provaMatematica = new Prova("Prova matemática", "02/01/2017", topicoMat);


        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);

        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "clicou na prova de " + prova.getMateria(), Toast.LENGTH_LONG).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionarProva(prova);


            }
        });

        return view;
    }
}
