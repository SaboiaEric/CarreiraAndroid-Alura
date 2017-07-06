package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.MenuItemHoverListener;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        //Coletando o click em um aluno na listView
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intentVaiParaFormulario = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                //Objeto enviado junto com a intent, para isso o objeto enviado precisa ser serializable
                intentVaiParaFormulario.putExtra("aluno",aluno);
                startActivity(intentVaiParaFormulario);
            }
        });

        Button btnNovoAluno = (Button) findViewById(R.id.novo_aluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this,FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        //Região ligação
        MenuItem itemLigar = menu.add("Ligação");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //Verifica se a tela já tem permissão para a "Ligação Telefonica"
                //Se existir continua a ligação, se não, pergunta se pode
                if(ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{android.Manifest.permission.CALL_PHONE},123);
                }
                else{
                Intent intentLigar = new Intent(Intent.ACTION_CALL);
                intentLigar.setData(Uri.parse("tel:"+aluno.getTelefone()));
                startActivity(intentLigar);
                }
                return false;
            }
        });



        //Região de envio de sms
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone())); //Padrão de envio de sms do android
        itemSMS.setIntent(intentSMS);

        //Região de acesso Google Maps
        MenuItem itemMAPA = menu.add("Visualizar Mapa");
        Intent intentMAPA = new Intent(Intent.ACTION_VIEW);
        intentMAPA.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco())); //Protocolo de acesso ao google maps do android
        itemMAPA.setIntent(intentMAPA);

        MenuItem itemSite = menu.add("Visualizar Site");
        //Cria uma intent que utiliza uma action view, ou seja, chamará uma ação para um local.
        Intent itentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if(!site.startsWith("http://"))
            site = "http://" + site;
        itentSite.setData(Uri.parse(site));
        itemSite.setIntent(itentSite);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                carregaLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}
