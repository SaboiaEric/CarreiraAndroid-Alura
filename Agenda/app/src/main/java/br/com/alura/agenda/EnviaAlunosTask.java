package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by erics on 12/07/2017.
 */

public class EnviaAlunosTask extends AsyncTask<Void,Void,String> {
    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }
    @Override
    protected void onPreExecute() {
        //Primeiro parametro contexto, segundo titulo, mensagem, tempo de processamento (true é indeterminado) e
        // o segundo  true diz que o processo é cancelavel.

        dialog = ProgressDialog.show(context,"Aguarde","Enviando alunos ...", true, true);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) { //Será processada em background em thread secundaria
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);
        WebClient client = new WebClient();
        String resposta = client.Post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) { //Será processada na thread principal a qual chamou este método
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
