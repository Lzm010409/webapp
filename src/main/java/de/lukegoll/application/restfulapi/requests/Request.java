package de.lukegoll.application.restfulapi.requests;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.*;

public class Request {
    @Async
    public ListenableFuture<String> httpPost(String XmlFile, String URL, String TOKEN) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringEntity requestEntity = new StringEntity(
                ladeDatei(XmlFile),
                ContentType.APPLICATION_XML
        );
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setEntity(requestEntity);
        String token  = "Basic["+TOKEN+"]";
        httpPost.setHeader("Authorization", token);

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            System.out.println(response.getCode() + " " + response.getReasonPhrase());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output = bufferedReader.readLine();
            httpclient.close();
            return AsyncResult.forValue(output);

        } catch (IOException e) {
            return AsyncResult.forExecutionException(e);
        }
    }

    public String httpPost(File file, String URL, String TOKEN) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addBinaryBody("file", file);
        entityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
        HttpEntity multiPartHttpEntity = entityBuilder.build();
        HttpPost httpPost = new HttpPost(URL);
        httpPost.setEntity(multiPartHttpEntity);
        httpPost.setHeader("Authorization", TOKEN);
        httpPost.setHeader("ContentType", ContentType.MULTIPART_FORM_DATA);

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            System.out.println(response.getCode() + " " + response.getReasonPhrase());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output = bufferedReader.readLine();
            httpclient.close();
            return output;

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    private String ladeDatei(String datName) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(datName);

        if (!file.canRead() || !file.isFile())
            System.exit(0);

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(datName));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                stringBuilder.append(zeile);
                System.out.println("Gelesene Zeile: " + zeile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return stringBuilder.toString();
    }

    public String httpGet(String JSON_STRING, String URL, String TOKEN) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringEntity requestEntity = new StringEntity(
                JSON_STRING,
                ContentType.APPLICATION_JSON
        );
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setEntity(requestEntity);
        httpGet.setHeader("Authorization", TOKEN);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            System.out.println(response.getCode() + " " + response.getReasonPhrase());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output = bufferedReader.readLine();
            httpclient.close();
            return output;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String httpGet(String URL, String TOKEN) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        httpGet.setHeader("Authorization", TOKEN);

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            System.out.println(response.getCode() + " " + response.getReasonPhrase());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output = bufferedReader.readLine();

            return output;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public String httpDelete(String URL, String TOKEN) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(URL);
        httpDelete.setHeader("Authorization", TOKEN);

        try (CloseableHttpResponse response = httpclient.execute(httpDelete)) {
            System.out.println(response.getCode() + " " + response.getReasonPhrase());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String output = bufferedReader.readLine();
            httpclient.close();
            return output;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
    }
}
