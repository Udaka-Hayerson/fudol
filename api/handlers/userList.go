package handlers

import (
	"context"
	"fudol_api/models"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
)

func (h *Handler) GetUserList(c echo.Context) error {
	cur, err := h.Store.Users.Find(context.TODO(), bson.D{})

	if err != nil {
		panic(err)
	}

	var users []models.User

	if err := cur.All(context.TODO(), &users); err != nil {
		c.Error(err)
	}

	return c.JSON(http.StatusOK, users)
}
