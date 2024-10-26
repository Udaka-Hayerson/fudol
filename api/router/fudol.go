package router

import (
	"fudol_api/handlers"
	"fudol_api/middlewares"

	"github.com/labstack/echo/v4"
)

func Fudol(e *echo.Echo, handler *handlers.Handler) {
	h := handlers.Fudol{
		Handler: *handler,
	}
	f := e.Group("/fudol", middlewares.AuthMiddleware())

	f.PATCH("/increase", h.TimeCountIncrease)
	f.PATCH("/reset", h.TimeCountReset)
	// f.GET("/users", h.UserRatingList)
}
