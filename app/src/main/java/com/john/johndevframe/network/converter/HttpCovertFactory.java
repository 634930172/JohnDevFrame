package com.john.johndevframe.network.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Author: ${John}
 * E-mail: 634930172@qq.com
 * Date: 2017/12/5 0005
 * <p/>
 * Description:
 */

public class HttpCovertFactory extends Converter.Factory {

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static HttpCovertFactory create() {
        return create(new GsonBuilder().serializeNulls().create());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static HttpCovertFactory create(Gson gson) {
        return new HttpCovertFactory(gson);
    }

    private final Gson gson;

    private HttpCovertFactory(Gson gson) {
        if (gson == null)
            throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new RxResponseBodyConverter<>(gson, type);
        //        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        //        return new HttpResponseBodyConverter<>(gson, adapter);

    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new RxRequestBodyConverter<>(gson, adapter);
        //        return new HttpRequestBodyConverter<>(gson, adapter);
    }


}
