# Fudol

## Docs

-   ### remote

    -   [https://mainbus.uno](https://mainbus.uno) API open port
    -   [https://mainbus.uno/docs/](https://mainbus.uno/docs/) api open port
    -   [https://adm.mainbus.uno](https://adm.mainbus.uno) admin panel

-   ### local

    -   [http://localhost:8080](http://localhost:8080) API open port
    -   [http://localhost:8080/docs/](http://localhost:8080/docs/) api open port
    -   [http://localhost:8081](http://localhost:8081/) admin panel

## Development

-   `docker compose up --build -d` build services
-   `docker compose start` to start **api** and **DB** services
-   `make gen_open_api` generate OpenAPI docs
