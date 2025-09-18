# LibraryApp

LibraryApp é uma aplicação modular Java para gerenciamento de livros, composta por:

- **catalog**: programa **CLI (Command Line Interface)** para cadastro, busca e listagem de livros.
- **notification**: serviço HTTP que envia notificações por email quando um livro é cadastrado.

---

## Pré-requisitos

- **Java 21 (OpenJDK)**
- **Maven 3.9+**
- **Docker** (para rodar o servidor SMTP)
- Terminal ou IDE (IntelliJ, VS Code)

---

## Rodando um servidor SMTP local

Para testes de envio de email, utilizamos o **MailHog**, que pode ser executado via Docker Compose:

1. Arquivo `docker-compose.yml` na raiz do projeto:

```yaml
version: '3'
services:
  mailhog:
    image: mailhog/mailhog
    ports:
      - "1025:1025" # Porta SMTP
      - "8025:8025" # Interface Web
````

2. Execute o Docker Compose:

```bash
docker-compose up -d
```

3. Verifique a interface web do MailHog:
   [http://localhost:8025](http://localhost:8025)

> Todos os emails enviados pelo serviço Notification aparecerão nessa interface.

---

## 🚀 Como rodar o projeto

### 1️⃣ Rodar o módulo Notification

No terminal:

```bash
mvn clean compile exec:java -pl notification
```

* O serviço ficará escutando na porta configurada (8081 por padrão).
* Ele deve estar rodando antes de cadastrar livros, para que as notificações funcionem.

### 2️⃣ Rodar o módulo Catalog (CLI)

Em outro terminal:

```bash
mvn clean compile exec:java -pl catalog
```

O **Catalog CLI** permite:

* Cadastrar livro
* Buscar livro por ID
* Listar todos os livros

> Ao cadastrar um livro, o sistema tenta notificar o serviço Notification.
> Caso o Notification não esteja rodando, você verá a mensagem:
> `Livro cadastrado, mas o sistema de notificação está indisponível.`

---

## 📋 Catalog CLI - Como usar

Ao iniciar o CLI do Catalog, você verá:

```
Escolha uma opção:
1 - Cadastrar livro
2 - Buscar livro por ID
3 - Listar todos os livros
0 - Sair
```

* **Cadastrar livro**: solicita título, autor e ano de publicação.
* **Buscar livro por ID**: permite consultar um livro específico.
* **Listar todos os livros**: exibe todos os livros cadastrados com formatação amigável.

Exemplo de saída de listagem:

```
Livro {
  id: 6296fb1d-1d15-4a06-a6da-34c93164cb3f,
  título: 'Teste',
  autor: 'Autor Exemplo',
  ano de publicação: 2025
}
```

---

## ⚠️ Observações importantes

* O **Catalog** é totalmente CLI, não depende de interface gráfica.
* O **Notification** é um microserviço HTTP que deve estar rodando para envio de emails.
* Para atualizar as dependências Maven e forçar downloads:

```bash
mvn clean install -U
```

* Se você parar o Notification, a porta será liberada e pode ser reutilizada sem precisar matar processos manualmente.
* É importante rodar **Notification antes do Catalog** para que as notificações funcionem corretamente.

---

## 👨‍💻 Autor

Francisco Lima

---

## 📝 Referências

* [Maven](https://maven.apache.org/)
* [Docker Compose](https://docs.docker.com/compose/)
* [MailHog](https://github.com/mailhog/MailHog)
* [Jakarta Mail](https://eclipse-ee4j.github.io/mail/)