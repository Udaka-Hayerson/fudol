package router

import (
	"fudol_api/handlers"
	"net/http"

	"github.com/labstack/echo/v4"
	echoSwagger "github.com/swaggo/echo-swagger"
)

func Router(e *echo.Echo, h *handlers.Handler) {
	e.GET("/docs/*", echoSwagger.WrapHandler)
	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "Welcome to Fudol API")
	})

	Auth(e, h)
	User(e, h)
	// Users(e, h)
	Fudol(e, h)
	TodoList(e, h)
}
