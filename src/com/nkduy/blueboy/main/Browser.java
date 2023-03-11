package com.nkduy.blueboy.main;

import java.awt.*;
import java.net.URL;

public class Browser {

    public static void openPage(String url) {
        try {
            Desktop.getDesktop().browse(new URL(url).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
