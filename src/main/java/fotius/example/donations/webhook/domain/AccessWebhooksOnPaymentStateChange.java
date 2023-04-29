package fotius.example.donations.webhook.domain;

import fotius.example.donations.payment.domain.PaymentChangeListener;
import fotius.example.donations.payment.domain.model.Payment;
import fotius.example.donations.webhook.domain.model.Webhook;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

@Component
@AllArgsConstructor
public class AccessWebhooksOnPaymentStateChange implements PaymentChangeListener {

    private final WebhookService webhookService;

    @Override
    public void onChanged(Payment changed) {
        webhookService
                .getWithMethodAndState(changed.getMethod(), changed.getState())
                .forEach(webhook -> {
                    System.out.println("Payment " + changed.getId() + " changed. "
                            + "Payment method: " + changed.getMethod() + "; "
                            + "Payment state: " + changed.getState() + ";");
////                    Or:
//                    try {
//                        sendPOST(webhook, "Payment " + changed.getId() + " changed. " +
//                                "Payment method: " + changed.getMethod() + "; " +
//                                "Payment state: " + changed.getState() + ";");
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                });
    }

    private static void sendPOST(Webhook webhook, String message) throws IOException {
        HttpURLConnection con = (HttpURLConnection) webhook.getTargetUrl().openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(("message=" + message).getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response);
        } else {
            System.out.println("POST request did not work.");
        }
    }
}
