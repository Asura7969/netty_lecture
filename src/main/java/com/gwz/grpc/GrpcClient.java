package com.gwz.grpc;

import com.gwz.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;

public class GrpcClient {
    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext(true).build();

        StudentServiceGrpc.StudentServiceBlockingStub newBlockingStub = StudentServiceGrpc.newBlockingStub(managedChannel);
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);

        MyRespones zhangsan = newBlockingStub.getRealNameByUserName(MyRequest.newBuilder().setUsername("zhangsan").build());
        System.out.println(zhangsan.getRealname());

        System.out.println("----------------------------------");

        Iterator<StudentRespones> iterator = newBlockingStub.getStidentsByAge(StudentRequest.newBuilder().setAge(20).build());
        while (iterator.hasNext()) {
            StudentRespones respones = iterator.next();
            System.out.println(respones.getName() + "," + respones.getAge() + "," + respones.getCity());
        }

        System.out.println("----------------------------------");

        StreamObserver<StudentResponesList> studentResponesListStreamObserver = new StreamObserver<StudentResponesList>(){

            @Override
            public void onNext(StudentResponesList value) {
                value.getStudentResponesList().forEach(studentRespones -> {
                    System.out.println(studentRespones.getName());
                    System.out.println(studentRespones.getAge());
                    System.out.println(studentRespones.getCity());
                    System.out.println("*****");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("completed!");
            }
        };

        StreamObserver<StudentRequest> studentsWapperByAges = stub.getStudentsWapperByAges(studentResponesListStreamObserver);
        studentsWapperByAges.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentsWapperByAges.onNext(StudentRequest.newBuilder().setAge(60).build());
        studentsWapperByAges.onNext(StudentRequest.newBuilder().setAge(50).build());
        studentsWapperByAges.onNext(StudentRequest.newBuilder().setAge(40).build());
        studentsWapperByAges.onNext(StudentRequest.newBuilder().setAge(30).build());

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        studentsWapperByAges.onCompleted();


        System.out.println("----------------------------------");


        StreamObserver<StreamRespones> streamResponesStreamObserver = stub.biTalk(new StreamObserver<StreamRespones>() {
            @Override
            public void onNext(StreamRespones value) {
                System.out.println(value.getResponesInfo());
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted!");
            }
        });

        for (int i = 0; i < 10; i++) {
            streamResponesStreamObserver.onNext(StreamRespones.newBuilder().setResponesInfo(LocalDateTime.now().toString()).build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
