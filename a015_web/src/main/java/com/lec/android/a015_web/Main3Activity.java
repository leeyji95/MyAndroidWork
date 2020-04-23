package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/* XML, Json 파싱2
 *
 * ■서울시 지하철 역사 정보
http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-12753&srvType=A&serviceKind=1&currentPageNo=1

샘플url

XML 버젼
http://swopenAPI.seoul.go.kr/api/subway/717077554f6c65653231554e5a6672/xml/stationInfo/1/5/서울

JSON 버젼
http://swopenAPI.seoul.go.kr/api/subway/717077554f6c65653231554e5a6672/json/stationInfo/1/5/서울
*/

public class Main3Activity extends AppCompatActivity {

    public static final String API_KEY = "717077554f6c65653231554e5a6672";

    EditText editText;
    Button btnXML, btnJSON, btnParse;
    static TextView tvResult;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = findViewById(R.id.editText);
        btnXML = findViewById(R.id.btnXML);
        btnJSON = findViewById(R.id.btnJSON);
        btnParse = findViewById(R.id.btnParse);
        tvResult = findViewById(R.id.tvResult);

        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url;
                try {
                    url = buildUrlAddress("xml", 1, 5, editText.getText().toString().trim());

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            request(url);
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;

            }
        });

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url;
                try {
                    url = buildUrlAddress("json", 1, 5, editText.getText().toString().trim());
        Log.d("myapp", "url" + url);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            request(url);
                Log.d("myapp", "요청" );
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
            }
        });

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    String url = buildUrlAddress("json", 1, 5, editText.getText().toString().trim());
                    parseJSON(tvResult.getText().toString());
                    tvResult.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    } // end onCreate


    public void request(String urlStr) {
        final StringBuilder sb = new StringBuilder();

        BufferedReader reader = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(5000); // 연결이 수립되는 시간. timeout 시간 설정. 경과하면 SocketTimeoutException 발생
                conn.setUseCaches(false); // 캐시 사용 안함
                conn.setRequestMethod("GET");   // GET 방식 request

                conn.setDoInput(true); // URLConnection 을 입력으로 사용하겟다. 이걸 받아옴.  이 값이 true. false 이면 출력용

                int responseCode = conn.getResponseCode(); // response  code 값. 성공하면 200

                if (responseCode == HttpURLConnection.HTTP_OK) { // 200 HTTP_OK
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;
                        sb.append(line + "\n");
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (conn != null) conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                tvResult.setText(sb.toString());
//                tvResult.setText("응답결과 -> " + sb.toString());  // StringBuilder  -> final 설정해주고 오기

            }
        });

    }


    public static void parseJSON(String url) throws JSONException {

        JSONObject jobj = new JSONObject(url); // JSON 파싱 : JSONObject <- jsonText /   우리가 response  받은게 오브젝트이기때문에  매개변수로 받은 jsonText String 타입을  JSONObject로 변환.
        JSONArray row = jobj.getJSONArray("stationList");  // 오브젝트를 가져욤. 에다가  row를 가져와야 함으로  제이슨배열로 접근

        Handler handler = new Handler();
//        System.out.println("row 의 개수 : " + row.length());
//        System.out.println();

        for(int i = 0; i < row.length(); i++) {
            JSONObject station = row.getJSONObject(i);

            final String statnNm = station.getString("statnNm");
            final String subwayNm = station.getString("subwayNm");
            final String subwayId = station.getString("subwayId");

            handler.post(new Runnable() {
                @Override
                public void run() {
                    tvResult.append(statnNm + " " +  subwayId + " " +  subwayNm + "\n");
                }
            });

        }
    } // end parseJSON()


    public static String buildUrlAddress(String reqType, int startIndex, int endIndex, String editText) throws IOException {

        String url_address = String.format("http://swopenapi.seoul.go.kr/api/subway/%s/%s/stationInfo/%d/%d/%s",
                API_KEY, reqType, startIndex, endIndex, editText);

        return url_address;
    }

} // end Activity
