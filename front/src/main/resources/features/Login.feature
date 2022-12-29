#language:pt

@test
Funcionalidade: Login

  Contexto: O candidato deve logar no sistema

  @pendente
  Cenario: Validar login com sucesso
    Dado que acesso a página de Login
    E preencho todos os campos válidos de Login
    Quando clico em 'LOGIN'
    Então devo estar logado no sistema e ser redirecionado para a página de Candidatos