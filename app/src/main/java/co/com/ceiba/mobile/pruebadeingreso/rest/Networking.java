package co.com.ceiba.mobile.pruebadeingreso.rest;
import android.os.Handler;
import android.os.Looper;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Networking {
    private static volatile Networking instance;
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Networking()
    {
        if (instance != null)
        {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }
    public static void get(String url, String token, ICallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();

        excecute(request, callback);
    }
    public static void excecute(Request request, final ICallback callback) {
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                final String error = e.getMessage();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(error);
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String resp = response.body().string();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(resp);
                        }
                    });
                } else {
                    String remoteResponse = response.body().string();
                    try {
                        JSONObject json = new JSONObject(remoteResponse);
                        final String error = json.getString("data");

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFail(error);
                            }
                        });
                    } catch (final JSONException e) {
                        //callback.onFail("SYSTEM", e.getMessage());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onFail(e.getMessage());
                            }
                        });
                    }

                }
            }
        });
    }
    public interface ICallback {
        void onFail(String error);
        void onSuccess(String response);
    }
}
