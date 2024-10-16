package handlers

import (
	"context"
	"fudol_api/constants"
	"fudol_api/helpers"
	"fudol_api/models"
	"net/http"

	"github.com/golang-jwt/jwt/v5"
	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
)

// OpenAPi
//
//	@Tags		User
//	@Summary	get user data
//	@Produce	json
//	@Security	ApiKeyAuth
//	@Header		200	{string}	Token	"Bearer"
//	@Success	200	{object}	models.User
//	@Router		/user [get]
func (h *Handler) GetUserData(c echo.Context) error {
	token, _ := c.Get(constants.TokenData).(*jwt.Token)
	claims, _ := token.Claims.(*helpers.JwtCustomClaims)
	r := h.Store.Users.FindOne(context.TODO(), bson.M{"_id": claims.User_id})
	var u models.User

	if err := r.Decode(&u); err != nil {
		return c.String(http.StatusNotFound, "user not found")
	}

	return c.JSON(http.StatusOK, u)
}
