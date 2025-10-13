# Portal de Estágios

> Plataforma REST construída com Spring Boot para conectar estudantes e empresas através de oportunidades de estágio. O projeto combina uma API robusta com protótipos web estáticos e foi desenvolvido para demonstrar arquitetura em camadas, modelagem relacional e boas práticas de documentação.

## Sumário
- [Por que este projeto importa](#por-que-este-projeto-importa)
- [Principais entregas e aprendizados](#principais-entregas-e-aprendizados)
- [Funcionalidades para usuários](#funcionalidades-para-usuários)
- [Stack técnica e habilidades demonstradas](#stack-técnica-e-habilidades-demonstradas)
- [Arquitetura e fluxo de dados](#arquitetura-e-fluxo-de-dados)
- [Modelagem do domínio](#modelagem-do-domínio)
- [Mapa da API REST](#mapa-da-api-rest)
- [Estrutura do repositório](#estrutura-do-repositório)
- [Como executar localmente](#como-executar-localmente)
  - [Pré-requisitos](#pré-requisitos)
  - [Instalação](#instalação)
  - [Rodando a aplicação](#rodando-a-aplicação)
  - [Console do H2](#console-do-h2)
- [Testes automatizados](#testes-automatizados)
- [Protótipos web de referência](#protótipos-web-de-referência)
- [Roadmap e ideias futuras](#roadmap-e-ideias-futuras)
- [Como este projeto pode evoluir seu aprendizado](#como-este-projeto-pode-evoluir-seu-aprendizado)
- [Executando em GitHub Codespaces](#executando-em-github-codespaces)

## Por que este projeto importa
- **Valor para empresas:** centraliza o cadastro de vagas e garante regras de negócio para evitar dados inconsistentes (ex.: impossibilita remover empresas com vagas ativas).
- **Valor para estudantes:** permite acompanhar candidaturas, filtrar vagas por área e manter perfil acadêmico atualizado.
- **Valor para recrutadores:** demonstra domínio de APIs REST, persistência relacional e documentação orientada a produto — componentes críticos em equipes de estágio ou júnior.

## Principais entregas e aprendizados
| Entrega | Resultado | Skill evidenciada |
| --- | --- | --- |
| Arquitetura em camadas (controllers, models, repositories) | Código modular, fácil de expandir para novos domínios | Design de software, boas práticas Spring Boot |
| Banco em memória com seed automático | Ambiente pronto para testes manuais ou automatizados em segundos | Automação de setup, uso de `@PostConstruct` |
| Documentação abrangente (README + protótipos) | Explica fluxo de ponta a ponta e acelera onboarding de novos devs | Comunicação técnica, documentação de APIs |
| Protótipos HTML/JS consumindo a API | Demonstra integração front-back sem frameworks complexos | Consumo de REST, UX de baixa fidelidade |

> **Aprendizado-chave:** Estruturei o projeto pensando em escalabilidade pedagógica — qualquer pessoa consegue clonar, subir e testar sem depender de ambiente externo. Essa abordagem evidencia planejamento e cuidado com DX (Developer Experience).

## Funcionalidades para usuários
- CRUD completo de **empresas, estudantes, áreas de atuação, vagas e inscrições**.
- **Regras de consistência** que impedem exclusões indevidas e permitem alternar status de vagas com segurança.
- **Filtros avançados** de vagas por empresa e por áreas de atuação.
- **Carga inicial** de dados para explorar o sistema imediatamente após o boot.
- **Protótipos web** que ilustram o fluxo de login e uso básico da API sem necessidade de frontend separado.

## Stack técnica e habilidades demonstradas
- **Linguagem:** Java 17+
- **Framework principal:** Spring Boot (Spring Web, Spring Data JPA)
- **Persistência:** H2 em memória (configurável via `application.properties`)
- **Build & tooling:** Maven Wrapper (`./mvnw`), Lombok opcional
- **Serialização:** Jackson
- **Habilidades:** arquitetura em camadas, modelagem relacional, documentação técnica, seed de dados, boas práticas REST, testes automatizados com Maven

## Arquitetura e fluxo de dados
1. `WebservicerestApplication` inicializa o contexto Spring Boot e expõe a API.
2. **Controllers** (pacote `controller/`) recebem requisições REST, validam payloads e aplicam regras de negócio.
3. **Models** (pacote `model/`) mapeiam as entidades JPA e controlam a serialização JSON.
4. **Repositories** (pacote `repositories/`) estendem `JpaRepository`, liberando CRUD automático e consultas derivadas.
5. Métodos anotados com `@PostConstruct` criam registros iniciais para acelerar experimentação.

Esse fluxo garante separação de responsabilidades e favorece testes unitários/mocks em cada camada.

## Modelagem do domínio
| Entidade | Atributos principais | Relacionamentos-chave |
| --- | --- | --- |
| `Empresa` | `id`, `nome`, `cnpj`, `email`, `telefone` | `1:N` com `VagaEstagio` |
| `VagaEstagio` | `id`, `titulo`, `descricao`, `modalidade`, `ativa`, `empresa` | `N:1` com `Empresa`; `N:N` com `AreaAtuacao`; `1:N` com `InscricaoVaga` |
| `AreaAtuacao` | `id`, `nome` | `N:N` com `VagaEstagio` e `Estudante` |
| `Estudante` | `id`, `nome`, `email`, `instituicao`, `periodo` | `N:N` com `AreaAtuacao`; `1:N` com `InscricaoVaga` |
| `InscricaoVaga` | `id`, `dataInscricao`, `status`, `estudante`, `vagaEstagio` | `N:1` com `Estudante` e `VagaEstagio` |

Essas relações fundamentam as regras de negócio implementadas nos controladores (por exemplo, impedir excluir áreas associadas a vagas ativas).

## Mapa da API REST
| Recurso | Endpoint base | Operações em destaque |
| --- | --- | --- |
| Empresas | `/api/empresas` | `GET` lista todas; `POST` cria nova empresa; `PUT /{id}` atualiza; `DELETE /{id}` bloqueia exclusões com vagas vinculadas |
| Estudantes | `/api/estudantes` | `GET` lista; `POST` cria validando e-mail único; `PUT /{id}` atualiza; `DELETE /{id}` impede remoção com inscrições |
| Áreas | `/api/areas` | `GET` lista; `POST` cria; `PUT /{id}` atualiza; `DELETE /{id}` valida vínculos |
| Vagas | `/api/vagas` | `GET` com filtros `?empresaId=` e `?areas=`; `POST` cria vaga ativa; `PUT /ativar/{id}` alterna status |
| Inscrições | `/api/inscricoes` | `POST` cria inscrição; `GET /estudante/{id}` retorna candidaturas; `GET /vaga/{id}` lista inscritos |

> Todas as rotas utilizam JSON e retornam `ResponseEntity` com códigos HTTP adequados, mantendo padrões de APIs modernas.

### Exemplo rápido de criação de vaga
```bash
curl -X POST http://localhost:8080/api/vagas \
  -H 'Content-Type: application/json' \
  -d '{
        "titulo": "Estágio em Backend",
        "descricao": "Suporte ao time de APIs",
        "modalidade": "Remoto",
        "empresa": { "id": 1 },
        "areasAtuacao": [{ "id": 1 }]
      }'
```

## Estrutura do repositório
```
g2-main/webservicerest/
├── pom.xml
├── src/main/java/com/portalestagio/webservicerest/
│   ├── WebservicerestApplication.java   # Ponto de entrada
│   ├── controller/                      # Controladores REST por domínio
│   ├── model/                           # Entidades JPA e regras de serialização JSON
│   └── repositories/                    # Interfaces JpaRepository e consultas derivadas
└── src/main/resources/
    ├── application.properties           # Configurações do banco e do Hibernate
    └── static/                          # Protótipos HTML/JS consumindo a API
```

## Como executar localmente

### Pré-requisitos
- Java 17 ou superior instalado
- Maven opcional (o projeto já inclui o Maven Wrapper `./mvnw`)

### Instalação
```bash
git clone <url-do-repo>
cd portalestagio/g2-main/webservicerest
```

### Rodando a aplicação
```bash
./mvnw spring-boot:run
```
A API ficará disponível em `http://localhost:8080`.

### Console do H2
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: `sa`
- Senha: *(vazia)*

O schema é recriado a cada inicialização (`spring.jpa.hibernate.ddl-auto=create`). Para persistir dados entre execuções, altere para `update` ou configure outro banco em `application.properties`.

## Testes automatizados
Execute a suíte de testes com:
```bash
./mvnw test
```

## Protótipos web de referência
As páginas em `src/main/resources/static` simulam fluxos de estudante e empresa. Com o backend ativo, acesse `http://localhost:8080/` e acompanhe as requisições `fetch` no DevTools para observar o consumo da API.

## Roadmap e ideias futuras
- [ ] Implementar autenticação/autorização (Spring Security + JWT) para diferenciar perfis.
- [ ] Adicionar paginação e ordenação aos endpoints de listagem.
- [ ] Criar testes de integração com banco em memória e mocks para regras de negócio críticas.
- [ ] Evoluir os protótipos estáticos para um frontend SPA (React ou Angular) consumindo a mesma API.
- [ ] Integrar pipelines CI/CD (GitHub Actions) para validar build e testes a cada PR.

## Como este projeto pode evoluir seu aprendizado
1. **Mapeie um fluxo ponta a ponta:** siga uma requisição `POST /api/vagas` desde o controller até o repositório e entenda como as validações são aplicadas.
2. **Explore o H2 Console:** visualize dados sem escrever SQL manual e valide relacionamentos N:N.
3. **Modele um novo domínio:** replique o padrão controller + model + repository para funcionalidades como "supervisores".
4. **Escreva testes adicionais:** cubra cenários de erro e regras de negócio para praticar testes unitários/integrados.
5. **Documente uma nova feature:** mantenha o README atualizado com aprendizados — documentação também é skill valorizada.

## Executando em GitHub Codespaces
1. Faça fork do repositório no GitHub.
2. No fork, clique em **Code → Open with Codespaces → New codespace**.
3. Aguarde o ambiente provisionar e rode:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse `https://<seu-codespace>-8080.app.github.dev/` para interagir com a API e os protótipos.
5. Pare o servidor com `Ctrl+C` e finalize o Codespace quando terminar.
