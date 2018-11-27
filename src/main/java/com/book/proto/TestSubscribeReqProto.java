package com.book.proto;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestSubscribeReqProto {

    private static byte[] encode(SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReq.parseFrom(body);
    }

    private static SubscribeReq createSubscribeReq(){
        SubscribeReq.Builder builder = SubscribeReq.newBuilder();

        builder.setSubReqID(1);
        builder.setUserName("zhangsan");
        builder.setProductName("Netty Book");

//        List<String> address = new ArrayList<>();
//        address.add("NanJing YuHuaTai");
//        address.add("ShangHai WaiTan");
//        address.add("ShenZhen FuTian");
        builder.setAddress("ShenZhen");
        return builder.build();

    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req.toString());
        SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode : " + req.toString());
        System.out.println("Assert equal : --> " + req2.equals(req));

    }

}
