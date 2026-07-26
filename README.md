# crypto-price-alert

Bot que monitora preços de criptomoedas na Binance e envia um alerta por email quando o preço de um símbolo atinge o limite definido pelo usuário.

## Como funciona

1. O usuário cadastra um alerta via API informando símbolo (ex: `BTCUSDT`), preço limite, tipo de alerta (`UP` ou `DOWN`) e o email de destino.
2. A cada 10 segundos, uma tarefa agendada (`BinanceTask`) consulta a API pública da Binance para cada símbolo que possui pelo menos um alerta ativo.
3. Para cada alerta ativo daquele símbolo, verifica se o preço atual cruzou o `priceLimit` na direção esperada (`UP`: preço atual >= limite; `DOWN`: preço atual <= limite).
4. Se cruzou, dispara um email de notificação e desativa o alerta (`active=false`), evitando reenvio a cada ciclo.
5. O preço consultado também é salvo no banco (`crypto_price`) como histórico.

## Stack

- Java 21 + Spring Boot 3.5 (Web, Data JPA, Mail)
- PostgreSQL
- Lombok

## Rodando localmente

### 1. Banco de dados

```bash
cp .env.example .env
docker compose up -d
```

Isso sobe um Postgres local com os dados definidos em `.env` (usuário/senha/porta), conforme `docker-compose.yml`.

### 2. Configuração da aplicação

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Edite `application.properties` e preencha:
- Dados de conexão com o banco (já vem com os valores default do `docker-compose.yml`/`.env.example`).
- `spring.mail.username` e `spring.mail.password` com as credenciais de uma conta Gmail que vai enviar os alertas.
  - O Gmail exige verificação em duas etapas + uma [senha de app](https://myaccount.google.com/apppasswords) — a senha normal da conta não funciona para SMTP.
  - Esse mesmo `spring.mail.username` é usado como remetente dos emails de alerta.

### 3. Subir a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`.

## API

### Criar alerta

```
POST /alerts
Content-Type: application/json

{
  "email": "seuemail@exemplo.com",
  "symbol": "BTCUSDT",
  "priceLimit": 65000,
  "alertType": "UP"
}
```

Retorna o alerta criado (`active: true`).

> Ainda não há endpoints para listar, editar ou desativar alertas manualmente — só criação.

## Status atual / limitações conhecidas

- Alerta é desativado automaticamente após disparar (modo simples). Não existe ainda uma forma de reativar automaticamente se o preço oscilar de novo — o usuário precisa cadastrar um novo alerta.
- Não há validação de entrada no cadastro (`priceLimit`/`symbol` nulos ou inválidos não são barrados).
- `PriceAlertService.calcularVariacao` e `CryptoPriceRepository.findFirstPriceToday` existem mas não são usados no fluxo atual — são a base planejada para um futuro modo de alerta por variação percentual (%), além do modo atual por preço fixo.
