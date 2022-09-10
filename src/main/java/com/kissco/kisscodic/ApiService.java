package com.kissco.kisscodic;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService {

    static String clientId = "4HhVSPcAZaA4F_6snB1x";
    static String clientSecret = "dk9airxklJ";
    static String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

    public String getMean(String word , String source) throws ParseException {

        try{
            word = URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String responseBody = post(apiURL, requestHeaders, word,source);
        System.out.println("responseBody = " + responseBody);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(responseBody);
        JSONObject jsonObject = (JSONObject) obj;

        JSONObject message = (JSONObject)jsonObject.get("message");

        JSONObject result = (JSONObject)message.get("result");


        String mean = (String) result.get("translatedText");
        System.out.println("mean = " + mean);
        return mean;
    }


    private String post(String apiURL, Map<String, String> requestHeaders, String word,String source) {
        String target ="ja";

        if (source.equals("ja")) {
            target = "ko";
        }
        System.out.println("target = " + target);
        System.out.println("source = " + source);

        HttpURLConnection con = connect(apiURL);

        String postParams = "source=" + source + "&"+ "target=" + target +"&text=" + word;

        try {
            con.setRequestMethod("POST");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            
            try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())){
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            }else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패 ", e);
        }
        finally {
            con.disconnect();
            
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    private HttpURLConnection connect(String apiURL) {
        try {
            URL url = new URL(apiURL);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiURL, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiURL, e);
        }
    }


}
