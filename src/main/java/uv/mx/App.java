package uv.mx;

import static spark.Spark.*;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.UUID;
/**
 * Hello world!
 *
 */
public class App 
{
    static HashMap<String,Usuario> usuarios = new HashMap<>();
    static Gson gson = new Gson();
    public static void main( String[] args )
    {
        port(getHerokuAssignedPort());
        get("/usuario", (req,res)->{
            res.type("application/json");
            // return gson.toJson(usuarios.values());
            return gson.toJson(DAO.getAllUsuarios());
        });
        get("/usuario/byId", (req,res)->{
            String id = req.queryParams("id");
            res.type("application/json");
            // return gson.toJson(usuarios.values());
            return gson.toJson(DAO.GetUsuariosFromId(id));
        });
        post("/usuario", (req,res)->{
            Usuario user = gson.fromJson(req.body(), Usuario.class);
            String id = UUID.randomUUID().toString();
            user.setId(id);
            usuarios.put(id, user);
            DAO.createUsuario(user);
            // System.out.println("i:"+user.getId());
            // res.type("application/json");
            res.type("application/json");
            res.status(200);
            return gson.toJson(id);

         });

         delete("/usuario/:id", (req, res) -> {
            // String id = req.queryParams("id");
            String id = req.params(":id");
            // String id = gson.fromJson(req.body(), String.class);
            Usuario u = DAO.deleteUsuario(id);
            res.type("application/json");
            if (u!=null) {
                res.status(200);
                return gson.toJson(u);                
            }else{
                res.status(404);
                JsonObject r = new JsonObject();
                r.addProperty("error", "Error al eliminar el usuario");
                return r;
            }
            // return gson.toJson(DAO.GetUsuariosFromId(id));
         });
         patch("/usuario", (req, res) -> {
            Usuario user = gson.fromJson(req.body(), Usuario.class);
            res.type("application/json");
            return gson.toJson( DAO.modifyUsuario(user));
         });
    }
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
