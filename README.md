# Employee Management Demo

A RESTful API built with Spring Boot, Spring Data JPA/Hibernate, and PostgreSQL. It manages Software Engineer entities and uses GitHub OAuth2 login with role-based authorization.

This is a learning project: my first steps into backend development with Java and Spring Boot.

## Tech stack

- Java, Spring Boot 4.1.0
- Spring Data JPA / Hibernate
- PostgreSQL
- Spring Security
- GitHub OAuth2 Login
- Role-based authorization using Spring Security authorities

## Getting started

### 1. Prerequisites

- JDK version configured in `pom.xml`
- Maven
- A running PostgreSQL instance with an empty database created for this app
- A GitHub OAuth App

### 2. Configure the database

Set your connection details in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/<your-db-name>
spring.datasource.username=<your-db-user>
spring.datasource.password=<your-db-password>

spring.jpa.hibernate.ddl-auto=update
```

### 3. Configure GitHub OAuth2

Create a GitHub OAuth App in GitHub Developer Settings.

Set the authorization callback URL to:

```text
http://localhost:8081/login/oauth2/code/github
```

Configure these properties in `application.properties`:

```properties
spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
spring.security.oauth2.client.registration.github.scope=read:user
```

Set `GITHUB_CLIENT_ID` and `GITHUB_CLIENT_SECRET` in your environment or IDE run configuration.

Never commit OAuth client secrets to Git.

### 4. Configure administrator accounts

The application uses an allowlist of GitHub usernames to determine which authenticated users receive administrator privileges.

For example:

```properties
app.security.admin-users=<github-username>,<another-github-username>
```

Replace the placeholders with the GitHub usernames you trust.

- Users on the allowlist receive `ROLE_ADMIN`.
- Other authenticated GitHub users receive `ROLE_USER`.
- Only trusted accounts should be granted administrator privileges.

The property name must match the one used by your custom OAuth2 user service.

### 5. Run the application

Start PostgreSQL and run:

```bash
mvn spring-boot:run
```

The application starts at:

```text
http://localhost:8081
```

## How authentication works

```text
1. Login
   User starts GitHub login.

2. OAuth2 authentication
   GitHub authenticates the user and redirects
   back to the Spring Boot application.

3. Role assignment
   The custom OAuth2 user service checks the
   GitHub username against the administrator allowlist.

4. Session creation
   Spring Security maintains the authenticated
   user in a server-side session.

5. API access
   The client sends its session cookie with
   requests to protected endpoints.
```

- The current application uses session-based authentication.
- GitHub authenticates the user; the application determines their authorization role.
- The API does not currently use application-issued JWT bearer tokens.
- Session cookies are sensitive credentials and must be protected.

## Software Engineer endpoints

Base URL:

```text
http://localhost:8081/software-engineers
```

All endpoints require an authenticated session.

| Method | Path | Description | Access |
|---|---|---|---|
| `GET` | `/get-engineers` | Get all stored engineers | Authenticated users |
| `GET` | `/get-by-id/{id}` | Get an engineer by ID | Authenticated users |
| `POST` | `/post-engineer` | Create a software engineer | `ROLE_ADMIN` |
| `DELETE` | `/{id}` | Delete a software engineer | `ROLE_ADMIN` |

The exact paths depend on the controller mappings configured in the application.

### Response codes

| Code | Meaning |
|---|---|
| `200` / `201` | Request succeeded |
| `302` | Redirect, potentially to GitHub login |
| `401` | Authentication is missing or invalid |
| `403` | Authenticated user lacks the required authority |

## Using the application

### 1. Log in with GitHub

Open:

```text
http://localhost:8081/oauth2/authorization/github
```

Complete the GitHub login flow. Spring Security processes the callback and creates an authenticated session.

### 2. Call a protected endpoint

After logging in, the browser stores the application's session cookie.

To call the API through Postman:

1. Log in through your browser.
2. Open Chrome DevTools.
3. Go to **Application → Cookies → `http://localhost:8081`**.
4. Identify the application's session cookie.
5. Configure the same cookie in Postman's Cookies manager for `localhost`.
6. Send a GET request to the appropriate endpoint.

Never share or commit the session cookie.

### 3. Test administrator access

Add the trusted GitHub username to `app.security.admin-users`.

Restart the application and log in again so the new session receives the updated authorities.

- An authenticated ordinary user can access GET endpoints.
- An administrator can access GET, POST, and DELETE endpoints.
- An ordinary user receives `403 Forbidden` for administrator-only operations.

## Database schema

### `software_engineer`

| Column | Type | Nullable |
|---|---|---|
| `id` | integer | No |
| `name` | character varying(255) | Yes |
| `tech_stacks` | character varying(255) | Yes |

### `users`

The application may retain a `users` table for local accounts or other application-specific data. GitHub OAuth2 login does not automatically require this table.

If persistent application users are introduced later, store provider identifiers and application roles in the database rather than relying solely on GitHub profile data.

## Security notes

- OAuth client secrets must be stored in environment variables or a secrets manager.
- Administrator roles must be assigned by trusted server-side logic.
- A successful GitHub login does not automatically grant administrator access.
- Session cookies must be protected and should not be shared.
- CSRF protection requires careful consideration when browser cookies authenticate requests.
- For production deployments, use HTTPS and secure cookie settings.
- GitHub OAuth2 login does not automatically make the REST API stateless.
