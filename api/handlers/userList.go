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

	users := make([]models.User, 0)

	if err := cur.All(context.TODO(), &users); err != nil {
		c.Error(err)
	}

	return c.JSON(http.StatusOK, users)
}

// OpenAPi
//
//	@Tags		User
//	@Summary	get user list
//	@Produce	json
//	@Success	200	{array}	models.UserPublic
//	@Router		/users [get]
func (h *Handler) GetUserPublicList(c echo.Context) error {
	cur, err := h.Store.Users.Find(context.TODO(), bson.D{})

	if err != nil {
		panic(err)
	}

	users := make([]models.UserPublic, 0)

	if err := cur.All(context.TODO(), &users); err != nil {
		c.Error(err)
	}

	return c.JSON(http.StatusOK, users)
}
