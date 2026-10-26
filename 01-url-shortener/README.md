# URL Shortener API
```bash
docker compose -f compose.yaml up --build
```
API em `http://localhost:8081`; Swagger em `/swagger-ui.html`.
- `POST /api/urls` body `{"url":"https://example.com"}`
- `GET /api/urls`, `GET /api/urls/{id}`, `PUT /api/urls/{id}`, `DELETE /api/urls/{id}`
- `GET /api/urls/r/{code}` redireciona para a URL original.
PostgreSQL fica exposto em `localhost:5433` para evitar conflito com instalações locais.
