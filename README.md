# FinanceFlow API

Este é o repositório da API FinanceFlow, construída com Kotlin, Spring Boot e PostgreSQL, utilizando Docker para ambiente de desenvolvimento.

---

## Perfis de ambiente

Defina a variável de ambiente para escolher o perfil:

## **PowerShell**
# Perfil de desenvolvimento
$env:SPRING_PROFILES_ACTIVE="dev"

# Perfil de teste
$env:SPRING_PROFILES_ACTIVE="test"

## **Comandos Docker**
# Com rebuild (quando mudar código Kotlin/Gradle):
docker-compose up --build

# Sem rebuild (quando mudar apenas application.yml ou migrations):
docker-compose up

# Parar containers e remover volumes:
docker-compose down -v

# Entrar nos containers:
API  docker exec -it financeflow_api /bin/sh
DB   docker exec -it financeflow_db psql -U postgres -d financeflow

# Dentro do container do Postgres:
Listar todas as tabelas: \dt
Consultar dados de uma tabela (exemplo users): SELECT * FROM users;

# Tabelas criadas via Flyway:
- users
- accounts
- transactions

# Rodar apenas código Kotlin:
.\run.ps1 -type code
# Rodar apenas configuração (banco de dados e dependências):
.\run.ps1 -type config

