package br.com.alura.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Infla, ou seja, exibe o xml (na pasta menu arquivo menu_formulario) no menu recebido como parametro
        getMenuInflater().inflate(R.menu.menu_formulario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegarAlunos();

                AlunoDAO dao = new AlunoDAO(this);
                dao.insere(aluno);
                dao.close();

                Toast.makeText(FormularioActivity.this,"Aluno "+ aluno.getNome()+" salvo!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
