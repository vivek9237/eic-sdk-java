package com.github.vivek9237.eic.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EicCommonUtils {
    public static Properties readPropertyFile(String path) throws IOException{
        Properties props=new Properties();
        FileReader reader=new FileReader(path);
        props.load(reader);
        return props;
    }
}
