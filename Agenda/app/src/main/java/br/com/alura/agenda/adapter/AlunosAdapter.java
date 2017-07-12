package br.com.alura.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.ListaAlunosActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by erics on 11/07/2017.
 */

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;

    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        //Infla o layout criado com base na View "context" passada como parâmetro no construtor
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;
        if(convertView == null) {
        /*Infla o layout, utiliza o layout do parent para tomar como base o arquivo pai
            e o 'false' faz com que ele não envie uma view dentro dela mesmo e ocorra um exception
         */
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        //Insere no campo com o id item_nome o valor do nome do aluno
        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if(caminhoFoto != null) {
            //Pega arquivo que está no caminhoFoto e decodifica-o para o formato bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            //Inclui uma escala no tamanho da imagem para que fique menor e insere true para dizer que será aplicado um filtro
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
