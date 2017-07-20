package br.com.alura.agenda;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);


        List<String> topicosPort = Arrays.asList("Sujeito","Objeto");
        Prova provaPortugues= new Prova("Português", "22/07/2017", topicosPort);

        List<String> topicosMat = Arrays.asList("Trigonometria","Inequações");
        Prova provaMatematica= new Prova("Matematica", "25/07/2017", topicosMat);


        List<Prova> provas = Arrays.asList(provaPortugues,provaMatematica);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(this,android.R.layout.simple_list_item_1,provas);

        ListView lista = (ListView) findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(ProvasActivity.this, "Clicou na prova: " + prova,Toast.LENGTH_SHORT).show();
                Intent vaiParaDetalhes = new Intent(ProvasActivity.this, DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova", prova);
                startActivity(vaiParaDetalhes);
            }
        });
    }


}