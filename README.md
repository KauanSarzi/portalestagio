
 # Portal de Estágios
 
-Este projeto é uma aplicação Spring Boot que implementa um backend (REST API) para gerenciamento de empresas, estudantes, áreas e vagas de estágio, junto com páginas HTML/JS de exemplo. A seguir estão as instruções para que um desenvolvedor faça fork do repositório e obtenha um ambiente de desenvolvimento pronto no GitHub Codespaces.
+Este projeto é uma aplicação Spring Boot que expõe uma API REST para gerenciar empresas, estudantes, áreas e vagas de estágio, além de páginas HTML/JS estáticas usadas como protótipo de consumo da API.
 
----
+## Visão geral da base de código
+- O código-fonte principal vive em `g2-main/webservicerest`, que contém o projeto Maven da aplicação.
+- A API segue o padrão clássico de controladores REST + entidades JPA + repositórios Spring Data, sem camada de serviço intermediária.
+- As páginas estáticas em `src/main/resources/static` demonstram o consumo dos endpoints diretamente do navegador.
+- A aplicação utiliza banco H2 em memória e dados "seed" para que você tenha registros de exemplo logo após subir a aplicação.
 
-## 1. Pré-requisitos
+### Mapa dos diretórios principais
+```
+g2-main/webservicerest/
+├── pom.xml
+├── src/main/java/com/portalestagio/webservicerest/
+│   ├── WebservicerestApplication.java
+│   ├── controller/         # Controladores REST organizados por domínio
+│   ├── model/              # Entidades JPA e relacionamentos
+│   └── repositories/       # Interfaces JpaRepository com queries derivadas
+└── src/main/resources/
+    ├── application.properties  # Configuração do H2 e comportamento do Hibernate
+    └── static/                 # Protótipos HTML/JS que consomem a API
+```
 
-- **GitHub Account**: você precisa estar logado em uma conta GitHub para criar o fork e iniciar um Codespace.
-- **Codespaces habilitado**: o Codespaces deve estar disponível para o seu repositório (disponível para planos GitHub Team ou GitHub Enterprise Cloud, ou repositórios públicos).
-- Não é necessário instalar Java, Maven ou banco de dados local: o Codespaces fornece container Linux com Java e Maven instalados, e o projeto usa injeção de dados “seed” para popular uma área, empresa e estudante de exemplo no H2 (ou em memória).
+### Arquitetura do backend
+- **Ponto de entrada**: `WebservicerestApplication` inicializa o contexto Spring Boot e publica os endpoints REST.
+- **Controladores REST**: classes em `controller/` como `VagaEstagioController`, `EmpresaController` e `EstudanteController` expõem rotas sob `/api/...`, validam campos obrigatórios e traduzem regras de negócio básicas (ex.: impedir edição de vagas inativas ou exclusão com vínculos).
+- **Modelagem do domínio**: as entidades em `model/` mapeiam relacionamentos muitos-para-muitos entre estudantes/áreas e vagas/áreas, além de vínculos entre vagas e empresas e inscrições; o uso de anotações Jackson evita ciclos ao serializar para JSON.
+- **Repositórios**: interfaces em `repositories/` estendem `JpaRepository` e oferecem consultas por convenção, como busca de vagas ativas por empresa ou por áreas.
+- **Carga inicial de dados**: métodos anotados com `@PostConstruct` em `EmpresaController` e `EstudanteController` garantem que exista ao menos uma empresa, um estudante e uma área cadastrados quando a aplicação sobe, facilitando a exploração inicial.
 
----
+### Modelagem de domínio em alto nível
+| Entidade            | Atributos principais                                                                 | Relacionamentos chave                                                  |
+|--------------------|---------------------------------------------------------------------------------------|------------------------------------------------------------------------|
+| `Empresa`          | `id`, `nome`, `cnpj`, `email`, `telefone`                                             | `1:N` com `VagaEstagio`                                                |
+| `VagaEstagio`      | `id`, `titulo`, `descricao`, `modalidade`, `ativa`, `empresa`                         | `N:1` com `Empresa`, `N:N` com `AreaAtuacao`, `N:N` com `Estudante` via inscrições |
+| `AreaAtuacao`      | `id`, `nome`                                                                          | `N:N` com `VagaEstagio` e `Estudante`                                  |
+| `Estudante`        | `id`, `nome`, `email`, `instituicao`, `periodo`                                      | `N:N` com `AreaAtuacao`, `1:N` com `InscricaoVaga`                     |
+| `InscricaoVaga`    | `id`, `dataInscricao`, `status`, `estudante`, `vagaEstagio`                          | `N:1` com `Estudante` e `VagaEstagio`                                  |
 
-## 2. Fazer fork do repositório
+Esses relacionamentos orientam as regras de negócio implementadas nos controladores, como impedir a remoção de uma vaga que ainda possui inscrições ativas ou garantir que uma área exista antes de ser associada a estudantes e vagas.
 
-1. Acesse a página principal deste repositório no GitHub.
-2. Clique no botão **“Fork”** (no canto superior direito).  
-   - Isso criará uma cópia do projeto no seu próprio perfil GitHub (por exemplo: `github.com/seu-usuario/portal-estagio`).
+### Principais endpoints REST
+| Recurso              | Endpoint base        | Operações frequentes                                                                                                                                 |
+|----------------------|----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
+| Empresas             | `/api/empresas`      | `GET /api/empresas` lista todas; `POST` cria; `PUT /{id}` atualiza; `DELETE /{id}` impede remoção quando há vagas vinculadas.                         |
+| Estudantes           | `/api/estudantes`    | `GET` para listar; `POST` cria validando e-mails únicos; `PUT /{id}` atualiza dados acadêmicos; `DELETE /{id}` bloqueado se houver inscrições.       |
+| Áreas de atuação     | `/api/areas`         | `GET` lista; `POST` cria; `PUT /{id}` altera nome; `DELETE /{id}` falha se estiver associada a estudantes ou vagas.                                   |
+| Vagas de estágio     | `/api/vagas`         | Filtros por empresa (`?empresaId=`) ou áreas (`?areas=`); `POST` cria vaga ativa por padrão; `PUT /ativar/{id}` alterna status ativo/inativo.         |
+| Inscrições em vagas  | `/api/inscricoes`    | `POST` cria inscrição para estudante e vaga existentes; `GET /estudante/{id}` e `GET /vaga/{id}` permitem rastrear candidaturas.                    |
 
----
+As páginas estáticas consomem essas rotas via `fetch` e operam sempre com JSON, então qualquer cliente HTTP (Postman, curl, etc.) consegue reproduzir as mesmas ações.
 
-## 3. Abrir o Codespace
+### Executando localmente
+1. **Instale o Java 17+** (ou use o wrapper Maven incluso).
+2. Dentro de `g2-main/webservicerest`, execute:
+   ```bash
+   ./mvnw spring-boot:run
+   ```
+3. Acesse `http://localhost:8080` para abrir o protótipo estático ou `http://localhost:8080/api/...` para testar os endpoints diretamente.
+4. O console do H2 fica disponível em `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:testdb`, usuário `sa`, senha vazia).
+
+Para rodar testes automatizados, utilize `./mvnw test`. Caso deseje que o banco persista entre execuções, altere `spring.jpa.hibernate.ddl-auto` para `update` ou configure um banco externo no `application.properties`.
+
+### Frontend de referência
+- Os arquivos HTML e JavaScript em `src/main/resources/static` são servidos diretamente pelo Spring Boot e funcionam como protótipos para estudantes e empresas. O `index.html` gerencia o login fictício, navegação entre perfis, consumo das APIs via `fetch` e exibição dos dados carregados.
+- Como são páginas estáticas simples, você pode abrí-las apontando o navegador para `http://localhost:8080/` (ou a porta configurada) após iniciar o backend.
+
+### Configuração e banco de dados
+- `application.properties` configura o banco H2 em memória, cria o schema a cada inicialização (`spring.jpa.hibernate.ddl-auto=create`) e habilita o console web do H2, útil para inspecionar os dados carregados automaticamente.
+- Como o banco é ephemeral, reiniciar a aplicação zera os dados — ótimo para testar diferentes cenários sem deixar sujeira.
+
+### Como aprofundar o aprendizado
+1. **Trace um fluxo ponta a ponta**: escolha um endpoint (por exemplo, `POST /api/vagas`) e siga o código do controlador até o repositório, verificando como as entidades estão relacionadas e quais validações ocorrem em cada etapa.
+2. **Interaja com o banco H2**: habilite o console (`/h2-console`) para observar a estrutura das tabelas e os registros criados automaticamente ou via API.
+3. **Brinque com os protótipos**: use `index.html` para simular o dia a dia de estudantes e empresas; isso ajuda a entender quais campos a API espera e quais payloads ela devolve.
+4. **Replique padrões existentes**: ao criar um novo domínio (ex.: "supervisores"), copie a estrutura de controlador + entidade + repositório, reutilizando anotações e convenções já presentes.
 
-1. No repositório que você acabou de “forkar”, clique no botão verde **“Code”** e, em seguida, selecione **“Open with Codespaces”** → **“New codespace”**.
-2. O GitHub iniciará automaticamente um container de desenvolvimento com todas as dependências necessárias (Java, Maven, etc.).
-3. Após alguns segundos, você deverá ver o VS Code do Codespaces com o código-fonte aberto.
+## Guia rápido: preparar Codespace a partir de um fork
 
----
+As etapas abaixo continuam válidas caso você deseje trabalhar no GitHub Codespaces.
 
-## 4. Executar a aplicação no Codespace
+### 1. Pré-requisitos
+- Conta GitHub com Codespaces habilitado (planos GitHub Team, Enterprise Cloud ou repositórios públicos).
+- Não é necessário instalar Java, Maven ou banco de dados local; o Codespaces já fornece tudo em um container Linux.
 
-Dentro do Codespace, siga estes passos:
+### 2. Fazer fork do repositório
+1. Acesse este repositório no GitHub.
+2. Clique em **Fork** para criar uma cópia em sua conta (ex.: `github.com/seu-usuario/portal-estagio`).
 
-1. **Verifique a versão do Java**  
-   No terminal integrado do Codespace (geralmente aberto por padrão), digite:
+### 3. Abrir o Codespace
+1. No fork, clique em **Code** → **Open with Codespaces** → **New codespace**.
+2. Aguarde o ambiente iniciar e o VS Code (web) carregar com o projeto.
+
+### 4. Executar a aplicação no Codespace
+1. **Verifique a versão do Java**
    ```bash
    java -version
+   ```
+2. **Instale dependências e suba a aplicação**
+   ```bash
+   ./mvnw spring-boot:run
+   ```
+3. Acesse `https://<seu-codespace>-8080.app.github.dev/` para usar as páginas estáticas ou `https://<seu-codespace>-8080.app.github.dev/api/...` para testar os endpoints diretamente.
+
+### 5. Encerrar o Codespace
+- Pare a aplicação com `Ctrl+C` no terminal.
+- Feche a aba do Codespace para liberar recursos.
+
+Com esse panorama, você consegue navegar pelo código com mais confiança, experimentar os endpoints rapidamente e evoluir novas features seguindo os padrões já estabelecidos. 
EOF
)
