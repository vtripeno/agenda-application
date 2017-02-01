package com.treinamento.victortripeno.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.treinamento.victortripeno.agenda.converter.AlunoConverter;
import com.treinamento.victortripeno.agenda.dao.AlunoDAO;
import com.treinamento.victortripeno.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by victor.tripeno on 01/12/2016.
 *
 * Parametros AsyncTask
 * primeiro - parametros do doInBackground (se não for utilizar parametros, passar Void)
 * meio - atualizações no meio da sua tarefa (quase não se usa) (passar Void caso não tenha intenção de alterar algo no meio da tarefa)
 * terceiro - altera o tipo dos parametros de saida do doInBackground e entrada do onPostExecute
 */
public class EnviarAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog dialog;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    /**
     * executado antes de doInBackground
     */
    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde...", "Enviando Alunos...", true, true);

    }

    @Override
    protected String doInBackground(Void... objects) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);
        //Toast.makeText(context, "Requisição para o servidor: " + resposta, Toast.LENGTH_SHORT).show();

        return resposta;
    }

    /**
     * método executado após doInBackground (é executado na thread primária do Android)
     * @param resposta -> resposta que vem de doInBackground
     */
    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, "Requisição para o servidor: " + resposta, Toast.LENGTH_SHORT).show();
    }
}
