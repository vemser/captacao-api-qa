#language:pt

@test
Funcionalidade: Home

  Contexto: O usuario deve acessar a página de inscrição

  @smoke
  Cenario: Validar botão de inscrição com sucesso
    Dado que estou na página inicial
    Quando clico em ‘Inscrição’
    Então devo ser redirecionado para a página de Informações

  @smoke
  Cenario: Validar botão de logar comon administrador com sucesso
    Dado que estou na página inicial
    Quando clico em ‘ENTRAR COMO ADMINISTRADOR’
    Então devo ser redirecionado para a página de Login