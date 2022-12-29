#language:pt

@test
Funcionalidade: Formulário

  Contexto: O candidato deve cadastrar formulário no sistema

  @bug
  Cenario: Validar cadastro de formulário com sucesso
    Dado que acesso a página de Inscrição
    E cadastro Informações corretamente
    E preencho todos os campos válidos de Formulário
    Quando clico em 'Enviar'
    Então devo ser redirecionado para a página de Finalização

  @smoke
  Cenario: Validar tentativa de cadastro de formulário clicando em não matriculado
    Dado que acesso a página de Inscrição
    E cadastro Informações corretamente
    Quando clico em 'Não' para matriculado
    Então devo visualizar mensagem de erro bloqueando a continuação do cadastro

  @pendente
  Cenario: Validar cadastro de formulário preenchendo campo deficiência com sucesso
    Dado que acesso a página de Inscrição
    E cadastro Informações corretamente
    E preencho todos os campos válidos de Formulário
    E seleciono 'Sim' em deficiência
    E preencho o campo deficiência
    Quando clico em 'Enviar'
    Então devo ser redirecionado para a página de Finalização

  @pendente
  Cenario: Validar cadastro de formulário preenchendo campo outro motivo com sucesso
    Dado que acesso a página de Inscrição
    E cadastro Informações corretamente
    E preencho todos os campos válidos de Formulário
    E clico em 'Outro motivo'
    E preencho o campo outro motivo
    Quando clico em 'Enviar'
    Então devo ser redirecionado para a página de Finalização

  @pendente
  Cenario: Validar botão VOLTAR
    Dado que acesso a página de Inscrição
    E cadastro Informações corretamente
    Quando clico em 'Voltar'
    Então devo ser retornar para a página de Informações
