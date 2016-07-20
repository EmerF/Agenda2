package com.example.emerson.agenda2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.emerson.agenda2.DAO.AlunoDAO;
import com.example.emerson.agenda2.converter.AlunoConverter;
import com.example.emerson.agenda2.modelo.Aluno;

import java.util.List;

/**
 * Created by emerson on 11/07/16.
 */
public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    private Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
         progress = ProgressDialog.show(context,"Aguarde", "Aguardando Alunos",true,true);
    }

    @Override
    protected String doInBackground(Void... params) {

        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        AlunoConverter converter = new AlunoConverter();
        String json = converter.converteParaJSON(alunos);
        WebClient client = new WebClient();
        String resposta = client.post(json);


        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta ) {
        progress.dismiss();
        Toast.makeText(context,resposta,Toast.LENGTH_LONG).show();
    }
}
