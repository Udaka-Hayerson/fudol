ARG GO_VERSION

FROM golang:${GO_VERSION}-alpine

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

RUN go install github.com/air-verse/air@latest
RUN go install github.com/swaggo/swag/cmd/swag@latest

CMD ["air", "-c", ".air.toml"]
