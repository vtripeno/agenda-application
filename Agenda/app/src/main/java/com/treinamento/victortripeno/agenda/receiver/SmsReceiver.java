package com.treinamento.victortripeno.agenda.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.treinamento.victortripeno.agenda.ListaAlunosActivity;
import com.treinamento.victortripeno.agenda.R;
import com.treinamento.victortripeno.agenda.dao.AlunoDAO;

import java.util.Objects;

/**
 * Created by victor.tripeno on 30/11/2016.
 */
public class SmsReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);

        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDAO dao = new AlunoDAO(context);
        if(dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        dao.close();

        // Código abaixo é utilizado para abrir a aplicação
        /*Intent intent1 = new Intent(context, ListaAlunosActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);*/

        /*if (intent.getAction() != null && intent.getAction().equals("com.google.android.gms.actions.SEARCH_ACTION")) {
            Intent intent1 = new Intent(context, ListaAlunosActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }*/

    }
}
