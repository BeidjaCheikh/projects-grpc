package ma.enset.Clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class BankGrpcClient4 {

        public static void main(String[] args) throws IOException {
            // client GRPC
            ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",500)
                    // pour la negotiation entre le client GRPC et le serveur
                    .usePlaintext()
                    .build();
            // creation de stub de type no bloquant Asynchron
            BankServiceGrpc.BankServiceStub asyncStub=BankServiceGrpc.newStub(managedChannel);

            // no bloquant
            // jenvoie la requete et j'attend la reponse
            StreamObserver<Bank.ConvertCurrencyRequest> performStream =
                    asyncStub.performStream(new StreamObserver<Bank.ConvertCurrencyReponse>() {
                @Override
                public void onNext(Bank.ConvertCurrencyReponse convertCurrencyReponse) {
                System.out.println("================================");
                System.out.println(convertCurrencyReponse);
                System.out.println("================================");
                }

                @Override
                public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                System.out.println("END");
                }
            });
            Timer timer=new Timer();
            timer.schedule(new TimerTask() {
                int conter=0;
                @Override
                public void run() {
                    Bank.ConvertCurrencyRequest currencyRequestrequest= Bank.ConvertCurrencyRequest.newBuilder()
                            .setAmount(Math.random()*700)
                            .build();
                    performStream.onNext(currencyRequestrequest);
                    System.out.println("======= conter"+conter);
                    ++conter;
                    if (conter==20){
                        performStream.onCompleted();
                        timer.cancel();
                    }
                }
            }, 1000, 1000);
            System.out.println(".......?");
            System.in.read();


        }

}
