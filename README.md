# Proyecto E-commerce con Docker

Ecosistema de microservicios con **Spring Boot**, empaquetado en imágenes **Docker** y orquestado con **Docker Compose**. Todo se levanta con un solo comando.

**Integrantes:** Juan Pablo Burgos Fernández · Julio Cabrera Schamun
**Curso:** Electiva I · Semana 15 · Taller 4

## Arquitectura

<img width="1192" height="746" alt="arquitectura" src="https://github.com/user-attachments/assets/8896472c-fd81-464f-8c7d-90916cee338d" />



| Servicio | Puerto | Publicado al equipo | Descripción |
|---|---|---|---|
| `api-gateway` | 8080 | Sí (entrada única) | Enruta las peticiones a los servicios registrados en Eureka |
| `eureka-server` | 8761 | Sí (panel) | Registro y descubrimiento de servicios |
| `config-server` | 8888 | No | Entrega configuración centralizada |
| `producto-service` | 8081 | No | API de productos (2 réplicas) |
| `pedido-service` | 8082 | No | API de pedidos |
| `postgres-productos` | 5432 | No | Base de datos de productos (volumen `pg-productos`) |
| `postgres-pedidos` | 5432 | No | Base de datos de pedidos (volumen `pg-pedidos`) |

## Requisitos

- Docker con Compose v2 (Docker Desktop o Docker Engine).
- 16 GB de RAM recomendados. Con 8 GB, deja `replicas: 1` en `producto-service`.
- No hace falta instalar JDK ni Maven: todo se compila dentro de Docker.

## Despliegue

```bash
git clone https://github.com/juanpabloburgos/Proyecto-ecommerce-con-Docker.git
cd Proyecto-ecommerce-con-Docker
cp .env.example .env        # en PowerShell: Copy-Item .env.example .env
docker compose up -d --build
docker compose ps
```

La primera construcción puede tardar varios minutos. Cuando los 8 contenedores muestren `healthy`, el sistema está listo.

![docker compose ps con todo en healthy](docs/docker-compose-ps.png)

Edita `.env` si quieres usar otras credenciales (el archivo no se versiona):

```
DB_USER=ecommerce
DB_PASSWORD=cambiar_esta_clave
```

## Ejemplos de uso

Todas las peticiones pasan por el Gateway (`http://localhost:8080`). La colección lista para importar está en [`postman/ecommerce.postman_collection.json`](postman/ecommerce.postman_collection.json).

| Acción | Método y URL |
|---|---|
| Crear producto | `POST http://localhost:8080/producto-service/api/productos` con body `{"nombre":"Teclado mecánico","precio":189900}` |
| Listar productos | `GET http://localhost:8080/producto-service/api/productos` |
| Ver qué réplica responde | `GET http://localhost:8080/producto-service/api/productos/instancia` |
| Panel de Eureka | `http://localhost:8761` |

Llama varias veces a `/instancia`: el valor alterna entre las dos réplicas de `producto-service`, lo que demuestra el balanceo de carga.

## Comandos útiles

```bash
docker compose logs -f producto-service   # ver logs de un servicio
docker compose stop producto-service      # detener un servicio (falla controlada)
docker compose up -d                      # restaurarlo
docker compose down                       # apagar y conservar los datos
docker compose down -v                    # apagar y borrar también los volúmenes
```

## Estructura del repositorio

```
├── docker-compose.yml
├── .env.example
├── config-server/
├── eureka-server/
├── api-gateway/
├── producto-service/
├── pedido-service/
├── docs/            (imágenes del README)
└── postman/         (colección de ejemplos)
```

Cada servicio tiene su propio `Dockerfile` multi-stage (imagen `eclipse-temurin:17-jre-alpine`, usuario no root) y su `.dockerignore`.





<img width="1192" height="746" alt="arquitectura" src="https://github.com/user-attachments/assets/651c97fc-85a4-419a-a578-4c0f4db3cbe0" />
