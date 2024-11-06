package router

import (
	"fudol_api/handlers"
	"fudol_api/middlewares"

	"github.com/labstack/echo/v4"
)

func TodoList(e *echo.Echo, handler *handlers.Handler) {
	h := handlers.TodoList{
		Handler: *handler,
	}

	t := e.Group("/todo", middlewares.AuthMiddleware())
	t.POST("", h.PostTodo)
	t.GET("", h.GetTodoList)
	t.DELETE("", h.RemoveTodo)
	t.PATCH("/completed", h.SetCompleted)
}
