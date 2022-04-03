package com.atob.shanna.service;

import com.google.protobuf.ByteString;

import java.io.IOException;
import java.io.InputStream;

public interface DetectionService {

    public String detect(final InputStream inputStream) throws IOException;
}
