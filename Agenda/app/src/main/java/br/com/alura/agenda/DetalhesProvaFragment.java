package br.com.alura.agenda;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.alura.agenda.modelo.Prova;

public class DetalhesProvaFragment extends android.support.v4.app.Fragment {


    private TextView campoMateria;
    private ListView listaTopicos;
    private TextView campoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = (TextView) view.findViewById(R.id.detalhes_prova_materia);
        campoData = (TextView) view.findViewById(R.id.detalhes_prova_data);
        listaTopicos = (ListView) view.findViewById(R.id.detalhes_prova_topicos);

        Bundle parametros = getArguments();
        if(parametros != null){
            //Coleta a data que foi passada no "pacote" do bundle para utilizamos
            Prova prova = (Prova) parametros.getSerializable("prova");
            populaCamposCom(prova);
        }


        return view;
    }

    public void populaCamposCom(Prova prova){
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> adapterTopicos =
                new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, prova.getTopicos());

        listaTopicos.setAdapter(adapterTopicos);

    }

}
