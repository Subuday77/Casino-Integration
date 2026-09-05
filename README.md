# Casino Integration Simulator

A historical Java/Spring Boot project built as a stateful casino-wallet integration simulator. It implements the casino side of a transactional JSON protocol so integration flows can be exercised against realistic authentication, balance and transaction state instead of static mocks.

> **Project status:** historical/portfolio project. The code reflects an older integration-support tool and is not intended to be deployed to the public Internet as-is.

## What the simulator implements

The service exposes protocol endpoints for:

- authentication;
- debit;
- rollback;
- credit.

Requests are validated against an HMAC signature and configured operator identity. The simulator keeps persistent user and transaction state in PostgreSQL and models behavior such as:

- initial-token to session-token authentication;
- session-token expiration;
- balance changes;
- insufficient funds;
- negative amounts;
- duplicate/idempotent transactions;
- debit after rollback;
- rollback amount validation;
- credit linked to a previous debit;
- prevention of duplicate credit processing;
- unknown or expired tokens and transaction IDs.

This makes the application useful as a reference/mock integration environment rather than a collection of isolated canned responses.

## Architecture

```text
Bundled web UI
      |
      v
Spring REST controllers
  |              |
  |              +--> user/test-data management
  |
  +--> /ezugi protocol endpoints
            |
            +--> HMAC + request validation
            +--> transactional rules
            +--> expected balance/state transitions
            |
            v
       DAO / JPA
            |
            v
       PostgreSQL
```

`User` stores account, balance and token state. `Storage` records transaction/token history used to implement idempotency and rollback/credit relationships. Scheduled cleanup jobs expire short-lived tokens and old storage records.

## Technology

- Java 11
- Spring Boot 2.3
- Spring Web / REST
- Spring Data JPA
- PostgreSQL
- Maven
- JSON processing
- HMAC-SHA256 request validation
- bundled Angular production build under `src/main/resources/static`

## Local configuration

Runtime credentials and integration secrets are not stored in the current source tree. Configure the environment using `.env.example` as a reference:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
CASINO_OPERATOR_ID
CASINO_HASH_KEY
```

Optional settings:

```text
HIBERNATE_DDL_AUTO
PORT
```

The values in `.env.example` are placeholders only.

## Build

The project uses the Maven wrapper:

```bash
./mvnw package
```

The current automated build compiles and packages the application while skipping the historical `contextLoads` smoke test, because that test depends on a configured database and the project does not contain a separate isolated test profile. A modernized version should replace this with containerized/in-memory integration tests.

## Security note

This project was built as an integration simulator and internal development tool, not as a hardened public service. The current source has been sanitized so database credentials and protocol signing keys are supplied externally.

The legacy `/usercontrol` endpoints and bundled UI predate a production-grade authentication model and should not be exposed directly to untrusted networks. A modern version would use Spring Security, password hashing, authenticated administration endpoints and explicit authorization rules.

Older Git history may still contain retired development credentials or signing values. Those values must be treated as compromised and must never be reused. This cleanup intentionally does not rewrite repository history.

## What I would redesign today

The core domain behavior is useful, but I would modernize the structure before extending it:

- separate protocol validation from transaction-state transitions;
- move HMAC/operator configuration behind injected configuration objects;
- replace repeated DAO lookups with explicit transactional service methods;
- model debit/rollback/credit operations as typed domain commands;
- centralize error-code mapping;
- use Spring Security for administrative endpoints;
- isolate tests with Testcontainers or an in-memory profile;
- add integration tests for idempotency, token lifetime and transaction ordering;
- remove shared mutable controller/component state so concurrent sessions do not depend on synchronized controller methods.

The main engineering value of the project is the stateful protocol model: responses depend on previous authentication and transaction history, including duplicate requests and cross-transaction relationships, rather than only on the current HTTP request.
