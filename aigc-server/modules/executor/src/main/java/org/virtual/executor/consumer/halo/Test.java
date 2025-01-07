package org.virtual.executor.consumer.halo;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import lombok.Data;
import okhttp3.*;

@Data
class Content {
    private String raw;
    private String rawType;
    private String content;
}
@Data
class PostRequest {
    private String apiVersion;
    private String kind;
    private Metadata metadata;
    private Spec spec;
    private Status status;
    private Content content;

    @Data
    public static class Metadata {
        private Map<String, String> annotations;
        private String creationTimestamp;
        private String deletionTimestamp;
        private List<String> finalizers;
        private String generateName;
        private Map<String, String> labels;
        private String name;
        private int version;
    }

    @Data
    public static class Spec {
        private boolean allowComment;
        private String baseSnapshot;
        private List<String> categories;
        private String cover;
        private boolean deleted;
        private Excerpt excerpt;
        private String headSnapshot;
        private List<Map<String, String>> htmlMetas;
        private String owner;
        private boolean pinned;
        private int priority;
        private boolean publish;
        private String publishTime;
        private String releaseSnapshot;
        private String slug;
        private List<String> tags;
        private String template;
        private String title;
        private String visible;

        @Data
        public static class Excerpt {
            private boolean autoGenerate;
            private String raw;
        }
    }

    @Data
    public static class Status {
        private int commentsCount;
        private List<Condition> conditions;
        private List<String> contributors;
        private String excerpt;
        private boolean hideFromList;
        private boolean inProgress;
        private String lastModifyTime;
        private int observedVersion;
        private String permalink;
        private String phase;

        @Data
        public static class Condition {
            private String lastTransitionTime;
            private String message;
            private String reason;
            private String status;
            private String type;
        }
    }
}
public class Test {
    public static void main(String[] args) {
        testPost();
    }

    public static void testPost() {


        OkHttpClient client = new OkHttpClient();
        PostRequest postRequest = new PostRequest();

        // 设置 Metadata
        PostRequest.Metadata metadata = new PostRequest.Metadata();
        metadata.setAnnotations(new HashMap<String, String>() {{
            put("additionalProp1", "value1");
            put("additionalProp2", "value2");
        }});
        metadata.setName("sample-name-222");
        metadata.setVersion(1);
        postRequest.setMetadata(metadata);

        // 设置 Spec
        PostRequest.Spec spec = new PostRequest.Spec();
        spec.setAllowComment(true);
        spec.setCategories(Arrays.asList("category1", "category2"));
        spec.setTitle("Sample Titl 11e");
        spec.setSlug("sample-title 33");
        spec.setVisible("PUBLIC");
        spec.setDeleted(false);
        spec.setPublish(true);
        spec.setPriority(0);

        PostRequest.Spec.Excerpt excerpt = new PostRequest.Spec.Excerpt();
        excerpt.setAutoGenerate(true);
        excerpt.setRaw("");
        spec.setExcerpt(excerpt);
        postRequest.setSpec(spec);

        Content content = new Content();
        content.setContent("Sample Content");
        content.setRaw("<p style=\\\"\\\">我是测试</p>");
        content.setRawType("HTML");
        postRequest.setContent(content);

        // 设置 Status
        PostRequest.Status status = new PostRequest.Status();
        status.setCommentsCount(0);
        postRequest.setStatus(status);
        postRequest.setKind("Post");
        postRequest.setApiVersion("content.halo.run/v1alpha1");

        // 将文章对象转换为 JSON
        Gson gson = new Gson();
        String json = gson.toJson(postRequest);
        RequestBody requestBody = RequestBody.create(
            json, MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
            .url("http://localhost:8090/apis/content.halo.run/v1alpha1/posts")
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .header("Accept-Language", "en-US,en;q=0.9,zh-TW;q=0.8,zh-CN;q=0.7,zh;q=0.6")
            .header("Cache-Control", "no-cache")
            .header("Connection", "keep-alive")
            //.header("Cookie", "Phpstorm-fa8a895e=01f52b21-19a2-4afb-ac2c-a1fe67be5419; ajs_anonymous_id=ac870643-786a-4b64-a970-9ab385f09489; Webstorm-f05af1c1=ce7ccc04-61d0-49d1-a2ae-0ee760767673; locale=en-us; Pycharm-c69fede8=7b331d56-8843-4340-8a11-8537610457e3; deviceId=f1bd74b0061b74973df58af84da3aa62; Webstorm-f05af1c2=3761932d-d8bc-4c7d-99ea-4fb55c5ec5a7; XSRF-TOKEN=bb298ce6-5646-4432-9567-0733d9eaff18; remember-me=NkMwak9sVHBiQk5SUENobHRYMmdudyUzRCUzRDpqU1UlMkJ1RXVwWUpuWW8lMkZDWEtYV2clMkJBJTNEJTNE; device_id=5d056d81bb394f2aab54fe1469ae0d76; SESSION=d74dc968-cf8e-4a3b-8c9e-5dcbce18481b")
            .header("Pragma", "no-cache")
            .header("Content-Type", "application/json")
            .header("Sec-Fetch-Dest", "document")
            .header("Sec-Fetch-Mode", "navigate")
            .header("Sec-Fetch-Site", "none")
            .header("Sec-Fetch-User", "?1")
            .header("Upgrade-Insecure-Requests", "1")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
            .header("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
            .header("sec-ch-ua-mobile", "?0")
            .header("sec-ch-ua-platform", "\"macOS\"")
            .header("Authorization", "Bearer pat_eyJraWQiOiJ6Q2d0WVRGX2s0czJwOGRaLVFDTFpYZnJJUlJkejdkYnRWcXhnbFdyWl9JIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwOTAvIiwic3ViIjoiYWlnYyIsImlhdCI6MTczNjIxMTU5NywianRpIjoiZTk5NmJkMTUtMGYyOC0yZGQ5LTM2MjAtZmNlMTYyZGE1OGNjIiwicGF0X25hbWUiOiJwYXQtYWlnYy1HUWdqRSJ9.IASsVc0-brUK4CAQt63x0bZh2HCpTl64WVpXlMiKjJpTULh-w_fICNj9WY2rN3SwInAxDt9TpjK6TlRIqy3WgdG_mlbTt5wEK_x3-zg7G4uzcZ0akPXcKz2ceP8w29OXLwSMRG-ysRq8GAeY7edhuKld420HUffxsyqoPekv9kaFzfFqJdUjNuycbd_9lw3QXk793ESR__iE3lK8JNrayKO7-qad88zcRjV77MDGbRCec01NeEMo-mW6WpIiDnwYosGv9BVwMgIsWmTib9P6A0pKjUpReFvPgqz5J22ZTqLw70PxN6r4JNRNilqePXhdBU9y9_xfMhB4BC8n-HhpwYcOxlgu0wG18e_FQzOQUhnGDu1WYWmUMSGhziDpi64GSqdL1oa78JKE36aE5MF_-t-cGfPOI13W29Pyx38Y7m_FBv4W9LvZ1pCvKugLX7OPM3c1LbZdRmgFEK-t8wSEYDmTKRcyU_gIxjjtCxbopwLluBNjItKsiVuoPBogKrPKrrx-dXPQDRic8u6qFw-wLHgav5WPa7QSMHmBzpqSQRkQuAiXBVAIy70WO5MoEQv9WfMhttjZef2fV_tJxrU-DGR754bD6Cxn384SxmcnIC2HzpbdE8yWC1Zb2o3NrGyt-V-cZO6UhNx66c-5lbkmTg5Kzs93GnEzos1vvDzHlgs")
            //.header("Cookie", "Bearer pat_eyJraWQiOiJ6Q2d0WVRGX2s0czJwOGRaLVFDTFpYZnJJUlJkejdkYnRWcXhnbFdyWl9JIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwOTAvIiwic3ViIjoiYWlnYyIsImlhdCI6MTczNjIxMTU5NywianRpIjoiZTk5NmJkMTUtMGYyOC0yZGQ5LTM2MjAtZmNlMTYyZGE1OGNjIiwicGF0X25hbWUiOiJwYXQtYWlnYy1HUWdqRSJ9.IASsVc0-brUK4CAQt63x0bZh2HCpTl64WVpXlMiKjJpTULh-w_fICNj9WY2rN3SwInAxDt9TpjK6TlRIqy3WgdG_mlbTt5wEK_x3-zg7G4uzcZ0akPXcKz2ceP8w29OXLwSMRG-ysRq8GAeY7edhuKld420HUffxsyqoPekv9kaFzfFqJdUjNuycbd_9lw3QXk793ESR__iE3lK8JNrayKO7-qad88zcRjV77MDGbRCec01NeEMo-mW6WpIiDnwYosGv9BVwMgIsWmTib9P6A0pKjUpReFvPgqz5J22ZTqLw70PxN6r4JNRNilqePXhdBU9y9_xfMhB4BC8n-HhpwYcOxlgu0wG18e_FQzOQUhnGDu1WYWmUMSGhziDpi64GSqdL1oa78JKE36aE5MF_-t-cGfPOI13W29Pyx38Y7m_FBv4W9LvZ1pCvKugLX7OPM3c1LbZdRmgFEK-t8wSEYDmTKRcyU_gIxjjtCxbopwLluBNjItKsiVuoPBogKrPKrrx-dXPQDRic8u6qFw-wLHgav5WPa7QSMHmBzpqSQRkQuAiXBVAIy70WO5MoEQv9WfMhttjZef2fV_tJxrU-DGR754bD6Cxn384SxmcnIC2HzpbdE8yWC1Zb2o3NrGyt-V-cZO6UhNx66c-5lbkmTg5Kzs93GnEzos1vvDzHlgs")
            .post(requestBody)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                //throw new IOException("Unexpected code " + response);
            }
            System.out.println("response = " + response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testGetPost() {


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url("http://localhost:8090/apis/content.halo.run/v1alpha1/posts")
            .get()
            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
            .header("Accept-Language", "en-US,en;q=0.9,zh-TW;q=0.8,zh-CN;q=0.7,zh;q=0.6")
            .header("Cache-Control", "no-cache")
            .header("Connection", "keep-alive")
            .header("Cookie", "Phpstorm-fa8a895e=01f52b21-19a2-4afb-ac2c-a1fe67be5419; ajs_anonymous_id=ac870643-786a-4b64-a970-9ab385f09489; Webstorm-f05af1c1=ce7ccc04-61d0-49d1-a2ae-0ee760767673; locale=en-us; Pycharm-c69fede8=7b331d56-8843-4340-8a11-8537610457e3; deviceId=f1bd74b0061b74973df58af84da3aa62; Webstorm-f05af1c2=3761932d-d8bc-4c7d-99ea-4fb55c5ec5a7; XSRF-TOKEN=bb298ce6-5646-4432-9567-0733d9eaff18; remember-me=NkMwak9sVHBiQk5SUENobHRYMmdudyUzRCUzRDpqU1UlMkJ1RXVwWUpuWW8lMkZDWEtYV2clMkJBJTNEJTNE; device_id=5d056d81bb394f2aab54fe1469ae0d76; SESSION=d74dc968-cf8e-4a3b-8c9e-5dcbce18481b")
            .header("Pragma", "no-cache")
            .header("Sec-Fetch-Dest", "document")
            .header("Sec-Fetch-Mode", "navigate")
            .header("Sec-Fetch-Site", "none")
            .header("Sec-Fetch-User", "?1")
            .header("Upgrade-Insecure-Requests", "1")
            .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
            .header("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
            .header("sec-ch-ua-mobile", "?0")
            .header("sec-ch-ua-platform", "\"macOS\"")
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println("response = " + response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
