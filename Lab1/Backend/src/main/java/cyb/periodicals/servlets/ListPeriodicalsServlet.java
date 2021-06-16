package cyb.periodicals.servlets;

import cyb.periodicals.entities.periodical.Periodical;
import cyb.periodicals.services.PeriodicalService;
import cyb.periodicals.services.SubscriptionService;
import cyb.periodicals.servlets.utils.JSONConverter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

@WebServlet(urlPatterns = "/periodicals")
public class ListPeriodicalsServlet extends HttpServlet {

    private static class AddPeriodicalToCartRequest {
        public String token;
        public long periodicalId;
    }

    private static class GetAllPeriodicalsResponse {
        public final List<Periodical> periodicals;

        public GetAllPeriodicalsResponse(List<Periodical> periodicals) {
            this.periodicals = periodicals;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Periodical> periodicals = PeriodicalService.getInstance().findAllPeriodicals();
            JSONConverter.writeObjectToJSONResponse(new GetAllPeriodicalsResponse(periodicals), response);
        } catch (Exception e) {
            response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Server error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            AddPeriodicalToCartRequest toCartRequest = JSONConverter.readObjectFromJSONRequest(
                    request, AddPeriodicalToCartRequest.class);

            SubscriptionService.getInstance().prepareSubscription(
                    Long.parseLong(toCartRequest.token), toCartRequest.periodicalId);
        } catch (Exception e) {
            response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Server error");
        }
    }
}
