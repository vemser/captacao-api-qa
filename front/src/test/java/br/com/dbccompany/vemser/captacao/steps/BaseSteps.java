package br.com.dbccompany.vemser.captacao.steps;

import br.com.dbccompany.vemser.captacao.utils.Browser;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BaseSteps extends Browser {

    @Before
    public void abrirNavegador(){
        browserUp("https://captacao-front.vercel.app/");
        //browserUp("http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-front");
    }

    @After
    public void fecharNavegador(){
        browserDown();
    }

}
