package handlers

import (
	"context"
	"fmt"
	"fudol_api/models"
	"net/http"
	"strings"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
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

func (h *User) RemoveUsers(c echo.Context) error {
	idsQ := c.QueryParam("ids")

	if idsQ == "" {
		return c.String(http.StatusBadRequest, "ids query param is not provided.")
	}

	idsS := strings.Split(idsQ, ",")
	var ids []primitive.ObjectID

	for _, v := range idsS {
		r, _ := primitive.ObjectIDFromHex(v)
		ids = append(ids, r)
	}

	_, err := h.Store.Users.DeleteMany(context.TODO(), bson.M{"_id": bson.M{"$in": ids}})

	if err != nil {
		return c.String(http.StatusInternalServerError, fmt.Sprintf("%v", err))
	}

	return c.NoContent(http.StatusOK)
}
