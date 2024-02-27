<div align="center">
  <img alt="VemSerDBC" src="https://img.shields.io/badge/Vem Ser 13-00599C?style=for-the-badge&logo=java&logoColor=white">
  <img alt="DBC" src="https://img.shields.io/badge/DBC-00599C?style=for-the-badge&logo=java&logoColor=white">
  <img alt="Java" src="https://img.shields.io/badge/Java-Ff0000?style=for-the-badge&logo=coffeescript&logoColor=white">
  <img alt="rest-assured" src="https://img.shields.io/badge/rest--assured-512DA8?style=for-the-badge&logo=selenium&logoColor=white">
</div>


# Vem Ser 13 - Capacitação + Provas ☕

Repositório para alocar os testes automatizados de API com Rest-Assured que foram desenvolvidos durante o trabalho final da 13° edição do programa Vem Ser.

## Link
- [Captação + Provas](http://vemser-dbc.dbccompany.com.br:39000/vemser/vemser-front)
- [API Swagger](http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back/swagger-ui/index.html)

## Pré-requisitos ⚙️

- [Intellij Idea](https://www.jetbrains.com/idea/)
- [JDK 17](https://www.oracle.com/java/technologies/downloads/)
- [Rest-Assured](https://rest-assured.io)




## Dependências Utilizadas 👀

```pom.xml
<properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>4.5.1</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.1</version>
        </dependency>

        <dependency>
            <groupId>net.datafaker</groupId>
            <artifactId>datafaker</artifactId>
            <version>1.7.0</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.14.1</version>
        </dependency>

        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-junit5</artifactId>
            <version>2.14.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.13.4</version>
        </dependency>
    </dependencies>
```

# Sistema de Teste Automatizado

## Ferramentas e Tecnologias Utilizadas

- **Rest-Assured**: Uma ferramenta para automação de testes de api.
- **Intellij**

## Casos de Teste

### 1. NOME DO CENARIO DE TESTE
Descrever o caso de teste


## Colaboradores 🧑‍💻

- [Adam Cardoso](https://github.com/adamcardoso)
- [Aron Adams](https://github.com/AronAdamsRapetto)
- [Débora Hickmann](https://github.com/Deboraaahickmann)
- [Filipe Prata](https://github.com/FilipePrata)
- [Luiz Fellipe](https://github.com/luizfdarb)
