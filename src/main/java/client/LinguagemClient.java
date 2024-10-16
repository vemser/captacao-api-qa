//package client;
//
//import io.restassured.response.Response;
//import models.linguagem.LinguagemModel;
//import utils.auth.Auth;
//
//import static io.restassured.RestAssured.given;
//
//public class LinguagemClient extends BaseClient {
//    private static final String AUTHORIZATION = "Authorization";
//    public static final String LINGUAGEM = "/linguagem";
//    public static final String LINGUAGEM_DELETE_FISICO_ID_LINGUAGEM = "/linguagem/delete-fisico/{idLinguagem}";
//    public static final String NOME = "nome";
//    public static final String ID_LINGUAGEM = "idLinguagem";
//
//    public Response criarLinguagemPassandoNome(String nomeDaLinguagem) {
//        Auth.usuarioGestaoDePessoas();
//
//        return
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                        .queryParam(NOME, nomeDaLinguagem)
//                .when()
//                        .post(LINGUAGEM)
//                ;
//    }
//
//    public Response criarLinguagemPassandoNomeSemAutenticacao(String nomeDaLinguagem) {
//        Auth.usuarioAluno();
//        return
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                        .queryParam(NOME, nomeDaLinguagem)
//                .when()
//                        .post(LINGUAGEM)
//                ;
//    }
//
//    public Response deletarLinguagemPorId(Integer idLinguagem) {
//        Auth.usuarioGestaoDePessoas();
//
//        return
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                        .pathParam(ID_LINGUAGEM, idLinguagem)
//                .when()
//                        .delete(LINGUAGEM_DELETE_FISICO_ID_LINGUAGEM)
//                ;
//    }
//
//    public Response deletarLinguagemPorIdSemAutenticacao(Integer idLinguagem) {
//        Auth.usuarioAluno();
//        return
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                        .pathParam(ID_LINGUAGEM, idLinguagem)
//                .when()
//                        .delete(LINGUAGEM_DELETE_FISICO_ID_LINGUAGEM)
//                ;
//    }
//
//    public Response listarLinguagens() {
//        Auth.usuarioGestaoDePessoas();
//
//        return
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                .when()
//                        .get(LINGUAGEM)
//                ;
//    }
//
//    public Response listarLinguagensSemAutenticacao() {
//        Auth.usuarioAluno();
//        return
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                .when()
//                        .get(LINGUAGEM)
//                ;
//    }
//
//    public LinguagemModel retornarPrimeiraLinguagemCadastrada() {
//        Auth.usuarioGestaoDePessoas();
//
//        Response response =
//                given()
//                        .spec(super.setUp())
//                        .header(AUTHORIZATION, AuthClient.getToken())
//                .when()
//                        .get(LINGUAGEM)
//                        .thenReturn()
//                ;
//
//        LinguagemModel[] linguagens = response.as(LinguagemModel[].class);
//        return linguagens[0];
//    }
//
//}