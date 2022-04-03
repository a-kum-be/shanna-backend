package com.atob.shanna.service.impl;

import com.atob.shanna.service.DetectionService;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DetectionServiceImpl implements DetectionService {

    @Override
    public String detect(final InputStream inputStream) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(inputStream);

        Image img = Image
                .newBuilder()
                .setContent(imgBytes)
                .build();
        Feature feat = Feature
                .newBuilder()
                .setType(Feature.Type.DOCUMENT_TEXT_DETECTION)
                .setModel("builtin/latest")
                .build();
        AnnotateImageRequest request = AnnotateImageRequest
                .newBuilder()
                .addFeatures(feat)
                .setImage(img)
                .build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            client.close();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return "";
                }

                TextAnnotation annotation = res.getFullTextAnnotation();
                for (Page page : annotation.getPagesList()) {
                    String pageText = "";
                    for (Block block : page.getBlocksList()) {
                        String blockText = "";
                        for (Paragraph para : block.getParagraphsList()) {
                            String paraText = "";
                            for (Word word : para.getWordsList()) {
                                String wordText = "";
                                for (Symbol symbol : word.getSymbolsList()) {
                                    wordText = wordText + symbol.getText();
                                }

                                paraText = String.format("%s %s", paraText, wordText);
                            }
                            blockText = blockText + paraText;
                        }
                        pageText = pageText + blockText;
                    }
                }
                return annotation.getText();
            }
        }
        return null;
    }

    public List<String> split(String text){
        return List.of(text.split("\\["));
    }
}
