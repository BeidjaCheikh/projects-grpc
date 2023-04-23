package ma.enset.Clients;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ma.enset.stubs.Bank;
import ma.enset.stubs.BankServiceGrpc;

public class BankGrpcClient1 {

        public static void main(String[] args) {
            // client GRPC
            ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",500)
                    // pour la negotiation entre le client GRPC et le serveur
                    .usePlaintext()
                    .build();
            // creation de stub de type bloquant Synchrone
            BankServiceGrpc.BankServiceBlockingStub blockingStub=BankServiceGrpc.newBlockingStub(managedChannel);
            Bank.ConvertCurrencyRequest request =Bank.ConvertCurrencyRequest.newBuilder()
                    .setCurrencyFrom("MAD")
                    .setCurrencyTo("USD")
                    .setAmount(123)
                    .build();
            //bloquant
           Bank.ConvertCurrencyReponse convertCurrencyReponse= blockingStub.convert(request);
           System.out.println(convertCurrencyReponse);

        }

}
