package ma.enset.service;

import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcService extends BankServiceGrpc.BankServiceImplBase /*herite de linterface qui contient les methodes*/ {
    @Override
    public void convert(Bank.ConvertCurrencyRequest request, StreamObserver<Bank.ConvertCurrencyReponse> responseObserver) {

        String currencyFrom=request.getCurrencyFrom();
        String currencyTo= request.getCurrencyTo();
        double amount= request.getAmount();
        Bank.ConvertCurrencyReponse reponse= Bank.ConvertCurrencyReponse.newBuilder()
                .setCurrencyFrom(currencyFrom)
                .setCurrencyTo(currencyTo)
                .setAmount(amount)
                .setResult(amount*14.5)
                .build();
        responseObserver.onNext(reponse);// pour envoyer la reponse
        responseObserver.onCompleted();// pour informer le client qu'il ce termine


    }

    @Override
    public void getCurrencySteam(Bank.ConvertCurrencyRequest request, StreamObserver<Bank.ConvertCurrencyReponse> responseObserver) {
        String currencyFrom=request.getCurrencyFrom();
        String currencyTo= request.getCurrencyTo();
        double amount= request.getAmount();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            int conter=0;
            @Override
            public void run() {
                Bank.ConvertCurrencyReponse reponse= Bank.ConvertCurrencyReponse.newBuilder()
                        .setCurrencyFrom(currencyFrom)
                        .setCurrencyTo(currencyTo)
                        .setAmount(amount)
                        .setResult(amount*Math.random()*100)
                        .build();
                responseObserver.onNext(reponse);
                ++conter;
                if (conter==20) {
                    responseObserver.onCompleted();
                    timer.cancel();
                }
            }
        }, 1000, 1000);
    }

    @Override
    public StreamObserver<Bank.ConvertCurrencyRequest> performStream(StreamObserver<Bank.ConvertCurrencyReponse> responseObserver) {
       return new StreamObserver<Bank.ConvertCurrencyRequest>() {
           double sum=0;
           @Override
           public void onNext(Bank.ConvertCurrencyRequest convertCurrencyRequest) {
               sum+=convertCurrencyRequest.getAmount();
           }

           @Override
           public void onError(Throwable throwable) {

           }

           @Override
           public void onCompleted() {
               Bank.ConvertCurrencyReponse reponse= Bank.ConvertCurrencyReponse.newBuilder()
                       .setResult(sum*11.4)
                       .build();
               responseObserver.onNext(reponse);
               responseObserver.onCompleted();
           }
       };
    }

    @Override
    public StreamObserver<Bank.ConvertCurrencyRequest> fullCurrencyStream(StreamObserver<Bank.ConvertCurrencyReponse> responseObserver) {
      return new StreamObserver<Bank.ConvertCurrencyRequest>() {
          @Override
          public void onNext(Bank.ConvertCurrencyRequest convertCurrencyRequest) {
              Bank.ConvertCurrencyReponse reponse= Bank.ConvertCurrencyReponse.newBuilder()
                      .setResult(convertCurrencyRequest.getAmount()*Math.random()*40)
                      .build();
              responseObserver.onNext(reponse);
          }

          @Override
          public void onError(Throwable throwable) {

              System.out.println(throwable.getMessage());
          }

          @Override
          public void onCompleted() {
               responseObserver.onCompleted();
          }
      };
    }
}
