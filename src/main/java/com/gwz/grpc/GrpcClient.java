package com.gwz.grpc;

import com.gwz.proto.MyRequest;
import com.gwz.proto.MyRespones;
import com.gwz.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899)
                .usePlaintext(true).build();

        StudentServiceGrpc.StudentServiceBlockingStub newBlockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        MyRespones zhangsan = newBlockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("zhangsan").build());
        System.out.println(zhangsan.getRealname());
    }
}
