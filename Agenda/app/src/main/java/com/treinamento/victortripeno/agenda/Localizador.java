package com.treinamento.victortripeno.agenda;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by victor.tripeno on 06/12/2016.
 */
public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleMap mapa;
    private final GoogleApiClient client;
    private final Context context;
    private final FragmentActivity fragmentActivity;

    public Localizador(Context context, GoogleMap mapa, FragmentActivity fragmentActivity) {
        client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        client.connect();

        this.context = context;
        this.fragmentActivity = fragmentActivity;

        this.mapa = mapa;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(50); // parametro diz a quantidade de metros que precisa ser percorrido para receber atualização do gps
        request.setInterval(1000); // intervalo entre duas atualizações do GPS
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // escolher qual o dispositivo no aparelho que fornece sua precisão


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(fragmentActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this); // avisar para o LocationServices que a conexao está pronta
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(coordenada);
        mapa.moveCamera(cameraUpdate);
    }
}
