package cyb.periodicals.servlets;

import cyb.periodicals.entities.periodical.Periodical;
import cyb.periodicals.entities.subscription.Subscription;
import cyb.periodicals.services.SubscriptionService;
import cyb.periodicals.services.exceptions.ServiceException;
import cyb.periodicals.servlets.utils.JSONConverter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

@WebServlet(urlPatterns = "/cart")
public class CartServlet extends HttpServlet {

    private static class GetCartItemsRequest {
        public String token;
    }

    private static class GetCartItemsResponse {
        public final List<Periodical> periodicals;

        public GetCartItemsResponse(List<Periodical> periodicals) {
            this.periodicals = periodicals;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            GetCartItemsRequest subscriptionsRequest = JSONConverter.readObjectFromJSONRequest(
                    request, GetCartItemsRequest.class);
            List<Subscription> subscriptions = SubscriptionService.getInstance().findAllSubscriptionsByUserAndStatus(
                    Long.parseLong(subscriptionsRequest.token), false);


        } catch (ServiceException e) {
            response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
