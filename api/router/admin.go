package router

import (
	"fudol_api/handlers"

	"github.com/labstack/echo/v4"
)

func Admin(e *echo.Echo, handler *handlers.Handler) {
	h := handlers.Admin{
		Handler: *handler,
	}
	adm := e.Group("/adm")

	adm.GET("/users", h.GetUserList)
	adm.DELETE("/users", h.RemoveUsers)
}
