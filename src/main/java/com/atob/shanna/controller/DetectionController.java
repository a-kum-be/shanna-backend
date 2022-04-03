package com.atob.shanna.controller;

import com.atob.shanna.dto.ImageDto;
import com.atob.shanna.service.DetectionService;
import com.google.protobuf.ByteString;
import io.grpc.netty.shaded.io.netty.buffer.ByteBuf;
import io.grpc.netty.shaded.io.netty.buffer.Unpooled;
import lombok.NonNull;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/detect")
public class DetectionController {

    private final DetectionService detectionService;

    @Autowired
    public DetectionController(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @PostMapping("/post")
    public String detect(@RequestParam("resource") MultipartFile image) throws IOException {
        InputStream is = image.getInputStream();

        return detectionService.detect(is);
    }
}
