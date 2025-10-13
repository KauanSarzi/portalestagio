# Portal de Estágios

Este projeto é uma aplicação Spring Boot que implementa um backend (REST API) para gerenciamento de empresas, estudantes, áreas e vagas de estágio, junto com páginas HTML/JS de exemplo. A seguir estão as instruções para que um desenvolvedor faça fork do repositório e obtenha um ambiente de desenvolvimento pronto no GitHub Codespaces.

---

## 1. Pré-requisitos

- **GitHub Account**: você precisa estar logado em uma conta GitHub para criar o fork e iniciar um Codespace.
- **Codespaces habilitado**: o Codespaces deve estar disponível para o seu repositório (disponível para planos GitHub Team ou GitHub Enterprise Cloud, ou repositórios públicos).
- Não é necessário instalar Java, Maven ou banco de dados local: o Codespaces fornece container Linux com Java e Maven instalados, e o projeto usa injeção de dados “seed” para popular uma área, empresa e estudante de exemplo no H2 (ou em memória).

---

## 2. Fazer fork do repositório

1. Acesse a página principal deste repositório no GitHub.
2. Clique no botão **“Fork”** (no canto superior direito).  
   - Isso criará uma cópia do projeto no seu próprio perfil GitHub (por exemplo: `github.com/seu-usuario/portal-estagio`).

---

## 3. Abrir o Codespace

1. No repositório que você acabou de “forkar”, clique no botão verde **“Code”** e, em seguida, selecione **“Open with Codespaces”** → **“New codespace”**.
2. O GitHub iniciará automaticamente um container de desenvolvimento com todas as dependências necessárias (Java, Maven, etc.).
3. Após alguns segundos, você deverá ver o VS Code do Codespaces com o código-fonte aberto.

---

## 4. Executar a aplicação no Codespace

Dentro do Codespace, siga estes passos:

1. **Verifique a versão do Java**  
   No terminal integrado do Codespace (geralmente aberto por padrão), digite:
   ```bash
   java -version

