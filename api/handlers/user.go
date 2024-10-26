package handlers

import (
	"context"
	"fudol_api/models"
	"net/http"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
)

type User struct {
	Handler
}

// OpenAPi
//
//	@Tags		User
//	@Summary	get user data
//	@Produce	json
//	@Security	ApiKeyAuth
//	@Header		200	{string}	Token	"Bearer"
//	@Success	200	{object}	models.UserPublic
//	@Router		/user [get]
func (h *User) GetUserData(c echo.Context) error {
	claims := h.GetTokenClaims(c)
	r := h.Store.Users.FindOne(context.TODO(), bson.M{"_id": claims.User_id})
	var u models.UserPublic

	if err := r.Decode(&u); err != nil {
		return c.String(http.StatusNotFound, "user not found")
	}

	return c.JSON(http.StatusOK, u)
}
