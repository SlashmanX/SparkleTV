/*
 *      Copyright (c) 2004-2013 Matthew Altman & Stuart Boston
 *
 *      This file is part of TheTVDB API.
 *
 *      TheTVDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheTVDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheTVDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.omertron.thetvdbapi.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.commons.codec.binary.Base64;

/**
 * Web browser with simple cookies support
 */
public final class WebBrowser {

    private static final Map<String, String> BROWSER_PROPERTIES = new HashMap<String, String>();
    private static Map<String, Map<String, String>> cookies = new HashMap<String, Map<String, String>>();
    private static String proxyHost = null;
    private static String proxyPort = null;
    private static String proxyUsername = null;
    private static String proxyPassword = null;
    private static String proxyEncodedPassword = null;
    private static int webTimeoutConnect = 25000;   // 25 second timeout
    private static int webTimeoutRead = 90000;      // 90 second timeout

    /**
     * Constructor for WebBrowser. Does instantiates the browser properties.
     */
    protected WebBrowser() {
        throw new UnsupportedOperationException("WebBrowser can not be instantiated!");
    }

    /**
     * Request the web page at the specified URL
     *
     * @param url
     * @throws IOException
     */
    public static String request(String url) throws IOException {
        return request(new URL(url));
    }

    /**
     * Open a connection using proxy parameters if they exist.
     *
     * @param url
     * @throws IOException
     */
    public static URLConnection openProxiedConnection(URL url) throws IOException {
        if (proxyHost != null) {
            System.getProperties().put("proxySet", "true");
            System.getProperties().put("proxyHost", proxyHost);
            System.getProperties().put("proxyPort", proxyPort);
        }

        URLConnection cnx = url.openConnection();

        if (proxyUsername != null) {
            cnx.setRequestProperty("Proxy-Authorization", proxyEncodedPassword);
        }

        return cnx;
    }

    /**
     * Request the web page at the specified URL
     *
     * @param url
     * @throws IOException
     */
    public static String request(URL url) throws IOException {
        StringBuilder content = new StringBuilder();

        BufferedReader in = null;
        URLConnection cnx = null;
        InputStreamReader isr = null;
        GZIPInputStream zis = null;

        try {
            cnx = openProxiedConnection(url);

            sendHeader(cnx);
            readHeader(cnx);

            // Check the content encoding of the connection. Null content encoding is standard HTTP
            if (cnx.getContentEncoding() == null) {
                //in = new BufferedReader(new InputStreamReader(cnx.getInputStream(), getCharset(cnx)));
                isr = new InputStreamReader(cnx.getInputStream(), "UTF-8");
            } else if (cnx.getContentEncoding().equalsIgnoreCase("gzip")) {
                zis = new GZIPInputStream(cnx.getInputStream());
                isr = new InputStreamReader(zis, "UTF-8");
            } else {
                return "";
            }

            in = new BufferedReader(isr);

            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                	ex.printStackTrace();
                }
            }

            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ex) {
                	ex.printStackTrace();
                }
            }

            if (zis != null) {
                try {
                    zis.close();
                } catch (IOException ex) {
                	ex.printStackTrace();
                }
            }

            if (cnx instanceof HttpURLConnection) {
                ((HttpURLConnection) cnx).disconnect();
            }
        }
        return content.toString();
    }

    /**
     * Set the header information for the connection
     *
     * @param cnx
     */
    private static void sendHeader(URLConnection cnx) {
        if (BROWSER_PROPERTIES.isEmpty()) {
            BROWSER_PROPERTIES.put("User-Agent", "Mozilla/5.25 Netscape/5.0 (Windows; I; Win95)");
        }

        // send browser properties
        for (Map.Entry<String, String> browserProperty : BROWSER_PROPERTIES.entrySet()) {
            cnx.setRequestProperty(browserProperty.getKey(), browserProperty.getValue());
        }
        // send cookies
        String cookieHeader = createCookieHeader(cnx);
        if (!cookieHeader.isEmpty()) {
            cnx.setRequestProperty("Cookie", cookieHeader);
        }
    }

    /**
     * Create the cookies for the header
     *
     * @param cnx
     */
    private static String createCookieHeader(URLConnection cnx) {
        String host = cnx.getURL().getHost();
        StringBuilder cookiesHeader = new StringBuilder();
        for (Map.Entry<String, Map<String, String>> domainCookies : cookies.entrySet()) {
            if (host.endsWith(domainCookies.getKey())) {
                for (Map.Entry<String, String> cookie : domainCookies.getValue().entrySet()) {
                    cookiesHeader.append(cookie.getKey());
                    cookiesHeader.append("=");
                    cookiesHeader.append(cookie.getValue());
                    cookiesHeader.append(";");
                }
            }
        }
        if (cookiesHeader.length() > 0) {
            // remove last ; char
            cookiesHeader.deleteCharAt(cookiesHeader.length() - 1);
        }
        return cookiesHeader.toString();
    }

    /**
     * Read the header information into the cookies
     *
     * @param cnx
     */
    private static void readHeader(URLConnection cnx) {
        // read new cookies and update our cookies
        for (Map.Entry<String, List<String>> header : cnx.getHeaderFields().entrySet()) {
            if ("Set-Cookie".equals(header.getKey())) {
                for (String cookieHeader : header.getValue()) {
                    String[] cookieElements = cookieHeader.split(" *; *");
                    if (cookieElements.length >= 1) {
                        String[] firstElem = cookieElements[0].split(" *= *");
                        String cookieName = firstElem[0];
                        String cookieValue = firstElem.length > 1 ? firstElem[1] : null;
                        String cookieDomain = null;
                        // find cookie domain
                        for (int i = 1; i < cookieElements.length; i++) {
                            String[] cookieElement = cookieElements[i].split(" *= *");
                            if ("domain".equals(cookieElement[0])) {
                                cookieDomain = cookieElement.length > 1 ? cookieElement[1] : null;
                                break;
                            }
                        }
                        if (cookieDomain == null) {
                            // if domain isn't set take current host
                            cookieDomain = cnx.getURL().getHost();
                        }
                        Map<String, String> domainCookies = cookies.get(cookieDomain);
                        if (domainCookies == null) {
                            domainCookies = new HashMap<String, String>();
                            cookies.put(cookieDomain, domainCookies);
                        }
                        // add or replace cookie
                        domainCookies.put(cookieName, cookieValue);
                    }
                }
            }
        }
    }

    /**
     * Return the proxy host name
     *
     */
    public static String getProxyHost() {
        return proxyHost;
    }

    /**
     * Set the proxy host name
     *
     * @param tvdbProxyHost
     */
    public static void setProxyHost(String tvdbProxyHost) {
        WebBrowser.proxyHost = tvdbProxyHost;
    }

    /**
     * Get the proxy port
     *
     */
    public static String getProxyPort() {
        return proxyPort;
    }

    /**
     * Set the proxy port
     *
     * @param proxyPort
     */
    public static void setProxyPort(String proxyPort) {
        WebBrowser.proxyPort = proxyPort;
    }

    /**
     * Get the proxy username
     *
     */
    public static String getProxyUsername() {
        return proxyUsername;
    }

    /**
     * Set the proxy username
     *
     * @param proxyUsername
     */
    public static void setProxyUsername(String proxyUsername) {
        WebBrowser.proxyUsername = proxyUsername;
    }

    /**
     * Get the proxy password
     *
     */
    public static String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * Set the proxy password. Note this will automatically encode the password
     *
     * @param proxyPassword
     */
    public static void setProxyPassword(String proxyPassword) {
        WebBrowser.proxyPassword = proxyPassword;

        if (proxyUsername != null) {
            proxyEncodedPassword = proxyUsername + ":" + proxyPassword;
            proxyEncodedPassword = "Basic " + new String(Base64.encodeBase64((proxyUsername + ":" + proxyPassword).getBytes()));
        }
    }

    /**
     * Get the current web connect timeout value
     *
     */
    public static int getWebTimeoutConnect() {
        return webTimeoutConnect;
    }

    /**
     * Get the current web read timeout value
     *
     */
    public static int getWebTimeoutRead() {
        return webTimeoutRead;
    }

    /**
     * Set the web connect timeout value
     *
     * @param webTimeoutConnect
     */
    public static void setWebTimeoutConnect(int webTimeoutConnect) {
        WebBrowser.webTimeoutConnect = webTimeoutConnect;
    }

    /**
     * Set the web read timeout value
     *
     * @param webTimeoutRead
     */
    public static void setWebTimeoutRead(int webTimeoutRead) {
        WebBrowser.webTimeoutRead = webTimeoutRead;
    }
}
