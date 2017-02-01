package com.treinamento.victortripeno.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.treinamento.victortripeno.agenda.dao.AlunoDAO;
import com.treinamento.victortripeno.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by victor.tripeno on 05/12/2016.
 */
public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this); // metodo para avisar quando o mapa está pronto, parametro é
                            // resposnsavel por ter o mapa pronto, porém necessita estar em conjunto
                            // com o método onMapReady
    }

    /**
     * método chamado após getMapAsync
     * @param googleMap - mesmo parametro que recebe o valor em getMapAsync
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng posicaoEscola = pegaCoordenadaDoEndereco("Rua Vergueiro, 3185, Vila Mariana, Sao Paulo");
        if(posicaoEscola != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoEscola, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO alunoDao = new AlunoDAO(getContext());

        for(Aluno aluno : alunoDao.buscaAlunos()) {
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if(coordenada != null) {
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
            alunoDao.close();

            new Localizador(getContext(), googleMap, this.getActivity());
        }

    }

    public LatLng pegaCoordenadaDoEndereco (String endereco) {
        try {
            Geocoder geoCoder = new Geocoder(getContext());
            List<Address> resultados = geoCoder.getFromLocationName(endereco, 1);
            if(!resultados.isEmpty()) {
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return posicao;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
