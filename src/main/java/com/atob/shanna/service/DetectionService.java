package com.atob.shanna.service;

import java.io.IOException;
import java.io.InputStream;

public interface DetectionService {

    String detect(final InputStream inputStream) throws IOException;
    String[] split(String text);
}
