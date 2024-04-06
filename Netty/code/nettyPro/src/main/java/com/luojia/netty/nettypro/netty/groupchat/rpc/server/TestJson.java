package com.luojia.netty.nettypro.netty.groupchat.rpc.server;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TestJson {

    public static void main(String[] args) {

//        final String s = new Gson().toJson(String.class);
//        System.out.println(s);

        final Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
        System.out.println(gson.toJson(String.class));

    }

    static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>>{
        @Override
        public Class<?> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                final String str = json.getAsString();
                return Class.forName(str);

            } catch (ClassNotFoundException e) {

                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Class<?> src, Type type, JsonSerializationContext jsonSerializationContext) {

            // JsonPrimitive 转化基本数据类型
            return new JsonPrimitive(src.getName());
        }
    }

}
