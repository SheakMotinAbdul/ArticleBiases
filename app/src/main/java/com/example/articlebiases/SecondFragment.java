          package com.example.articlebiases;

          import android.os.AsyncTask;
          import android.os.Bundle;
          import android.text.method.ScrollingMovementMethod;
          import android.view.LayoutInflater;
          import android.view.View;
          import android.view.ViewGroup;
          import android.widget.Button;
          import android.widget.TextView;

          import androidx.fragment.app.Fragment;

          import com.anychart.AnyChart;
          import com.anychart.AnyChartView;
          import com.anychart.chart.common.dataentry.DataEntry;
          import com.anychart.chart.common.dataentry.ValueDataEntry;
          import com.anychart.charts.Pie;
          import com.monkeylearn.MonkeyLearn;
          import com.monkeylearn.MonkeyLearnException;
          import com.monkeylearn.MonkeyLearnResponse;

          import org.json.simple.JSONArray;
          import org.json.simple.JSONObject;
          import org.json.simple.parser.JSONParser;
          import org.json.simple.parser.ParseException;

          import java.util.ArrayList;
          import java.util.List;


          /**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment  {
    TextView Article;
    String url;
    String words;
    AnyChartView chart;
    TextView title;
    String [] sentiment = {"positive", "negative"};
    int[] sentimentScore = {50, 50};
    Double confidence;
    String label;
    TextView probability;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_second,container,false);
        Bundle bundle = getArguments();
        words = bundle.getString("TextArticle");
        title = (TextView)rootView.findViewById(R.id.title);
        probability = (TextView)rootView.findViewById(R.id.probability);
        Article = (TextView)rootView.findViewById(R.id.wordsArticle);
        Article.setText(words);
        Article.setMovementMethod(new ScrollingMovementMethod());

        callMonkey task = new callMonkey();
        task.execute();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        chart = (AnyChartView) rootView.findViewById(R.id.chart) ;
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < sentiment.length; i++){
            dataEntries.add(new ValueDataEntry(sentiment[i], sentimentScore[i]));
        }

        pie.data(dataEntries);
        chart.setChart(pie);

        return rootView;

    }


    private class callMonkey extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params){


            try {
            MonkeyLearn ml = new MonkeyLearn("5cca42426fbf0a54c5dcdf075c35f402d81357a6");
            String moduleId = "cl_eGMcHoX5";
            String[] textList = {words};
            MonkeyLearnResponse res = ml.classifiers.classify(moduleId, textList, true);
            System.out.println(res.arrayResult);


            JSONArray jsonArray = res.arrayResult;
            String tempArray = jsonArray.toJSONString();
            String strippedArray = tempArray.substring(3, tempArray.length() - 3);
            JSONObject finalArray =(JSONObject) new JSONParser()
                        .parse(strippedArray);


            confidence = (Double) finalArray.get("confidence");
            label = (String) finalArray.get("label");

            if (label.equals("positive")){
                sentimentScore[0] = (int) (confidence * 100);
                sentimentScore[1] = 100 - sentimentScore[0];
                }
            else {
                sentimentScore[1] = (int) (confidence * 100);
                sentimentScore[0] = 100 - sentimentScore[1];
            }


            double probCalc = (double) finalArray.get("probability")*100;
            String prob = "The probability is: " + probCalc + "%";
            probability.setText(prob);
            System.out.println(confidence + " " + label);


            } catch (MonkeyLearnException | ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            System.out.println( res.arrayResult );
        }

    }
}