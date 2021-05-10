package com.example.articlebiases;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    TextView textA;
    SearchView urlSearch;
    Button butt1;
    String words;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first, container, false);
        TextView welcome = (TextView)rootView.findViewById(R.id.welcome);
        textA = (TextView)rootView.findViewById(R.id.text1);
        urlSearch = (SearchView) rootView.findViewById(R.id.urlSearch);
        butt1 = (Button) rootView.findViewById(R.id.button1);


        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new doit().execute();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Bundle bundle = new Bundle();
                bundle.putString("TextArticle", words);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SecondFragment secondFragment = new SecondFragment();
                secondFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, secondFragment);
                fragmentTransaction.commit();


            }
        });

        return rootView;

    }


    public class doit extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... params){
            String urlArticle = urlSearch.getQuery().toString();

            try {
                Document doc = Jsoup.connect(urlArticle).get();
                //words = doc.select("div.article__content").text();
                words = doc.select("p").text();

            }catch(Exception e) {e.printStackTrace();}


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }


}
