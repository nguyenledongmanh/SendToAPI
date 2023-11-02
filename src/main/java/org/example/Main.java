package org.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws
            IOException {
        for (String fileName: listFilesUsingJavaIO(AppConstant.COMPRESSED_SRC)) {
            File file = new File(AppConstant.COMPRESSED_SRC + "/" + fileName);
//            System.out.println(file);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("file", new FileBody(file));
            builder.addTextBody("fileName", file.getName());
            builder.addTextBody("timeSent", String.valueOf(new Date().getTime()));
            HttpPost post = new HttpPost(AppConstant.URL);
            post.setEntity(builder.build());
            HttpClient client = HttpClientBuilder.create()
                                                 .build();
            HttpResponse response = client.execute(post);
        }
        System.out.println("Successfully");
    }


    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                     .filter(file -> !file.isDirectory())
                     .map(File::getName)
                     .collect(Collectors.toSet());
    }
}