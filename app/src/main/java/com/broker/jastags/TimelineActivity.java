package com.broker.jastags;

import android.app.Activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.broker.jastags.utils.ConstantsUtils;
import com.broker.jastags.utils.TwitterUtils;

public class TimelineActivity extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

        //objeto anónimo de la clase interna
        new GetTimeLineTask().execute();
	}










    //hacemos una llamada asincrona con la clase AsyncTask para no entorpecer al UI TRHEAD (Además de que es una
    //operación ilegal.
    class GetTimeLineTask extends AsyncTask<Object, Void, Void>{ //clase interna
    // primera parametro: lo que le queramos pasar, admite cualquier cosa ( object)
    // Segundo parametro: el tipo de valor que regreso en doInBackground, como no queremos nada ponemos void
        private ProgressDialog pd;

        //muestra un loader para que el usuario sepa que hay una carga de datos y tiene que esperar
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pd = new ProgressDialog(TimelineActivity.this);
            pd.setTitle(getResources().getString(R.string.label_tweet_search_loader));
            pd.show();
        }

        @Override
        protected void onPostExecute(Void result){ //cuando termine de cargar quitamos el progress dialog
            super.onPostExecute(result);
            pd.dismiss(); //eliminamos en progressDialog
        }

/*
Explicacion puntos suspensivos:
Son equivalentes a definir el parámetro como un array del tipo que le indiquemos
(da igual que sea primitivo o una clase definida por el usuario) pero sin tener que crearlo
explícitamente en la invocación al método
 */

        @Override
        protected Void doInBackground(Object... params) {
            TwitterUtils.getTimelineForSearchTerm(ConstantsUtils.MEJORANDROID_TERM);
            return null;
        }


    }


}
