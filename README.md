# Estágio - Spring Boot

Este repositório contém um projeto desenvolvido com Spring Boot para o gerenciamento de vagas de estágio e cadastro de estagiários. A aplicação permite a criação, listagem, atualização e exclusão de vagas e estagiários via API REST.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Maven

## 📁 Estrutura do Projeto

```
estagio-spring-boot/
├── src/
│   ├── main/
│   │   ├── java/com/seuprojeto/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   └── model/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🧪 Funcionalidades

- Cadastro de vagas
- Cadastro de estagiários
- Atualização de informações
- Remoção de registros
- Consulta de dados por ID ou listagem geral

## ⚙️ Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/Vini9-6/estagio-spring-boot.git
   cd estagio-spring-boot
   ```

2. Abra o projeto em uma IDE como IntelliJ IDEA ou Eclipse.

3. Compile e execute o projeto com Maven ou diretamente pela IDE:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Acesse a aplicação (exemplo):
   - API REST: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console` (se configurado)

## 🔒 Configurações Sensíveis

Configure suas variáveis de ambiente ou use o `application.properties` com os dados corretos de conexão, como:

```properties
spring.datasource.url=jdbc:h2:mem:estagio
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

## 🧪 Testes

Execute os testes com:

```bash
./mvnw test
```

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Feito para fins de aprendizado e prática.

(terceira nota da disciplina de Análise de Sistemas - UEMA)
