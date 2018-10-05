package com.gwz.grpc;

import com.gwz.proto.MyRequest;
import com.gwz.proto.MyRespones;
import com.gwz.proto.StudentServiceGrpc;
import io.grpc.stub.StreamObserver;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyRespones> responseObserver) {
        System.out.println("接受客户端信息:" + request.getUsername());
        responseObserver.onNext(MyRespones.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }
}
