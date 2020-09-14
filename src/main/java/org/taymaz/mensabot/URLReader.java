package org.taymaz.mensabot;

import java.net.*;
import java.io.*;

public class URLReader {
    public String readUrl(String args) throws Exception {

        URL url = new URL(args);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String output = "";

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            output += inputLine;
        in.close();
        String[] arr = output.split("meals");
        output = arr[1];
        arr = output.split("location");
        output = arr[0];
        output = output.substring(2, output.length()-2);
        return output;
    }
}