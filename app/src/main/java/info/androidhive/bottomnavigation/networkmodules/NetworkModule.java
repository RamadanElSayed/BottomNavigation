package info.androidhive.bottomnavigation.networkmodules;

import android.os.Build;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


public class NetworkModule {

    public static Retrofit provideRetrofit() {

        OkHttpClient okHttpClient=getNewHttpClient(loggingInterceptor());
        Gson gson=   provideGson();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.androidhive.info/json/")
                .client(okHttpClient)
                .build();
    }

    private static HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    private static OkHttpClient getNewHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);


        return enableTls12OnPreLollipop(client).build();

    }

    /**
     * Enables TLSv1.2 protocol (which is disabled by default)
     * on pre-Lollipop devices, as well as on Lollipop, because some issues can take place on Samsung devices.
     *
     * @param client OKHtp client builder
     * @return
     */
//        private static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
//                try {
//                    SSLContext sc = SSLContext.getInstance("TLSv1.2");
//                    sc.init(null, null, null);
//                    client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));
//client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()),)
//                    ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                            .tlsVersions(TlsVersion.TLS_1_2)
//                            .build();
//
//                    List<ConnectionSpec> specs = new ArrayList<>();
//                    specs.add(cs);
//                    specs.add(ConnectionSpec.COMPATIBLE_TLS);
//                    specs.add(ConnectionSpec.CLEARTEXT);
//
//                    client.connectionSpecs(specs);
//                } catch (Exception ignored) {
//                }
//            }
//            return client;
//        }

    private static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT <  Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);

                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                        TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:"
                            + Arrays.toString(trustManagers));
                }
                X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()), trustManager);

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }

    private static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

}
