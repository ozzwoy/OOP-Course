package cyb.periodicals.servlets.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public abstract class JSONConverter {
    private static final Logger logger = Logger.getLogger(JSONConverter.class.getName());

    public static void writeObjectToJSONResponse(Object object, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new ObjectMapper().writeValueAsString(object);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    public static <T> T readObjectFromJSONRequest(HttpServletRequest request, Class<T> tClass) throws IOException {
        try {
            return new ObjectMapper().readValue(request.getReader(), tClass);
        } catch (IOException e) {
            logger.error("Error while parsing JSON", e);
            throw e;
        }
    }
}
