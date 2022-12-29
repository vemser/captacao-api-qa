#language:pt

@test
Funcionalidade: Informações

  Contexto: O candidato deve cadastrar suas informações no sistema

  @smoke
  Cenario: Validar cadastro de informações com sucesso
    Dado que acesso a página de Inscrição
    E preencho todos os campos válidos de Informações
    Quando clico em 'Próximo'
    Então devo ser redirecionado para a página de Formulário

  @smoke
  Cenario: Validar tentativa de cadastro de informações sem preencher campos obrigatórios
    Dado que acesso a página de Inscrição
    Quando clico em 'Próximo'
    Então devo visualizar mensagens de erro para campos vazios na tela Formulário

  @smoke
  Cenario: Validar tentativa de cadastro de informações com nome sem sobrenome
    Dado que acesso a página de Inscrição
    E preencho o campo nome completo inválido
    Quando clico em 'Próximo'
    Então devo visualizar mensagem de erro para nome completo inválido

  @smoke
  Cenario: Validar tentativa de cadastro de informações com email inválido
    Dado que acesso a página de Inscrição
    E preencho o campo email inválido
    Quando clico em 'Próximo'
    Então devo visualizar mensagem de erro para email inválido

  @bug
  Cenario: Validar tentativa de cadastro de informações com rg inválido
    Dado que acesso a página de Inscrição
    E preencho o campo rg inválido
    Quando clico em 'Próximo'
    Então devo visualizar mensagem de erro para rg inválido

  @smoke
  Cenario: Validar tentativa de cadastro de informações com cpf inválido
    Dado que acesso a página de Inscrição
    E preencho o campo cpf inválido
    Quando clico em 'Próximo'
    Então devo visualizar mensagem de erro para cpf inválido

  @smoke
  Cenario: Validar tentativa de cadastro de informações com telefone inválido
    Dado que acesso a página de Inscrição
    E preencho o campo telefone inválido
    Quando clico em 'Próximo'
    Então devo visualizar mensagem de erro para telefone inválido

  @smoke
  Cenario: Validar tentativa de cadastro de informações com idade menor de 16 anos
    Dado que acesso a página de Inscrição
    E preencho o campo data de nascimento inválido
    Quando clico em 'Próximo'
    Então devo visualizar mensagem de erro para idade inválida