package router

import (
	"fudol_api/handlers"

	"github.com/labstack/echo/v4"
)

func Auth(e *echo.Echo, handler *handlers.Handler) {
	h := handlers.Auth{
		Handler: *handler,
	}
	a := e.Group("/auth")

	a.POST("/signup", h.SignUp)
	a.POST("/signin", h.SignIn)
}
