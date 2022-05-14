
package vertx.registoCliente;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import static io.vertx.ext.web.handler.StaticHandler.DEFAULT_WEB_ROOT;

/**
 *
 * @author Misterio
 */
public class VerticleRSJson extends AbstractVerticle {

    String webRoot = DEFAULT_WEB_ROOT;
    Router router;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
              
        Repository repo = new Repository();
        Handlers handlers = new Handlers(repo);
        router = routes(handlers);
        
        HttpServerOptions options = new HttpServerOptions();
        options.setHost("127.0.0.1").setPort(8080);

        vertx.createHttpServer(options)
            .requestHandler(router) //usa o router para manipular qualquer pedido
            .listen(res -> {
                  if (res.succeeded()) {               
                      startPromise.complete();
                      System.out.println("Servidor HTTP no porto " + options.getPort());
                  } else {               
                      startPromise.fail(res.cause());
                      System.out.println("Nao foi possivel iniciar o servidor HTTP");
                  }
          });
    }

    private Router routes(Handlers handlers) {
        router = Router.router(vertx);

        router.route().handler(StaticHandler.create().setWebRoot(webRoot));
        // serve index
        router.route("/").handler(StaticHandler.create(webRoot));


        router.route(HttpMethod.GET, "/registosJson").handler(handlers::sendStringJson);
        router.route(HttpMethod.GET, "/registosString").handler(handlers::sendArrayAsString);
        router.route(HttpMethod.GET, "/paginaNova")
                .handler(handlers::paginaNova);//ou StaticHandler.create(webRoot + "/html/nova1.html"));
        router.route(HttpMethod.GET, "/paginaNova2")
                .handler(handlers::paginaNova2);//ou StaticHandler.create(webRoot + "/html/nova1.html"));

        
        // ATENÇÃO: necessário usar "bodyHandler" quando se pretende ler o body do pedido
        //  - nos POST seguintes o body contém os dados do aluno
        router.route("/registos/*").handler(BodyHandler.create());
        // criarALuno() e actualizarALuno() estão na classe Handlers
        router.route(HttpMethod.POST, "/registos").handler(handlers::criarRegisto);
        router.route(HttpMethod.PUT, "/registos/:IdRegisto").handler(handlers::actualizarRegisto);

        return router;
    }


}
