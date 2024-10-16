package handlers

import (
	"context"
	"fmt"
	"fudol_api/constants"
	"fudol_api/helpers"
	"net/http"

	"github.com/golang-jwt/jwt/v5"
	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/bson"
)

// OpenAPi
//
//	@Tags		Time count
//	@Summary	reset time count
//	@Security	ApiKeyAuth
//	@Accept		json
//	@Produce	json
//	@Header		200	{string}	Token	"Bearer"
//	@Success	200
//	@Router		/timecount/reset [patch]
func (h *Handler) TimeCountReset(c echo.Context) error {
	token, _ := c.Get(constants.TokenData).(*jwt.Token)
	claims, _ := token.Claims.(*helpers.JwtCustomClaims)

	res, err := h.Store.Users.UpdateByID(
		context.TODO(),
		claims.User_id,
		bson.M{"$set": bson.D{{Key: "time_count", Value: 0}}},
	)

	if err != nil {
		fmt.Println(err)
		c.String(http.StatusInternalServerError, "error during reseting time count")
	}

	if res.MatchedCount == 0 {
		c.String(http.StatusInternalServerError, "no matched users by id.")
	}

	return c.NoContent(http.StatusOK)
}
