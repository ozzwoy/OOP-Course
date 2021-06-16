package cyb.periodicals.servlets;

import cyb.periodicals.entities.user.Role;
import cyb.periodicals.entities.user.User;
import cyb.periodicals.services.UserService;
import cyb.periodicals.servlets.utils.JSONConverter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static class LoginRequest {
        public String username;
        public String password;
    }

    private static class LoginResponse {
        public String token;
        public Role role;

        public LoginResponse(String token, Role role) {
            this.token = token;
            this.role = role;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            LoginRequest loginRequest = JSONConverter.readObjectFromJSONRequest(request, LoginRequest.class);
            User user = UserService.getInstance().loginUser(loginRequest.username, loginRequest.password);

            if (user == null) {
                JSONConverter.writeObjectToJSONResponse(new LoginResponse("", null), response);
            } else {
                JSONConverter.writeObjectToJSONResponse(new LoginResponse(
                        user.getId().toString(), user.getRole()), response);
            }
        } catch (Exception e) {
            response.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR, "Server error");
        }
    }
}
