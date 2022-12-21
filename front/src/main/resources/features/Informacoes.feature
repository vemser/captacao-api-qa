#language:pt

@test
Funcionalidade: Informações

  Contexto: O usuário deve cadastrar suas informações no sistema

  @smoke
  Cenario: Validar cadastro de informações com sucesso
    Dado que acesso a página de Informações
    E preencho todos os campos válidos
    Quando clico em 'Enviar'
    Então devo ser redirecionado para a página de Formulário