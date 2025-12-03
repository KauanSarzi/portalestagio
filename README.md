# 🎓 Portal de Estágios

> Sistema completo de gerenciamento de vagas de estágio, conectando estudantes e empresas de forma eficiente e intuitiva.

[![Java](https://img.shields.io/badge/Java-21-red?style=flat&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen?style=flat&logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue?style=flat&logo=apachemaven)](https://maven.apache.org/)
[![H2 Database](https://img.shields.io/badge/H2-Database-orange?style=flat)](https://www.h2database.com/)

## 📋 Sobre o Projeto

O **Portal de Estágios** é uma aplicação web full-stack desenvolvida com Spring Boot que facilita a conexão entre estudantes universitários e empresas que oferecem oportunidades de estágio. O sistema oferece funcionalidades completas de CRUD (Create, Read, Update, Delete) para gerenciar empresas, estudantes, áreas de interesse, vagas e inscrições.

### ✨ Principais Funcionalidades

- **Gestão de Empresas**: Cadastro completo com CNPJ, contato, endereço e ramo de atuação
- **Perfil de Estudantes**: Gerenciamento de dados acadêmicos e áreas de interesse
- **Sistema de Vagas**: Publicação de oportunidades com filtros por área, modalidade e empresa
- **Inscrições**: Processo completo de candidatura com acompanhamento de status
- **Áreas de Interesse**: Categorização para melhor match entre vagas e estudantes
- **Interface Intuitiva**: Frontend responsivo com experiência otimizada para usuários

## 🏗️ Arquitetura do Sistema

O projeto segue uma arquitetura em camadas (Layered Architecture) bem definida:

```
┌─────────────────────────────────────┐
│     Frontend (HTML/CSS/JS)          │
│   - Interface do Usuário            │
│   - Consumo da API REST             │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│   Controllers (REST API)            │
│   - Endpoints HTTP                  │
│   - Validação de requisições        │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│   Models (Entidades JPA)            │
│   - Mapeamento objeto-relacional    │
│   - Relacionamentos entre entidades │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│   Repositories (Spring Data JPA)    │
│   - Acesso aos dados                │
│   - Queries personalizadas          │
└─────────────────┬───────────────────┘
                  │
┌─────────────────▼───────────────────┐
│   Banco de Dados H2 (In-Memory)    │
│   - Persistência de dados           │
│   - Modo de desenvolvimento         │
└─────────────────────────────────────┘
```

## 🗄️ Modelo de Dados

### Diagrama de Relacionamentos

```
┌──────────────┐         ┌──────────────┐
│   EMPRESA    │         │     ÁREA     │
│──────────────│         │──────────────│
│ id (PK)      │         │ id (PK)      │
│ cnpj         │         │ nome         │
│ nomeFantasia │         └──────┬───────┘
│ emailContato │                │
│ endereco     │                │ N:M
│ descricao    │                │
│ telefone     │         ┌──────▼───────┐
│ ramoAtuacao  │    ┌────│  ESTUDANTE   │
└──────┬───────┘    │    │──────────────│
       │            │    │ id (PK)      │
       │ 1:N        │    │ nome         │
       │            │    │ email        │
┌──────▼───────┐   │    │ anoIngresso  │
│ VAGA_ESTAGIO │   │    │ curso        │
│──────────────│   │    │ matricula    │
│ id (PK)      │   │    │ periodoAtual │
│ titulo       │   │    └──────┬───────┘
│ descricao    │   │           │
│ dataPub      │   │           │ 1:N
│ ativo        │   │           │
│ salario      │   │    ┌──────▼───────┐
│ cargaHoraria │   └────│  INSCRICAO   │
│ modalidade   │        │──────────────│
│ empresa_id   │        │ id (PK)      │
└──────┬───────┘        │ dataInscricao│
       │                │ status       │
       │ N:M            │ mensagem     │
       │                │ estudante_id │
       └────────────────│ vaga_id      │
                        └──────────────┘
```

### Relacionamentos Principais

- **Empresa → Vaga de Estágio**: Uma empresa pode publicar várias vagas (1:N)
- **Vaga de Estágio ↔ Área**: Vagas podem ter múltiplas áreas e áreas podem estar em várias vagas (N:M)
- **Estudante ↔ Área**: Estudantes podem ter interesse em múltiplas áreas (N:M)
- **Estudante → Inscrição**: Um estudante pode fazer várias inscrições (1:N)
- **Vaga de Estágio → Inscrição**: Uma vaga pode receber várias inscrições (1:N)

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 21**: Linguagem de programação moderna e robusta
- **Spring Boot 3.4.5**: Framework para desenvolvimento rápido de aplicações
- **Spring Data JPA**: Abstração para persistência de dados
- **Hibernate**: ORM (Object-Relational Mapping)
- **H2 Database**: Banco de dados em memória para desenvolvimento
- **Maven**: Gerenciamento de dependências e build

### Frontend
- **HTML5**: Estrutura das páginas
- **CSS3**: Estilização (Sakura.css para design minimalista)
- **JavaScript (Vanilla)**: Interatividade e consumo da API REST

## 🚀 Como Executar o Projeto

### Pré-requisitos

- Java 21 ou superior
- Maven 3.9+ (ou usar o Maven Wrapper incluído)
- Git

### Passo a Passo

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/portalestagio.git
cd portalestagio/webservicerest
```

2. **Compile o projeto**
```bash
./mvnw clean install
```

3. **Execute a aplicação**
```bash
./mvnw spring-boot:run
```

4. **Acesse no navegador**
```
http://localhost:8080
```

### Usando GitHub Codespaces

1. Faça fork do repositório
2. Clique em **Code** → **Codespaces** → **Create codespace**
3. Execute `./mvnw spring-boot:run` no terminal integrado
4. Acesse a porta encaminhada automaticamente

## 📡 API REST - Endpoints

### Empresas
```http
GET    /api/empresas          # Lista todas as empresas
GET    /api/empresas/{id}     # Busca empresa por ID
POST   /api/empresas          # Cria nova empresa
PUT    /api/empresas/{id}     # Atualiza empresa
DELETE /api/empresas/{id}     # Remove empresa
```

### Estudantes
```http
GET    /api/estudantes        # Lista todos os estudantes
GET    /api/estudantes/{id}   # Busca estudante por ID
POST   /api/estudantes        # Cria novo estudante
PUT    /api/estudantes/{id}   # Atualiza estudante
DELETE /api/estudantes/{id}   # Remove estudante
```

### Vagas de Estágio
```http
GET    /api/vagas                    # Lista vagas (com filtros)
GET    /api/vagas/{id}               # Busca vaga por ID
POST   /api/vagas                    # Cria nova vaga
PUT    /api/vagas/{id}               # Atualiza vaga
PATCH  /api/vagas/{id}/encerrar      # Encerra vaga
DELETE /api/vagas/{id}               # Remove vaga
```

**Parâmetros de filtro para GET /api/vagas:**
- `areaIds`: Lista de IDs de áreas (ex: ?areaIds=1,2)
- `empresaId`: ID da empresa (ex: ?empresaId=1)
- `page`: Número da página (paginação)
- `size`: Tamanho da página

### Áreas
```http
GET    /api/areas              # Lista todas as áreas
GET    /api/areas/{id}         # Busca área por ID
POST   /api/areas              # Cria nova área
PUT    /api/areas/{id}         # Atualiza área
DELETE /api/areas/{id}         # Remove área
```

### Inscrições
```http
GET    /api/inscricoes         # Lista inscrições (paginado)
POST   /api/inscricoes         # Cria nova inscrição
PATCH  /api/inscricoes/{id}    # Atualiza status/mensagem
DELETE /api/inscricoes/{id}    # Remove inscrição
```

## 📱 Páginas da Aplicação

| Página | Descrição |
|--------|-----------|
| `index.html` | Página inicial com login de Estudante/Empresa |
| `empresa.html` | Gerenciamento de empresas |
| `estudante.html` | Gerenciamento de estudantes |
| `vagaestagio.html` | Gerenciamento de vagas |
| `area.html` | Gerenciamento de áreas |
| `inscricao.html` | Gerenciamento de inscrições |

## 💡 Funcionalidades Especiais

### Sistema de Filtros
- Vagas filtradas por áreas de interesse do estudante
- Busca de vagas por empresa
- Filtro de vagas ativas/inativas

### Validações Implementadas
- ✅ Campos obrigatórios em todas as entidades
- ✅ Email único para estudantes
- ✅ CNPJ obrigatório para empresas
- ✅ Matrícula única por estudante
- ✅ Prevenção de exclusão com dependências (integridade referencial)

### Dados Iniciais (Seed)
O sistema cria automaticamente ao iniciar:
- 1 Empresa padrão (ID: 1)
- 1 Estudante padrão (ID: 1)
- 1 Área padrão (Engenharia)

## 🔒 Boas Práticas Implementadas

- ✅ Arquitetura em camadas bem definida
- ✅ Uso de DTOs implícitos (através das entidades)
- ✅ Tratamento de erros com ResponseEntity
- ✅ Validação de dados no backend
- ✅ Uso de @JsonProperty para prevenir loops de serialização
- ✅ FetchType.LAZY para otimização de queries
- ✅ Relacionamentos bidirecionais bem gerenciados
- ✅ Código limpo e bem documentado

## 📈 Melhorias Futuras

- [ ] Autenticação e autorização com Spring Security
- [ ] Upload de currículos (PDF)
- [ ] Sistema de notificações por email
- [ ] Dashboard com estatísticas
- [ ] Exportação de relatórios
- [ ] Integração com banco de dados PostgreSQL/MySQL
- [ ] Testes unitários e de integração
- [ ] Deploy em ambiente de produção (AWS/Heroku)

## 👥 Autor

**Seu Nome**
- GitHub: [KauanSarzi](https://github.com/KauanSarzi)
- LinkedIn: [Kauan Sarzi](https://linkedin.com/in/kauan-sarzi )
- Email: kauansarzi24@gmail.com

---

⭐ **Se este projeto foi útil, considere dar uma estrela no repositório!**

📫 **Dúvidas ou sugestões?** Abra uma [issue](https://github.com/seu-usuario/portal-estagios/issues) ou entre em contato!
