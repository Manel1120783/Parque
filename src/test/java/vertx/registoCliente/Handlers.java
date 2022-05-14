package vertx.registoCliente;

import vertx.registoCliente.Registo;
import vertx.registoCliente.Repository;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import static io.vertx.ext.web.handler.StaticHandler.DEFAULT_WEB_ROOT;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Misterio
 */
class Handlers {

    List<Registo> BDfalsa = new ArrayList<>();
    String webRoot = DEFAULT_WEB_ROOT;

    Repository repo;
    public Handlers(Repository repo) {
        this.repo = repo;
    }

    //Nota: para alguns dos métodos foi necessário incluir a biblioteca Jackson no pom.xmml
    
    // responde ao pedido get
    public void sendStringJson(RoutingContext rc) {
        String jsonResult = "[ {\"nome\": \"1.Manuel\"}, "
                + "{\"apelido\": \"2.Pereira\"} ]";
        rc.response().headers().set("Content-Type", "application/json; charset=UTF-8");
        rc.response().end(jsonResult);
    }
    
    // responde ao pedido get - alternativa ao anterior
    public void sendArrayAsString(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain; charset=utf-8");
        List<Registo> registos = new ArrayList<>();
        registos.add(new Registo("Manuel", "nome", 1));
        registos.add(new Registo("Sampaio", "apelido", 2));
        response.setStatusCode(200);
        response.end(registos.toString());
     
    }


    public void criarRegisto(RoutingContext rc) {
// Solucao1: requer a biblioteca Jackson - dependencias no pom.xml
        JsonObject body = rc.getBodyAsJson();
        String nome = body.getString("nome");
        String apelido = body.getString("apelido");
        String email = body.getString("email");
        String NIF =  body.getString("NIF");
        String contacto =  body.getString("contacto");
        String QR =  body.getString("QR");
        
        Registo aluno = new Registo(nome, apelido, email, Long.parseLong(NIF), contacto, Long.parseLong(QR));
        Registo registo = null;

        // insere na BD falsa
        BDfalsa.add(registo);
        System.out.println("registo criado: " + registo.toString());
        rc.response()
                .setStatusCode(201)
                .putHeader("content-type",
                        "application/json; charset=utf-8")
                .end(Json.encodePrettily(aluno));
    }

    public void actualizarRegisto(RoutingContext rc) {
        //requer a biblioteca Jackson - dependencias no pom
        String Id = rc.request().getParam("IdRegisto");
        Registo registo = rc.getBodyAsJson().mapTo(Registo.class);

        int indice = -1;
        for (int i = 0; i < BDfalsa.size(); i++) {
            if (BDfalsa.get(i).Id == Integer.parseInt(Id)) {
                indice = i;
            }
        }
        if (indice == -1) {
            System.out.println("inexistente");
        } else {
            BDfalsa.set(indice, registo);
            System.out.println("Registo actualizado: " + registo.toString());
        }

        rc.response()
                .setStatusCode(201)
                .putHeader("content-type",
                        "application/json; charset=utf-8")
                .end(Json.encodePrettily(registo));
    }

    // ressponde a pedido get the uma página nova (estática)
    public void paginaNova(RoutingContext rc) {
        rc.response().setStatusCode(200)
                .putHeader("content-type", "text/html")
                .sendFile(webRoot + "/html/nova1.html");

    }
        // ressponde a pedido get the uma página nova (estática)
    public void paginaNova2(RoutingContext rc) {
        rc.response().setStatusCode(200)
                .putHeader("content-type", "text/html")
                .sendFile(webRoot + "/html/nova2.html");

    }

}
