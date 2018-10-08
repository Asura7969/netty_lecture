package com.gwz.grpc;

import com.gwz.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase{

    @Override
    public void getRealNameByUserName(MyRequest request, StreamObserver<MyRespones> responseObserver) {
        System.out.println("接受客户端信息:" + request.getUsername());
        responseObserver.onNext(MyRespones.newBuilder().setRealname("张三").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStidentsByAge(StudentRequest request, StreamObserver<StudentRespones> responseObserver) {
        System.out.println("接受客户端信息:" + request.getAge());

        responseObserver.onNext(StudentRespones.newBuilder().setName("张三").setAge(20).setCity("常州").build());
        responseObserver.onNext(StudentRespones.newBuilder().setName("李四").setAge(20).setCity("上海").build());
        responseObserver.onNext(StudentRespones.newBuilder().setName("王五").setAge(20).setCity("北京").build());
        responseObserver.onNext(StudentRespones.newBuilder().setName("赵六").setAge(20).setCity("深圳").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWapperByAges(StreamObserver<StudentResponesList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext:" + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentRespones build1 = StudentRespones.newBuilder().setName("战三").setAge(20).setCity("武汉").build();
                StudentRespones build2 = StudentRespones.newBuilder().setName("战二").setAge(20).setCity("武汉2").build();
                StudentResponesList responesList = StudentResponesList.newBuilder().addStudentRespones(build1).addStudentRespones(build2).build();

                responseObserver.onNext(responesList);
                responseObserver.onCompleted();

            }
        };
    }

    @Override
    public StreamObserver<StreamRespones> biTalk(StreamObserver<StreamRespones> responseObserver) {
        return new StreamObserver<StreamRespones>() {
            @Override
            public void onNext(StreamRespones value) {
                System.out.println(value.getResponesInfo());

                responseObserver.onNext(StreamRespones.newBuilder().setResponesInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
