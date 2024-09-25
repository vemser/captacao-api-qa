<div align="center">
  <img alt="VemSerDBC" src="https://img.shields.io/badge/Vem Ser 13-00599C?style=for-the-badge&logo=java&logoColor=white">
  <img alt="DBC" src="https://img.shields.io/badge/DBC-00599C?style=for-the-badge&logo=java&logoColor=white">
  <img alt="Java" src="https://img.shields.io/badge/Java-Ff0000?style=for-the-badge&logo=coffeescript&logoColor=white">
  <img alt="rest-assured" src="https://img.shields.io/badge/rest--assured-512DA8?style=for-the-badge&logo=rest-assured&logoColor=white">
</div>


# Vem Ser 13 - Capacitação + Provas ☕

Repositório para alocar os testes automatizados de API com Rest-Assured que foram desenvolvidos durante o trabalho final da 13° edição do programa Vem Ser.

## Documentação
- [Plano de Testes](https://docs.google.com/document/d/1VYfTStATCmQv_4LrLfesFFRp3tFiYMGKj9X8MYQ-TIw)
- [User Stories + Cenários de Testes](https://drive.google.com/file/d/1sUtOdkRkSD8SSjM0HYnmp3xVuOXup5Eh/view)
- [Trello](https://trello.com/b/y0mKWbUq/captação-provas)

## Links
- [Captação + Provas](http://vemser-dbc.dbccompany.com.br:39000/vemser/vemser-front)
- [API Swagger](http://vemser-dbc.dbccompany.com.br:39000/vemser/captacao-back-release/swagger-ui/index.html)
- [API Swagger Login](http://vemser-dbc.dbccompany.com.br:39000/vemser/usuario-back/swagger-ui/index.html)
- [API Swagger no Render](https://captacao-back-release.onrender.com/swagger-ui/index.html#/Questão/criarQuestaoPratica)
- [API Swagger Login no Render](https://usuario-back.onrender.com/swagger-ui/index.html#/)


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
        <maven-resources-plugin.version>3.1.0</maven-resources-plugin.version>
        <lombok.version>1.18.24</lombok.version>
        <rest-assured.version>5.4.0</rest-assured.version>
        <junit5.version>5.10.0</junit5.version>
        <junit-platform.version>1.5.2</junit-platform.version>
        <datafaker.version>1.7.0</datafaker.version>
        <gson.version>2.8.9</gson.version>
        <allure-junit5.version>2.14.0</allure-junit5.version>
        <allure-maven.version>2.10.0</allure-maven.version>
        <aspectj.version>1.9.7</aspectj.version>
        <allure.version>2.20.1</allure.version>
        <log4j.version>2.13.1</log4j.version>
        <maven-compiler-plugin.version>3.8.1</maven-compiler-plugin.version>
        <maven-surefire-plugin.version>2.22.2</maven-surefire-plugin.version>
        <junit-surefire.version>1.3.2</junit-surefire.version>
        <allure.results.directory>${project.build.directory}/allure-results</allure.results.directory>
        <jackson-databind.version>2.14.1</jackson-databind.version>
        <jackson-datatype.version>2.13.4</jackson-datatype.version>
        <skipITs>true</skipITs>
        <skipTests>false</skipTests>
        <skipUTs>${skipTests}</skipUTs>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j-impl</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${rest-assured.version}</version>
        </dependency>
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>json-schema-validator</artifactId>
            <version>${rest-assured.version}</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit5.version}</version>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-runner</artifactId>
            <version>${junit-platform.version}</version>
        </dependency>
        <dependency>
            <groupId>net.datafaker</groupId>
            <artifactId>datafaker</artifactId>
            <version>${datafaker.version}</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>
        </dependency>
        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-junit5</artifactId>
            <version>${allure-junit5.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson-databind.version}</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>${jackson-datatype.version}</version>
        </dependency>
    </dependencies>
```

# Sistema de Teste Automatizado

## Ferramentas e Tecnologias Utilizadas

- **Rest-Assured**: Uma ferramenta para automação de testes de api.
- **Intellij**


## Colaboradores 🧑‍💻

- [Adam Cardoso](https://github.com/adamcardoso)
- [Aron Adams](https://github.com/AronAdamsRapetto)
- [Débora Hickmann](https://github.com/Deboraaahickmann)
- [Filipe Prata](https://github.com/FilipePrata)
- [Luiz Fellipe](https://github.com/luizfdarb)
