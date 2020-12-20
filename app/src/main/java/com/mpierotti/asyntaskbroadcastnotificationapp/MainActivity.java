package com.mpierotti.asyntaskbroadcastnotificationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    private ImageButton bt_notif;
    private Notificacion notificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_notif = findViewById(R.id.imageButton);
        notificacion = new Notificacion();
        bt_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacion.execute();
                Toast toast1 = Toast.makeText(getApplicationContext(), "el boton anda(?)", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
    }

    public class Notificacion extends AsyncTask<Double,Integer,String> {

        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(Double ...doubles) {
            try{
                Thread.sleep(5000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s){

            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, default_notification_channel_id);
            builder.setContentTitle("Notification App");
            builder.setContentText("Notificación creada con éxito");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setAutoCancel(true);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            System.out.println("PARTE 1, seteo de datos en el PostExecute y etc");
            Intent notificationIntent = new Intent(MainActivity.this, MyNotificationPublisher.class);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, builder.build());
            System.out.println("PARTE 2, se hizo el intent");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, pendingIntent);
            System.out.println("PARTE 3, se hizo le pendingintent y lo de alarm(?)");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onCancelled(String s) {

        }

    }
}