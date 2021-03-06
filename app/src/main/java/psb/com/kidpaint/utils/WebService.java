package psb.com.kidpaint.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import psb.com.kidpaint.App;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebService {
    public static final String BASE_URL = "http://www.getboomboom.ir/api/";
    private static Retrofit retrofit;
    private static UserProfile userProfile;

    public static Retrofit getClient() {
        userProfile = new UserProfile(App.getContext());
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        if (userProfile.get_KEY_JWT( "-1")!=null && !userProfile.get_KEY_JWT( "-1").equals("-1")) {
                            ongoing.addHeader("Authorization", "Bearer "+userProfile.get_KEY_JWT("-1"));
                        }
                        ongoing.addHeader("DeviceId", Utils.getDeviceId(App.getContext()));
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public Retrofit getRetrofitNoLog() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

}