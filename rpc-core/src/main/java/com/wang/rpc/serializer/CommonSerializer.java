package com.wang.rpc.serializer;

/**
 * 通用的序列化反序列化接口
 *
 * @author C.Wang
 * @CreateTime 2022/4/27 22:07
 */


public interface CommonSerializer {

    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERIALIZER = 2;
    Integer PROTOBUF_SERIALIZER = 3;

    Integer DEFAULT_SERIALIZER = KRYO_SERIALIZER;

    //根据编号获取序列化器
    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();//Kryo,作为默认序列化器
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();

            default:
                return null;
        }
    }

    //序列化
    byte[] serialize(Object obj);

    //反序列化
    Object deserialize(byte[] bytes, Class<?> clazz);

    //获得该序列化器的编号
    int getCode();
}
