package br.com.alura.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by erics on 24/07/2017.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    //Este método é chamado quando a tela e o mapa já foram rederizados passando o mapa como parâmetro
    @Override
    public void onMapReady(GoogleMap googleMap) {
        String endereco = "Rua Vicente Lázaro Filho, 135 - Wanel Ville, Sorocaba - SP";
        LatLng posicaoDeBusca = pegaCoordenadaDoEndereco(endereco);

        if(posicaoDeBusca != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDeBusca,25);
            googleMap.moveCamera(update);
        }
        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()){
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if(coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                //Adiciona o marcador no mapa recebido como parâmetro da API do google.
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();
        new Localizador(getContext(),googleMap);
    }

    private LatLng pegaCoordenadaDoEndereco(String endereco){
        try{
            Geocoder _geocoder = new Geocoder(getContext());
            List<Address> resultados = _geocoder.getFromLocationName(endereco, 1);
            if(!resultados.isEmpty()){
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
