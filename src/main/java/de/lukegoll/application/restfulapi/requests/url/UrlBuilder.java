package de.lukegoll.application.restfulapi.requests.url;

public class UrlBuilder {

    public String buildUrl(URL URL) {
        String url = "https://my.sevdesk.de/api/v1" + URL.getExtender();
        return url;
    }
}
