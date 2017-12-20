package tr.exemple.demo.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Servlet implementation class Ajax
 */
@WebServlet("/ajax")
public class Ajax extends HttpServlet {
    private static final long serialVersionUID = 1L;
    final static Logger log = Logger.getLogger(Ajax.class);

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* 1. Récupère les données JSON de la requête Ajax */
        // BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        // String json = "";
        // if (br != null) {
        // json = br.readLine();
        // }
        // log.trace(json);
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");
        HashMap<String, String> hmap = new HashMap<String, String>();
        hmap.put("email", email);
        hmap.put("motDePasse", motDePasse);

        /* 2. Convertie le hasmpa au format json */
        String json = new Gson().toJson(hmap);
        log.trace(json);

        // /* 2. Initialise jackson mapper */
        // ObjectMapper mapper = new ObjectMapper();
        //
        // /* 3. Converti les données JSON reçues en bean Utilisateur */
        // Utilisateur utilisateur = mapper.readValue(json, Utilisateur.class);
        // log.trace(utilisateur);

        /* 4. Definit une réponse au format JSON et encoder UTF8 */
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        /* Envoie Utilisateur au format JSON à la réponse Ajax */
        // mapper.writeValue(response.getOutputStream(), utilisateur);
        response.getWriter().write(json);

    }

}
