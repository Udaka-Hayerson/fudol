package router

import (
	"fudol_api/handlers"
	"fudol_api/middlewares"

	"github.com/labstack/echo/v4"
)

func User(e *echo.Echo, handler *handlers.Handler) {
	h := handlers.User{
		Handler: *handler,
	}
	u := e.Group("/user", middlewares.AuthMiddleware())

	u.GET("", h.GetUserData)
}
