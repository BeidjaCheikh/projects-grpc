package ma.enset.Clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

import java.io.IOException;

public class BankGrpcClient2 {

        public static void main(String[] args) throws IOException {
            // client GRPC
            ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",500)
                    // pour la negotiation entre le client GRPC et le serveur
                    .usePlaintext()
                    .build();
            // creation de stub de type no bloquant Asynchron
            BankServiceGrpc.BankServiceStub asyncStub=BankServiceGrpc.newStub(managedChannel);
            Bank.ConvertCurrencyRequest request =Bank.ConvertCurrencyRequest.newBuilder()
                    .setCurrencyFrom("MAD")
                    .setCurrencyTo("USD")
                    .setAmount(123)
                    .build();
            //bloquant
           asyncStub.convert(request, new StreamObserver<Bank.ConvertCurrencyReponse>() {
               @Override
               public void onNext(Bank.ConvertCurrencyReponse convertCurrencyReponse) {
                   System.out.println("********************************");
                   System.out.println(convertCurrencyReponse);
                   System.out.println("********************************");
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
           System.out.println(".......?");
           System.in.read();


        }

}
