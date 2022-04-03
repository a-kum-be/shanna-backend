package com.atob.shanna.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DetectionService {

    String detect(final InputStream inputStream) throws IOException;
    List<String> split(String text);
}
