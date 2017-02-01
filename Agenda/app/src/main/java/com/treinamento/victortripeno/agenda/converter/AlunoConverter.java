package com.treinamento.victortripeno.agenda.converter;

import com.treinamento.victortripeno.agenda.modelo.Aluno;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by victor.tripeno on 01/12/2016.
 */
public class AlunoConverter {

    public String converteParaJSON(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            // object = {
            // key = nome do nó
            js.object().key("List").array()
                    .object().key("aluno").array();

            for(Aluno aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }

}
