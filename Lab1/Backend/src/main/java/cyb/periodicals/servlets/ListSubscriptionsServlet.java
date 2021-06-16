package cyb.periodicals.servlets;

import cyb.periodicals.entities.subscription.Subscription;
import cyb.periodicals.services.exceptions.ServiceException;
import cyb.periodicals.services.SubscriptionService;
import cyb.periodicals.servlets.utils.JSONConverter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

@WebServlet(urlPatterns = "/subscriptions")
public class ListSubscriptionsServlet extends HttpServlet {
    private static class SubscriptionsRequest {
        public String token;
    }

    private static class SubscriptionsResponse {
        public List<Subscription> subscriptions;

        public SubscriptionsResponse(List<Subscription> subscriptions) {
            this.subscriptions = subscriptions;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            SubscriptionsRequest subscriptionsRequest = JSONConverter.readObjectFromJSONRequest(
                    request, SubscriptionsRequest.class);
            List<Subscription> subscriptions = SubscriptionService.getInstance().findAllSubscriptionsByUserAndStatus(
                    Long.parseLong(subscriptionsRequest.token), true);

            JSONConverter.writeObjectToJSONResponse(new SubscriptionsResponse(subscriptions), response);
        } catch (ServiceException e) {
            response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Server error");
        }
    }
}
